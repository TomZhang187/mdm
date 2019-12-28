package com.hqhop.modules.system.service.rest;

import com.hqhop.aop.log.Log;
import com.hqhop.config.DataScope;
import com.hqhop.exception.BadRequestException;
import com.hqhop.modules.system.domain.Dept;
import com.hqhop.modules.system.repository.DeptRepository;
import com.hqhop.modules.system.service.DeptService;
import com.hqhop.modules.system.service.dto.DeptDTO;
import com.hqhop.modules.system.service.dto.DeptQueryCriteria;
import com.hqhop.modules.system.service.DeptDingService;
import com.hqhop.utils.ThrowableUtil;
import com.taobao.api.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

/**
* @author Zheng Jie
* @date 2019-03-25
*/
@RestController
@RequestMapping("api")
public class DeptController {

    @Autowired
    private DeptService deptService;

    @Autowired
    private DataScope dataScope;

    @Autowired
    private DeptRepository deptRepository;

    @Autowired
    private DeptDingService deptDingService;

    private static final String ENTITY_NAME = "dept";

    @Log("查询部门")
    @GetMapping(value = "/dept")
//   @PreAuthorize("hasAnyRole('ADMIN','USER_ALL','USER_SELECT','DEPT_ALL','DEPT_SELECT')")
    public ResponseEntity getDepts(DeptQueryCriteria criteria){
        // 数据权限
            criteria.setIds(dataScope.getDeptIds());
            if(criteria.getIds().isEmpty()){
                criteria.setIds(null);
            }
        List<DeptDTO> deptDTOS = deptService.queryAll(criteria);
            return new ResponseEntity(deptService.buildTree(deptDTOS),HttpStatus.OK);
    }

    @Log("新增部门")
    @PostMapping(value = "/dept")
    @PreAuthorize("hasAnyRole('ADMIN','DEPT_ALL','DEPT_CREATE')")
    public ResponseEntity create(@Validated @RequestBody Dept resources)throws
            ApiException {
        if (resources.getId() != null) {
            throw new BadRequestException("A new "+ ENTITY_NAME +" cannot already have an ID");
        }
        return new ResponseEntity(deptService.create(resources),HttpStatus.CREATED);
    }

    @Log("修改部门")
    @PutMapping(value = "/dept")
    @PreAuthorize("hasAnyRole('ADMIN','DEPT_ALL','DEPT_EDIT')")
    public ResponseEntity update(@Validated(Dept.Update.class) @RequestBody  Dept resources){
        try {
            deptService.update(resources);
        } catch (ApiException e) {
            e.printStackTrace();
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除部门")
    @DeleteMapping(value = "/dept/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','DEPT_ALL','DEPT_DELETE')")
    public ResponseEntity delete(@PathVariable Long id){
        try {
            deptService.delete(id);
        }catch (Throwable e){
            ThrowableUtil.throwForeignKeyException(e, "该部门存在岗位或者角色关联，请取消关联后再试");
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @Log("拿到根部门ID")
    @GetMapping(value = "/getRootDeptId")
//    @PreAuthorize("hasAnyRole('ADMIN','DEPT_ALL','DEPT_DELETE')")
    public ResponseEntity getRootDeptId(){

        List<Dept> depts = deptRepository.findByPid(0L);
        if(depts != null){
            return new ResponseEntity(depts.get(0).getId(),HttpStatus.OK);
        }
       return null;
    }

    @Log("同步钉钉上的用户信息")
    @PutMapping(value = "/syncDingDept")
//    @PreAuthorize("hasAnyRole('ADMIN','DEPT_ALL','DEPT_DELETE')")
    public ResponseEntity syncDingDept()throws
            ApiException {
       deptDingService.syncDeptToDingDept();
        return new ResponseEntity(HttpStatus.OK);
    }
}
