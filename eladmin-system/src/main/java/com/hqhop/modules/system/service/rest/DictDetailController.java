package com.hqhop.modules.system.service.rest;

import com.hqhop.aop.log.Log;
import com.hqhop.exception.BadRequestException;
import com.hqhop.modules.system.domain.DictDetail;
import com.hqhop.modules.system.service.DictDetailService;
import com.hqhop.modules.system.service.dto.DictDetailQueryCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
* @author Zheng Jie
* @date 2019-04-10
*/
@RestController
@RequestMapping("api")
public class DictDetailController {

    @Autowired
    private DictDetailService dictDetailService;

    private static final String ENTITY_NAME = "dictDetail";

    @Log("查询字典详情")
    @GetMapping(value = "/dictDetail")
    public ResponseEntity getDictDetails(DictDetailQueryCriteria criteria,
                                         @PageableDefault(value = 10, sort = {"sort"}, direction = Sort.Direction.ASC) Pageable pageable){
        String[] names = criteria.getDictName().split(",");
        return new ResponseEntity(dictDetailService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @Log("查询多个字典详情")
    @GetMapping(value = "/dictDetail/map")
    public ResponseEntity getDictDetailMaps(DictDetailQueryCriteria criteria,
                                         @PageableDefault(value = 10, sort = {"sort"}, direction = Sort.Direction.ASC) Pageable pageable){
        String[] names = criteria.getDictName().split(",");
        Map map = new HashMap(names.length);
        for (String name : names) {
            criteria.setDictName(name);
            map.put(name,dictDetailService.queryAll(criteria,pageable).get("content"));
        }
        return new ResponseEntity(map,HttpStatus.OK);
    }

    @Log("新增字典详情")
    @PostMapping(value = "/dictDetail")
//    @PreAuthorize("hasAnyRole('ADMIN','DICT_ALL','DICT_CREATE')")
    public ResponseEntity create(@Validated @RequestBody DictDetail resources){
        if (resources.getId() != null) {
            throw new BadRequestException("A new "+ ENTITY_NAME +" cannot already have an ID");
        }
        return new ResponseEntity(dictDetailService.create(resources),HttpStatus.CREATED);
    }

    @Log("修改字典详情")
    @PutMapping(value = "/dictDetail")
//    @PreAuthorize("hasAnyRole('ADMIN','DICT_ALL','DICT_EDIT')")
    public ResponseEntity update(@Validated(DictDetail.Update.class) @RequestBody DictDetail resources){
        dictDetailService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除字典详情")
    @DeleteMapping(value = "/dictDetail/{id}")
//    @PreAuthorize("hasAnyRole('ADMIN','DICT_ALL','DICT_DELETE')")
    public ResponseEntity delete(@PathVariable Long id){
        dictDetailService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }



    @Log("返回label-value Map集合对象")
    @GetMapping(value = "/getLabelByValue/map")
    public ResponseEntity getLabelByValue(DictDetailQueryCriteria criteria){
       return new ResponseEntity(dictDetailService.getLabelByValue(criteria),HttpStatus.OK);
    }

    @Log("返回value-label Map集合集合对象")
    @GetMapping(value = "/getValueByLabel/map")
    public ResponseEntity getValueByLabel(DictDetailQueryCriteria criteria){
        return new ResponseEntity(dictDetailService.getValueByLabel(criteria),HttpStatus.OK);
    }
}
