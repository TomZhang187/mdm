package com.hqhop.modules.employee.rest;

import me.zhengjie.aop.log.Log;
import com.hqhop.modules.employee.domain.Employee;
import com.hqhop.modules.employee.service.EmployeeService;
import com.hqhop.modules.employee.service.dto.EmployeeQueryCriteria;
import me.zhengjie.modules.system.domain.Dept;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
* @author chengy
* @date 2019-10-21
*/
@Api(tags = "Employee管理")
@RestController
@RequestMapping("api")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Log("查询Employee")
    @ApiOperation(value = "查询Employee")
    @GetMapping(value = "/employee")
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE_ALL','EMPLOYEE_SELECT')")
    public ResponseEntity getEmployees(EmployeeQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity(employeeService.queryAll(criteria,pageable),HttpStatus.OK);
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

    @Log("导出员工数据")
    @GetMapping(value = "/employee/download")
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE_ALL','EMPLOYEE_SELECT')")
    public void update(HttpServletResponse response, EmployeeQueryCriteria criteria) throws IOException {
        employeeService.download(employeeService.queryAll(criteria), response);
    }

    @Log("根据工号查询员工数据")
    @GetMapping(value = "/employee/code")
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE_ALL','EMPLOYEE_SELECT')")
    public ResponseEntity getEmploysByCode(Dept resources) throws IOException {
        return  new ResponseEntity(employeeService.findByEmployeeCode(resources.getEmployeeCode()),HttpStatus.OK);
    }
}