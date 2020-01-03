package com.hqhop.modules.company.rest;

import com.dingtalk.api.response.OapiUserGetResponse;
import com.hqhop.aop.log.Log;
import com.hqhop.common.dingtalk.DingTalkUtils;
import com.hqhop.common.dingtalk.dingtalkVo.DingUser;
import com.hqhop.modules.company.domain.CompanyInfo;
import com.hqhop.modules.company.domain.CompanyUpdate;
import com.hqhop.modules.company.repository.CompanyInfoRepository;
import com.hqhop.modules.company.service.CompanyDingService;
import com.hqhop.modules.company.service.CompanyInfoService;
import com.hqhop.modules.company.service.dto.CompanyInfoDTO;
import com.hqhop.modules.company.service.dto.CompanyInfoQueryCriteria;


import com.taobao.api.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;

;


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

    @Autowired
    private CompanyDingService companyDingService;

    @Autowired
    private CompanyInfoRepository companyInfoRepository;

    @Log("查询CompanyInfo")
    @ApiOperation(value = "查询CompanyInfo")
    @GetMapping(value = "/companyInfo")
//    @PreAuthorize("hasAnyRole('ADMIN','COMPANYINFO_ALL','COMPANYINFO_SELECT')")
    public ResponseEntity getCompanyInfos(CompanyInfoQueryCriteria criteria, Pageable pageable,String id) {
        return new ResponseEntity(companyInfoService.queryAll(criteria, pageable,id), HttpStatus.OK);
    }

    @Log("查询所有CompanyInfo")
    @ApiOperation(value = "查询所有CompanyInfo")
    @GetMapping(value = "/allCompanyInfo")
//    @PreAuthorize("hasAnyRole('ADMIN','COMPANYINFO_ALL','COMPANYINFO_SELECT')")
    public ResponseEntity getAllCompanyInfo(CompanyInfoQueryCriteria criteria) {

        return new ResponseEntity(companyInfoService.queryAll(criteria), HttpStatus.OK);
    }

    @Log("查询CompanyInfo通过ID")
    @ApiOperation(value = "查询CompanyInfo通过ID")
    @GetMapping(value = "/findByID")
//    @PreAuthorize("hasAnyRole('ADMIN','COMPANYINFO_ALL','COMPANYINFO_SELECT')")
    public ResponseEntity getCompanyInfoById(Long companyKey) {
        return new ResponseEntity(companyInfoService.findById(companyKey), HttpStatus.OK);
    }


    @Log("保存和修改接口CompanyInfo")
    @ApiOperation(value = "保存和修改接口CompanyInfo")
    @PostMapping(value = "/companyInfo")
//    @PreAuthorize("hasAnyRole('ADMIN','COMPANYINFO_ALL','COMPANYINFO_CREATE')")
    public ResponseEntity create(@Validated @RequestBody CompanyInfo resources) {

      return new ResponseEntity(companyInfoService.createAndUpadte(resources), HttpStatus.CREATED);

    }

    @Log("修改CompanyInfo")
    @ApiOperation(value = "修改CompanyInfo")
    @PutMapping(value = "/companyInfo")
    @PreAuthorize("hasAnyRole('ADMIN','COMPANYINFO_ALL','COMPANYINFO_EDIT')")
    public ResponseEntity update(@Validated @RequestBody CompanyInfo resources) {




        companyInfoService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除CompanyInfo")
    @ApiOperation(value = "删除CompanyInfo")
    @DeleteMapping(value = "/companyInfo/{companyKey}")
    @PreAuthorize("hasAnyRole('ADMIN','COMPANYINFO_ALL','COMPANYINFO_DELETE')")
    public ResponseEntity delete(@PathVariable Long companyKey) {
        companyInfoService.delete(companyKey);
        return new ResponseEntity(HttpStatus.OK);
    }


    @Log("添加之前客商验证VerifyAdd")
    @ApiOperation(value = " 添加之前客商验证VerifyAdd")
    @GetMapping(value = "/verifyAdd")
//    @PreAuthorize("hasAnyRole('ADMIN','COMPANYINFO_ALL','COMPANYINFO_SELECT')")
    public ResponseEntity VerifyResult(CompanyInfoDTO resources) {
        return new ResponseEntity(companyInfoService.VerifyAdd(resources), HttpStatus.OK);
    }

    @Log("添加之前客商基本档案查询")
    @ApiOperation(value = " 添加之前客商基本档案查询")
    @GetMapping(value = "/findCompanyBasicByTaxId")
//    @PreAuthorize("hasAnyRole('ADMIN','COMPANYINFO_ALL','COMPANYINFO_SELECT')")
    public ResponseEntity findCompanyBasicByTaxId(CompanyInfoDTO resources) {
        return new ResponseEntity(companyInfoService.findCompanyBasicByTaxId(resources), HttpStatus.OK);
    }


    @Log("钉钉调用测试")
    @ApiOperation(value = "钉钉调用测试")
    @GetMapping(value = "/dingTest")
//    @PreAuthorize("hasAnyRole('ADMIN','COMPANYINFO_ALL','COMPANYINFO_SELECT')")
    public ResponseEntity DingTest(String userId,Long depteId) throws
            ApiException {
//        System.out.println("用户ID"+userId +"部门ID"+depteId);
//       companyDingService.saveApproval(userId,depteId);
//        return new ResponseEntity( HttpStatus.OK);

        return null;
    }

    @Log("客商管理权限验证")
    @ApiOperation(value = "客商管理权限验证")
    @GetMapping(value = "/verifyPermission")
//    @PreAuthorize("hasAnyRole('ADMIN','COMPANYINFO_ALL','COMPANYINFO_SELECT')")
    public ResponseEntity  VerifyPermission(Long companyKey) throws
            ApiException {
        return new ResponseEntity(companyInfoService.VerifyPermission(companyKey), HttpStatus.OK);
    }

    @Log("客商审批接口")
    @ApiOperation(value = "客商审批接口")
    @GetMapping(value = "/companyApproval")
//    @PreAuthorize("hasAnyRole('ADMIN','COMPANYINFO_ALL','COMPANYINFO_SELECT')")
    public ResponseEntity addApproval( DingUser dingUser,CompanyUpdate resouces)throws
            ApiException {
        System.out.println("操作类型" + resouces.getOperationType());
        System.out.println("公司主键" + resouces.getCompanyKey());
        System.out.println("用户名" + dingUser.getName());
        System.out.println("用户Id" + dingUser.getUserid());
       if(dingUser.getDepteId() == null){

           OapiUserGetResponse userInfo = DingTalkUtils.getUserInfo(dingUser.getUserid());
           dingUser.setDepartment(userInfo.getDepartment());
           dingUser.setDepteId(userInfo.getDepartment().get(0));
       }


        if(resouces.getCompanyKey() != null){
            CompanyInfo companyInfo = companyInfoRepository.getOne(resouces.getCompanyKey());
            resouces.setApproveTime(companyInfo.getApproveTime());
            resouces.setCreateTime(companyInfo.getCreateTime());
        }

        if (resouces.getCompanyKey() != null && !"".equals(resouces.getCompanyKey()) && "4".equals(resouces.getCompanyState())) {

            //修改审批调用
            companyDingService.updateApproval(resouces, dingUser);

        } else if ("isAble".equals(resouces.getOperationType())) {
            //停用/启用 审批调用
            companyDingService.isAbleApproval(resouces, dingUser);

            return new ResponseEntity(HttpStatus.OK);
        } else {
            //新增审批调用
            companyDingService.addApprovel(resouces, dingUser);

        }

        return new ResponseEntity(HttpStatus.OK);
    }


    @Log("客商权限审批接口")
    @ApiOperation(value = "客商权限审批接口")
    @GetMapping(value = "/getCustomerPermission")
//    @PreAuthorize("hasAnyRole('ADMIN','COMPANYINFO_ALL','COMPANYINFO_SELECT')")
    public ResponseEntity getCustomerPermission( Long companyKey,DingUser dingUser ) throws
    ApiException {

        CompanyInfo byCompanyKey = companyInfoRepository.findByCompanyKey(companyKey);
        CompanyUpdate companyUpdate = new CompanyUpdate();
        companyUpdate.copyCompanyInfo(byCompanyKey);
        companyDingService.getCustomerPermission(companyUpdate, dingUser);

        return new ResponseEntity(HttpStatus.OK);
    }




}