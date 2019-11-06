package com.hqhop.modules.material.rest;

import com.hqhop.aop.log.Log;
import com.hqhop.modules.material.domain.MaterialType;
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
    public ResponseEntity addBigType( MaterialTypeDTO materialTypeDTO){
        //如果父類大於1，則是小分類
        if(materialTypeDTO.getParentId()>1||materialTypeDTO==null){
            return new ResponseEntity(HttpStatus.EXPECTATION_FAILED);
        }
        MaterialType materialType1 = materialTypeMapper.toEntity(materialTypeDTO);

        MaterialType materialType = materialTypeService.addSmallType(materialType1);
        return new ResponseEntity(materialType1,HttpStatus.OK);

    }
}