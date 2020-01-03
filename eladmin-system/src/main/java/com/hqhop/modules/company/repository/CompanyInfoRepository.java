package com.hqhop.modules.company.repository;


import com.hqhop.modules.company.domain.CompanyInfo;
import com.hqhop.modules.company.service.dto.CompanyDictDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @author zf
 * @date 2019-10-22
 */
public interface CompanyInfoRepository extends JpaRepository<CompanyInfo, Long>, JpaSpecificationExecutor {

    //通过纳税登记号和所属公司查询客商数据
    CompanyInfo findByTaxIdAndBelongCompany(String taxId, String belongCompanyId);

    @org.springframework.data.jpa.repository.Query(value = "select * from company_info where tax_id=?1 and belong_company =?2", nativeQuery = true)
    List<CompanyInfo> findByTaxIdAndBelongCompanys(String taxId, String belongCompanyId);

    //通过纳税登记号查询客商数据
    List<CompanyInfo> findByTaxId(String taxId);

    //通过主键获取数据
    CompanyInfo findByCompanyKey(Long key);


    @org.springframework.data.jpa.repository.Query(value = "select * from company_info",nativeQuery = true)
   List<CompanyInfo>  findAllCompanyInfo();


    List<CompanyInfo>  findByBelongCompany(String code);


    @org.springframework.data.jpa.repository.Query(value = " select * from company_info WHERE belong_company !=?1", nativeQuery = true)
    List<CompanyInfo>  findByNotBelongCompany(String code);


    List<CompanyInfo> findByCustomerProp(String prop);


    List<CompanyInfo> findByCustomerPropAndBelongCompany(String prop,String belognCompany);

    CompanyInfo findByCustomerPropAndBelongCompanyAndTaxId(String prop,String belongCompany,String taxId);

    @org.springframework.data.jpa.repository.Query(value = " select company_key,company_name,tax_id from company_info WHERE company_name like %?1%", nativeQuery = true)
    List<CompanyDictDto> findByLikeName(String name);






}