package com.hqhop.modules.company.repository;


import com.hqhop.modules.company.domain.CompanyInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @author zf
 * @date 2019-10-22
 */
public interface CompanyInfoRepository extends JpaRepository<CompanyInfo, Long>, JpaSpecificationExecutor {

    //通过纳税登记号和所属公司查询客商数据
    CompanyInfo findByTaxIdAndBelongCompany(String taxId, int belongCompanyId);

    //通过纳税登记号查询客商数据
    List<CompanyInfo> findByTaxId(String taxId);

}