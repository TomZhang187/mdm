package com.hqhop.modules.company.repository;

import com.hqhop.modules.company.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

/**
* @author zf
* @date 2019-11-06
*/
public interface AccountRepository extends JpaRepository<Account, Long>, JpaSpecificationExecutor {

    Account findByAccountKey(Long key);


    Set<Account> findByCompanyKey(Long key);


    @Query(value = " select company_name from company_info where company_key in (select company_key from account WHERE accountKey =?1)", nativeQuery = true)
    String findBelongCompanyName(Long key);


    @Query(value = " select account_key from account where company_key in ?1", nativeQuery = true)
    Set<Long> findAccountKeysKeysByCompnayKes(List<Long> key);
}