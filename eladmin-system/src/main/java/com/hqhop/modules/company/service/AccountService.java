package com.hqhop.modules.company.service;

import com.hqhop.modules.company.domain.Account;
import com.hqhop.modules.company.service.dto.AccountDTO;
import com.hqhop.modules.company.service.dto.AccountQueryCriteria;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import java.util.Map;
import java.util.List;

/**
* @author zf
* @date 2019-11-06
*/
//@CacheConfig(cacheNames = "account")
public interface AccountService {

    /**
    * 查询数据分页
    * @param criteria
    * @param pageable
    * @return
    */
    //@Cacheable
    Map<String,Object> queryAll(AccountQueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria
    * @return
    */
    //@Cacheable
    List<AccountDTO> queryAll(AccountQueryCriteria criteria);

    /**
     * 根据ID查询
     * @param accountKey
     * @return
     */
    //@Cacheable(key = "#p0")
    AccountDTO findById(Long accountKey);

    /**
     * 创建
     * @param resources
     * @return
     */
    //@CacheEvict(allEntries = true)
    AccountDTO create(Account resources);

    /**
     * 编辑
     * @param resources
     */
    //@CacheEvict(allEntries = true)
    void update(Account resources);

    /**
     * 删除
     * @param accountKey
     */
    //@CacheEvict(allEntries = true)
    void delete(Long accountKey);
}