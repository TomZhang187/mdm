package com.hqhop.modules.company.rest;

import com.hqhop.aop.log.Log;
import com.hqhop.common.dingtalk.dingtalkVo.DingUser;
import com.hqhop.modules.company.domain.Contact;
import com.hqhop.modules.company.repository.ContactRepository;
import com.hqhop.modules.company.service.ContactDingService;
import com.hqhop.modules.company.service.ContactService;
import com.hqhop.modules.company.service.dto.ContactQueryCriteria;
import com.taobao.api.ApiException;
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

    @Autowired
    private ContactDingService contactDingService;

    @Autowired
    private ContactRepository contactRepository;

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


    @Log("联系人审批接口")
    @ApiOperation(value = "联系人审批接口")
    @GetMapping(value = "/contactApproval")
//    @PreAuthorize("hasAnyRole('ADMIN','COMPANYINFO_ALL','COMPANYINFO_SELECT')")
    public ResponseEntity addApproval(DingUser dingUser, Contact resouces)throws
            ApiException {



        if (resouces.getContactKey() != null && !"".equals(resouces.getContactKey()) && resouces.getContactState()==4 ) {

            if(resouces.getContactName() != null && !"".equals(resouces.getContactName())){
                //修改审批调用
                contactDingService.updateApproval(resouces,dingUser);
            }else {
                //解除绑定审批
                Contact contact = contactRepository.getOne(resouces.getContactKey());
               contactDingService.removeApproval(contact,dingUser);
            }
        } else {
            //新增审批调用
            contactDingService.addApprovel(resouces,dingUser);
        }

        return new ResponseEntity(HttpStatus.OK);
    }


    @Log("获取联系人审批链接getDingUrl")
    @ApiOperation(value = "获取联系人审批链接getDingUrl")
    @GetMapping(value = "/contactDingUrl")
//    @PreAuthorize("hasAnyRole('ADMIN','COMPANYUPDATE_ALL','COMPANYUPDATE_CREATE')")
    public ResponseEntity getContactDingUrl(Contact resources,DingUser dingUser){

        return new ResponseEntity(contactService.getDingUrl(resources,dingUser),HttpStatus.CREATED);
    }
}
