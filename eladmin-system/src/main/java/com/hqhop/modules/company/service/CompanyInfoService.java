package com.hqhop.modules.company.service;

import com.dingtalk.api.response.OapiProcessinstanceCreateResponse;
import com.hqhop.modules.company.domain.CompanyInfo;
import com.hqhop.modules.company.service.dto.CompanyInfoDTO;
import com.hqhop.modules.company.service.dto.CompanyInfoQueryCriteria;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.List;

/**
 * @author zf
 * @date 2019-10-22
 */
//@CacheConfig(cacheNames = "companyInfo")
public interface CompanyInfoService {

    /**
     * 查询数据分页
     *
     * @param criteria
     * @param pageable
     * @return
     */
    //@Cacheable
    Map<String, Object> queryAll(CompanyInfoQueryCriteria criteria, Pageable pageable);

    /**
     * 查询所有数据不分页
     *
     * @param criteria
     * @return
     */
    //@Cacheable
    List<CompanyInfoDTO> queryAll(CompanyInfoQueryCriteria criteria);

    /**
     * 根据ID查询
     *
     * @param companyKey
     * @return
     */
    //@Cacheable(key = "#p0")
    CompanyInfoDTO findById(Long companyKey);

    /**
     * 保存和修改
     *
     * @param resources
     * @return
     */
    //@CacheEvict(allEntries = true)
    CompanyInfo createAndUpadte(CompanyInfo resources);


    /*
     * 客商保存审批流接口
     *
     * */
    @Transactional(rollbackFor = Exception.class)
    OapiProcessinstanceCreateResponse saveApprovel(CompanyInfo resources);

    /**
     * 编辑
     *
     * @param resources
     */
    //@CacheEvict(allEntries = true)
    void update(CompanyInfo resources);

    /**
     * 删除
     *
     * @param companyKey
     */
    //@CacheEvict(allEntries = true)
    void delete(Long companyKey);


    /*
       添加前客商验证
       * */
    CompanyInfo VerifyAdd(CompanyInfoDTO resources);


}