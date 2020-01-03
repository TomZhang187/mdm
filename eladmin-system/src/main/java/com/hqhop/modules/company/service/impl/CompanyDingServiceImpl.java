package com.hqhop.modules.company.service.impl;

import com.alibaba.fastjson.JSON;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.request.OapiProcessinstanceCreateRequest;
import com.dingtalk.api.response.OapiProcessinstanceCreateResponse;
import com.hqhop.common.dingtalk.DingTalkConstant;
import com.hqhop.common.dingtalk.DingTalkUtils;
import com.hqhop.common.dingtalk.dingtalkVo.DingUser;
import com.hqhop.modules.company.domain.CompanyBasic;
import com.hqhop.modules.company.domain.CompanyInfo;
import com.hqhop.modules.company.domain.CompanyUpdate;
import com.hqhop.modules.company.repository.CompanyBasicRepository;
import com.hqhop.modules.company.repository.CompanyInfoRepository;
import com.hqhop.modules.company.repository.CompanyUpdateRepository;
import com.hqhop.modules.company.service.CompanyDingService;
import com.hqhop.modules.company.service.CompanyInfoService;
import com.hqhop.modules.company.service.mapper.CompanyU8cService;
import com.hqhop.modules.company.utils.CompanyUtils;
import com.hqhop.modules.system.domain.DictDetail;
import com.hqhop.modules.system.domain.Employee;
import com.hqhop.modules.system.repository.DictDetailRepository;
import com.hqhop.modules.system.repository.EmployeeRepository;
import com.hqhop.modules.system.service.DictDetailService;
import com.taobao.api.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.*;

/**
 * @author ：张丰
 * @date ：Created in 2019/11/1 0001 16:05
 * @description：客商钉钉业务实现类
 * @modified By：
 * @version: $
 */

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class CompanyDingServiceImpl implements CompanyDingService  {

    @Autowired
    private CompanyUpdateRepository companyUpdateRepository;

    @Autowired
    private CompanyInfoRepository companyInfoRepository;

    @Autowired
    private  DictDetailRepository dictDetailRepository;

    @Autowired
    private DictDetailService dictDetailService;

    @Autowired
    private CompanyInfoService companyInfoService;

    @Autowired
    private CompanyBasicRepository companyBasicRepository;

    @Autowired
    private CompanyU8cService companyU8cService;

    @Autowired
    private EmployeeCompanyServiceImpl employeeCompanyService;

    @Autowired
    private EmployeeRepository employeeRepository;






    //客商审批开始回调
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void startApproval(String processId, String url,String dicValue) throws
            ApiException {
        CompanyUpdate companyUpdate = companyUpdateRepository.findByProcessIdAndOperationType(processId,dicValue);
        companyUpdate.setDingUrl(url);
        companyUpdateRepository.save(companyUpdate);
//         Timer timer = new Timer();  //定时任务
//         timer.schedule(new TimerTask() {
//             @Override
//             public void run() {
//             }
//         },500);
    }

    //客商新增审批
    @Override
    public CompanyUpdate addApprovel(CompanyUpdate resouces,DingUser dingUser) throws
            ApiException {

            DefaultDingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/processinstance/create");
            OapiProcessinstanceCreateRequest request = new OapiProcessinstanceCreateRequest();
            request.setProcessCode(DingTalkConstant.PROCESSCODE_CUSTOMER_MANAGE);

            List<OapiProcessinstanceCreateRequest.FormComponentValueVo> listForm = new ArrayList<OapiProcessinstanceCreateRequest.FormComponentValueVo>();

        // 单行输入框
        OapiProcessinstanceCreateRequest.FormComponentValueVo input = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        input.setName("审批类型");
        //1 新增 2 修改 3停用....更多对照字典
        input.setValue(dictDetailService.getDicLabel("company_operation_type",1));

            OapiProcessinstanceCreateRequest.FormComponentValueVo vo4 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
            vo4.setName("客商");
           //vo4.setValue(JSON.toJSONString(Arrays.asList(Arrays.asList(ItemName1, ItemName2, ItemName3,ItemName4))));
            vo4.setValue(JSON.toJSONString(Arrays.asList(getItemList(resouces))));
            listForm.add(input);
            listForm.add(vo4);

            request.setFormComponentValues(listForm);
            request.setOriginatorUserId(dingUser.getUserid());
            request.setDeptId(dingUser.getDepteId());
            OapiProcessinstanceCreateResponse response = client.execute(request,DingTalkUtils.getAccessToken());

        //1 新增 2 修改 3停用4启用....更多对照字典
        resouces.setOperationType("1");
            //放入审批实例ID
            resouces.setProcessId(response.getProcessInstanceId());
            //放入当前用户ID
            resouces.setUserId(dingUser.getUserid());

            resouces.setCreateMan(dingUser.getName());

           //1 新增状态 2 新增审批中 3 驳回 4 审批通过
        CompanyInfo companyInfo2 = resouces.toCompanyInfo();
        companyInfo2.setCompanyState(2);

        CompanyInfo  companyInfo =  companyInfoService.createAndUpadte(companyInfo2);
              //放入新增客商商主键
            resouces.setCompanyKey(companyInfo.getCompanyKey());
            CompanyUpdate companyUpdate = companyUpdateRepository.save(resouces);
      return companyUpdate;
    }


    //新增客商审批通过
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void agreeAddApproval(String processId) {


            CompanyUpdate companyUpdate = companyUpdateRepository.findByProcessIdAndApproveResult(processId,"未知");
            if(companyUpdate !=null){
            companyUpdate.setApproveResult("通过");
            companyUpdate.setApproveTime(new Timestamp(new Date().getTime()));
            companyUpdateRepository.save(companyUpdate);

            CompanyInfo companyInfo = companyInfoRepository.getOne(companyUpdate.getCompanyKey());
            //1新增 2审批中 3驳回 4审核通过
            companyInfo.setCompanyState(4);
                DictDetail dictDetail = dictDetailRepository.findByLabelAndDict_Id(companyInfo.getCompanyName(), 11L);
                if(dictDetail !=null){
                    companyInfo.setCustomerType("3");
                }
                companyInfo.setApproveTime(new Timestamp(new Date().getTime()));
                CompanyInfo save = companyInfoRepository.save(companyInfo);

                CompanyBasic companyBasic = companyBasicRepository.findByTaxId(save.getTaxId());
                if(companyBasic == null){
                    CompanyBasic companyBasic1 = new CompanyBasic();
                    companyBasic1.setTaxId(save.getTaxId());
                    companyBasic1.setCompanyName(save.getCompanyName());
                    companyBasic1.setBelongArea(save.getBelongArea());
                    companyBasic1.setBelongCompany(save.getBelongCompany());
                    companyBasic1.setCompanyShortName(save.getCompanyShortName());
                    companyBasic1.setCustomerType(save.getCustomerType());
                    companyBasic1.setBelongCompany(save.getBelongCompany());

                    CompanyInfo companyInfo1 = companyInfoRepository.findByCompanyKey(save.getParentCompanyId());
                    companyBasic1.setHeadOfficeCode(companyInfo1!=null?companyInfo1.getTaxId():null);

                    companyBasic1.setCreateMan(save.getCreateMan());
                    companyBasic1.setCreateTime(save.getCreateTime());
                    companyBasic1.setUpdateMan(save.getUpdateMan());
                    companyBasic1.setUpdateTime(save.getUpdateTime());

                    CompanyBasic companyBasic2 = companyBasicRepository.save(companyBasic1);

                    companyU8cService.addToU8C(companyInfo,companyBasic2.getKey().toString());

                }else {
                    companyU8cService.addToU8C(companyInfo,companyBasic.getKey().toString());
                }



            }



    }


    //新增客商审批驳回
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void refuseAddApproval(String processId){

        CompanyUpdate companyUpdate = companyUpdateRepository.findByProcessIdAndApproveResult(processId,"未知");
        if(companyUpdate !=null){
        companyUpdate.setApproveResult("驳回");
        companyUpdate.setApproveTime(new Timestamp(new Date().getTime()));
        companyUpdateRepository.save(companyUpdate);
        CompanyInfo companyInfo = companyInfoRepository.getOne(companyUpdate.getCompanyKey());
        //1新增 2审批中 3驳回 4审核通过
        companyInfo.setCompanyState(3);
        companyInfoRepository.save(companyInfo);
        }

    }

    //新增客商审批撤销
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void terminateAddApproval(String processId) {

        CompanyUpdate companyUpdate = companyUpdateRepository.findByProcessIdAndApproveResult(processId,"未知");
        if(companyUpdate !=null){

        CompanyInfo companyInfo = companyInfoRepository.getOne(companyUpdate.getCompanyKey());
        //1新增 2审批中 3驳回 4审核通过
        companyInfo.setCompanyState(companyUpdate.getCompanyStateInt());
        companyInfoRepository.save(companyInfo);
        companyUpdateRepository.deleteById(companyUpdate.getOperateKey());
        }

    }

    //修改客商审批
    @Override
    public void updateApproval(CompanyUpdate resouces, DingUser dingUser) throws
            ApiException {

//       CompanyUpdate  temporaryRecord= null;   //临时记录，审批通过修改客商数据用
//        temporaryRecord.copy(resouces);

        resouces.setUserId(dingUser.getUserid());
        resouces.setCreateMan(dingUser.getName());
         CompanyUpdate companyUpdate1 = companyUpdateRepository.findByCompanyKeyAndUserIdAndApproveResult(resouces.getCompanyKey(),dingUser.getUserid(),"未知");
         if(companyUpdate1 != null){

             resouces.setOperateKey(companyUpdate1.getOperateKey());
         }

       Long operateKey = companyUpdateRepository.save(resouces).getOperateKey();

                resouces.setOperateKey(null);
        resouces = getUpdateDetails(resouces);

        DefaultDingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/processinstance/create");
        OapiProcessinstanceCreateRequest request = new OapiProcessinstanceCreateRequest();
        request.setProcessCode(DingTalkConstant.PROCESSCODE_CUSTOMER_MANAGE);

        List<OapiProcessinstanceCreateRequest.FormComponentValueVo> listForm = new ArrayList<OapiProcessinstanceCreateRequest.FormComponentValueVo>();

        // 单行输入框
        OapiProcessinstanceCreateRequest.FormComponentValueVo input = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        input.setName("审批类型");
        //1 新增 2 修改 3停用 4启用....更多对照字典
        input.setValue(dictDetailService.getDicLabel("company_operation_type",2));
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
        ItemName4.setValue(dictDetailService.getDicLabel("inside_company",resouces.getBelongCompany()));
        list1.add(ItemName4);

        // 明细-单行输入框
        OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName5 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        ItemName5.setName("法人");
        ItemName5.setValue(resouces.getLegalbody());
        list1.add(ItemName5);

        // 明细-单行输入框
        OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName6 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        ItemName6.setName("注册资金");
        ItemName6.setValue(resouces.getStrRegisterfund());   //用getStrRegisterfund
        list1.add(ItemName6);

        // 明细-单行输入框
        OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName7 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        ItemName7.setName("所属地区");
        ItemName7.setValue(resouces.getBelongArea());
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
        ItemName10.setValue(resouces.getCompanyProp());
        list1.add(ItemName10);

        // 明细-单行输入框
        OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName11 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        ItemName11.setName("公司类型");
        ItemName11.setValue(resouces.getCustomerProp());
        list1.add(ItemName11);

        // 明细-单行输入框
        OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName12 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        ItemName12.setName("所属行业");
        ItemName12.setValue(resouces.getTrade());
        list1.add(ItemName12);

        // 明细-单行输入框
        OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName13 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        ItemName13.setName("经济类型");
        ItemName13.setValue(resouces.getEconomicType());
        list1.add(ItemName13);

        // 明细-单行输入框
        OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName14 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        ItemName14.setName("是否散户");
        ItemName14.setValue(resouces.getIsRetai());
        list1.add(ItemName14);

        // 明细-单行输入框
        OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName15 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        ItemName15.setName("是否协同付款");
        ItemName15.setValue(resouces.getIsSynergyPay());
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

        OapiProcessinstanceCreateRequest.FormComponentValueVo vo4 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        vo4.setName("客商");
        //vo4.setValue(JSON.toJSONString(Arrays.asList(Arrays.asList(ItemName1, ItemName2, ItemName3,ItemName4))));
        vo4.setValue(JSON.toJSONString(Arrays.asList(list1)));
        listForm.add(input);
        listForm.add(vo4);

        request.setFormComponentValues(listForm);
        request.setOriginatorUserId(dingUser.getUserid());
        request.setDeptId(dingUser.getDepteId());
        OapiProcessinstanceCreateResponse response = client.execute(request,DingTalkUtils.getAccessToken());


        CompanyUpdate temporaryRecord = companyUpdateRepository.findByOperateKey(operateKey);
        //放入审批实例ID
        resouces.setProcessId(response.getProcessInstanceId());         //操作记录
        temporaryRecord.setProcessId(response.getProcessInstanceId());    //审批通过修改数据用
        //放入当前用户ID
        resouces.setUserId(dingUser.getUserid());
        temporaryRecord.setUserId(dingUser.getUserid());

        resouces.setCreateMan(dingUser.getName());
        temporaryRecord.setCreateMan(dingUser.getName());

        //1 新增 2 修改 3停用4启用....9本地保存 更多对照字典
        resouces.setOperationType("2");
        //1 新增 2 修改 3停用4启用....9本地保存 更多对照字典
        temporaryRecord.setOperationType("9");

       companyUpdateRepository.save(resouces);

       companyUpdateRepository.save(temporaryRecord);

        //1 新增状态 2 新增审批中 3 驳回 4 审批通过5变更审批中
        CompanyInfo companyInfo = companyInfoRepository.findByCompanyKey(resouces.getCompanyKey());
        companyInfo.setCompanyState(5);
        companyInfoService.createAndUpadte( companyInfo);

    }

    //修改客商审批通过
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void agreeUpdateApproval(String processId, String dicValue) {
        //1 新增 2 修改 3停用4启用....9本地保存 更多对照字典
        CompanyUpdate companyUpdate1 = companyUpdateRepository.findByProcessIdAndOperationType(processId,"9");
        if(companyUpdate1 !=null){


        CompanyUpdate companyUpdate = companyUpdateRepository.findByProcessIdAndOperationType(processId,dicValue);
        companyUpdate.setApproveResult("通过");
            companyUpdate.setApproveTime(new Timestamp(new Date().getTime()));
            companyUpdateRepository.save(companyUpdate);

            //用来修改客商数据的记录  1 新增 2 修改 3停用4启用....9本地保存 更多对照字典
            CompanyUpdate temporaryRecord  = companyUpdateRepository.findByProcessIdAndOperationType(processId,"9");
            CompanyInfo companyInfo = companyInfoRepository.getOne(temporaryRecord.getCompanyKey());
            CompanyInfo updateData = temporaryRecord.toCompanyInfo();
            updateData.setCompanyKey(companyInfo.getCompanyKey());
            updateData.setUpdateTime(new Timestamp(new Date().getTime()));
            //1新增 2审批中 3驳回 4审核通过
            updateData.setCompanyState(4);
            CompanyInfo save = companyInfoRepository.save(updateData);
            companyUpdateRepository.deleteById(temporaryRecord.getOperateKey());

            //客商基本档案更新
            CompanyBasic companyBasic1 = companyBasicRepository.findByTaxId(save.getTaxId());
            if(companyBasic1 != null){
            companyBasic1.setTaxId(save.getTaxId());
            companyBasic1.setCompanyName(save.getCompanyName());
            companyBasic1.setBelongArea(save.getBelongArea());
            companyBasic1.setBelongCompany(save.getBelongCompany());
            companyBasic1.setCompanyShortName(save.getCompanyShortName());
            companyBasic1.setCustomerType(save.getCustomerType());
            companyBasic1.setBelongCompany(save.getBelongCompany());

            CompanyInfo companyInfo1 = companyInfoRepository.findByCompanyKey(save.getParentCompanyId());
            companyBasic1.setHeadOfficeCode(companyInfo1!=null?companyInfo1.getTaxId():null);

            companyBasic1.setCreateMan(save.getCreateMan());
            companyBasic1.setCreateTime(save.getCreateTime());
            companyBasic1.setUpdateMan(save.getUpdateMan());
            companyBasic1.setUpdateTime(save.getUpdateTime());
            CompanyBasic companyBasic2 = companyBasicRepository.save(companyBasic1);
            companyU8cService.updateToU8C(companyInfo,companyBasic2 .getKey().toString());

            }

        }
    }

    //修改客商审批驳回
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void refuseUpdateApproval(String processId, String dicValue){
        //1 新增 2 修改 3停用4启用....9本地保存 更多对照字典
        CompanyUpdate companyUpdate1 = companyUpdateRepository.findByProcessIdAndOperationType(processId,"9");
        if(companyUpdate1 !=null){
        CompanyUpdate companyUpdate = companyUpdateRepository.findByProcessIdAndOperationType(processId,dicValue);
        companyUpdate.setApproveResult("驳回");
        companyUpdate.setApproveTime(new Timestamp(new Date().getTime()));
        companyUpdateRepository.save(companyUpdate);

        //用来修改客商数据的记录  1 新增 2 修改 3停用4启用....9本地保存 更多对照字典
        CompanyUpdate temporaryRecord  = companyUpdateRepository.findByProcessIdAndOperationType(processId,"9");
        companyUpdateRepository.deleteById(temporaryRecord.getOperateKey());

        CompanyInfo companyInfo = companyInfoRepository.getOne(temporaryRecord.getCompanyKey());
        //1新增 2审批中 3驳回 4审核通过
        companyInfo.setCompanyState(4);
        companyInfoRepository.save( companyInfo);
        }
    }

    //修改客商审批撤销
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void terminateUpdateApproval(String processId, String dicValue) {
        //1 新增 2 修改 3停用4启用....9本地保存 更多对照字典
        CompanyUpdate companyUpdate1 = companyUpdateRepository.findByProcessIdAndOperationType(processId,"9");
        if(companyUpdate1 !=null){
        CompanyUpdate companyUpdate = companyUpdateRepository.findByProcessIdAndOperationType(processId,dicValue);

        CompanyInfo companyInfo = companyInfoRepository.getOne(companyUpdate.getCompanyKey());
        //1新增 2审批中 3驳回 4审核通过
        companyInfo.setCompanyState(4);
        companyInfoRepository.save(companyInfo);

        //用来修改客商数据的记录  1 新增 2 修改 3停用4启用....9本地保存 更多对照字典
        CompanyUpdate temporaryRecord  = companyUpdateRepository.findByProcessIdAndOperationType(processId,"9");
        companyUpdateRepository.deleteById(temporaryRecord.getOperateKey());
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


    //新旧客商数据对比比
    @Override
    public CompanyUpdate getUpdateDetails(CompanyUpdate resouces) {

        CompanyInfo companyInfo = companyInfoRepository.findByCompanyKey(resouces.getCompanyKey());

        resouces.setCompanyName(getChange(resouces.getCompanyName(),companyInfo.getCompanyName()));
        resouces.setCompanyShortName(getChange(resouces.getCompanyShortName(),companyInfo.getCompanyShortName()));
        resouces.setForeignName(getChange(resouces.getForeignName(),companyInfo.getForeignName()));
//        resouces.setBelongCompany(getDictChange("inside_company",resouces.getBelongCompany(),companyInfo.getBelongCompany()));
        resouces.setLegalbody(getChange(resouces.getLegalbody(),companyInfo.getLegalbody()));
        resouces.setStrRegisterfund(getChange(resouces.getRegisterfund().toString(),CompanyUtils.removeTrim(companyInfo.getRegisterfund().toString())));
        resouces.setTaxId(getChange(resouces.getTaxId(),companyInfo.getTaxId()));
        resouces.setBelongArea(getDictChange("area",resouces.getBelongArea(),companyInfo.getBelongArea()));
        resouces.setContactAddress(getChange(resouces.getContactAddress(),companyInfo.getContactAddress()));
        resouces.setPostalCode(getChange(resouces.getPostalCode(),companyInfo.getPostalCode()));
        resouces.setCompanyProp(getDictChange("company_prop",resouces.getCompanyProp(),companyInfo.getCompanyProp()));
        resouces.setCustomerProp(getDictChange("customer_prop",resouces.getCustomerProp(),companyInfo.getCustomerProp()));
        resouces.setTrade(getDictChange("trade",resouces.getTrade(),companyInfo.getTrade()));
        resouces.setEconomicType(getDictChange("economic_type",resouces.getEconomicType(),companyInfo.getEconomicType()));
        resouces.setIsRetai(getChange(resouces.getIsRetaiInt()==0?"否":"是",companyInfo.getIsRetai()==0?"否":"是"));
        resouces.setIsSynergyPay(getChange(resouces.getIsSynergyPayInt()==0?"否":"是",companyInfo.getIsSynergyPay()==0?"否":"是"));
        resouces.setRemark(getChange(resouces.getRemark(),companyInfo.getRemark()));

        return  resouces;

    }

    //客商 停用/启用 审批
    @Override
    public void isAbleApproval(CompanyUpdate resouces, DingUser dingUser) throws
            ApiException {
         resouces.copyCompanyInfo(companyInfoRepository.getOne(resouces.getCompanyKey()));


        DefaultDingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/processinstance/create");
        OapiProcessinstanceCreateRequest request = new OapiProcessinstanceCreateRequest();
        request.setProcessCode(DingTalkConstant.PROCESSCODE_CUSTOMER_MANAGE);

        List<OapiProcessinstanceCreateRequest.FormComponentValueVo> listForm = new ArrayList<OapiProcessinstanceCreateRequest.FormComponentValueVo>();

        // 单行输入框
        OapiProcessinstanceCreateRequest.FormComponentValueVo input = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        input.setName("审批类型");
        if("1".equals(resouces.getIsDisable())) {
            //1 新增 2 修改 3停用4启用....更多对照字典
            input.setValue(dictDetailService.getDicLabel("company_operation_type",3));
        }else{
            //1 新增 2 修改 3停用4启用....更多对照字典
            input.setValue(dictDetailService.getDicLabel("company_operation_type",4));
        }
        OapiProcessinstanceCreateRequest.FormComponentValueVo vo4 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        vo4.setName("客商");
        //vo4.setValue(JSON.toJSONString(Arrays.asList(Arrays.asList(ItemName1, ItemName2, ItemName3,ItemName4))));
        vo4.setValue(JSON.toJSONString(Arrays.asList(getItemList(resouces))));
        listForm.add(input);
        listForm.add(vo4);

        request.setFormComponentValues(listForm);
        request.setOriginatorUserId(dingUser.getUserid());
        request.setDeptId(dingUser.getDepteId());
        OapiProcessinstanceCreateResponse response = client.execute(request,DingTalkUtils.getAccessToken());


        if("1".equals(resouces.getIsDisable())) {
            //1 新增 2 修改 3停用4启用....更多对照字典
            resouces.setOperationType("3");

        }else {
            //1 新增 2 修改 3停用4启用....更多对照字典
            resouces.setOperationType("4");
        }

        //放入审批实例ID
        resouces.setProcessId(response.getProcessInstanceId());
        //放入当前用户ID
        resouces.setUserId(dingUser.getUserid());

        resouces.setCreateMan(dingUser.getName());

        //1 新增状态 2 审批中 3 驳回 4 审批通过5变更审批中
      CompanyInfo companyInfo1 = resouces.toCompanyInfo();
      companyInfo1.setCompanyState(5);
        CompanyInfo  companyInfo =  companyInfoService.createAndUpadte(companyInfo1 );
        //放入新增客商商主键
        resouces.setCompanyKey(companyInfo.getCompanyKey());

       companyUpdateRepository.save(resouces);

    }
    //客商 停用/启用 审批通过
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void agreeIsAbleApproval(String processId) {

        CompanyUpdate companyUpdate = companyUpdateRepository.findByProcessIdAndApproveResult(processId,"未知");
        if(companyUpdate !=null){

            companyUpdate.setApproveResult("通过");
            companyUpdate.setApproveTime(new Timestamp(new Date().getTime()));
            companyUpdateRepository.save(companyUpdate);

            CompanyInfo companyInfo = companyInfoRepository.findByCompanyKey(companyUpdate.getCompanyKey());
            //1新增 2审批中 3驳回 4审核通过
            companyInfo.setCompanyState(companyUpdate.getCompanyStateInt());
            //1 新增 2 修改 3停用4启用....更多对照字典
            if("3".equals(companyUpdate.getOperationType())){
                companyInfo.setIsDisable(0);

            }else if("4".equals(companyUpdate.getOperationType())) {
                companyInfo.setIsDisable(1);
            }
            CompanyInfo save = companyInfoRepository.save(companyInfo);
            CompanyBasic companyBasic = companyBasicRepository.findByTaxId(save.getTaxId());
            if(companyBasic != null){
                companyU8cService.updateToU8C(companyInfo,companyBasic.getKey().toString());

            }

        }

    }

    //客商 停用/启用 审批驳回
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void refuseIsAbleApproval(String processId){

        CompanyUpdate companyUpdate = companyUpdateRepository.findByProcessIdAndApproveResult(processId,"未知");
        if(companyUpdate !=null){

            companyUpdate.setApproveResult("驳回");
            companyUpdate.setApproveTime(new Timestamp(new Date().getTime()));
            companyUpdateRepository.save(companyUpdate);
            CompanyInfo companyInfo = companyInfoRepository.getOne(companyUpdate.getCompanyKey());
            //1新增 2审批中 3驳回 4审核通过
            companyInfo.setCompanyState(companyUpdate.getCompanyStateInt());
            companyInfoRepository.save(companyInfo);
        }



    }

    //客商 停用/启用 审批撤销
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void terminateIsAbleApproval(String processId) {

        CompanyUpdate companyUpdate = companyUpdateRepository.findByProcessIdAndApproveResult(processId,"未知");
        if(companyUpdate !=null){
            CompanyInfo companyInfo = companyInfoRepository.getOne(companyUpdate.getCompanyKey());
            //1新增 2审批中 3驳回 4审核通过
            companyInfo.setCompanyState(4);
            companyInfoRepository.save(companyInfo);
            companyUpdateRepository.deleteById(companyUpdate.getOperateKey());

        }



    }

    //客商管理权限申请审批
    @Override
    public void getCustomerPermission(CompanyUpdate resouces, DingUser dingUser) throws
            ApiException {

        DefaultDingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/processinstance/create");
        OapiProcessinstanceCreateRequest request = new OapiProcessinstanceCreateRequest();
        request.setProcessCode(DingTalkConstant.PROCESSCODE_CUSTOMER_MANAGE);

        List<OapiProcessinstanceCreateRequest.FormComponentValueVo> listForm = new ArrayList<OapiProcessinstanceCreateRequest.FormComponentValueVo>();

        // 单行输入框
        OapiProcessinstanceCreateRequest.FormComponentValueVo input = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        input.setName("审批类型");
        //1 新增 2 修改 3停用....更多对照字典
        input.setValue(dictDetailService.getDicLabel("company_operation_type",12));

        OapiProcessinstanceCreateRequest.FormComponentValueVo vo4 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        vo4.setName("客商管理权限申请");
        //vo4.setValue(JSON.toJSONString(Arrays.asList(Arrays.asList(ItemName1, ItemName2, ItemName3,ItemName4))));
        vo4.setValue(JSON.toJSONString(Arrays.asList(getItemList(resouces))));
        listForm.add(input);
        listForm.add(vo4);
        request.setFormComponentValues(listForm);
        request.setOriginatorUserId(dingUser.getUserid());
        request.setDeptId(dingUser.getDepteId());
        OapiProcessinstanceCreateResponse response = client.execute(request,DingTalkUtils.getAccessToken());

        CompanyUpdate companyUpdate = new CompanyUpdate();
        companyUpdate.setCompanyKey(resouces.getCompanyKey());
        companyUpdate.setTaxId(resouces.getTaxId());
        companyUpdate.setBelongCompany(resouces.getBelongCompany());


        //1 新增 2 修改 3停用10管理权限申请....更多对照字典
        companyUpdate.setOperationType("12");
        //放入审批实例ID
        companyUpdate.setProcessId(response.getProcessInstanceId());
        //放入当前用户ID
        companyUpdate.setUserId(dingUser.getUserid());
        companyUpdate.setCreateMan(dingUser.getName());
        CompanyUpdate companyUpdate2 = companyUpdateRepository.save(companyUpdate);

    }


    //客商管理权限申请审批通过
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void agreeGetCustomerPermission(String processId) {
        CompanyUpdate companyUpdate = companyUpdateRepository.findByProcessIdAndApproveResult(processId,"未知");
        if(companyUpdate !=null){
            companyUpdate.setApproveResult("通过");
            companyUpdate.setApproveTime(new Timestamp(new Date().getTime()));
            companyUpdateRepository.save(companyUpdate);
            Employee employee = employeeRepository.findByDingId(companyUpdate.getUserId());
            employeeCompanyService.saveBykey(employee.getId(),companyUpdate.getCompanyKey());
        }
    }


    //客商管理权限申请审批驳回
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void refuseGetCustomerPermission(String processId) {
        CompanyUpdate companyUpdate = companyUpdateRepository.findByProcessIdAndApproveResult(processId,"未知");
        if(companyUpdate !=null){
            companyUpdate.setApproveResult("驳回");
            companyUpdate.setApproveTime(new Timestamp(new Date().getTime()));
            companyUpdateRepository.save(companyUpdate);
        }

    }


    //拿到审批 公司明细列表
    public  List getItemList(CompanyUpdate resouces) throws ApiException {

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
        ItemName4.setValue(dictDetailService.getDicLabel("inside_company",resouces.getBelongCompany()));
        list1.add(ItemName4);

        // 明细-单行输入框
        OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName5 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        ItemName5.setName("法人");
        ItemName5.setValue(resouces.getLegalbody());
        list1.add(ItemName5);

        // 明细-单行输入框
        OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName6 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        ItemName6.setName("注册资金");
        ItemName6.setValue(resouces.getRegisterfund()!=null?resouces.getRegisterfund().toString():null);
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
        ItemName11.setValue(dictDetailService.getDicLabel("customer_prop",resouces.getCustomerProp()));
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

        if(resouces.getIsRetai() != null){
            // 明细-单行输入框
            OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName14 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
            ItemName14.setName("是否散户");
            ItemName14.setValue(resouces.getIsRetaiInt() == 1 ?"是":"否");
            list1.add(ItemName14);
        }



        if(resouces.getIsSynergyPay() != null){
            // 明细-单行输入框
            OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName15 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
            ItemName15.setName("是否协同付款");
            ItemName15.setValue(resouces.getIsSynergyPayInt() == 1 ?"是":"否");

            list1.add(ItemName15);
        }


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





