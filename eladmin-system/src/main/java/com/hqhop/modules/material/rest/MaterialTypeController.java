package com.hqhop.modules.material.rest;

import com.hqhop.aop.log.Log;
import com.hqhop.modules.material.domain.Attribute;
import com.hqhop.modules.material.domain.MaterialType;
import com.hqhop.modules.material.service.AttributeService;
import com.hqhop.modules.material.service.MaterialTypeService;
import com.hqhop.modules.material.service.dto.MaterialTypeDTO;
import com.hqhop.modules.material.service.dto.MaterialTypeQueryCriteria;
import com.hqhop.modules.material.service.mapper.MaterialTypeMapper;
import com.hqhop.modules.material.service.vo.TypeAttributeVo;
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
import java.util.*;

/**
* @author KinLin
* @date 2019-10-30
*/
@Api(tags = "分类管理")
@RestController
@RequestMapping("api")
public class MaterialTypeController {

    @Autowired
    private MaterialTypeService materialTypeService;

    @Autowired
    private DataUtil dataUtil;

    @Autowired
    private MaterialTypeMapper materialTypeMapper;

    @Autowired
    private AttributeService attributeService;

    @Log("查询物料分类")
    @ApiOperation(value = "查询物料分类")
    @GetMapping(value = "/classify")
    //@PreAuthorize("hasAnyRole('ADMIN','USER_ALL','USER_SELECT','DEPT_ALL','DEPT_SELECT')")
    public ResponseEntity getMaterialTypes(MaterialTypeQueryCriteria criteria){
//         数据权限
        criteria.setIds(dataUtil.getMaterialTypeIds());

        List<MaterialTypeDTO> materialTypes = materialTypeService.queryAll(criteria);
        System.out.println(materialTypes);
        return new ResponseEntity(materialTypeService.buildTree(materialTypes),HttpStatus.OK);

    }
    @Log("增加物料小类")
    @ApiOperation(value = "增加物料小类")
    @GetMapping(value = "/addSmallType")
    //@PreAuthorize("hasAnyRole('ADMIN','USER_ALL','USER_SELECT','DEPT_ALL','DEPT_SELECT')")
    public ResponseEntity addSmallType(@Valid @RequestBody(required = true)MaterialTypeDTO materialTypeDTO){
        if(materialTypeDTO==null|| materialTypeDTO.getParentId()<=1){
            String msg = "数据错误，或请输入正确的小分类！";
            Map<String,Object> message = new HashMap<>();
            message.put("message",msg);
            return new ResponseEntity(message,HttpStatus.EXPECTATION_FAILED);
        }
        MaterialType materialType = materialTypeMapper.toEntity(materialTypeDTO);
        MaterialType materialType1 = materialTypeService.addSmallType(materialType);
        if(materialType1==null){
            return new ResponseEntity(HttpStatus.EXPECTATION_FAILED);
        }
        //進行其他功能
        return new ResponseEntity(materialType1,HttpStatus.OK);

    }
    @Log("增加物料大类")
    @ApiOperation(value = "增加物料大类")
    @GetMapping(value = "/addBigType")
    //@PreAuthorize("hasAnyRole('ADMIN','USER_ALL','USER_SELECT','DEPT_ALL','DEPT_SELECT')")
    public ResponseEntity addBigType( @Valid @RequestBody(required = true)MaterialTypeDTO materialTypeDTO){
        //如果父類大於1，則是小分類
        if(materialTypeDTO.getParentId()>1||materialTypeDTO==null){
            return new ResponseEntity(HttpStatus.EXPECTATION_FAILED);
        }
        MaterialType materialType1 = materialTypeMapper.toEntity(materialTypeDTO);

        MaterialType materialType = materialTypeService.addSmallType(materialType1);
        return new ResponseEntity(materialType1,HttpStatus.OK);
    }

    @Log("修改分类信息")
    @ApiOperation(value = "修改分类信息")
    @GetMapping(value = "/updateType")
    //@PreAuthorize("hasAnyRole('ADMIN','USER_ALL','USER_SELECT','DEPT_ALL','DEPT_SELECT')")
    public ResponseEntity updateType( @Valid @RequestBody(required = true)MaterialTypeDTO materialTypeDTO){
//        materialTypeService.update()
        return null;
    }


    @Log("给分类添加属性")
    @ApiOperation(value = "设置属性")
    @GetMapping(value = "/setAttributes")
    //@PreAuthorize("hasAnyRole('ADMIN','USER_ALL','USER_SELECT','DEPT_ALL','DEPT_SELECT')")
    public ResponseEntity addTypeAttribute( @Valid @RequestBody(required = true) TypeAttributeVo typeAttributeVo){
        List<Attribute> materialTypes = attributeService.findList(typeAttributeVo.getAttributes());
        MaterialType materialType = materialTypeService.findById(typeAttributeVo.getTypeId());
        Set<Attribute> set=new HashSet<Attribute>(materialTypes);
        materialType.setAttributes(set);
        MaterialType update = materialTypeService.update(materialType);
        return new ResponseEntity(update,HttpStatus.OK);
    }


    @Log("给分类取消属性")
    @ApiOperation(value = "取消属性")
    @GetMapping(value = "/deleteAttributes")
    //@PreAuthorize("hasAnyRole('ADMIN','USER_ALL','USER_SELECT','DEPT_ALL','DEPT_SELECT')")
    public ResponseEntity deleteTypeAttribute( @Valid @RequestBody(required = true) TypeAttributeVo typeAttributeVo){
        List<Attribute> materialAttributes = attributeService.findList(typeAttributeVo.getAttributes());
        MaterialType materialType = materialTypeService.findById(typeAttributeVo.getTypeId());
        Set<Attribute> set=new HashSet<Attribute>(materialAttributes);
        //去除原有的属性
        materialType.getAttributes().removeAll(set);

        MaterialType update = materialTypeService.update(materialType);
        return new ResponseEntity(update,HttpStatus.OK);
    }

}