package com.hqhop.modules.material.rest;

import com.hqhop.aop.log.Log;
import com.hqhop.modules.material.domain.Attribute;
import com.hqhop.modules.material.domain.MaterialType;
import com.hqhop.modules.material.service.AttributeService;
import com.hqhop.modules.material.service.vo.AttributeTypeVo;
import com.hqhop.modules.material.service.vo.AttributeVo;
import com.mchange.v1.util.ListUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author KinLin
 * @date 2019-10-30
 */
@Api(tags = "属性配置")
@RestController
@RequestMapping("api")
public class AttributeController {

    @Autowired
    private AttributeService attributeService;

    @Log("物料分类属性")
    @ApiOperation(value = "物料属性")
    @GetMapping(value = "/getMaterialAttributes")
    //@PreAuthorize("hasAnyRole('ADMIN','USER_ALL','USER_SELECT','DEPT_ALL','DEPT_SELECT')")
    public ResponseEntity getMaterialAttributes(@RequestParam(value = "id", required = true) Long id) {
        List<Attribute> attributes = attributeService.queryAllByMaterialType(id);

        return new ResponseEntity(attributes, HttpStatus.OK);
    }


    @Log("新增某种属性")
    @ApiOperation(value = "新增物料属性")
    @PostMapping(value = "/addMaterialAttribute")
    //@PreAuthorize("hasAnyRole('ADMIN','USER_ALL','USER_SELECT','DEPT_ALL','DEPT_SELECT')")
    public ResponseEntity addAttribute(@RequestBody(required = true) Attribute attribute) {
        Attribute attribute1 = attributeService.addAttribute(attribute);
        if (attribute1 == null) {
            return new ResponseEntity("该属性已经存在！", HttpStatus.EXPECTATION_FAILED);
        }
        return new ResponseEntity(attribute1, HttpStatus.OK);
    }


    @Log("修改物料属性")
    @ApiOperation(value = "修改物料属性")
    @PutMapping(value = "/updateAttribute")
    //@PreAuthorize("hasAnyRole('ADMIN','USER_ALL','USER_SELECT','DEPT_ALL','DEPT_SELECT')")
    public ResponseEntity updateAttribute(@Validated @RequestBody AttributeVo attributeVo) {
        System.out.println(attributeVo);
        attributeService.updateAttribute(attributeVo);
        return new ResponseEntity(attributeVo, HttpStatus.OK);
    }


    @Log("删除类型属性")
    @ApiOperation(value = "删除类型属性")
    @GetMapping(value = "/attributeDelete")
    //@PreAuthorize("hasAnyRole('ADMIN','USER_ALL','USER_SELECT','DEPT_ALL','DEPT_SELECT')")
    public ResponseEntity attributeDelete(@RequestParam(value = "attributeId", required = true) Long attributeId, @RequestParam(value = "id", required = true) Long id) {
        attributeService.attributeDelete(id, attributeId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @Log("获取全部类型属性")
    @ApiOperation(value = "获取全部类型属性")
    @GetMapping(value = "/attributeAll")
    //@PreAuthorize("hasAnyRole('ADMIN','USER_ALL','USER_SELECT','DEPT_ALL','DEPT_SELECT')")
    public ResponseEntity findAll() {
        List<Attribute> all = attributeService.findAll();
        return new ResponseEntity(all, HttpStatus.OK);
    }

    @Log("删除指定属性")
    @ApiOperation(value = "删除指定属性")
    @DeleteMapping(value = "/deleteAttribute/{id}")
    //@PreAuthorize("hasAnyRole('ADMIN','USER_ALL','USER_SELECT','DEPT_ALL','DEPT_SELECT')")
    public ResponseEntity deleteAttribute(@PathVariable Long id) {
        attributeService.deleteOne(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @Log("物料分类属性")
    @ApiOperation(value = "物料属性")
    @GetMapping(value = "/getMaterialAttribute")
    //@PreAuthorize("hasAnyRole('ADMIN','USER_ALL','USER_SELECT','DEPT_ALL','DEPT_SELECT')")
    public ResponseEntity getMaterialAttribute(@RequestParam(value = "id", required = true) Long id) {

        List<Attribute> attributes = attributeService.queryAllByMaterialId(id);

        return new ResponseEntity(attributes, HttpStatus.OK);
    }

    @Log("新增属性")
    @ApiOperation(value = "新增物料属性")
    @PostMapping(value = "/addAttribute")
    //@PreAuthorize("hasAnyRole('ADMIN','USER_ALL','USER_SELECT','DEPT_ALL','DEPT_SELECT')")
    public ResponseEntity addAttributes(@RequestBody(required = true) Attribute attribute) {
        Attribute attribute1 = attributeService.findAttributeByAttributeName(attribute.getAttributeName());
        if (attribute1 != null) {
            return new ResponseEntity("该属性已经存在！", HttpStatus.EXPECTATION_FAILED);
        }
        Attribute attribute2 = attributeService.addAttribute(attribute);
        return new ResponseEntity(attribute2, HttpStatus.OK);
    }

    @Log("新增类型属性")
    @ApiOperation(value = "新增物料属性")
    @PostMapping(value = "/addTypeAttribute")
    //@PreAuthorize("hasAnyRole('ADMIN','USER_ALL','USER_SELECT','DEPT_ALL','DEPT_SELECT')")
    public ResponseEntity addAttributes(@RequestBody(required = true) AttributeTypeVo attributeTypeVo) {
        System.out.println(attributeTypeVo);
        Attribute attribute = attributeService.findAttributeByAttributeIdAndMaterialsTypeId(attributeTypeVo.getAttributeId(), attributeTypeVo.getTypeId());
        if (attribute == null) {
            attributeService.setTypeAttribute(attributeTypeVo.getAttributeId(), attributeTypeVo.getTypeId());
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity("该属性已经存在！", HttpStatus.EXPECTATION_FAILED);
    }


}