package com.hqhop.modules.company.rest;

import com.hqhop.aop.log.Log;
import com.hqhop.modules.company.domain.CompanyBasic;
import com.hqhop.modules.company.service.CompanyBasicService;
import com.hqhop.modules.company.service.dto.CompanyBasicQueryCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;

/**
* @author lyl
* @date 2020-01-01
*/
@Api(tags = "CompanyBasic管理")
@RestController
@RequestMapping("api")
public class CompanyBasicController {

    @Autowired
    private CompanyBasicService companyBasicService;

    @Log("查询CompanyBasic")
    @ApiOperation(value = "查询CompanyBasic")
    @GetMapping(value = "/companyBasic")
    @PreAuthorize("hasAnyRole('ADMIN','COMPANYBASIC_ALL','COMPANYBASIC_SELECT')")
    public ResponseEntity getCompanyBasics(CompanyBasicQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity(companyBasicService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @Log("新增CompanyBasic")
    @ApiOperation(value = "新增CompanyBasic")
    @PostMapping(value = "/companyBasic")
    @PreAuthorize("hasAnyRole('ADMIN','COMPANYBASIC_ALL','COMPANYBASIC_CREATE')")
    public ResponseEntity create(@Validated @RequestBody CompanyBasic resources){
        return new ResponseEntity(companyBasicService.create(resources),HttpStatus.CREATED);
    }

    @Log("修改CompanyBasic")
    @ApiOperation(value = "修改CompanyBasic")
    @PutMapping(value = "/companyBasic")
    @PreAuthorize("hasAnyRole('ADMIN','COMPANYBASIC_ALL','COMPANYBASIC_EDIT')")
    public ResponseEntity update(@Validated @RequestBody CompanyBasic resources){
        companyBasicService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除CompanyBasic")
    @ApiOperation(value = "删除CompanyBasic")
    @DeleteMapping(value = "/companyBasic/{key}")
    @PreAuthorize("hasAnyRole('ADMIN','COMPANYBASIC_ALL','COMPANYBASIC_DELETE')")
    public ResponseEntity delete(@PathVariable Long key){
        companyBasicService.delete(key);
        return new ResponseEntity(HttpStatus.OK);
    }
}
