package com.hqhop.modules.material.rest;

import com.hqhop.aop.log.Log;
import com.hqhop.modules.material.domain.Attribute;
import com.hqhop.modules.material.domain.MaterialType;
import com.hqhop.modules.material.service.AttributeService;
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
    @GetMapping(value = "/getMaterialAttribute")
    //@PreAuthorize("hasAnyRole('ADMIN','USER_ALL','USER_SELECT','DEPT_ALL','DEPT_SELECT')")
    public ResponseEntity getMaterialAttribute(@RequestParam(value = "id",required = true)Long id){

        List<Attribute> attributes = attributeService.queryAllByMaterialType(id);

        return new ResponseEntity(attributes,HttpStatus.OK);
    }


    @Log("新增某种属性")
    @ApiOperation(value = "新增物料属性")
    @PostMapping(value = "/addMaterialAttribute")
    //@PreAuthorize("hasAnyRole('ADMIN','USER_ALL','USER_SELECT','DEPT_ALL','DEPT_SELECT')")
    public ResponseEntity addAttribute(@RequestBody(required = true)Attribute attribute){
        Attribute attribute1 = attributeService.addAttribute(attribute);
        if(attribute1==null){
            return new ResponseEntity("该属性已经存在！",HttpStatus.EXPECTATION_FAILED);
        }
        return new ResponseEntity(attribute1,HttpStatus.OK);
    }


    @Log("修改物料属性")
    @ApiOperation(value = "修改物料属性")
    @PutMapping(value = "/updateAttribute")
    //@PreAuthorize("hasAnyRole('ADMIN','USER_ALL','USER_SELECT','DEPT_ALL','DEPT_SELECT')")
    public ResponseEntity updateAttribute(@Validated @RequestBody MaterialType materialType){
        return null;
    }
}