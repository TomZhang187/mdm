package com.hqhop.common.dingtalk;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.request.OapiProcessinstanceCreateRequest;
import com.dingtalk.api.response.OapiProcessinstanceCreateResponse;
import com.hqhop.exception.BadRequestException;
import com.taobao.api.ApiException;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 对接工作流工具类
 */
public class ProcessUtils {

    /**
     * 获取封装后的单个文本框对象
     *
     * @param name  文本框的名称
     * @param value 文本框的值
     * @return
     */
    public static OapiProcessinstanceCreateRequest.FormComponentValueVo getFormComponentVo(String name, String value) {
        return getFormComponentVo(name, value, null);
    }


    /**
     * 获取封装后的单个文本框对象
     *
     * @param name     文本框的名称
     * @param value    文本框的值
     * @param extValue 文本框的扩展值
     * @return
     */
    public static OapiProcessinstanceCreateRequest.FormComponentValueVo getFormComponentVo(String name, String value, String extValue) {
        OapiProcessinstanceCreateRequest.FormComponentValueVo vo = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        vo.setName(name);
        vo.setValue(value);
        if (!StringUtils.isEmpty(extValue)) {
            vo.setExtValue(extValue);
        }
        return vo;
    }


    /**
     * 保存钉钉审批流程
     *
     * @param processCode 钉钉流程的processCode
     * @param userid      流程发起人的userid
     * @param deptid      流程发起人所在的部门id
     * @param listForm    保存流程表单列表
     * @return
     * @throws ApiException
     */
    public static String sendProcess(String processCode, String userid, Long deptid, List<OapiProcessinstanceCreateRequest.FormComponentValueVo> listForm) throws ApiException {
        return sendProcess(processCode, userid, deptid, listForm, Long.parseLong(DingTalkConstant.AGENTID));
    }

    /**
     * 保存钉钉审批流程
     *
     * @param processCode 钉钉流程的processCode
     * @param userid      流程发起人的userid
     * @param deptid      流程发起人所在的部门id
     * @param listForm    保存流程表单列表
     * @param agentId     发起流程应用的agentId
     * @return
     * @throws ApiException
     */
    public static String sendProcess(String processCode, String userid, Long deptid, List<OapiProcessinstanceCreateRequest.FormComponentValueVo> listForm, Long agentId) throws BadRequestException {

        DefaultDingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/processinstance/create");
        OapiProcessinstanceCreateRequest request = new OapiProcessinstanceCreateRequest();
        request.setAgentId(agentId);
        request.setProcessCode(processCode);
        request.setOriginatorUserId(userid); // 发起人userid
        request.setDeptId(deptid);
        request.setFormComponentValues(listForm);
        OapiProcessinstanceCreateResponse rsp = null;
        try {
            rsp = client.execute(request, DingTalkUtils.getAccessToken());
            return rsp.getErrcode().equals("0") ? rsp.getProcessInstanceId() : null;
        } catch (ApiException e) {
            e.printStackTrace();
            throw new BadRequestException("钉钉流程发起失败："+ e.getErrMsg());
        }
    }


    public static void main(String[] args) {
//        List<OapiProcessinstanceCreateRequest.FormComponentValueVo> listForm = new ArrayList<OapiProcessinstanceCreateRequest.FormComponentValueVo>();

//        listForm.add(ProcessUtils.getFormComponentVo("付款类型", ));
//        listForm.add(ProcessUtils.getFormComponentVo("流程主题", payment.getStatus() == 4 ? "工程退款申请" : "工程付款申请"));
//        listForm.add(ProcessUtils.getFormComponentVo("安装合同编号", contract.getNumber()));
//        listForm.add(ProcessUtils.getFormComponentVo("安装公司名称全称", contract.getInstallationCompany()));
//        listForm.add(ProcessUtils.getFormComponentVo("合同金额（含税）", contract.getAllAmount().toString()));
//        listForm.add(ProcessUtils.getFormComponentVo("已累计付款金额", contract.getPayAmount().toString()));
//        listForm.add(ProcessUtils.getFormComponentVo("已累计付款比例", payPercent.toString()));
//        listForm.add(ProcessUtils.getFormComponentVo("本次付款比例", payment.getThisPayPercent().toString()));
//        listForm.add(ProcessUtils.getFormComponentVo("本次付款金额", payment.getThisPayAmount().toString()));
//
//        if (!StringUtils.isEmpty(payment.getBank())) {
//            listForm.add(ProcessUtils.getFormComponentVo("开户银行名称", payment.getBank()));
//        }
//        if (!StringUtils.isEmpty(payment.getAccount())) {
//            listForm.add(ProcessUtils.getFormComponentVo("开户银行账号", payment.getAccount()));
//        }
//        listForm.add(ProcessUtils.getFormComponentVo("备注", payment.getRemark()));

//        Set<ProcessMedia> medias = payment.getProcessMedia();
//        if (medias != null && medias.size() > 0) {
//            List<ProcessMedia> media_List = medias.stream().filter(a -> a.getDel() == 0).collect(Collectors.toList());
//            listForm.add(ProcessUtils.getFormComponentVo("附件", JSONArray.toJSONString(media_List)));
//        }

//        List site_list = new ArrayList();
//        contract.getSites().forEach(site -> {
//            List<OapiProcessinstanceCreateRequest.FormComponentValueVo> siteValList = new ArrayList();
//            siteValList.add(ProcessUtils.getFormComponentVo("站点名称", site.getSiteName()));
//            siteValList.add(ProcessUtils.getFormComponentVo("数量", site.getQty().toString()));
//            siteValList.add(ProcessUtils.getFormComponentVo("含税单价",site.getTaxPrice().toString()));
//            siteValList.add(ProcessUtils.getFormComponentVo("税率", site.getTaxRate() + ""));
//            siteValList.add(ProcessUtils.getFormComponentVo("税额", site.getTaxAmount().subtract(site.getAmount()).toString()));
//            siteValList.add(ProcessUtils.getFormComponentVo("不含税金额", site.getTaxAmount().subtract(site.getAmount()).toString()));
//            site_list.add(siteValList);
//        });

//        listForm.add(ProcessUtils.getFormComponentVo("站点明细", JSONArray.toJSONString(site_list)));
//        OapiProcessinstanceCreateRequest.FormComponentValueVo form_site = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
//
//        if (payment.getStatus() == 4) { // 如果为退款流程，择单独设置审批人员
//            // TODO
//
//        }
//        OapiProcessinstanceCreateResponse rsp = ProcessUtils.sendProcess(DingTalkConstant.PROCESSCODE_PAYMENT_ADD, userid, deptid, listForm);

    }
}
