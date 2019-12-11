package com.hqhop.modules.material.service.impl;

import com.dingtalk.api.request.OapiProcessinstanceCreateRequest;
import com.hqhop.modules.material.domain.MaterialOperationRecord;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：张丰
 * @date ：Created in 2019/12/10 0010 13:58
 * @description：生产档案钉钉审批业务
 * @modified By：
 * @version: $
 */



@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class MaterialProductionDingServiceImpl {





    public List getMaterialProductionList(MaterialOperationRecord resources){
        List list2 = new ArrayList();

        // 单行输入框
        OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName61 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        ItemName61.setName("说明文字");
        ItemName61.setValue("补充内容");
        list2.add(ItemName61);

        // 单行输入框
        OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName7 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        ItemName7.setName("默认工厂");
        ItemName7.setValue("");
        list2.add(ItemName7);


        // 单选框
        OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName8 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        ItemName8.setName("出入库跟踪");
        ItemName8.setValue("");
        list2 .add(ItemName8);


        // 单选框
        OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName9 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        ItemName9.setName("需求管理");
        ItemName9.setValue("否");
        list2 .add(ItemName9);


        //单选框
        OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName10 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        ItemName10.setName("序列号管理");
        ItemName10.setValue("否");
        list2 .add(ItemName10);


        // 单行输入框
        OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName12 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        ItemName12.setName("物料类别");
        ItemName12.setValue("");
        list2 .add(ItemName12);

        // 单行输入框
        OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName13 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        ItemName13.setName("物料形态");
        ItemName13.setValue("");
        list2 .add(ItemName13);


        // 单选框
        OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName14 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        ItemName14.setName("需求合并");
        ItemName14.setValue("否");
        list2 .add(ItemName14);


        // 单选框
        OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName15 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        ItemName15.setName("虚项");
        ItemName15.setValue("否");
        list2 .add(ItemName15);


        // 单选框
        OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName16 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        ItemName16.setName("是否成本对象");
        ItemName16.setValue("否");
        list2 .add( ItemName16);

        //单选框
        OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName17 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        ItemName17.setName("是否发料");
        ItemName17.setValue("否");
        list2 .add( ItemName17);


        // 单选框
        OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName18 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        ItemName18.setName("根据检验结果入库");
        ItemName18.setValue("否");
        list2 .add(ItemName18);


        // 单选框
        OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName19 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        ItemName19.setName("免检");
        ItemName19.setValue("否");
        list2 .add(ItemName19);


        // 单选框
        OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName20 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        ItemName20.setName("按生产订单核算成本");
        ItemName20.setValue("否");
        list2 .add(ItemName20);


        // 单选框
        OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName21 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        ItemName21.setName("按成本中心统计产量");
        ItemName21.setValue("否");
        list2 .add(ItemName21);

        // 单选框
        OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName22 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        ItemName22.setName("是否出入库");
        ItemName22.setValue("否");
        list2 .add(ItemName22);


        // 单行输入框
        OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName23 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        ItemName23.setName("计价方式");
        ItemName23.setValue("");
        list2 .add(ItemName23);


        // 单行输入框
        OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName24 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        ItemName24.setName("生产业务员");
        ItemName24.setValue("[]");
        list2 .add(ItemName24);


        // 单行输入框
        OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName25 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        ItemName25.setName("计划属性");
        ItemName25.setValue("");
        list2 .add(ItemName25);


        // 单行输入框
        OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName26 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        ItemName26.setName("委外类型");
        ItemName26.setValue("");
        list2 .add(ItemName26);


        // 单行输入框
        OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName27 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        ItemName27.setName("物料级别");
        ItemName27.setValue("");
        list2 .add(ItemName27);


        //
//        //附件
//        OapiProcessinstanceCreateRequest.FormComponentValueVo attachmentComponent = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
//
//        JSONArray array = new JSONArray();
//        Set<Accessory> list4 = resources.getAccessories();
//        for (Accessory accessory : list4) {
//            JSONObject attachmentJson = new JSONObject();
//            attachmentJson.put("fileId",accessory.getFileId());
//            attachmentJson.put("fileName", accessory.getFileName());
//            attachmentJson.put("fileType", accessory.getFileType());
//            attachmentJson.put("spaceId", accessory.getSpaceId());
//            attachmentJson.put("fileSize", accessory.getFileSize());
//            array.add(attachmentJson);
//        }
//        attachmentComponent.setValue(array.toJSONString());
//        attachmentComponent.setName("设计图纸");

        return  list2;
    }

}
