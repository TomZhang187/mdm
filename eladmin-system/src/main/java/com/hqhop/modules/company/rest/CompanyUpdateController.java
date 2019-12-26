package com.hqhop.modules.company.rest;

import com.hqhop.aop.log.Log;
import com.hqhop.config.dingtalk.dingtalkVo.DingUser;
import com.hqhop.modules.company.domain.CompanyUpdate;
import com.hqhop.modules.company.service.CompanyUpdateService;
import com.hqhop.modules.company.service.dto.CompanyUpdateQueryCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;

import java.util.List;

/**
* @author zf
* @date 2019-11-07
*/
@Api(tags = "CompanyUpdate管理")
@RestController
@RequestMapping("api")
public class CompanyUpdateController {

    @Autowired
    private CompanyUpdateService companyUpdateService;

    @Log("查询CompanyUpdate")
    @ApiOperation(value = "查询CompanyUpdate")
    @GetMapping(value = "/companyUpdate")
//    @PreAuthorize("hasAnyRole('ADMIN','COMPANYUPDATE_ALL','COMPANYUPDATE_SELECT')")
    public ResponseEntity getCompanyUpdates(CompanyUpdateQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity(companyUpdateService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @Log("新增CompanyUpdate")
    @ApiOperation(value = "新增CompanyUpdate")
    @PostMapping(value = "/companyUpdate")
    @PreAuthorize("hasAnyRole('ADMIN','COMPANYUPDATE_ALL','COMPANYUPDATE_CREATE')")
    public ResponseEntity create(@Validated @RequestBody CompanyUpdate resources){
        return new ResponseEntity(companyUpdateService.create(resources),HttpStatus.CREATED);
    }

    @Log("修改CompanyUpdate")
    @ApiOperation(value = "修改CompanyUpdate")
    @PutMapping(value = "/companyUpdate")
    @PreAuthorize("hasAnyRole('ADMIN','COMPANYUPDATE_ALL','COMPANYUPDATE_EDIT')")
    public ResponseEntity update(@Validated @RequestBody CompanyUpdate resources){
        companyUpdateService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除CompanyUpdate")
    @ApiOperation(value = "删除CompanyUpdate")
    @DeleteMapping(value = "/companyUpdate/{operateKey}")
    @PreAuthorize("hasAnyRole('ADMIN','COMPANYUPDATE_ALL','COMPANYUPDATE_DELETE')")
    public ResponseEntity delete(@PathVariable Long operateKey){
        companyUpdateService.delete(operateKey);
        return new ResponseEntity(HttpStatus.OK);
    }


    @Log("查询该客商的CompanyUpdate")
    @ApiOperation(value = "查询该客商的CompanyUpdate")
    @GetMapping(value = "/findByCompanyKey")
//    @PreAuthorize("hasAnyRole('ADMIN','COMPANYUPDATE_ALL','COMPANYUPDATE_SELECT')")
    public ResponseEntity getByCompanyKey(Long companyKey,String state,String userId,String name){
        return new ResponseEntity(companyUpdateService.loadUpdateRecord(companyKey,state,userId,name),HttpStatus.OK);
    }



    @Log("新增该客商的CompanyUpdate")
    @ApiOperation(value = "新增该客商的CompanyUpdate")
    @GetMapping(value = "/saveCompamyUpdate")
//    @PreAuthorize("hasAnyRole('ADMIN','COMPANYUPDATE_ALL','COMPANYUPDATE_CREATE')")
    public ResponseEntity save(CompanyUpdate resources, DingUser dingUser){

//        System.out.println("CompanyKey"+resources.getCompanyKey());
//        System.out.println("用户ID"+dingUser.getUserid());
//        System.out.println("用户名"+dingUser.getName());

        return new ResponseEntity(companyUpdateService.saveCompanyUpdate(resources,dingUser),HttpStatus.CREATED);
    }


    @Log("获取改客商审批链接getDingUrl")
    @ApiOperation(value = "获取改客商审批链接getDingUrl")
    @GetMapping(value = "/getDingUrl")
//    @PreAuthorize("hasAnyRole('ADMIN','COMPANYUPDATE_ALL','COMPANYUPDATE_CREATE')")
    public ResponseEntity getDingUrl(CompanyUpdate resources,DingUser dingUser){

        return new ResponseEntity(companyUpdateService.getDingUrl(resources,dingUser),HttpStatus.OK);
    }

}
