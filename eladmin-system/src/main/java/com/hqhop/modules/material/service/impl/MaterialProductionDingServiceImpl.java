package com.hqhop.modules.material.service.impl;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.request.OapiProcessinstanceCreateRequest;
import com.dingtalk.api.response.OapiProcessinstanceCreateResponse;
import com.dingtalk.api.response.OapiProcessinstanceGetResponse;
import com.hqhop.common.dingtalk.DingTalkConstant;
import com.hqhop.common.dingtalk.DingTalkUtils;
import com.hqhop.common.dingtalk.URLConstant;
import com.hqhop.modules.material.domain.Material;
import com.hqhop.modules.material.domain.MaterialOperationRecord;
import com.hqhop.modules.material.domain.MaterialProduction;
import com.hqhop.modules.material.repository.MaterialOperationRecordRepository;
import com.hqhop.modules.material.repository.MaterialProductionRepository;
import com.hqhop.modules.material.repository.MaterialRepository;
import com.hqhop.modules.material.service.MaterialProducionDingService;
import com.hqhop.modules.system.repository.DeptRepository;
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
import java.util.ArrayList;
import java.util.Date;
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
public class MaterialProductionDingServiceImpl  implements MaterialProducionDingService {


    @Autowired
    private MaterialRepository materialRepository;

    @Autowired
    private DictDetailService dictDetailService;

    @Autowired
    private UserService userService;

    @Autowired
    private MaterialOperationRecordRepository materialOperationRecordRepository;

    @Autowired
    private MaterialProductionRepository materialProductionRepository;

    @Autowired
     private DeptRepository deptRepository;

    //物料生产档案审批开始回调
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void startApproval(String processId, String url, String dicValue) throws
            ApiException {
        MaterialOperationRecord record = materialOperationRecordRepository.findByProcessIdAndOperationType(processId,dicValue);
        record.setDingUrl(url);
        materialOperationRecordRepository.save(record);
    }

    //生产档案新增审批
    @Override
    public void addApprovel(MaterialOperationRecord resouces) throws
            ApiException {

        //3生产档案新增  4生产档案变更....更多对照字典
        resouces.setOperationType("3");
        OapiProcessinstanceCreateResponse response = getApprovalResponse(resouces);
        if(response.getErrcode() == 0L){

            MaterialProduction materialProduction = resouces.getMaterialProduction();
            Material material = materialRepository.findByKey(resouces.getMaterialId());
            materialProduction.setMaterial(material);
            //1 新增状态 2 新增审批中 3 驳回 4 审批通过....更多对照字典
            materialProduction.setApprovalState("2");
            MaterialProduction materialProduction1 = materialProductionRepository.save(materialProduction);

            resouces.setProcessId(response.getProcessInstanceId());
            resouces.setId(Long.valueOf(materialProduction1.getId()));
            resouces.setCreator(SecurityUtils.getEmployeeName());
            resouces.setUserId(SecurityUtils.getDingId());
            resouces.setApproveResult("未知");
            resouces.setId(materialProduction1.getId().longValue());
            materialOperationRecordRepository.save(resouces);
        }
    }

    //物料生产档案新增审批通过
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void agreeAddApproval(String processId)  throws
            ApiException {

        OapiProcessinstanceGetResponse response = DingTalkUtils.getProcessInfo(processId);
        MaterialOperationRecord record = materialOperationRecordRepository.findByProcessIdAndApproveResult(processId,"未知");
        if(record !=null){

            record.setApproveResult("通过");
            record.setApproveTime(new Timestamp(new Date().getTime()));
            materialOperationRecordRepository.save(record);

            MaterialProduction materialProduction = materialProductionRepository.getOne(record.getId().intValue());

            List<OapiProcessinstanceGetResponse.FormComponentValueVo> form = response.getProcessInstance().getFormComponentValues();
            //出入库跟踪
            materialProduction.setIsOutTrackWarehousing("是".equals(form.get(12).getValue()));
           //需求管理
            materialProduction.setIsDemand("是".equals(form.get(13).getValue()));
            //序列号管理
            materialProduction.setIsSerial("是".equals(form.get(14).getValue()));
            //物料类型
            materialProduction.setMaterialType(form.get(15).getValue()!=null?form.get(15).getValue():null);
            //物料形态
            materialProduction.setMaterialKenel(form.get(16).getValue()!=null?form.get(16).getValue():null);
            //是否需求合并
            materialProduction.setIsDemandConsolidation("是".equals(form.get(17).getValue()));
            //虚项
            materialProduction.setIsImaginaryTerm("是".equals(form.get(18).getValue()));
            //是否成本对象
            materialProduction.setIsCostObject("是".equals(form.get(19).getValue()));
            //是否发料
            materialProduction.setIsHairFeed("是".equals(form.get(20).getValue()));
            //是否检验入库
            materialProduction.setIsInspectionWarehousing("是".equals(form.get(21).getValue()));
            //是否免检
            materialProduction.setIsInspect("是".equals(form.get(22).getValue()));
            //  是否按生产订单核算成本
            materialProduction.setIsOrderCost("是".equals(form.get(23).getValue()));
            // 是否按成本中心统计产量
            materialProduction.setIsCenterStatistics("是".equals(form.get(24).getValue()));
            // 是否出入库
            materialProduction.setIsOutgoingWarehousing("是".equals(form.get(25).getValue()));
            //计价方式
            materialProduction.setValuationMethod(form.get(26).getValue()!=null?form.get(26).getValue():null);
            //生产业务员
            materialProduction.setProductionSalesman(form.get(27).getValue()!=null?form.get(27).getValue():null);
            //计划属性
            materialProduction.setPlanningAttribute(form.get(28).getValue()!=null?form.get(28).getValue():null);
            //委外类型
            materialProduction.setOutsourcingType(form.get(29).getValue()!=null?form.get(29).getValue():null);
            //物料级别
            materialProduction.setMaterialLevel(form.get(30).getValue()!=null?form.get(30).getValue():null);

            //1 新增状态 2 新增审批中 3 驳回 4 审批通过....更多对照字典
            materialProduction.setApprovalState("4");
            materialProductionRepository.save(materialProduction);
        }
    }

    //物料生产档案新增审批驳回
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void refuseAddApproval(String processId){

        MaterialOperationRecord record = materialOperationRecordRepository.findByProcessIdAndApproveResult(processId,"未知");
        if(record  !=null){
            record.setApproveResult("驳回");
            record.setApproveTime(new Timestamp(new Date().getTime()));
            materialOperationRecordRepository.save( record);
            MaterialProduction one = materialProductionRepository.getOne(record.getId().intValue());
            //1新增 2审批中 3驳回 4审核通过
            one.setApprovalState("3");
            materialProductionRepository.save(one);
        }

    }

    //物料生产档案新增审批撤销
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void terminateAddApproval(String processId) {
        MaterialOperationRecord record = materialOperationRecordRepository.findByProcessIdAndApproveResult(processId,"未知");
        if(record  !=null){
            MaterialProduction one = materialProductionRepository.getOne(record.getId().intValue());
            //1新增 2审批中 3驳回 4审核通过
            one.setApprovalState("1");
            materialProductionRepository.save(one);
            materialOperationRecordRepository.deleteById(record.getKey());
        }

    }


    //物料生产档案变更审批
    @Override
    public void updateApprovel(MaterialOperationRecord resources) throws
            ApiException {

        //3生产档案新增  4生产档案变更....更多对照字典
        resources.setOperationType("4");
        OapiProcessinstanceCreateResponse response = getApprovalResponse(resources);

        if(response.getErrcode() == 0L){
                   //数据库的数据
                 MaterialProduction materialProduction = materialProductionRepository.findByKey(resources.getId().intValue());

            //1新增 2审批中 3驳回 4审核通过5变更审批中
               materialProduction.setApprovalState("5");
               materialProductionRepository.save(materialProduction);
               resources.setProcessId(response.getProcessInstanceId());
               resources.setCreator(SecurityUtils.getEmployeeName());
               resources.setUserId(SecurityUtils.getDingId());
               resources.setApproveResult("未知");
            materialOperationRecordRepository.save(resources);
        }
    }

    //物料生产档案变更审批通过
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void agreeUpdateApproval(String processId, String dicValue)  throws
            ApiException {

        OapiProcessinstanceGetResponse response = DingTalkUtils.getProcessInfo(processId);
        MaterialOperationRecord record = materialOperationRecordRepository.findByProcessIdAndOperationType(processId,dicValue);
        if(record !=null){
            record.setApproveResult("通过");
            record.setApproveTime(new Timestamp(new Date().getTime()));
            materialOperationRecordRepository.save(record);

            MaterialProduction materialProduction = materialProductionRepository.getOne(record.getId().intValue());

            List<OapiProcessinstanceGetResponse.FormComponentValueVo> form = response.getProcessInstance().getFormComponentValues();
            //出入库跟踪
            materialProduction.setIsOutTrackWarehousing("是".equals(form.get(12).getValue()));
            //需求管理
            materialProduction.setIsDemand("是".equals(form.get(13).getValue()));
            //序列号管理
            materialProduction.setIsSerial("是".equals(form.get(14).getValue()));
            //物料类型
            materialProduction.setMaterialType(form.get(15).getValue()!=null?form.get(15).getValue():null);
            //物料形态
            materialProduction.setMaterialKenel(form.get(16).getValue()!=null?form.get(16).getValue():null);
            //是否需求合并
            materialProduction.setIsDemandConsolidation("是".equals(form.get(17).getValue()));
            //虚项
            materialProduction.setIsImaginaryTerm("是".equals(form.get(18).getValue()));
            //是否成本对象
            materialProduction.setIsCostObject("是".equals(form.get(19).getValue()));
            //是否发料
            materialProduction.setIsHairFeed("是".equals(form.get(20).getValue()));
            //是否检验入库
            materialProduction.setIsInspectionWarehousing("是".equals(form.get(21).getValue()));
            //是否免检
            materialProduction.setIsInspect("是".equals(form.get(22).getValue()));
            //  是否按生产订单核算成本
            materialProduction.setIsOrderCost("是".equals(form.get(23).getValue()));
            // 是否按成本中心统计产量
            materialProduction.setIsCenterStatistics("是".equals(form.get(24).getValue()));
            // 是否出入库
            materialProduction.setIsOutgoingWarehousing("是".equals(form.get(25).getValue()));
            //计价方式
            materialProduction.setValuationMethod(form.get(26).getValue()!=null?form.get(26).getValue():null);
            //生产业务员
            materialProduction.setProductionSalesman(form.get(27).getValue()!=null?form.get(27).getValue():null);
            //计划属性
            materialProduction.setPlanningAttribute(form.get(28).getValue()!=null?form.get(28).getValue():null);
            //委外类型
            materialProduction.setOutsourcingType(form.get(29).getValue()!=null?form.get(29).getValue():null);
            //物料级别
            materialProduction.setMaterialLevel(form.get(30).getValue()!=null?form.get(30).getValue():null);

            //1 新增状态 2 新增审批中 3 驳回 4 审批通过....更多对照字典
            materialProduction.setApprovalState("4");
            materialProductionRepository.save(materialProduction);
        }
    }

    //物料生产档案变更审批驳回
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void refuseUpdateApproval(String processId, String dicValue){

        MaterialOperationRecord record = materialOperationRecordRepository.findByProcessIdAndOperationType(processId,dicValue);
        if(record !=null){
            record.setApproveResult("驳回");
            record.setApproveTime(new Timestamp(new Date().getTime()));
            materialOperationRecordRepository.save(record);

           MaterialProduction materialProduction = materialProductionRepository.getOne(record.getId().intValue());
            //1新增 2审批中 3驳回 4审核通过
            materialProduction.setApprovalState("4");
            materialProductionRepository.save(materialProduction);
        }
    }

    //物料生产档案变更审批撤销
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void terminateUpdateApproval(String processId, String dicValue) {

        MaterialOperationRecord record = materialOperationRecordRepository.findByProcessIdAndOperationType(processId,dicValue);

        if(record !=null){

          MaterialProduction materialProduction = materialProductionRepository.getOne(record.getId().intValue());
            //1新增 2审批中 3驳回 4审核通过
            materialProduction.setApprovalState("4");
            materialProductionRepository.save(materialProduction);
            materialOperationRecordRepository.deleteById(record.getKey());

        }
    }


    //物料生产档案 停用/启用 审批
    @Override
    public void isAbleApproval(Integer id) throws
            ApiException {
        MaterialProduction byKey = materialProductionRepository.findByKey(id);
        MaterialOperationRecord record = new MaterialOperationRecord();
        record.getDataByMateriaProduction(byKey);

        if(byKey.getEnable()){
            //5生产档案停用  6生产档案启用....更多对照字典
            record.setOperationType("5");
        }else {
            //5生产档案停用  6生产档案启用....更多对照字典
            record.setOperationType("6");
        }

        OapiProcessinstanceCreateResponse response = getApprovalResponse(record);
        if(response.getErrcode() == 0L){
            //1新增 2审批中 3驳回 4审核通过5变更审批中
            byKey.setApprovalState("5");
            MaterialProduction save = materialProductionRepository.save(byKey);
            record.setProcessId(response.getProcessInstanceId());
            record.setUserId(SecurityUtils.getDingId());
            record.setCreator(SecurityUtils.getEmployeeName());
            record.getDataByMateriaProduction(save);
            record.setApproveResult("未知");
            materialOperationRecordRepository.save(record);

        }
    }



    //物料生产档案 停用/启用 审批通过
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void agreeIsAbleApproval(String processId) {

        MaterialOperationRecord record = materialOperationRecordRepository.findByProcessIdAndApproveResult(processId,"未知");
        if(record !=null){
            record.setApproveResult("通过");
            record.setApproveTime(new Timestamp(new Date().getTime()));
            materialOperationRecordRepository.save(record);

            MaterialProduction materialProduction = materialProductionRepository.getOne(record.getId().intValue());
            if(materialProduction.getEnable()){
                materialProduction.setEnable(false);
            } else {
                materialProduction.setEnable(true);
            }
            //1新增 2审批中 3驳回 4审核通过
            materialProduction.setApprovalState("4");
          materialProductionRepository.save(materialProduction);
        }

    }

    //物料生产档案 停用/启用 审批驳回
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void refuseIsAbleApproval(String processId){

        MaterialOperationRecord record = materialOperationRecordRepository.findByProcessIdAndApproveResult(processId,"未知");
        if(record !=null){
            record.setApproveResult("驳回");
            record.setApproveTime(new Timestamp(new Date().getTime()));
            materialOperationRecordRepository.save(record);

            MaterialProduction materialProduction = materialProductionRepository.getOne(record.getId().intValue());
            //1新增 2审批中 3驳回 4审核通过
            materialProduction.setApprovalState("4");
            materialProductionRepository.save(materialProduction);
        }



    }

    //物料生产档案 停用/启用 审批撤销
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void terminateIsAbleApproval(String processId) {

        MaterialOperationRecord record = materialOperationRecordRepository.findByProcessIdAndApproveResult(processId,"未知");
        if(record !=null){

            MaterialProduction materialProduction = materialProductionRepository.getOne(record.getId().intValue());
            //1新增 2审批中 3驳回 4审核通过
            materialProduction.setApprovalState("4");
            materialProductionRepository.save(materialProduction);

            materialOperationRecordRepository.deleteById(record.getKey());
        }


    }


    public OapiProcessinstanceCreateResponse getApprovalResponse(MaterialOperationRecord resources)throws
            ApiException {

        DefaultDingTalkClient client = new DefaultDingTalkClient(URLConstant.URL_PROCESSINSTANCE_START);
        OapiProcessinstanceCreateRequest request = new OapiProcessinstanceCreateRequest();
        //物料基本档案审批模板
        request.setProcessCode(DingTalkConstant.PROCESSCODE_MATERIALPRODUCTION_ADD);
        List<OapiProcessinstanceCreateRequest.FormComponentValueVo> listForm = new ArrayList<OapiProcessinstanceCreateRequest.FormComponentValueVo>();
        // 单行输入框
        OapiProcessinstanceCreateRequest.FormComponentValueVo input = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        input.setName("审批类型");
        //3生产档案新增  4生产档案变更....更多对照字典
        input.setValue(dictDetailService.getDicLabel("material_operation_type",resources.getOperationType()));
        listForm.add(input);

        //1 基本档案新增2基本档案变更....更多对照字典
            listForm.addAll(getMaterialProductionList(resources));

        request.setFormComponentValues(listForm);
        UserDTO userDTO = userService.findByName(SecurityUtils.getUsername());
        request.setOriginatorUserId(SecurityUtils.getDingId());
        request.setDeptId(Long.valueOf(userDTO.getId()));
        OapiProcessinstanceCreateResponse response = client.execute(request,DingTalkUtils.getAccessToken());
        return  response;
    }


    public List getMaterialProductionList(MaterialOperationRecord resources){

        List list1 = new ArrayList();
        Material material =  materialRepository.findByKey(resources.getMaterialId());
        // 明细-单行输入框
        OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName1 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        ItemName1.setName("物料编码");
        ItemName1.setValue(material.getRemark()!=null?material.getRemark():"");
        ItemName1.setExtValue("扩展值");
        list1.add(ItemName1);

        // 明细-单行输入框
        OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName2 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        ItemName2.setName("名称");
        ItemName2.setValue(material.getName()!=null?material.getName():"");
        list1.add(ItemName2);


        // 明细-单行输入框
        OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName3 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        ItemName3.setName("物料型号");
        ItemName3.setValue(material.getModel()!=null?material.getModel():"");
        list1.add(ItemName3);

        // 明细-单行输入框
        OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName4 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        ItemName4.setName("设计单位");
        ItemName4.setValue(material.getUnit()!=null?material.getUnit():"");
        list1.add(ItemName4);

        // 明细-单行输入框
        OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName8 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        ItemName8.setName("税目");
        ItemName8.setValue(material.getTaxRating()!=null?material.getTaxRating():"");
        list1.add(ItemName8);


        // 明细-单行输入框
        OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName5 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        ItemName5.setName("是否应税");
        ItemName5.setValue(material.getIsTaxable()!=null?material.getIsTaxable()?"是":"否":"");
        list1.add(ItemName5);


        // 明细-单行输入框
        OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName6 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        ItemName6.setName("创建人");
        ItemName6.setValue(material.getCreatePerson()!=null?material.getCreatePerson():"张");
        list1.add(ItemName6);

//        // 单行输入框
//        OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName61 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
//        ItemName61.setName("说明文字");
//        ItemName61.setValue("补充内容");
//        list1.add(ItemName61);


        // 单行输入框
        OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName7 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        ItemName7.setName("默认工厂");
        ItemName7.setValue(deptRepository.findNameByDingId(resources.getDefaultFactory()!=null?resources.getDefaultFactory():"1"));
        list1.add(ItemName7);


        if(resources.getIsOutTrackWarehousing() != null){
            // 单选框
            OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName81 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
            ItemName81.setName("出入库跟踪");
            ItemName81.setValue(resources.getIsOutTrackWarehousing()?"是":"否");
            list1.add(ItemName81);
        }




        if(resources.getIsDemand() != null) {
            // 单选框
            OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName9 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
            ItemName9.setName("需求管理");
            ItemName9.setValue(resources.getIsDemand()?"是":"否");
            list1 .add(ItemName9);
        }


      if(resources.getIsSerial() != null) {
          //单选框
          OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName10 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
          ItemName10.setName("序列号管理");
          ItemName10.setValue(resources.getIsSerial()?"是":"否");
          list1 .add(ItemName10);
      }
        // 单行输入框
        OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName12 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        ItemName12.setName("物料类型");
        ItemName12.setValue(resources.getMaterialType()!=null?resources.getMaterialType():"");
        list1 .add(ItemName12);

        // 单行输入框
        OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName13 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        ItemName13.setName("物料形态");
        ItemName13.setValue(resources.getMaterialKenel()!=null?resources.getMaterialKenel():"");
        list1 .add(ItemName13);


        if(resources.getIsDemandConsolidation() != null){
            // 单选框
            OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName14 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
            ItemName14.setName("需求合并");
            ItemName14.setValue(resources.getIsDemandConsolidation()?"是":"否");
            list1 .add(ItemName14);
        }


        if(resources.getIsImaginaryTerm() != null) {
            // 单选框
            OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName15 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
            ItemName15.setName("虚项");
            ItemName15.setValue(resources.getIsImaginaryTerm()?"是":"否");
            list1 .add(ItemName15);
        }


        if(resources.getIsCostObject() != null){
            // 单选框
            OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName16 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
            ItemName16.setName("是否成本对象");
            ItemName16.setValue(resources.getIsCostObject()?"是":"否");
            list1 .add( ItemName16);
        }


        if(resources.getIsHairFeed() != null){
            //单选框
            OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName17 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
            ItemName17.setName("是否发料");
            ItemName17.setValue(resources.getIsHairFeed()?"是":"否");
            list1 .add( ItemName17);
        }

      if(resources.getIsInspectionWarehousing() != null){
          // 单选框
          OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName18 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
          ItemName18.setName("根据检验结果入库");
          ItemName18.setValue(resources.getIsInspectionWarehousing()?"是":"否");
          list1 .add(ItemName18);
      }

      if(resources.getIsInspect() != null){
          // 单选框
          OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName19 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
          ItemName19.setName("免检");
          ItemName19.setValue(resources.getIsInspect()?"是":"否");
          list1 .add(ItemName19);
      }

      if(resources.getIsOrderCost() != null){
          // 单选框
          OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName20 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
          ItemName20.setName("按生产订单核算成本");
          ItemName20.setValue(resources.getIsOrderCost()?"是":"否");
          list1 .add(ItemName20);
      }

      if(resources.getIsCenterStatistics() != null){
          // 单选框
          OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName21 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
          ItemName21.setName("按成本中心统计产量");
          ItemName21.setValue(resources.getIsCenterStatistics()?"是":"否");
          list1 .add(ItemName21);
      }

    if(resources.getIsOutgoingWarehousing() != null){
        // 单选框
        OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName22 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        ItemName22.setName("是否出入库");
        ItemName22.setValue(resources.getIsOutgoingWarehousing()?"是":"否");
        list1 .add(ItemName22);
    }


        // 单行输入框
        OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName23 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        ItemName23.setName("计价方式");
        ItemName23.setValue(resources.getValuationMethod()!=null?resources.getValuationMethod():"");
        list1 .add(ItemName23);


        // 单行输入框
        OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName24 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        ItemName24.setName("生产业务员");
        String id = SecurityUtils.getDingId();
        ItemName24.setValue("[\""+id+"\"]");
        list1 .add(ItemName24);


        // 单行输入框
        OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName25 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        ItemName25.setName("计划属性");
        ItemName25.setValue(resources.getPlanningAttribute()!=null?resources.getPlanningAttribute():"");
        list1 .add(ItemName25);


        // 单行输入框
        OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName26 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        ItemName26.setName("委外类型");
        ItemName26.setValue(resources.getOutsourcingType()!=null?resources.getOutsourcingType():"");
        list1 .add(ItemName26);


        // 单行输入框
        OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName27 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        ItemName27.setName("物料级别");
        ItemName27.setValue(resources.getMaterialLevel()!=null?resources.getMaterialLevel():"");
        list1 .add(ItemName27);


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
        return  list1;
    }



    public List getMaterialProductionChangeList(MaterialOperationRecord resources){
        return  null;
    }


































}
