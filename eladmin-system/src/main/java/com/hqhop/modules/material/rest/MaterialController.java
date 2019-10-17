package com.hqhop.modules.material.rest;

import me.zhengjie.aop.log.Log;
import com.hqhop.modules.material.domain.Material;
import com.hqhop.modules.material.service.MaterialService;
import com.hqhop.modules.material.service.dto.MaterialQueryCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;

/**
* @author chengy
* @date 2019-10-17
*/
@Api(tags = "Material管理")
@RestController
@RequestMapping("api")
public class MaterialController {

    @Autowired
    private MaterialService materialService;

    @Log("查询Material")
    @ApiOperation(value = "查询Material")
    @GetMapping(value = "/material")
    @PreAuthorize("hasAnyRole('ADMIN','MATERIAL_ALL','MATERIAL_SELECT')")
    public ResponseEntity getMaterials(MaterialQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity(materialService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @Log("新增Material")
    @ApiOperation(value = "新增Material")
    @PostMapping(value = "/material")
    @PreAuthorize("hasAnyRole('ADMIN','MATERIAL_ALL','MATERIAL_CREATE')")
    public ResponseEntity create(@Validated @RequestBody Material resources){
        return new ResponseEntity(materialService.create(resources),HttpStatus.CREATED);
    }

    @Log("修改Material")
    @ApiOperation(value = "修改Material")
    @PutMapping(value = "/material")
    @PreAuthorize("hasAnyRole('ADMIN','MATERIAL_ALL','MATERIAL_EDIT')")
    public ResponseEntity update(@Validated @RequestBody Material resources){
        materialService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除Material")
    @ApiOperation(value = "删除Material")
    @DeleteMapping(value = "/material/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','MATERIAL_ALL','MATERIAL_DELETE')")
    public ResponseEntity delete(@PathVariable Integer id){
        materialService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}