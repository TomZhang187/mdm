package com.hqhop.modules.company.rest;

import com.hqhop.aop.log.Log;
import com.hqhop.modules.company.domain.CompanyInfo;
import com.hqhop.modules.company.service.CompanyInfoService;
import com.hqhop.modules.company.service.dto.CompanyInfoDTO;
import com.hqhop.modules.company.service.dto.CompanyInfoQueryCriteria;




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
* @date 2019-10-22
*/
@Api(tags = "CompanyInfo管理")
@RestController
@RequestMapping("api")
public class CompanyInfoController {

    @Autowired
    private CompanyInfoService companyInfoService;

    @Log("查询CompanyInfo")
    @ApiOperation(value = "查询CompanyInfo")
    @GetMapping(value = "/companyInfo")
//    @PreAuthorize("hasAnyRole('ADMIN','COMPANYINFO_ALL','COMPANYINFO_SELECT')")
    public ResponseEntity getCompanyInfos(CompanyInfoQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity(companyInfoService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @Log("保存和修改接口CompanyInfo")
    @ApiOperation(value = "保存和修改接口CompanyInfo")
    @PostMapping(value = "/companyInfo")
//    @PreAuthorize("hasAnyRole('ADMIN','COMPANYINFO_ALL','COMPANYINFO_CREATE')")
    public ResponseEntity create(@Validated @RequestBody CompanyInfo resources){
        return new ResponseEntity(companyInfoService.createAndUpadte(resources),HttpStatus.CREATED);
    }

    @Log("修改CompanyInfo")
    @ApiOperation(value = "修改CompanyInfo")
    @PutMapping(value = "/companyInfo")
    @PreAuthorize("hasAnyRole('ADMIN','COMPANYINFO_ALL','COMPANYINFO_EDIT')")
    public ResponseEntity update(@Validated @RequestBody CompanyInfo resources){
        companyInfoService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除CompanyInfo")
    @ApiOperation(value = "删除CompanyInfo")
    @DeleteMapping(value = "/companyInfo/{companyKey}")
    @PreAuthorize("hasAnyRole('ADMIN','COMPANYINFO_ALL','COMPANYINFO_DELETE')")
    public ResponseEntity delete(@PathVariable Long companyKey){
        companyInfoService.delete(companyKey);
        return new ResponseEntity(HttpStatus.OK);
    }


    @Log("添加之前客商验证VerifyAdd")
    @ApiOperation(value = " 添加之前客商验证VerifyAdd")
    @GetMapping(value = "/VerifyAdd")
//    @PreAuthorize("hasAnyRole('ADMIN','COMPANYINFO_ALL','COMPANYINFO_SELECT')")
    public ResponseEntity VerifyResult(CompanyInfoDTO resources){
        return new ResponseEntity(companyInfoService.VerifyAdd(resources),HttpStatus.OK);
    }


}