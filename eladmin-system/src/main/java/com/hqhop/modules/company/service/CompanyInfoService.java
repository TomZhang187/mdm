package com.hqhop.modules.company.service;

import com.hqhop.modules.company.domain.CompanyInfo;
import com.hqhop.modules.company.domain.EmployeeCompany;
import com.hqhop.modules.company.service.dto.CompanyDictDto;
import com.hqhop.modules.company.service.dto.CompanyInfoDTO;
import com.hqhop.modules.company.service.dto.CompanyInfoQueryCriteria;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

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
    Map<String, Object> queryAll(CompanyInfoQueryCriteria criteria, Pageable pageable,String dingId);

    /**
     * 查询所有数据不分页
     *
     * @param criteria
     * @return
     */
    //@Cacheable
    List<CompanyInfo> queryAll(CompanyInfoQueryCriteria criteria);



    /**
     * 根据ID查询
     *
     * @param companyKey
     * @return
     */
    //@Cacheable(key = "#p0")
    CompanyInfo findById(Long companyKey);

    /**
     * 保存和修改
     *
     * @param resources
     * @return
     */
    //@CacheEvict(allEntries = true)
    CompanyInfo createAndUpadte(CompanyInfo resources);


//    /*
//     * 客商修改预先保存
//     *
//     * */
//    @Transactional(rollbackFor = Exception.class)
//    OapiProcessinstanceCreateResponse saveApprovel(CompanyInfo resources);

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


    /*
    客商管理权限验证
 * */
    EmployeeCompany VerifyPermission(Long companyKey);



    List<CompanyDictDto>  findPermissonCompany();
}