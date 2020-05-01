package com.hqhop.modules.company.service.impl;

import com.alibaba.fastjson.JSON;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.request.OapiProcessinstanceCreateRequest;
import com.dingtalk.api.response.OapiProcessinstanceCreateResponse;
import com.hqhop.common.dingtalk.DingTalkConstant;
import com.hqhop.common.dingtalk.DingTalkUtils;
import com.hqhop.common.dingtalk.dingtalkVo.DingUser;
import com.hqhop.modules.company.domain.CompanyInfo;
import com.hqhop.modules.company.domain.CompanyUpdate;
import com.hqhop.modules.company.domain.Contact;
import com.hqhop.modules.company.repository.CompanyInfoRepository;
import com.hqhop.modules.company.repository.CompanyUpdateRepository;
import com.hqhop.modules.company.repository.ContactRepository;
import com.hqhop.modules.company.service.ContactDingService;
import com.hqhop.modules.system.service.DictDetailService;
import com.taobao.api.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author ：张丰
 * @date ：Created in 2019/11/24 0024 20:48
 * @description：联系人审批业务
 * @modified By：
 * @version: $
 */
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ContactDingServiceImpl implements ContactDingService {

    @Autowired
    private CompanyInfoRepository companyInfoRepository;

    @Autowired
    private CompanyUpdateRepository companyUpdateRepository;
    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private DictDetailService dictDetailService;




    //联系人审批开始回调
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void startApproval(String processId, String url,String dicValue) throws
            ApiException {
        CompanyUpdate companyUpdate = companyUpdateRepository.findByProcessIdAndOperationType(processId,dicValue);
        companyUpdate.setDingUrl(url);
        companyUpdateRepository.save(companyUpdate);
    }

    //联系人分配审批
    @Override
    public Contact addApprovel(Contact resouces, DingUser dingUser) throws
            ApiException {

        DefaultDingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/processinstance/create");
        OapiProcessinstanceCreateRequest request = new OapiProcessinstanceCreateRequest();
        request.setProcessCode(DingTalkConstant.PROCESSCODE_CUSTOMER_MANAGE);

        List<OapiProcessinstanceCreateRequest.FormComponentValueVo> listForm = new ArrayList<OapiProcessinstanceCreateRequest.FormComponentValueVo>();

        // 单行输入框
        OapiProcessinstanceCreateRequest.FormComponentValueVo input = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        input.setName("审批类型");
        //5联系人新增 6联系人修改...更多对照字典
        input.setValue(dictDetailService.getDicLabel("company_operation_type",5));

        OapiProcessinstanceCreateRequest.FormComponentValueVo vo4 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        vo4.setName("联系人");
        //vo4.setValue(JSON.toJSONString(Arrays.asList(Arrays.asList(ItemName1, ItemName2, ItemName3,ItemName4))));
        vo4.setValue(JSON.toJSONString(Arrays.asList(getItemList(resouces))));
        listForm.add(input);
        listForm.add(vo4);

//        CompanyInfo companyInfo = companyInfoRepository.getOne(resouces.getCompanyKey());
//        OapiProcessinstanceCreateRequest.FormComponentValueVo vo5 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
//        vo5.setName("客商");
//        vo4.setValue(JSON.toJSONString(Arrays.asList(getCompanyItemList(companyInfo))));
//        listForm.add(input);
//        listForm.add(vo5);
        request.setFormComponentValues(listForm);
        request.setOriginatorUserId(dingUser.getUserid());
        request.setDeptId(dingUser.getDepteId());
        OapiProcessinstanceCreateResponse response = client.execute(request,DingTalkUtils.getAccessToken());

        CompanyUpdate companyUpdate = new CompanyUpdate();
        CompanyInfo companyInfo = companyInfoRepository.getOne(resouces.getCompanyKey());
        companyUpdate.copyCompanyInfo(companyInfo);
        //5客商联系人新增  6客商联系人修改 7....更多对照字典
        companyUpdate.setOperationType("5");
        //放入审批实例ID
        companyUpdate.setProcessId(response.getProcessInstanceId());
        //放入当前用户ID
        companyUpdate.setUserId(dingUser.getUserid());

        companyUpdate.setCreateMan(dingUser.getName());

        //1 新增状态 2 新增审批中 3 驳回 4 审批通过
        resouces.setContactState(2);
        Contact contact = contactRepository.save( resouces);
        //放入新增联系人主键
        companyUpdate.setContactKey(contact.getContactKey());

        companyUpdateRepository.save(companyUpdate);


        return contact;
    }


    //分配联系人审批通过
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void agreeAddApproval(String processId) {


        CompanyUpdate companyUpdate = companyUpdateRepository.findByProcessIdAndApproveResult(processId,"未知");
        if(companyUpdate !=null){

            companyUpdate.setApproveResult("通过");
            companyUpdate.setApproveTime(new Timestamp(new Date().getTime()));
            companyUpdateRepository.save(companyUpdate);

            Contact contact = contactRepository.getOne(companyUpdate.getContactKey());
            //1新增 2审批中 3驳回 4审核通过
           contact.setContactState(4);
           Contact contact1 =  contactRepository.save(contact);
           CompanyInfo companyInfo = companyInfoRepository.findByCompanyKey(contact.getCompanyKey());
           companyInfo.getContacts().add(contact1);
           companyInfoRepository.save(companyInfo);


        }



    }


    //分配联系人审批驳回
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void refuseAddApproval(String processId){

        CompanyUpdate companyUpdate = companyUpdateRepository.findByProcessIdAndApproveResult(processId,"未知");
        if(companyUpdate !=null){
            companyUpdate.setApproveResult("驳回");
            companyUpdate.setApproveTime(new Timestamp(new Date().getTime()));
            companyUpdateRepository.save(companyUpdate);

            Contact contact = contactRepository.getOne(companyUpdate.getContactKey());
            //1新增 2审批中 3驳回 4审核通过
            contact.setContactState(3);
            contactRepository.save(contact);

        }

    }

    //分配联系人审批撤销
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void terminateAddApproval(String processId) {

        CompanyUpdate companyUpdate = companyUpdateRepository.findByProcessIdAndApproveResult(processId,"未知");
        if(companyUpdate !=null){
            Contact contact = contactRepository.getOne(companyUpdate.getContactKey());
            //1新增 2审批中 3驳回 4审核通过
            contact.setContactState(1);
            contactRepository.save(contact);
            companyUpdateRepository.deleteById(companyUpdate.getOperateKey());
        }

    }

    //修改联系人审批
    @Override
    public void updateApproval(Contact resouces, DingUser dingUser) throws
            ApiException {

        Contact contact = contactRepository.getOne(resouces.getContactKey());

        DefaultDingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/processinstance/create");
        OapiProcessinstanceCreateRequest request = new OapiProcessinstanceCreateRequest();
        request.setProcessCode(DingTalkConstant.PROCESSCODE_CUSTOMER_MANAGE);

        List<OapiProcessinstanceCreateRequest.FormComponentValueVo> listForm = new ArrayList<OapiProcessinstanceCreateRequest.FormComponentValueVo>();

        // 单行输入框
        OapiProcessinstanceCreateRequest.FormComponentValueVo input = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        input.setName("审批类型");
        //5联系人新增 6联系人修改...更多对照字典
        input.setValue(dictDetailService.getDicLabel("company_operation_type",6));
        List list1 = new ArrayList();


        // 明细-单行输入框
        OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName1 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        ItemName1.setName("姓名");
        ItemName1.setValue(getChange(resouces.getContactName(),contact.getContactName()));
        list1.add(ItemName1);

        // 明细-单行输入框
        OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName2 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        ItemName2.setName("电话");
        ItemName2.setValue(getChange(resouces.getPhone(),contact.getPhone()));
        list1.add(ItemName2);

        // 明细-单行输入框
        OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName3 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        ItemName3.setName("邮箱");
        ItemName3.setValue(getChange(resouces.getEmail(),contact.getEmail()));
        list1.add(ItemName3);

        // 明细-单行输入框
        OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName4 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        ItemName4.setName("职务");
        ItemName4.setValue(getChange(resouces.getPosition(),contact.getPosition()));
        list1.add(ItemName4);

        // 明细-单行输入框
        OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName5 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        ItemName5.setName("所属客商");
        ItemName5.setValue(getChange(companyInfoRepository.findByCompanyKey(resouces.getCompanyKey()).getCompanyName(),companyInfoRepository.findByCompanyKey(contact.getCompanyKey()).getCompanyName()));
        list1.add(ItemName5);

        // 明细-单行输入框
        OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName6 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        ItemName6.setName("联系地址");
        ItemName6.setValue(getChange(resouces.getContactAddress(),contact.getContactAddress()));   //用getStrRegisterfund
        list1.add(ItemName6);

        // 明细-单行输入框
        OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName7 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        ItemName7.setName("类型");
        ItemName7.setValue(getDictChange("customer",resouces.getContactType(),contact.getContactType()));
        list1.add(ItemName7);

        // 明细-单行输入框
        OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName8 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        ItemName8.setName("收发货地址");
        ItemName8.setValue(getChange(resouces.getDeliveryAddress(),contact.getDeliveryAddress()));
        list1.add(ItemName8);

        // 明细-单行输入框
        OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName9 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        ItemName9.setName("默认地址");
        ItemName9.setValue(getChange(resouces.getIsDefaultAddress()==0?"否":"是",contact.getIsDefaultAddress()==0?"否":"是"));
        list1.add(ItemName9);

        OapiProcessinstanceCreateRequest.FormComponentValueVo vo4 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        vo4.setName("联系人");
        //vo4.setValue(JSON.toJSONString(Arrays.asList(Arrays.asList(ItemName1, ItemName2, ItemName3,ItemName4))));
        vo4.setValue(JSON.toJSONString(Arrays.asList(list1)));
        listForm.add(input);
        listForm.add(vo4);

        request.setFormComponentValues(listForm);
        request.setOriginatorUserId(dingUser.getUserid());
        request.setDeptId(dingUser.getDepteId());
        OapiProcessinstanceCreateResponse response = client.execute(request,DingTalkUtils.getAccessToken());

        String  errmsage = response.getErrmsg();
        System.out.println(errmsage);

       CompanyUpdate companyUpdate = new  CompanyUpdate();
        CompanyInfo companyInfo = companyInfoRepository.findByCompanyKey(resouces.getCompanyKey());
        companyUpdate.copyCompanyInfo(companyInfo);

        //放入审批实例ID
        companyUpdate.setProcessId(response.getProcessInstanceId());
        //放入当前用户ID
        companyUpdate.setUserId(dingUser.getUserid());
        companyUpdate.setCreateMan(dingUser.getName());

        //1 新增 2 修改 3停用4启用....9本地保存 更多对照字典
        companyUpdate.setOperationType("6");
        //1 新增状态 2 新增审批中 3 驳回 4 审批通过5变更审批中
        resouces.setContactState(5);
        resouces.setCompanyKey(null);
        Contact contact1 = contactRepository.save(resouces);
        companyUpdate.setContactKey(contact1.getContactKey());
        companyUpdateRepository.save( companyUpdate);

    }

    //修改联系人审批通过
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void agreeUpdateApproval(String processId, String dicValue) {
        //1 新增 2 修改 3停用4启用....9本地保存 更多对照字典
        CompanyUpdate companyUpdate = companyUpdateRepository.findByProcessIdAndOperationType(processId,dicValue);
        if(companyUpdate !=null){

            companyUpdate.setApproveResult("通过");
            companyUpdate.setApproveTime(new Timestamp(new Date().getTime()));
            companyUpdateRepository.save(companyUpdate);

            Contact contact = contactRepository.getOne(companyUpdate.getContactKey());
            //1新增 2审批中 3驳回 4审核通过
            contact.setContactState(4);

            Contact contact1 =  contactRepository.save(contact);
            CompanyInfo companyInfo = companyInfoRepository.findByCompanyKey(contact.getCompanyKey());
            companyInfo.getContacts().add(contact1);
            companyInfoRepository.save(companyInfo);
        }
    }

    //修改联系人审批驳回
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void refuseUpdateApproval(String processId, String dicValue){
        //1 新增 2 修改 3停用4启用....9本地保存 更多对照字典
        CompanyUpdate companyUpdate = companyUpdateRepository.findByProcessIdAndOperationType(processId,dicValue);
        if(companyUpdate !=null){

            companyUpdate.setApproveResult("驳回");
            companyUpdate.setApproveTime(new Timestamp(new Date().getTime()));
            companyUpdateRepository.save(companyUpdate);

            Contact contact = contactRepository.getOne(companyUpdate.getContactKey());
            //1新增 2审批中 3驳回 4审核通过
            contact.setContactState(3);
            contact.setCompanyKey(null);
            contactRepository.save(contact);
        }
    }

    //修改联系人审批撤销
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void terminateUpdateApproval(String processId, String dicValue) {
        //1 新增 2 修改 3停用4启用....9本地保存 更多对照字典
        CompanyUpdate companyUpdate = companyUpdateRepository.findByProcessIdAndOperationType(processId,dicValue);
        if(companyUpdate !=null){
            Contact contact = contactRepository.getOne(companyUpdate.getContactKey());
            //1新增 2审批中 3驳回 4审核通过
            contact.setContactState(1);
            contact.setCompanyKey(null);
            contactRepository.save(contact);
            companyUpdateRepository.deleteById(companyUpdate.getOperateKey());
        }
    }


    //字符串新旧对比变化
    public  String getChange(String update ,String now) {
        if(update.equals(now)){
            return  update;
        }else {
            return  now+" -> "+update;
        }
    }
    //字典值对比
    public  String getDictChange(String dictName,String update ,String now) {

        if(update.equals(now)){
            System.out.println("所属公司"+dictDetailService.getDicLabel(dictName,now));
            return  dictDetailService.getDicLabel(dictName,now);
        }else {
            return dictDetailService.getDicLabel(dictName,now)+" -> "+dictDetailService.getDicLabel(dictName,update);
        }
    }


    //联系人取消分配
    @Override
    public void removeApproval(Contact resouces, DingUser dingUser) throws
            ApiException {

        DefaultDingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/processinstance/create");
        OapiProcessinstanceCreateRequest request = new OapiProcessinstanceCreateRequest();
        request.setProcessCode(DingTalkConstant.PROCESSCODE_CUSTOMER_MANAGE);

        List<OapiProcessinstanceCreateRequest.FormComponentValueVo> listForm = new ArrayList<OapiProcessinstanceCreateRequest.FormComponentValueVo>();

        // 单行输入框
        OapiProcessinstanceCreateRequest.FormComponentValueVo input = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        input.setName("审批类型");
            //10 客商联系人解绑....更多对照字典
        input.setValue(dictDetailService.getDicLabel("company_operation_type",10));
        OapiProcessinstanceCreateRequest.FormComponentValueVo vo4 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        vo4.setName("联系人");
        Contact contact = contactRepository.getOne(resouces.getContactKey());
        vo4.setValue(JSON.toJSONString(Arrays.asList(getItemList(contact ))));
        listForm.add(input);
        listForm.add(vo4);

        request.setFormComponentValues(listForm);
        request.setOriginatorUserId(dingUser.getUserid());
        request.setDeptId(dingUser.getDepteId());
        OapiProcessinstanceCreateResponse response = client.execute(request,DingTalkUtils.getAccessToken());

        CompanyUpdate companyUpdate = new CompanyUpdate();
        CompanyInfo companyInfo = companyInfoRepository.findByCompanyKey(resouces.getCompanyKey());
        companyUpdate.copyCompanyInfo(companyInfo);

        //放入审批实例ID
        companyUpdate.setProcessId(response.getProcessInstanceId());
        //放入当前用户ID
        companyUpdate.setUserId(dingUser.getUserid());
        companyUpdate.setCreateMan(dingUser.getName());

        //10 客商联系人解绑....更多对照字典
        companyUpdate.setOperationType("10");
        //1 新增状态 2 新增审批中 3 驳回 4 审批通过5变更审批中
        resouces.setContactState(5);
        contact.setCompanyKey(null);
        Contact contact1 = contactRepository.save(resouces);
        companyUpdate.setContactKey(contact1.getContactKey());
        companyUpdateRepository.save( companyUpdate);

    }
    //联系人取消分配 审批通过
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void agreeRemoveApproval(String processId) {

        CompanyUpdate companyUpdate = companyUpdateRepository.findByProcessIdAndApproveResult(processId,"未知");
        if(companyUpdate !=null){

            companyUpdate.setApproveResult("通过");
            companyUpdate.setApproveTime(new Timestamp(new Date().getTime()));
            companyUpdateRepository.save(companyUpdate);

            Contact contact = contactRepository.getOne(companyUpdate.getContactKey());
            //1新增 2审批中 3驳回 4审核通过
            contact.setContactState(1);
            contact.setCompanyKey(null);
            contact.setBelongCompany(null);
            contactRepository.save(contact);
        }

    }

    //联系人取消分配 审批驳回
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void refuseRemoveApproval(String processId, String dicValue){

        //1 新增 2 修改 3停用4启用....9本地保存 更多对照字典
        CompanyUpdate companyUpdate = companyUpdateRepository.findByProcessIdAndOperationType(processId,dicValue);
        if(companyUpdate !=null){

            companyUpdate.setApproveResult("驳回");
            companyUpdate.setApproveTime(new Timestamp(new Date().getTime()));
            companyUpdateRepository.save(companyUpdate);

            Contact contact = contactRepository.getOne(companyUpdate.getContactKey());
            //1新增 2审批中 3驳回 4审核通过
            contact.setContactState(4);
            Contact contact1 =  contactRepository.save(contact);
            CompanyInfo companyInfo = companyInfoRepository.findByCompanyKey(contact.getCompanyKey());
            companyInfo.getContacts().add(contact1);
            companyInfoRepository.save(companyInfo);
        }


    }

    //联系人取消分配 审批撤销
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void terminateRemoveApproval(String processId, String dicValue) {

        //1 新增 2 修改 3停用4启用....9本地保存 更多对照字典
        CompanyUpdate companyUpdate = companyUpdateRepository.findByProcessIdAndOperationType(processId,dicValue);
        if(companyUpdate !=null){
            Contact contact = contactRepository.getOne(companyUpdate.getContactKey());
            //1新增 2审批中 3驳回 4审核通过
            contact.setContactState(4);
            Contact contact1 =  contactRepository.save(contact);
            CompanyInfo companyInfo = companyInfoRepository.findByCompanyKey(contact.getCompanyKey());
            companyInfo.getContacts().add(contact1);
            companyInfoRepository.save(companyInfo);
            companyUpdateRepository.deleteById(companyUpdate.getOperateKey());
        }

    }



    //拿到审批 公司明细列表
    public  List getItemList(Contact resouces) throws ApiException {

        List list1 = new ArrayList();
        // 明细-单行输入框
        OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName1 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        ItemName1.setName("姓名");
        ItemName1.setValue(resouces.getContactName());
        list1.add(ItemName1);

        // 明细-单行输入框
        OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName2 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        ItemName2.setName("电话");
        ItemName2.setValue(resouces.getPhone());
        list1.add(ItemName2);

        // 明细-单行输入框
        OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName3 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        ItemName3.setName("邮箱");
        ItemName3.setValue(resouces.getEmail());
        list1.add(ItemName3);

        // 明细-单行输入框
        OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName4 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        ItemName4.setName("职务");
        ItemName4.setValue(resouces.getPosition());
        list1.add(ItemName4);

        // 明细-单行输入框
        OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName5 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        ItemName5.setName("所属客商");
        ItemName5.setValue(companyInfoRepository.getOne(resouces.getCompanyKey()).getCompanyName());
        list1.add(ItemName5);

        // 明细-单行输入框
        OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName6 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        ItemName6.setName("联系地址");
        ItemName6.setValue(resouces.getContactAddress());
        list1.add(ItemName6);

        // 明细-单行输入框
        OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName7 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        ItemName7.setName("类型");
        ItemName7.setValue(dictDetailService.getDicLabel("company_type",resouces.getContactType()));
        list1.add(ItemName7);

        // 明细-单行输入框
        OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName8 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        ItemName8.setName("发货地址");
        ItemName8.setValue(resouces.getContactAddress());
        list1.add(ItemName8);

        // 明细-单行输入框
        OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName9 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        ItemName9.setName("默认地址");
        ItemName9.setValue(resouces.getIsDefaultAddress() == 1 ?"是":"否");
        list1.add(ItemName9);


        return  list1;

    }
    //拿到审批 公司明细列表
    public  List getCompanyItemList(CompanyInfo resouces) throws ApiException {

        List list1 = new ArrayList();
        // 明细-单行输入框
        OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName1 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        ItemName1.setName("公司名");
        ItemName1.setValue(resouces.getCompanyName());
        list1.add(ItemName1);

        // 明细-单行输入框
        OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName2 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        ItemName2.setName("公司简称");
        ItemName2.setValue(resouces.getCompanyShortName());
        list1.add(ItemName2);

        // 明细-单行输入框
        OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName3 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        ItemName3.setName("外文名称");
        ItemName3.setValue(resouces.getForeignName());
        list1.add(ItemName3);

        // 明细-单行输入框
        OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName4 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        ItemName4.setName("所属公司");
        ItemName4.setValue(companyInfoRepository.findByCompanyKey(resouces.getCompanyKey()).getCompanyName());
        list1.add(ItemName4);

        // 明细-单行输入框
        OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName5 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        ItemName5.setName("法人");
        ItemName5.setValue(resouces.getLegalbody());
        list1.add(ItemName5);

        // 明细-单行输入框
        OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName6 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        ItemName6.setName("注册资金");
        ItemName6.setValue(resouces.getRegisterfund().toString());
        list1.add(ItemName6);

        // 明细-单行输入框
        OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName7 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        ItemName7.setName("所属地区");
        ItemName7.setValue(dictDetailService.getDicLabel("area",resouces.getBelongArea()));
        list1.add(ItemName7);

        // 明细-单行输入框
        OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName8 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        ItemName8.setName("联系地址");
        ItemName8.setValue(resouces.getContactAddress());
        list1.add(ItemName8);

        // 明细-单行输入框
        OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName9 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        ItemName9.setName("邮政编码");
        ItemName9.setValue(resouces.getPostalCode());
        list1.add(ItemName9);

        // 明细-单行输入框
        OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName10 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        ItemName10.setName("公司属性");
        ItemName10.setValue(dictDetailService.getDicLabel("company_prop",resouces.getCompanyProp()));
        list1.add(ItemName10);

        // 明细-单行输入框
        OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName11 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        ItemName11.setName("公司类型");
        ItemName11.setValue(dictDetailService.getDicLabel("company_type",resouces.getCustomerProp()));
        list1.add(ItemName11);

        // 明细-单行输入框
        OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName12 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        ItemName12.setName("所属行业");
        ItemName12.setValue(dictDetailService.getDicLabel("trade",resouces.getTrade()));
        list1.add(ItemName12);

        // 明细-单行输入框
        OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName13 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        ItemName13.setName("经济类型");
        ItemName13.setValue(dictDetailService.getDicLabel("economic_type",resouces.getEconomicType()));
        list1.add(ItemName13);



        // 明细-单行输入框
        OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName15 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        ItemName15.setName("是否协同付款");
        ItemName15.setValue(resouces.getIsSynergyPay() == 1 ?"是":"否");

        list1.add(ItemName15);

        OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName16 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        ItemName16.setName("备注");
        ItemName16.setValue(resouces.getRemark());
        list1.add(ItemName16);

        // 明细-单行输入框
        OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName17 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        ItemName17.setName("纳税编号");
        ItemName17.setValue(resouces.getTaxId());
        list1.add(ItemName17);

        return  list1;

    }

}










