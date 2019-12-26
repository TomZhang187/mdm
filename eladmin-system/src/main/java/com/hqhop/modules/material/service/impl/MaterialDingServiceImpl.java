package com.hqhop.modules.material.service.impl;
import cn.hutool.json.JSONObject;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiProcessSaveRequest;
import com.dingtalk.api.request.OapiProcessinstanceCreateRequest;
import com.dingtalk.api.response.OapiProcessSaveResponse;
import com.dingtalk.api.response.OapiProcessinstanceCreateResponse;
import com.google.common.collect.Lists;
import com.hqhop.config.dingtalk.DingTalkConstant;
import com.hqhop.config.dingtalk.DingTalkUtils;
import com.hqhop.config.dingtalk.URLConstant;
import com.hqhop.config.dingtalk.dingtalkVo.DingUser;
import com.hqhop.modules.company.domain.CompanyInfo;
import com.hqhop.modules.company.domain.CompanyUpdate;
import com.hqhop.modules.material.domain.Accessory;
import com.hqhop.modules.material.domain.Attribute;
import com.hqhop.modules.material.domain.Material;
import com.hqhop.modules.material.domain.MaterialOperationRecord;
import com.hqhop.modules.material.repository.MaterialOperationRecordRepository;
import com.hqhop.modules.material.repository.MaterialRepository;
import com.hqhop.modules.material.service.MaterialDingService;
import com.hqhop.modules.material.service.MaterialService;
import com.hqhop.modules.system.service.DictDetailService;
import com.hqhop.modules.system.service.UserService;
import com.hqhop.modules.system.service.dto.UserDTO;
import com.hqhop.utils.SecurityUtils;
import com.taobao.api.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

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

    @Autowired
    private MaterialRepository materialRepository;

    @Autowired
    private MaterialOperationRecordRepository materialOperationRecordRepository;

    @Autowired
    private MaterialServiceImpl materialService;

    @Autowired
    private AttributeServiceImpl attributeService;



    //物料基本档案审批开始回调
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void startApproval(String processId, String url, String dicValue) throws
            ApiException {
       MaterialOperationRecord record = materialOperationRecordRepository.findByProcessIdAndOperationType(processId,dicValue);
        record.setDingUrl(url);
        materialOperationRecordRepository.save(record);
    }


    //物料基本档案新增审批
    @Override
    public void addApprovel(Material  resources) throws
            ApiException {


        MaterialOperationRecord record = new MaterialOperationRecord();

        UserDTO userDTO = userService.findByName("admin");
        //1 基础档案新增 2 基础档案修改 ....更多对照字典
        record .setOperationType("1");
        record .setUserId(userDTO.getEmployee().getDingId());
        OapiProcessinstanceCreateResponse response = getApprovalResponse(resources,userDTO);

        if(response.getErrcode() == 0L){

            //1 新增状态 2 新增审批中 3 驳回 4 审批通过....更多对照字典
           resources.setApprovalState("2");
           Material material1 =  materialService.create(resources);

            record.getDataByMaterial(material1);
            record.setProcessId(response.getProcessInstanceId());
            record.setId(material1.getId());
            record.setApproveResult("未知");
//            materialOperationRecordRepository.save(resources);
        }
    }

    //物料基本档案新增审批通过
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void agreeAddApproval(String processId) {


        MaterialOperationRecord record = materialOperationRecordRepository.findByProcessIdAndApproveResult(processId,"未知");
        if(record !=null){
            record.setApproveResult("通过");
            record.setApproveTime(new Timestamp(new Date().getTime()));
            materialOperationRecordRepository.save(record);

            Material material = materialRepository.getOne(record.getId());
            //1新增 2审批中 3驳回 4审核通过
            material.setApprovalState("4");
            materialService.update(material);
        }

    }


    //物料基本档案新增审批驳回
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void refuseAddApproval(String processId){

        MaterialOperationRecord record = materialOperationRecordRepository.findByProcessIdAndApproveResult(processId,"未知");
        if(record  !=null){
            record.setApproveResult("驳回");
            record.setApproveTime(new Timestamp(new Date().getTime()));
            materialOperationRecordRepository.save( record);

            Material material = materialRepository.getOne(record.getId());
            //1新增 2审批中 3驳回 4审核通过
            material.setApprovalState("4");
            materialService.update(material);

        }

    }

    //物料基本档案新增审批撤销
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void terminateAddApproval(String processId) {

        MaterialOperationRecord record = materialOperationRecordRepository.findByProcessIdAndApproveResult(processId,"未知");
        if(record  !=null){
            Material material = materialRepository.getOne(record.getId());
            //1新增 2审批中 3驳回 4审核通过
            material.setApprovalState(record.getApprovalState());
            materialService.update(material);
           materialOperationRecordRepository.deleteById(record.getKey());
        }

    }



    //物料基本档案变更审批
    @Override
    public void updateApprovel(Material resources) throws
            ApiException {

       Material material = materialRepository.findByKey(resources.getId());
       resources.setIsTaxable(null);

        MaterialOperationRecord record = new MaterialOperationRecord();
        UserDTO userDTO = userService.findByName("admin");
        //1 基础档案新增 2 基础档案变更 ....更多对照字典
        record.setOperationType("2");
        record.setUserId(userDTO.getEmployee().getDingId());
        OapiProcessinstanceCreateResponse response = getApprovalResponse(resources,userDTO);

        if(response.getErrcode() == 0L){

            // 4 审批通过5变更审批中....更多对照字典
            material.setApprovalState("5");
            Material material1 = materialService.update(material);

            resources.setEnable(false);
            Material material2 = materialRepository.save(resources);

            record.setProcessId(response.getProcessInstanceId());
            record.setId(material.getId());
            record.setTemporaryId(material2.getId());
            record.setApproveResult("未知");
            materialOperationRecordRepository.save( record);
        }
    }

    //物料基本档案变更审批通过
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void agreeUpdateApproval(String processId, String dicValue) {


       MaterialOperationRecord record = materialOperationRecordRepository.findByProcessIdAndOperationType(processId,dicValue);
        if(record !=null){
            record.setApproveResult("通过");
            record.setApproveTime(new Timestamp(new Date().getTime()));
            materialOperationRecordRepository.save(record);

            Material material = materialRepository.getOne(record.getId());
            Material material1 = materialRepository.getOne(record.getTemporaryId());

            materialService.deleteMaterial(material1);

            //用来修改客商数据的记录  1 新增 2 修改 3停用4启用....9本地保存 更多对照字典
            material1.setId(material.getId());
            material1.setEnable(true);
            //1新增 2审批中 3驳回 4审核通过
            material1.setApprovalState("4");
          materialService.update(material1);
        }
    }

    //物料基本档案变更审批驳回
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void refuseUpdateApproval(String processId, String dicValue){

        MaterialOperationRecord record = materialOperationRecordRepository.findByProcessIdAndOperationType(processId,dicValue);

        if(record !=null){


            record.setApproveResult("驳回");
            record.setApproveTime(new Timestamp(new Date().getTime()));
            materialOperationRecordRepository.save(record);

            Material material = materialRepository.getOne(record.getId());
            Material material1 = materialRepository.getOne(record.getTemporaryId());
            //1新增 2审批中 3驳回 4审核通过
            material.setApprovalState("4");

            materialService.deleteMaterial(material1);

        }
    }

    //物料基本档案变更审批撤销
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void terminateUpdateApproval(String processId, String dicValue) {

        MaterialOperationRecord record = materialOperationRecordRepository.findByProcessIdAndOperationType(processId,dicValue);

        if(record !=null){

            Material material = materialRepository.getOne(record.getId());
            Material material1 = materialRepository.getOne(record.getTemporaryId());
            //1新增 2审批中 3驳回 4审核通过
            material.setApprovalState("4");
           materialService.update(material);
           materialService.deleteMaterial(material1);
           materialOperationRecordRepository.deleteById(record.getKey());
        }
    }


    public OapiProcessinstanceCreateResponse getApprovalResponse(Material resources,UserDTO userDTO)throws
            ApiException {

        DefaultDingTalkClient client = new DefaultDingTalkClient(URLConstant.URL_PROCESSINSTANCE_START);
        OapiProcessinstanceCreateRequest request = new OapiProcessinstanceCreateRequest();
        //物料基本档案审批模板
        request.setProcessCode(DingTalkConstant.PROCESSCODE_MATERIAL_ADD);
        List<OapiProcessinstanceCreateRequest.FormComponentValueVo> listForm = new ArrayList<OapiProcessinstanceCreateRequest.FormComponentValueVo>();
        // 单行输入框
        OapiProcessinstanceCreateRequest.FormComponentValueVo input = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        input.setName("审批类型");
        //1 基本档案新增....更多对照字典
        input.setValue(dictDetailService.getDicLabel("material_operation_type",resources.getId()!=null?2:1));
        listForm.add(input);

        if(resources.getId() != null){
            listForm.addAll(getMaterialChangeList(resources));
        }else {
            listForm.addAll(getMaterialList(resources));
        }
        request.setFormComponentValues(listForm);
        request.setOriginatorUserId(userDTO.getEmployee().getDingId());
        request.setDeptId(userDTO.getDeptId());
        OapiProcessinstanceCreateResponse response = client.execute(request,DingTalkUtils.getAccessToken());
        return  response;
    }

    public List getMaterialList(Material resources){

        List list1 = new ArrayList();

        // 明细-单行输入框
        OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName1 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        ItemName1.setName("物料编码");
        ItemName1.setValue(resources.getRemark()!=null?resources.getRemark():"");
        list1.add(ItemName1);

        // 明细-单行输入框
        OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName2 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        ItemName2.setName("名称");
        ItemName2.setValue(resources.getName()!=null?resources.getName():"");
        list1.add(ItemName2);


        // 明细-单行输入框
        OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName3 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        ItemName3.setName("物料型号");
        ItemName3.setValue(resources.getModel()!=null?resources.getModel():"");
        list1.add(ItemName3);

        // 明细-单行输入框
        OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName4 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        ItemName4.setName("设计单位");
        ItemName4.setValue(resources.getUnit()!=null?resources.getUnit():"");
        list1.add(ItemName4);

        // 明细-单行输入框
        OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName5 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        ItemName5.setName("是否应税");
        ItemName5.setValue(resources.getIsTaxable()!=null?resources.getIsTaxable()?"是":"否":"");
        list1.add(ItemName5);


        // 明细-单行输入框
        OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName6 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        ItemName6.setName("创建人");
        ItemName6.setValue(resources.getCreatePerson()!=null?resources.getCreatePerson():"");
        list1.add(ItemName6);


        // 多行输入框
        OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName7 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        ItemName7.setName("属性");
        ItemName7.setValue(resources.getAttributes()!=null?getAttributes(resources.getAttributes()):"");
        list1.add(ItemName7);

        return  list1;
    }


    public List getMaterialChangeList(Material resources){

        Material material = materialRepository.findByKey(resources.getId());
        Set<Attribute> collect = attributeService.queryAllByMaterialId(material.getId()).stream().collect(Collectors.toSet());

        List list1 = new ArrayList();

        // 明细-单行输入框
        OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName1 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        ItemName1.setName("物料编码");
        ItemName1.setValue(resources.getRemark()!=null?getChange(resources.getRemark(),material.getRemark()):"");
        list1.add(ItemName1);

        // 明细-单行输入框
        OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName2 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        ItemName2.setName("名称");
        ItemName2.setValue(resources.getName()!=null?getChange(resources.getName(),material.getName()):"");
        list1.add(ItemName2);


        // 明细-单行输入框
        OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName3 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        ItemName3.setName("物料型号");
        ItemName3.setValue(resources.getModel()!=null?getChange(resources.getModel(),material.getModel()):"");
        list1.add(ItemName3);

        // 明细-单行输入框
        OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName4 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        ItemName4.setName("设计单位");
        ItemName4.setValue(resources.getUnit()!=null?getChange(resources.getUnit(),material.getUnit()):"");
        list1.add(ItemName4);

        // 明细-单行输入框
        OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName5 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        ItemName5.setName("是否应税");
        ItemName5.setValue(resources.getIsTaxable()!=null?getChange(resources.getIsTaxable()?"是":"否",material.getIsTaxable()?"是":"否"):"");
        list1.add(ItemName5);


        // 明细-单行输入框
        OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName6 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        ItemName6.setName("创建人");
        ItemName6.setValue(resources.getCreatePerson()!=null?resources.getCreatePerson():"");
        list1.add(ItemName6);


        // 多行输入框
        OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName7 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        ItemName7.setName("属性");
        ItemName7.setValue(resources.getAttributes()!=null?getChangeAttribute(resources.getAttributes(),collect):"");
        list1.add(ItemName7);

        return  list1;
    }



    //字符串新旧对比变化
    public  String getChange(String update ,String now) {
        if(update.equals(now)){
            return  update;
        }else {
            return  now+" -> "+update;
        }
    }

    //属性拼接
    public  String  getAttributes(Set<Attribute> attributes){
        String str = "";
        for (Attribute attribute : attributes) {
               str+=attribute.getAttributeName()+"   "+attribute.getAttributeValue()+"\n";
        }
      return  str;
    }
  //属性对比并拼接
    public String getChangeAttribute(Set<Attribute>  news,Set<Attribute> olds){
        String str = "";
        for (Attribute attribute : news) {

            if(attribute.getAttributeName() == null || attribute.getAttributeValue()==null){
                break;
            }
            String name = attribute.getAttributeName();
            Boolean isRepetition = false;

            for (Attribute old : olds) {
                if(old.getAttributeName().equals(name)){
                    isRepetition = true;
                    str +=attribute.getAttributeName()+"    " +getChange(attribute.getAttributeValue(),old.getAttributeValue())+"\n";
                    break;
                }
            }
            if(!isRepetition){
                str+=attribute.getAttributeName()+"   "+attribute.getAttributeValue()+"\n";
;
            }
        }
        return  str;
    }


}
