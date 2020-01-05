package com.hqhop.modules.company.service.impl;

import com.alibaba.fastjson.JSON;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.request.OapiProcessinstanceCreateRequest;
import com.dingtalk.api.response.OapiProcessinstanceCreateResponse;
import com.hqhop.common.dingtalk.DingTalkConstant;
import com.hqhop.common.dingtalk.DingTalkUtils;
import com.hqhop.common.dingtalk.dingtalkVo.DingUser;
import com.hqhop.modules.company.domain.Account;
import com.hqhop.modules.company.domain.CompanyInfo;
import com.hqhop.modules.company.domain.CompanyUpdate;
import com.hqhop.modules.company.repository.AccountRepository;
import com.hqhop.modules.company.repository.CompanyInfoRepository;
import com.hqhop.modules.company.repository.CompanyUpdateRepository;
import com.hqhop.modules.company.service.AccountDingService;
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
 * @date ：Created in 2019/11/26 0026 9:21
 * @description：客商账户业务实现
 * @modified By：
 * @version: $
 */
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class AccountDingServiceImpl implements AccountDingService {

        @Autowired
        private CompanyInfoRepository companyInfoRepository;

        @Autowired
        private CompanyUpdateRepository companyUpdateRepository;


        @Autowired
        private AccountRepository accountRepository;

        @Autowired
        private DictDetailService dictDetailService;




        //账户分配审批
        @Override
        public Account addApprovel(Account resouces, DingUser dingUser) throws
                ApiException {

            DefaultDingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/processinstance/create");
            OapiProcessinstanceCreateRequest request = new OapiProcessinstanceCreateRequest();
            request.setProcessCode(DingTalkConstant.PROCESSCODE_CUSTOMER_MANAGE);

            List<OapiProcessinstanceCreateRequest.FormComponentValueVo> listForm = new ArrayList<OapiProcessinstanceCreateRequest.FormComponentValueVo>();

            // 单行输入框
            OapiProcessinstanceCreateRequest.FormComponentValueVo input = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
            input.setName("审批类型");
            //5联系人新增 6联系人修改7客商账户新增...更多对照字典
            input.setValue(dictDetailService.getDicLabel("company_operation_type",7));

            OapiProcessinstanceCreateRequest.FormComponentValueVo vo4 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
            vo4.setName("客商账户");
            //vo4.setValue(JSON.toJSONString(Arrays.asList(Arrays.asList(ItemName1, ItemName2, ItemName3,ItemName4))));
            vo4.setValue(JSON.toJSONString(Arrays.asList(getItemList(resouces))));
            listForm.add(input);
            listForm.add(vo4);

//        CompanyInfo companyInfo = companyInfoRepository.getOne(resouces.getBelongCompany());
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
            CompanyInfo companyInfo = companyInfoRepository.findByCompanyKey(resouces.getCompanyKey());
            companyUpdate.copyCompanyInfo(companyInfo);
            //5客商联系人新增  6客商联系人修改 7客商账户新增....更多对照字典
            companyUpdate.setOperationType("7");
            //放入审批实例ID
            companyUpdate.setProcessId(response.getProcessInstanceId());
            //放入当前用户ID
            companyUpdate.setUserId(dingUser.getUserid());

            companyUpdate.setCreateMan(dingUser.getName());

            //1 新增状态 2 新增审批中 3 驳回 4 审批通过
            resouces.setAccountState(2);
            Account account = accountRepository.save(resouces);
            //放入新增账户主键
            companyUpdate.setAccountKey(account.getAccountKey());

            companyUpdateRepository.save(companyUpdate);


            return account;
        }


        //分配账户审批通过
        @Override
        @Transactional(rollbackFor = Exception.class)
        public void agreeAddApproval(String processId) {


            CompanyUpdate companyUpdate = companyUpdateRepository.findByProcessIdAndApproveResult(processId,"未知");
            if(companyUpdate !=null){

                companyUpdate.setApproveResult("通过");
                companyUpdate.setApproveTime(new Timestamp(new Date().getTime()));
                companyUpdateRepository.save(companyUpdate);

                Account account = accountRepository.getOne(companyUpdate.getAccountKey());
                //1新增 2审批中 3驳回 4审核通过
                account.setAccountState(4);

                Account account1 = accountRepository.save(account);
                CompanyInfo companyInfo = companyInfoRepository.findByCompanyKey(account.getCompanyKey());
                companyInfo.getAccounts().add(account1);
                companyInfoRepository.save(companyInfo);

            }



        }


        //分配账户审批驳回
        @Override
        @Transactional(rollbackFor = Exception.class)
        public void refuseAddApproval(String processId){

            CompanyUpdate companyUpdate = companyUpdateRepository.findByProcessIdAndApproveResult(processId,"未知");
            if(companyUpdate !=null){
                companyUpdate.setApproveResult("驳回");
                companyUpdate.setApproveTime(new Timestamp(new Date().getTime()));
                companyUpdateRepository.save(companyUpdate);
                Account account = accountRepository.getOne(companyUpdate.getAccountKey());
                //1新增 2审批中 3驳回 4审核通过
                account.setAccountState(3);
                accountRepository.save(account);
            }

        }

        //分配账户审批撤销
        @Override
        @Transactional(rollbackFor = Exception.class)
        public void terminateAddApproval(String processId) {

            CompanyUpdate companyUpdate = companyUpdateRepository.findByProcessIdAndApproveResult(processId,"未知");
            if(companyUpdate !=null){
                Account account = accountRepository.getOne(companyUpdate.getAccountKey());
                //1新增 2审批中 3驳回 4审核通过
                account.setAccountState(1);
                accountRepository.save(account);
                companyUpdateRepository.deleteById(companyUpdate.getOperateKey());
            }

        }

        //修改账户审批
        @Override
        public void updateApproval(Account resouces, DingUser dingUser) throws
                ApiException {


            Account account = accountRepository.getOne(resouces.getAccountKey());

            DefaultDingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/processinstance/create");
            OapiProcessinstanceCreateRequest request = new OapiProcessinstanceCreateRequest();
            request.setProcessCode(DingTalkConstant.PROCESSCODE_CUSTOMER_MANAGE);

            List<OapiProcessinstanceCreateRequest.FormComponentValueVo> listForm = new ArrayList<OapiProcessinstanceCreateRequest.FormComponentValueVo>();

            // 单行输入框
            OapiProcessinstanceCreateRequest.FormComponentValueVo input = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
            input.setName("审批类型");
            //5联系人新增 6联系人修改8客商账户修改...更多对照字典
            input.setValue(dictDetailService.getDicLabel("company_operation_type",8));
            List list1 = new ArrayList();


            // 明细-单行输入框
            OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName1 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
            ItemName1.setName("所属客商");
            ItemName1.setValue(getChange(companyInfoRepository.findByCompanyKey(resouces.getCompanyKey()).getCompanyName(),companyInfoRepository.findByCompanyKey(account.getCompanyKey()).getCompanyName()));
            list1.add(ItemName1);

            // 明细-单行输入框
            OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName2 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
            ItemName2.setName("账户名");
            ItemName2.setValue(getChange(resouces.getAccountName(),account.getAccountName()));
            list1.add(ItemName2);

            // 明细-单行输入框
            OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName3 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
            ItemName3.setName("账号");
            ItemName3.setValue(getChange(resouces.getAccount(),account.getAccount()));
            list1.add(ItemName3);

            // 明细-单行输入框
            OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName4 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
            ItemName4.setName("开户行");
            ItemName4.setValue(getChange(resouces.getAccountBlank(),account.getAccountBlank()));
            list1.add(ItemName4);

            // 明细-单行输入框
            OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName5 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
            ItemName5.setName("银行类型");
            ItemName5.setValue(getDictChange("blank_class",resouces.getBlankClass(),account.getBlankClass()));
            list1.add(ItemName5);

            // 明细-单行输入框
            OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName6 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
            ItemName6.setName("币种");
            ItemName6.setValue(getDictChange("currency",resouces.getCurrency(),account.getCurrency()));   //用getStrRegisterfund
            list1.add(ItemName6);


            // 明细-单行输入框
            OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName9 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
            ItemName9.setName("默认");
            ItemName9.setValue(getChange(resouces.getIsDefalut()==0?"否":"是",account.getIsDefalut()==0?"否":"是"));
            list1.add(ItemName9);

            OapiProcessinstanceCreateRequest.FormComponentValueVo vo4 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
            vo4.setName("客商账户");
            //vo4.setValue(JSON.toJSONString(Arrays.asList(Arrays.asList(ItemName1, ItemName2, ItemName3,ItemName4))));
            vo4.setValue(JSON.toJSONString(Arrays.asList(list1)));
            listForm.add(input);
            listForm.add(vo4);

            request.setFormComponentValues(listForm);
            request.setOriginatorUserId(dingUser.getUserid());
            request.setDeptId(dingUser.getDepteId());
            OapiProcessinstanceCreateResponse response = client.execute(request,DingTalkUtils.getAccessToken());



            CompanyUpdate companyUpdate = new  CompanyUpdate();
            CompanyInfo companyInfo = companyInfoRepository.findByCompanyKey(resouces.getCompanyKey());
            companyUpdate.copyCompanyInfo(companyInfo);

            //放入审批实例ID
            companyUpdate.setProcessId(response.getProcessInstanceId());
            //放入当前用户ID
            companyUpdate.setUserId(dingUser.getUserid());
            companyUpdate.setCreateMan(dingUser.getName());

            //1 新增 2 修改 3停用4启用....8客商账户修改 更多对照字典
            companyUpdate.setOperationType("8");
            //1 新增状态 2 新增审批中 3 驳回 4 审批通过5变更审批中
            resouces.setAccountState(5);
            resouces.setCompanyKey(null);
            Account account1 = accountRepository.save(resouces);
            companyUpdate.setAccountKey(account1.getAccountKey());
            companyUpdateRepository.save( companyUpdate);

        }

        //修改账户审批通过
        @Override
        @Transactional(rollbackFor = Exception.class)
        public void agreeUpdateApproval(String processId, String dicValue) {
            //1 新增 2 修改 3停用4启用....9本地保存 更多对照字典
            CompanyUpdate companyUpdate = companyUpdateRepository.findByProcessIdAndOperationType(processId,dicValue);
            if(companyUpdate !=null){

                companyUpdate.setApproveResult("通过");
                companyUpdate.setApproveTime(new Timestamp(new Date().getTime()));
                companyUpdateRepository.save(companyUpdate);


                Account account = accountRepository.getOne(companyUpdate.getAccountKey());
                //1新增 2审批中 3驳回 4审核通过
                account.setAccountState(4);
//                account.setCompanyKey(account.getBelongCompany());
                Account account1 = accountRepository.save(account);
                CompanyInfo companyInfo = companyInfoRepository.findByCompanyKey(account.getCompanyKey());
                companyInfo.getAccounts().add(account1);
                companyInfoRepository.save(companyInfo);

            }
        }

        //修改账户审批驳回
        @Override
        @Transactional(rollbackFor = Exception.class)
        public void refuseUpdateApproval(String processId, String dicValue){
            //1 新增 2 修改 3停用4启用....9本地保存 更多对照字典
            CompanyUpdate companyUpdate = companyUpdateRepository.findByProcessIdAndOperationType(processId,dicValue);
            if(companyUpdate !=null){

                companyUpdate.setApproveResult("驳回");
                companyUpdate.setApproveTime(new Timestamp(new Date().getTime()));
                companyUpdateRepository.save(companyUpdate);


                Account account = accountRepository.getOne(companyUpdate.getAccountKey());
                //1新增 2审批中 3驳回 4审核通过
                account.setAccountState(3);
                account.setCompanyKey(null);
                accountRepository.save(account);

            }
        }

        //修改账户审批撤销
        @Override
        @Transactional(rollbackFor = Exception.class)
        public void terminateUpdateApproval(String processId, String dicValue) {
            //1 新增 2 修改 3停用4启用....9本地保存 更多对照字典
            CompanyUpdate companyUpdate = companyUpdateRepository.findByProcessIdAndOperationType(processId,dicValue);
            if(companyUpdate !=null){

                Account account = accountRepository.getOne(companyUpdate.getAccountKey());
                //1新增 2审批中 3驳回 4审核通过
                account.setAccountState(1);
               account.setCompanyKey(null);
                accountRepository.save(account);
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


        //账户取消分配
        @Override
        public void removeApproval(Account resouces, DingUser dingUser) throws
                ApiException {

            DefaultDingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/processinstance/create");
            OapiProcessinstanceCreateRequest request = new OapiProcessinstanceCreateRequest();
            request.setProcessCode(DingTalkConstant.PROCESSCODE_CUSTOMER_MANAGE);

            List<OapiProcessinstanceCreateRequest.FormComponentValueVo> listForm = new ArrayList<OapiProcessinstanceCreateRequest.FormComponentValueVo>();

            // 单行输入框
            OapiProcessinstanceCreateRequest.FormComponentValueVo input = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
            input.setName("审批类型");
            //10 客商账户解绑....更多对照字典
            input.setValue(dictDetailService.getDicLabel("company_operation_type",11));
            OapiProcessinstanceCreateRequest.FormComponentValueVo vo4 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
            vo4.setName("客商账户");

            Account account = accountRepository.getOne(resouces.getAccountKey());
            vo4.setValue(JSON.toJSONString(Arrays.asList(getItemList(account ))));
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

            //11 客商账户解绑....更多对照字典
            companyUpdate.setOperationType("11");
            //1 新增状态 2 新增审批中 3 驳回 4 审批通过5变更审批中
            resouces.setAccountState(5);
            resouces.setCompanyKey(null);
            Account account1 = accountRepository.save(resouces);
            companyUpdate.setAccountKey(account.getAccountKey());
            companyUpdateRepository.save( companyUpdate);

        }
        //账户取消分配 审批通过
        @Override
        @Transactional(rollbackFor = Exception.class)
        public void agreeRemoveApproval(String processId) {

            CompanyUpdate companyUpdate = companyUpdateRepository.findByProcessIdAndApproveResult(processId,"未知");
            if(companyUpdate !=null){

                companyUpdate.setApproveResult("通过");
                companyUpdate.setApproveTime(new Timestamp(new Date().getTime()));
                companyUpdateRepository.save(companyUpdate);

                Account account = accountRepository.getOne(companyUpdate.getAccountKey());
                //1新增 2审批中 3驳回 4审核通过
                account.setAccountState(1);
                account.setCompanyKey(null);
               account.setBelongCompany(null);
               accountRepository.save(account);

            }

        }

        //账户取消分配 审批驳回
        @Override
        @Transactional(rollbackFor = Exception.class)
        public void refuseRemoveApproval(String processId, String dicValue){

            //1 新增 2 修改 3停用4启用....9本地保存 更多对照字典
            CompanyUpdate companyUpdate = companyUpdateRepository.findByProcessIdAndOperationType(processId,dicValue);
            if(companyUpdate !=null){

                companyUpdate.setApproveResult("驳回");
                companyUpdate.setApproveTime(new Timestamp(new Date().getTime()));
                companyUpdateRepository.save(companyUpdate);
                Account account = accountRepository.getOne(companyUpdate.getAccountKey());
                //1新增 2审批中 3驳回 4审核通过
                account.setAccountState(4);
                Account account1 = accountRepository.save(account);
                CompanyInfo companyInfo = companyInfoRepository.findByCompanyKey(account.getCompanyKey());
                companyInfo.getAccounts().add(account1);
                companyInfoRepository.save(companyInfo);

            }


        }

        //账户取消分配 审批撤销
        @Override
        @Transactional(rollbackFor = Exception.class)
        public void terminateRemoveApproval(String processId, String dicValue) {
            //1 新增 2 修改 3停用4启用....9本地保存 更多对照字典
            CompanyUpdate companyUpdate = companyUpdateRepository.findByProcessIdAndOperationType(processId,dicValue);
            if(companyUpdate !=null){
                Account account = accountRepository.getOne(companyUpdate.getAccountKey());
                //1新增 2审批中 3驳回 4审核通过
                account.setAccountState(4);
                Account account1 = accountRepository.save(account);
                CompanyInfo companyInfo = companyInfoRepository.findByCompanyKey(account.getCompanyKey());
                companyInfo.getAccounts().add(account1);
                companyInfoRepository.save(companyInfo);
                companyUpdateRepository.deleteById(companyUpdate.getOperateKey());
            }

        }



        //拿到审批 公司明细列表
        public  List getItemList(Account resouces) throws ApiException {

            List list1 = new ArrayList();
            // 明细-单行输入框
            OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName1 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
            ItemName1.setName("所属客商");
            ItemName1.setValue(companyInfoRepository.findByCompanyKey(resouces.getCompanyKey()).getCompanyName());
            list1.add(ItemName1);

            // 明细-单行输入框
            OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName2 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
            ItemName2.setName("账户名");
            ItemName2.setValue(resouces.getAccountName());
            list1.add(ItemName2);

            // 明细-单行输入框
            OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName3 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
            ItemName3.setName("账号");
            ItemName3.setValue(resouces.getAccount());
            list1.add(ItemName3);

            // 明细-单行输入框
            OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName4 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
            ItemName4.setName("开户行");
            ItemName4.setValue(resouces.getAccountBlank());
            list1.add(ItemName4);

            // 明细-单行输入框
            OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName5 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
            ItemName5.setName("银行类型");
            ItemName5.setValue(dictDetailService.getDicLabel("blank_class",resouces.getBlankClass()));
            list1.add(ItemName5);

            // 明细-单行输入框
            OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName6 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
            ItemName6.setName("币种");
            ItemName6.setValue(dictDetailService.getDicLabel("currency",resouces.getCurrency()));
            list1.add(ItemName6);

            // 明细-单行输入框
            OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName7 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
            ItemName7.setName("默认");
            ItemName7.setValue(resouces.getIsDefalut() == 1 ?"是":"否");
            list1.add(ItemName7);

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


