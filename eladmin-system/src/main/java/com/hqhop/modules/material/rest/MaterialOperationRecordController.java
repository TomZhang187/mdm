package com.hqhop.modules.material.rest;

import com.hqhop.aop.log.Log;
import com.hqhop.modules.material.domain.Material;
import com.hqhop.modules.material.domain.MaterialOperationRecord;
import com.hqhop.modules.material.service.MaterialOperationRecordService;
import com.hqhop.modules.material.service.dto.MaterialOperationRecordQueryCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;

/**
* @author zf
* @date 2019-12-09
*/
@Api(tags = "MaterialOperationRecord管理")
@RestController
@RequestMapping("api")
public class MaterialOperationRecordController {

    @Autowired
    private MaterialOperationRecordService materialOperationRecordService;

    @Log("查询MaterialOperationRecord")
    @ApiOperation(value = "查询MaterialOperationRecord")
    @GetMapping(value = "/materialOperationRecord")
//    @PreAuthorize("hasAnyRole('ADMIN','MATERIALOPERATIONRECORD_ALL','MATERIALOPERATIONRECORD_SELECT')")
    public ResponseEntity getMaterialOperationRecords(MaterialOperationRecordQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity(materialOperationRecordService.queryAll(criteria,pageable),HttpStatus.OK);
    }


    @Log("新增MaterialOperationRecord")
    @ApiOperation(value = "新增MaterialOperationRecord")
    @PostMapping(value = "/materialOperationRecord")
    @PreAuthorize("hasAnyRole('ADMIN','MATERIALOPERATIONRECORD_ALL','MATERIALOPERATIONRECORD_CREATE')")
    public ResponseEntity create(@Validated @RequestBody MaterialOperationRecord resources){
        return new ResponseEntity(materialOperationRecordService.create(resources),HttpStatus.CREATED);
    }


    @Log("修改MaterialOperationRecord")
    @ApiOperation(value = "修改MaterialOperationRecord")
    @PutMapping(value = "/materialOperationRecord")
    @PreAuthorize("hasAnyRole('ADMIN','MATERIALOPERATIONRECORD_ALL','MATERIALOPERATIONRECORD_EDIT')")
    public ResponseEntity update(@Validated @RequestBody MaterialOperationRecord resources){
        materialOperationRecordService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除MaterialOperationRecord")
    @ApiOperation(value = "删除MaterialOperationRecord")
    @DeleteMapping(value = "/materialOperationRecord/{key}")
    @PreAuthorize("hasAnyRole('ADMIN','MATERIALOPERATIONRECORD_ALL','MATERIALOPERATIONRECORD_DELETE')")
    public ResponseEntity delete(@PathVariable Long key){
        materialOperationRecordService.delete(key);
        return new ResponseEntity(HttpStatus.OK);
    }


    @Log("获取改物料基本档案审批链接getDingUrl")
    @ApiOperation(value = "获取改物料基本档案审批链接getDingUrl")
    @GetMapping(value = "/getMaterialDingUrl")
//    @PreAuthorize("hasAnyRole('ADMIN','COMPANYUPDATE_ALL','COMPANYUPDATE_CREATE')")
    public ResponseEntity getMaterialDingUrl(Material material){

        return new ResponseEntity(materialOperationRecordService.getDingUrl(material),HttpStatus.OK);
    }


}
