package com.hqhop.modules.company.rest;

import com.hqhop.aop.log.Log;
import com.hqhop.modules.company.domain.Contact;
import com.hqhop.modules.company.service.ContactService;
import com.hqhop.modules.company.service.dto.ContactQueryCriteria;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
* @author zf
* @date 2019-10-22
*/
@Api(tags = "Contact管理")
@RestController
@RequestMapping("api")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @Log("查询Contact")
    @ApiOperation(value = "查询Contact")
    @GetMapping(value = "/contact")
//    @PreAuthorize("hasAnyRole('ADMIN','CONTACT_ALL','CONTACT_SELECT')")
    public ResponseEntity getContacts(ContactQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity(contactService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @Log("新增Contact")
    @ApiOperation(value = "新增Contact")
    @PostMapping(value = "/contact")
    @PreAuthorize("hasAnyRole('ADMIN','CONTACT_ALL','CONTACT_CREATE')")
    public ResponseEntity create(@Validated @RequestBody Contact resources){
        return new ResponseEntity(contactService.create(resources),HttpStatus.CREATED);
    }

    @Log("修改Contact")
    @ApiOperation(value = "修改Contact")
    @PutMapping(value = "/contact")
    @PreAuthorize("hasAnyRole('ADMIN','CONTACT_ALL','CONTACT_EDIT')")
    public ResponseEntity update(@Validated @RequestBody Contact resources){
        contactService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除Contact")
    @ApiOperation(value = "删除Contact")
    @DeleteMapping(value = "/contact/{contactKey}")
    @PreAuthorize("hasAnyRole('ADMIN','CONTACT_ALL','CONTACT_DELETE')")
    public ResponseEntity delete(@PathVariable Long contactKey){
        contactService.delete(contactKey);
        return new ResponseEntity(HttpStatus.OK);
    }
}