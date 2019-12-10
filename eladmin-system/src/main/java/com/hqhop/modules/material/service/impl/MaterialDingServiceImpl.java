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
import com.hqhop.modules.material.domain.Accessory;
import com.hqhop.modules.material.domain.Material;
import com.hqhop.modules.material.domain.MaterialOperationRecord;
import com.hqhop.modules.material.repository.MaterialOperationRecordRepository;
import com.hqhop.modules.material.repository.MaterialRepository;
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

import java.sql.Timestamp;
import java.util.*;

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




    //物料新增审批
    @Override
    public void addApprovel(MaterialOperationRecord resources) throws
            ApiException {

        UserDTO userDTO = userService.findByName("admin");
        //1 基础档案新增 2 基础档案修改 ....更多对照字典
        resources.setOperationType("1");
        resources.setCreator(userDTO.getEmployee().getEmployeeName());
        resources.setCreateTime(new Timestamp(new Date().getTime()));
        resources.setUserId(userDTO.getEmployee().getDingId());
        OapiProcessinstanceCreateResponse response = getApprovalResponse(resources,userDTO);

        if(response.getErrcode() == 0L){
            Material material = resources.getMaterial();
            //1 新增状态 2 新增审批中 3 驳回 4 审批通过....更多对照字典
            material.setApprovalState("2");
           Material material1 = materialRepository.save(material);

           resources.setId(material.getId());
            resources.setApproveResult("未知");
            materialOperationRecordRepository.save(resources);
        }
    }

    //物料新增审批通过
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
           materialRepository.save(material);

        }



    }















































    public OapiProcessinstanceCreateResponse getApprovalResponse(MaterialOperationRecord resources,UserDTO userDTO)throws
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
        input.setValue(dictDetailService.getDicLabel("material_operation_type",resources.getOperationType()));
        listForm.add(input);
        listForm.addAll(getMaterialList(resources));
        request.setFormComponentValues(listForm);
        request.setOriginatorUserId(userDTO.getEmployee().getDingId());
        request.setDeptId(userDTO.getDeptId());
        OapiProcessinstanceCreateResponse response = client.execute(request,DingTalkUtils.getAccessToken());
        return  response;
    }



    public List getMaterialList(MaterialOperationRecord resources){
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
        ItemName6.setValue(resources.getCreator()!=null?resources.getCreator():"");
        list1.add(ItemName6);

        return  list1;
    }






}
