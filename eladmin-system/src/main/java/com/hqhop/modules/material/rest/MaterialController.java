package com.hqhop.modules.material.rest;

import com.hqhop.aop.log.Log;
import com.hqhop.modules.material.domain.Material;
import com.hqhop.modules.material.domain.MaterialType;
import com.hqhop.modules.material.service.MaterialService;
import com.hqhop.modules.material.service.MaterialTypeService;
import com.hqhop.modules.material.service.dto.MaterialQueryCriteria;
import com.hqhop.modules.material.utils.PageBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
* @author KinLin
* @date 2019-10-30
*/
@Api(tags = "material-Controller物料管理")
@RestController
@RequestMapping("api")
public class MaterialController {

    @Autowired
    private MaterialService materialService;

    @Autowired
    private MaterialTypeService materialTypeService;

    @Log("查询Material")
    @ApiOperation(value = "查询Material")
    @GetMapping(value = "/material")
//    @PreAuthorize("hasAnyRole('ADMIN','MATERIAL_ALL','MATERIAL_SELECT')")
    public ResponseEntity getMaterials(MaterialQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity(materialService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @Log("根据物料类型查询物料")
    @ApiOperation(value = "根据物料类型查询物料")
    @GetMapping(value = "/getMaterialByTypeId")
//    @PreAuthorize("hasAnyRole('ADMIN','MATERIAL_ALL','MATERIAL_SELECT')")
    public ResponseEntity getMaterialsByTypeId(@RequestParam( value = "id",required = true) Long materialTypeId,@RequestParam( value = "page",required = true)Integer pageNo,
                                               @RequestParam(value = "rows",required = true)Integer pageSize){
        //先先将该分类的私有属性查出来
        MaterialType byId = materialTypeService.findById(materialTypeId);
        System.out.println(byId.getAttributes());

        List<Material> materials = materialService.queryAllByType(materialTypeId,(pageNo-1)*pageSize,pageSize);
        for (Material material :materials) {
            material.setType(byId);
        }

        PageBean pageBean = new PageBean();
        Integer count = materialService.getCountByTypeId(materialTypeId);
        Integer totalPages = (count + pageSize - 1)/pageSize;
        pageBean.setTotalPages(totalPages);
        pageBean.setCurrPage(pageNo);
        pageBean.setTotalElements(count);
        pageBean.setSize(pageSize);
        pageBean.setContent(materials);
        return new ResponseEntity(pageBean,HttpStatus.OK);
    }

    @Log("物料详情")
    @ApiOperation(value = "根據物料id查詢详情")
    @PostMapping(value = "/getMaterialById")
    //@PreAuthorize("hasAnyRole('ADMIN','MATERIAL_ALL','MATERIAL_CREATE')")
    public ResponseEntity detail(@RequestParam(value = "id",required = true) Long id){

        return new ResponseEntity(materialService.findById(id),HttpStatus.CREATED);
    }


    @Log("新增Material")
    @ApiOperation(value = "新增物料Material")
    @PostMapping(value = "/createMaterial")
    @PreAuthorize("hasAnyRole('ADMIN','MATERIAL_ALL','MATERIAL_CREATE')")
    public ResponseEntity create(@Validated @RequestBody Material resources){
        return new ResponseEntity(materialService.create(resources),HttpStatus.CREATED);
    }


    @Log("修改Material")
    @ApiOperation(value = "修改Material")
    @PutMapping(value = "/updateMaterial")
    @PreAuthorize("hasAnyRole('ADMIN','MATERIAL_ALL','MATERIAL_EDIT')")
    public ResponseEntity update(@Validated @RequestBody Material resources){
        materialService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除Material")
    @ApiOperation(value = "删除Material")
    @DeleteMapping(value = "/deleteMaterial")
//    @PreAuthorize("hasAnyRole('ADMIN','MATERIAL_ALL','MATERIAL_DELETE')")
    public ResponseEntity delete(@PathVariable Long id){
        materialService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}