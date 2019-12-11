package com.hqhop.modules.material.rest;

import com.hqhop.aop.log.Log;
import com.hqhop.modules.material.domain.MaterialProduction;
import com.hqhop.modules.material.service.MaterialProductionService;
import com.hqhop.modules.material.service.dto.MaterialProductionQueryCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;

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
}
