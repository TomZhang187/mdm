package com.hqhop.modules.company.service;


import com.hqhop.config.dingtalk.dingtalkVo.DingUser;
import com.hqhop.modules.company.domain.Contact;
import com.hqhop.modules.company.service.dto.ContactDTO;
import com.hqhop.modules.company.service.dto.ContactQueryCriteria;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
* @author zf
* @date 2019-11-07
*/
//@CacheConfig(cacheNames = "contact")
public interface ContactService {

    /**
    * 查询数据分页
    * @param criteria
    * @param pageable
    * @return
    */
    //@Cacheable
    Map<String,Object> queryAll(ContactQueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria
    * @return
    */
    //@Cacheable
    List<ContactDTO> queryAll(ContactQueryCriteria criteria);

    /**
     * 根据ID查询
     * @param contactKey
     * @return
     */
    //@Cacheable(key = "#p0")
    ContactDTO findById(Long contactKey);

    /**
     * 创建
     * @param resources
     * @return
     */
    //@CacheEvict(allEntries = true)
    ContactDTO create(Contact resources);

    /**
     * 编辑
     * @param resources
     */
    //@CacheEvict(allEntries = true)
    void update(Contact resources);

    /**
     * 删除
     * @param contactKey
     */
    //@CacheEvict(allEntries = true)
    void delete(Long contactKey);


    @Transactional(rollbackFor = Exception.class)
    String getDingUrl(Contact resources, DingUser dingUser);
}