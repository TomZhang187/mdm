package com.hqhop.modules.material.rest;

import com.hqhop.aop.log.Log;
import com.hqhop.easyexcel.excelRead.MaterialExcelUtils;
import com.hqhop.easyexcel.model.IncMaterial;
import com.hqhop.exception.BadRequestException;
import com.hqhop.modules.material.domain.Material;
import com.hqhop.modules.material.domain.MaterialOperationRecord;
import com.hqhop.modules.material.domain.MaterialProduction;
import com.hqhop.modules.material.domain.MaterialType;
import com.hqhop.modules.material.service.MaterialProducionDingService;
import com.hqhop.modules.material.service.MaterialProductionService;
import com.hqhop.modules.material.service.dto.MaterialProductionQueryCriteria;
import com.hqhop.modules.material.service.impl.MaterialProductionDingServiceImpl;
import com.taobao.api.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;

import java.util.List;

/**
* @author wst
* @date 2019-11-26
*/
@Api(tags = "MaterialProduction管理")
@RestController
@RequestMapping("api")
public class MaterialProductionController {

    @Autowired
    private MaterialProductionService materialProductionService;

   @Autowired
    private MaterialProducionDingService materialProductionDingService;

    @Log("查询MaterialProduction")
    @ApiOperation(value = "查询MaterialProduction")
    @GetMapping(value = "/materialProduction")
    @PreAuthorize("hasAnyRole('ADMIN','MATERIALPRODUCTION_ALL','MATERIALPRODUCTION_SELECT')")
    public ResponseEntity getMaterialProductions(MaterialProductionQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity(materialProductionService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @Log("新增MaterialProduction")
    @ApiOperation(value = "新增MaterialProduction")
    @PostMapping(value = "/materialProduction")
    @PreAuthorize("hasAnyRole('ADMIN','MATERIALPRODUCTION_ALL','MATERIALPRODUCTION_CREATE')")
    public ResponseEntity create(@Validated @RequestBody MaterialProduction resources){
        return new ResponseEntity(materialProductionService.create(resources),HttpStatus.CREATED);
    }

    @Log("修改MaterialProduction")
    @ApiOperation(value = "修改MaterialProduction")
    @PutMapping(value = "/materialProduction")
    @PreAuthorize("hasAnyRole('ADMIN','MATERIALPRODUCTION_ALL','MATERIALPRODUCTION_EDIT')")
    public ResponseEntity update(@Validated @RequestBody MaterialProduction resources){
        materialProductionService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除MaterialProduction")
    @ApiOperation(value = "删除MaterialProduction")
    @DeleteMapping(value = "/materialProduction/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','MATERIALPRODUCTION_ALL','MATERIALPRODUCTION_DELETE')")
    public ResponseEntity delete(@PathVariable Integer id){
        materialProductionService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @Log("同步物料数据")
    @ApiOperation(value = "同步物料数据")
    @GetMapping(value = "/materialProduction/sync/{id}")
//    @PreAuthorize("hasAnyRole('ADMIN','MATERIALPRODUCTION_ALL','MATERIALPRODUCTION_SYNC')")
    public ResponseEntity sysnToU8Cloud(@PathVariable Integer id){
        materialProductionService.sysnToU8Cloud(id);
        return new ResponseEntity(HttpStatus.OK);
    }


    @Log("加载当前用户可选的默认工厂集合getUserDefaultFactory")
    @ApiOperation(value = "加载当前用户可选的默认工厂集合getUserDefaultFactory")
    @PostMapping(value = "/getUserDefaultFactory")
//    @PreAuthorize("hasAnyRole('ADMIN','MATERIALPRODUCTION_ALL','MATERIALPRODUCTION_DELETE')")
    public ResponseEntity getUserDefaultFactory(){
        return new ResponseEntity(materialProductionService.getUserDefaultFactory(),HttpStatus.OK);
    }

    @Log("物料生产档案审批接口")
    @ApiOperation(value = "物料生产档案审批接口")
    @PostMapping(value = "/materialProductionApproval")
//    @PreAuthorize("hasAnyRole('ADMIN','COMPANYINFO_ALL','COMPANYINFO_SELECT')")
    public ResponseEntity doApproval(@Validated @RequestBody MaterialProduction resources)throws
            ApiException {

        if (resources.getId() != null && !"".equals(resources.getId()) && "4".equals(resources.getApprovalState())){

            //变更审批调用
            MaterialOperationRecord record = materialProductionService.approvalCreate(resources);
            materialProductionDingService.updateApprovel(record);

        } else if(resources.getId() == null || !"4".equals(resources.getApprovalState() ) && resources.getApprovalState()!=null) {

            //新增审批调用
            MaterialOperationRecord record = new MaterialOperationRecord();
                     record.getDataByMateriaProduction(resources);
                     if(resources.getEnable() == null){
                         record.setEnable(true);
                     }
                     record.setMaterialId(resources.getMaterial().getId());
                 materialProductionDingService.addApprovel(record);

        } else if(resources.getApprovalState() ==null && resources.getId() != null){
             //停用 /启用审批调用
                materialProductionDingService.isAbleApproval(resources.getId());
        }
        return new ResponseEntity(HttpStatus.OK);
    }


    @Log("获取当前用户临时修改数据")
    @ApiOperation(value = "获取当前用户临时修改数据")
    @PostMapping(value = "/getProductionTemporaryData")
//    @PreAuthorize("hasAnyRole('ADMIN','COMPANYINFO_ALL','COMPANYINFO_SELECT')")
    public ResponseEntity  getTemporaryData(@Validated @RequestBody MaterialProduction resources)throws
            ApiException {

        return new ResponseEntity(materialProductionService.getTemporaryData(resources),HttpStatus.OK);
    }


}





