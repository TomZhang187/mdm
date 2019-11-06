package com.hqhop.modules.material.rest;

import com.hqhop.aop.log.Log;
import com.hqhop.modules.material.domain.Attribute;
import com.hqhop.modules.material.domain.MaterialType;
import com.hqhop.modules.material.service.AttributeService;
import com.hqhop.modules.material.service.MaterialTypeService;
import com.hqhop.modules.material.service.dto.MaterialTypeDTO;
import com.hqhop.modules.material.service.dto.MaterialTypeQueryCriteria;
import com.hqhop.modules.material.service.mapper.MaterialTypeMapper;
import com.hqhop.modules.material.utils.DataUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public ResponseEntity getMaterialAttribute(Long id){

        List<Attribute> attributes = attributeService.queryAllByMaterialType(id);

        return new ResponseEntity(attributes,HttpStatus.OK);
    }

}