package com.hqhop.modules.company.repository;

import com.hqhop.modules.company.domain.CompanyBasic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
* @author lyl
* @date 2020-01-01
*/
public interface CompanyBasicRepository extends JpaRepository<CompanyBasic, Long>, JpaSpecificationExecutor {


    CompanyBasic findByTaxId(String taxId);
}