package com.hqhop.modules.system.service.rest;

import com.hqhop.aop.log.Log;
import com.hqhop.modules.system.domain.Employee;
import com.hqhop.modules.system.service.EmployeeDingService;
import com.hqhop.modules.system.service.EmployeeService;
import com.hqhop.modules.system.service.dto.EmployeeQueryCriteria;
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
* @date 2019-11-28
*/
@Api(tags = "Employee管理")
@RestController
@RequestMapping("api")
public class EmployeeController {



    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private EmployeeDingService employeeDingService;

    @Log("查询Employee")
    @ApiOperation(value = "查询Employee")
    @GetMapping(value = "/employee")
//    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE_ALL','EMPLOYEE_SELECT')")
    public ResponseEntity getEmployees(EmployeeQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity(employeeService.queryAll(criteria,pageable),HttpStatus.OK);
    }



    @Log("查询所有Employee")
    @ApiOperation(value = "查询所有Employee")
    @GetMapping(value = "/allEmployee")
//    @PreAuthorize("hasAnyRole('ADMIN','COMPANYINFO_ALL','COMPANYINFO_SELECT')")
    public ResponseEntity getAllCompanyInfo(EmployeeQueryCriteria criteria) {
        return new ResponseEntity(employeeService.queryAll(criteria), HttpStatus.OK);
    }

    @Log("通过工号查询Employee")
    @ApiOperation(value = "通过工号查询Employee")
    @GetMapping(value = "/getEmployeeByCode")
//    @PreAuthorize("hasAnyRole('ADMIN','COMPANYINFO_ALL','COMPANYINFO_SELECT')")
    public ResponseEntity getEmployeeByCode(EmployeeQueryCriteria criteria) {

        return new ResponseEntity(employeeService.getEmployeeByCode(criteria.getEmployeeCode()), HttpStatus.OK);
    }

    @Log("新增Employee")
    @ApiOperation(value = "新增Employee")
    @PostMapping(value = "/employee")
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE_ALL','EMPLOYEE_CREATE')")
    public ResponseEntity create(@Validated @RequestBody Employee resources){
        return new ResponseEntity(employeeService.create(resources),HttpStatus.CREATED);
    }

    @Log("修改Employee")
    @ApiOperation(value = "修改Employee")
    @PutMapping(value = "/employee")
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE_ALL','EMPLOYEE_EDIT')")
    public ResponseEntity update(@Validated @RequestBody Employee resources){
        employeeService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除Employee")
    @ApiOperation(value = "删除Employee")
    @DeleteMapping(value = "/employee/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE_ALL','EMPLOYEE_DELETE')")
    public ResponseEntity delete(@PathVariable Long id){
        employeeService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }



    @Log("同步钉钉数据Employee")
    @ApiOperation(value = "同步钉钉数据Employee")
    @PutMapping(value = "/syncDingUser")
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE_ALL','EMPLOYEE_EDIT')")
    public ResponseEntity syncDingUser(){
        try {
            employeeDingService.syncDingUser();
        } catch (ApiException e) {
            e.printStackTrace();
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
