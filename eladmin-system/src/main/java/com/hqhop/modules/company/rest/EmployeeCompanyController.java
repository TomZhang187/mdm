package com.hqhop.modules.company.rest;

import com.hqhop.aop.log.Log;
import com.hqhop.modules.company.domain.EmployeeCompany;
import com.hqhop.modules.company.service.EmployeeCompanyService;
import com.hqhop.modules.company.service.dto.EmployeeCompanyQueryCriteria;
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
* @date 2020-01-02
*/
@Api(tags = "EmployeeCompany管理")
@RestController
@RequestMapping("api")
public class EmployeeCompanyController {

    @Autowired
    private EmployeeCompanyService employeeCompanyService;

    @Log("查询EmployeeCompany")
    @ApiOperation(value = "查询EmployeeCompany")
    @GetMapping(value = "/employeeCompany")
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEECOMPANY_ALL','EMPLOYEECOMPANY_SELECT')")
    public ResponseEntity getEmployeeCompanys(EmployeeCompanyQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity(employeeCompanyService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @Log("新增EmployeeCompany")
    @ApiOperation(value = "新增EmployeeCompany")
    @PostMapping(value = "/employeeCompany")
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEECOMPANY_ALL','EMPLOYEECOMPANY_CREATE')")
    public ResponseEntity create(@Validated @RequestBody EmployeeCompany resources){
        return new ResponseEntity(employeeCompanyService.create(resources),HttpStatus.CREATED);
    }

    @Log("修改EmployeeCompany")
    @ApiOperation(value = "修改EmployeeCompany")
    @PutMapping(value = "/employeeCompany")
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEECOMPANY_ALL','EMPLOYEECOMPANY_EDIT')")
    public ResponseEntity update(@Validated @RequestBody EmployeeCompany resources){
        employeeCompanyService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除EmployeeCompany")
    @ApiOperation(value = "删除EmployeeCompany")
    @DeleteMapping(value = "/employeeCompany/{companyKey}")
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEECOMPANY_ALL','EMPLOYEECOMPANY_DELETE')")
    public ResponseEntity delete(@PathVariable Long companyKey){
        employeeCompanyService.delete(companyKey);
        return new ResponseEntity(HttpStatus.OK);
    }
}
