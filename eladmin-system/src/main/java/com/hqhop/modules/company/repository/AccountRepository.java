package com.hqhop.modules.company.repository;

import com.hqhop.modules.company.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
* @author zf
* @date 2019-11-06
*/
public interface AccountRepository extends JpaRepository<Account, Long>, JpaSpecificationExecutor {

    Account findByAccountKey(Long key);
}