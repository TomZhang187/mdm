package com.hqhop.modules.company.rest;

import com.hqhop.aop.log.Log;
import com.hqhop.modules.company.domain.Account;
import com.hqhop.modules.company.service.AccountService;
import com.hqhop.modules.company.service.dto.AccountQueryCriteria;
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
* @date 2019-11-06
*/
@Api(tags = "Account管理")
@RestController
@RequestMapping("api")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Log("查询Account")
    @ApiOperation(value = "查询Account")
    @GetMapping(value = "/account")
//    @PreAuthorize("hasAnyRole('ADMIN','ACCOUNT_ALL','ACCOUNT_SELECT')")
    public ResponseEntity getAccounts(AccountQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity(accountService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @Log("新增Account")
    @ApiOperation(value = "新增Account")
    @PostMapping(value = "/account")
    @PreAuthorize("hasAnyRole('ADMIN','ACCOUNT_ALL','ACCOUNT_CREATE')")
    public ResponseEntity create(@Validated @RequestBody Account resources){
        return new ResponseEntity(accountService.create(resources),HttpStatus.CREATED);
    }

    @Log("修改Account")
    @ApiOperation(value = "修改Account")
    @PutMapping(value = "/account")
    @PreAuthorize("hasAnyRole('ADMIN','ACCOUNT_ALL','ACCOUNT_EDIT')")
    public ResponseEntity update(@Validated @RequestBody Account resources){
        accountService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除Account")
    @ApiOperation(value = "删除Account")
    @DeleteMapping(value = "/account/{accountKey}")
    @PreAuthorize("hasAnyRole('ADMIN','ACCOUNT_ALL','ACCOUNT_DELETE')")
    public ResponseEntity delete(@PathVariable Long accountKey){
        accountService.delete(accountKey);
        return new ResponseEntity(HttpStatus.OK);
    }
}
