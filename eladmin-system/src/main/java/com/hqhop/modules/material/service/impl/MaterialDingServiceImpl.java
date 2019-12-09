package com.hqhop.modules.material.service.impl;
import cn.hutool.json.JSONObject;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.request.OapiProcessinstanceCreateRequest;
import com.dingtalk.api.response.OapiProcessinstanceCreateResponse;
import com.hqhop.config.dingtalk.DingTalkConstant;
import com.hqhop.config.dingtalk.DingTalkUtils;
import com.hqhop.config.dingtalk.URLConstant;
import com.hqhop.config.dingtalk.domain.Accessory;
import com.hqhop.modules.material.domain.MaterialOperationRecord;
import com.hqhop.modules.material.service.MaterialDingService;
import com.hqhop.modules.system.service.DictDetailService;
import com.hqhop.modules.system.service.UserService;
import com.hqhop.modules.system.service.dto.UserDTO;
import com.hqhop.utils.SecurityUtils;
import com.taobao.api.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
/**
 * @author ：张丰
 * @date ：Created in 2019/12/6 0006 9:05
 * @description：物料钉钉业务类
 * @modified By：
 * @version: $
 */


@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class MaterialDingServiceImpl implements MaterialDingService  {


    @Autowired
    private DictDetailService dictDetailService;

    @Autowired
    private UserService userService;



//
//    //物料新增审批
//    @Override
//    public void addApprovel(MaterialOperationRecord resources) throws
//            ApiException {
//
//
////        OapiProcessinstanceCreateResponse response = getApprovalResponse(resources);
////
////        //1 新增 2 修改 3停用4启用....更多对照字典
////        resouces.setOperationType("1");
////        //放入审批实例ID
////        resouces.setProcessId(response.getProcessInstanceId());
////        //放入当前用户ID
////        resouces.setUserId(dingUser.getUserid());
////
////        resouces.setCreateMan(dingUser.getName());
////
////        //1 新增状态 2 新增审批中 3 驳回 4 审批通过
////        CompanyInfo companyInfo2 = resouces.toCompanyInfo();
////        companyInfo2.setCompanyState(2);
////
////        CompanyInfo  companyInfo =  companyInfoService.createAndUpadte(companyInfo2);
////        //放入新增客商商主键
////        resouces.setCompanyKey(companyInfo.getCompanyKey());
////        CompanyUpdate companyUpdate = companyUpdateRepository.save(resouces);
////        return companyUpdate;
//    }





















































































    public OapiProcessinstanceCreateResponse getApprovalResponse(MaterialOperationRecord resources)throws
            ApiException {

        DefaultDingTalkClient client = new DefaultDingTalkClient(URLConstant.URL_PROCESSINSTANCE_START);
        OapiProcessinstanceCreateRequest request = new OapiProcessinstanceCreateRequest();
        request.setProcessCode(DingTalkConstant.PROCESSCODE_MATERIAL_ADD);
        List<OapiProcessinstanceCreateRequest.FormComponentValueVo> listForm = new ArrayList<OapiProcessinstanceCreateRequest.FormComponentValueVo>();

        // 单行输入框
        OapiProcessinstanceCreateRequest.FormComponentValueVo input = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        input.setName("审批类型");
        //1 新增 2 修改 3停用....更多对照字典
        input.setValue(dictDetailService.getDicLabel("material_operation_type",resources.getOperationType()));

        OapiProcessinstanceCreateRequest.FormComponentValueVo vo4 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        vo4.setName("物料基本档案");

        List list1 = new ArrayList();

        // 明细-单行输入框
        OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName1 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        ItemName1.setName("物料编码");
        ItemName1.setValue(resources.getRemark());
        list1.add(ItemName1);

        // 明细-单行输入框
        OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName2 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        ItemName2.setName("名称");
        ItemName2.setValue(resources.getName());
        list1.add(ItemName2);


        // 明细-单行输入框
        OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName3 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        ItemName3.setName("物料型号");
        ItemName3.setValue(resources.getModel());
        list1.add(ItemName3);

        // 明细-单行输入框
        OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName4 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        ItemName4.setName("设计单位");
        ItemName4.setValue(resources.getUnit());
        list1.add(ItemName4);

        // 明细-单行输入框
        OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName5 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        ItemName5.setName("是否应税");
        ItemName5.setValue(resources.getIsTaxable()?"是":"否");
        list1.add(ItemName5);


        // 明细-单行输入框
        OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName6 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        ItemName6.setName("创建人");
        ItemName6.setValue(resources.getName());
        list1.add(ItemName6);

        vo4.setValue(JSON.toJSONString(Arrays.asList(list1)));



        OapiProcessinstanceCreateRequest.FormComponentValueVo vo5 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        vo5.setName("审批人补充内容");

        List list2 = new ArrayList();

        // 明细-单行输入框
        OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName7 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        ItemName7.setName("默认工厂");
        ItemName7.setValue("");
        list2.add(ItemName7);


        // 明细-单行输入框
        OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName8 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        ItemName8.setName("出入库跟踪");
        ItemName8.setValue("");
        list2 .add(ItemName8);


        // 明细-单行输入框
        OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName9 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        ItemName9.setName("需求管理");
        ItemName9.setValue("");
        list2 .add(ItemName9);


        // 明细-单行输入框
        OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName10 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        ItemName10.setName("序列号管理");
        ItemName10.setValue("");
        list2 .add(ItemName10);


        // 明细-单行输入框
        OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName11 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        ItemName11.setName("默认工厂");
        ItemName11.setValue("");
        list2 .add(ItemName11);


        // 明细-单行输入框
        OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName12 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        ItemName12.setName("物料类别");
        ItemName12.setValue("");
        list2 .add(ItemName12);

        // 明细-单行输入框
        OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName13 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        ItemName13.setName("物料形态");
        ItemName13.setValue("");
        list2 .add(ItemName13);


        // 明细-单行输入框
        OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName14 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        ItemName14.setName("需求合并");
        ItemName14.setValue("");
        list2 .add(ItemName14);


        // 明细-单行输入框
        OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName15 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        ItemName15.setName("虚项");
        ItemName15.setValue("");
        list2 .add(ItemName15);


        // 明细-单行输入框
        OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName16 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        ItemName16.setName("成本对象");
        ItemName16.setValue("");
        list2 .add( ItemName16);

        // 明细-单行输入框
        OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName17 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        ItemName17.setName("是否发料");
        ItemName17.setValue("");
        list2 .add( ItemName17);


        // 明细-单行输入框
        OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName18 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        ItemName18.setName("根据检验结果入库");
        ItemName18.setValue("");
        list2 .add(ItemName18);


        // 明细-单行输入框
        OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName19 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        ItemName19.setName("免检");
        ItemName19.setValue("");
        list2 .add(ItemName19);


        // 明细-单行输入框
        OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName20 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        ItemName20.setName("按生产订单核算成本");
        ItemName20.setValue("");
        list2 .add(ItemName20);


        // 明细-单行输入框
        OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName21 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        ItemName21.setName("按成本中心统计产量");
        ItemName21.setValue("");
        list2 .add(ItemName21);

        // 明细-单行输入框
        OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName22 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        ItemName22.setName("是否出入库");
        ItemName22.setValue("");
        list2 .add(ItemName22);


        // 明细-单行输入框
        OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName23 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        ItemName23.setName("计价方式");
        ItemName23.setValue("");
        list2 .add(ItemName23);


        // 明细-单行输入框
        OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName24 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        ItemName24.setName("生产业务员");
        ItemName24.setValue("");
        list2 .add(ItemName24);


        // 明细-单行输入框
        OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName25 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        ItemName25.setName("计划属性");
        ItemName25.setValue("");
        list2 .add(ItemName25);


        // 明细-单行输入框
        OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName26 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        ItemName26.setName("委外类型");
        ItemName26.setValue("");
        list2 .add(ItemName26);


        // 明细-单行输入框
        OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName27 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        ItemName27.setName("物料级别");
        ItemName27.setValue("");
        list2 .add(ItemName27);


        vo5.setValue(JSON.toJSONString(Arrays.asList(list2)));



        //附件
        OapiProcessinstanceCreateRequest.FormComponentValueVo attachmentComponent = new OapiProcessinstanceCreateRequest.FormComponentValueVo();

        JSONArray array = new JSONArray();
        Set<Accessory> list4 = resources.getAccessorys();
        for (Accessory accessory : list4) {
            JSONObject attachmentJson = new JSONObject();
            attachmentJson.put("fileId",accessory.getFileId());
            attachmentJson.put("fileName", accessory.getFileName());
            attachmentJson.put("fileType", accessory.getFileType());
            attachmentJson.put("spaceId", accessory.getSpaceId());
            attachmentJson.put("fileSize", accessory.getFileSize());
            array.add(attachmentJson);
        }
        attachmentComponent.setValue(array.toJSONString());
        attachmentComponent.setName("设计图纸");


        listForm.add(input);
        listForm.add(vo4);
        listForm.add(vo5);
        listForm.add(attachmentComponent);

        UserDTO userDTO = userService.findByName(SecurityUtils.getUsername());

        request.setFormComponentValues(listForm);
        request.setOriginatorUserId(userDTO.getEmployee().getDingId());
        request.setDeptId(userDTO.getDeptId());
        OapiProcessinstanceCreateResponse response = client.execute(request,DingTalkUtils.getAccessToken());
        return  response;
    }

}
