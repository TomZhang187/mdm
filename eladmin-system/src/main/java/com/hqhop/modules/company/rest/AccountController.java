package com.hqhop.modules.company.rest;

import com.hqhop.aop.log.Log;
import com.hqhop.config.dingtalk.dingtalkVo.DingUser;
import com.hqhop.modules.company.domain.Account;
import com.hqhop.modules.company.repository.AccountRepository;
import com.hqhop.modules.company.service.AccountDingService;
import com.hqhop.modules.company.service.AccountService;
import com.hqhop.modules.company.service.dto.AccountQueryCriteria;
import com.taobao.api.ApiException;
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
    @Autowired
    private AccountDingService accountDingService;
    @Autowired
    private AccountRepository accountRepository;

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

    @Log("客商账户审批接口")
    @ApiOperation(value = "客商账户审批接口")
    @GetMapping(value = "/accountApproval")
//    @PreAuthorize("hasAnyRole('ADMIN','COMPANYINFO_ALL','COMPANYINFO_SELECT')")
    public ResponseEntity accountApproval(DingUser dingUser, Account resouces)throws
            ApiException {



        if (resouces.getAccountKey() != null && !"".equals(resouces.getAccountKey()) && resouces.getAccountState()==4 ) {

            if(resouces.getAccountName() != null && !"".equals(resouces.getAccountName())){
                //修改审批调用
             accountDingService.updateApproval(resouces,dingUser);
            }else {
                //解除绑定审批
               Account account =accountRepository.getOne(resouces.getAccountKey());
                accountDingService.removeApproval(account,dingUser);
            }
        } else {
            //新增审批调用
            accountDingService.addApprovel(resouces,dingUser);
        }

        return new ResponseEntity(HttpStatus.OK);
    }


    @Log("获取账户审批链接getDingUrl")
    @ApiOperation(value = "获取账户审批链接getDingUrl")
    @GetMapping(value = "/accountDingUrl")
//    @PreAuthorize("hasAnyRole('ADMIN','COMPANYUPDATE_ALL','COMPANYUPDATE_CREATE')")
    public ResponseEntity getContactDingUrl(Account resources,DingUser dingUser){

        return new ResponseEntity(accountService.getDingUrl(resources,dingUser),HttpStatus.CREATED);
    }
}
