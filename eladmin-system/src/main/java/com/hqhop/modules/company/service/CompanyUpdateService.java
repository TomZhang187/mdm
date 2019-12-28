package com.hqhop.modules.company.service;

import com.hqhop.common.dingtalk.dingtalkVo.DingUser;
import com.hqhop.modules.company.domain.CompanyUpdate;
import com.hqhop.modules.company.service.dto.CompanyUpdateDTO;
import com.hqhop.modules.company.service.dto.CompanyUpdateQueryCriteria;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.List;

/**
* @author zf
* @date 2019-11-07
*/
//@CacheConfig(cacheNames = "companyUpdate")
public interface CompanyUpdateService {

    /**
    * 查询数据分页
    * @param criteria
    * @param pageable
    * @return
    */
    //@Cacheable
    Map<String,Object> queryAll(CompanyUpdateQueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria
    * @return
    */
    //@Cacheable
    List<CompanyUpdateDTO> queryAll(CompanyUpdateQueryCriteria criteria);

    /**
     * 根据ID查询
     * @param operateKey
     * @return
     */
    //@Cacheable(key = "#p0")
    CompanyUpdateDTO findById(Long operateKey);

    /**
     * 创建
     * @param resources
     * @return
     */
    //@CacheEvict(allEntries = true)
    CompanyUpdateDTO create(CompanyUpdate resources);

    /**
     * 编辑
     * @param resources
     */
    //@CacheEvict(allEntries = true)
    void update(CompanyUpdate resources);

    /**
     * 删除
     * @param operateKey
     */
    //@CacheEvict(allEntries = true)
    void delete(Long operateKey);

    /*
  查询该客商修改记录
    * */
    CompanyUpdate loadUpdateRecord(Long companyKey,String state,String userId,String name);

    /**
     * 新增该客商的修改记录
     */
    //@CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    CompanyUpdate saveCompanyUpdate(CompanyUpdate resources,DingUser dingUser);


    @Transactional(rollbackFor = Exception.class)
    String getDingUrl(CompanyUpdate resources, DingUser dingUser);
}
