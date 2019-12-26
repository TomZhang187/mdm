package com.hqhop.modules.company.repository;

import com.hqhop.modules.company.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Set;

/**
* @author zf
* @date 2019-11-06
*/
public interface AccountRepository extends JpaRepository<Account, Long>, JpaSpecificationExecutor {

    Account findByAccountKey(Long key);


    Set<Account> findByCompanyKey(Long key);
}