package com.hqhop.modules.company.service.impl;

import com.hqhop.modules.company.domain.Account;
import com.hqhop.utils.ValidationUtil;
import com.hqhop.modules.company.repository.AccountRepository;
import com.hqhop.modules.company.service.AccountService;
import com.hqhop.modules.company.service.dto.AccountDTO;
import com.hqhop.modules.company.service.dto.AccountQueryCriteria;
import com.hqhop.modules.company.service.mapper.AccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.hqhop.utils.PageUtil;
import com.hqhop.utils.QueryHelp;
import java.util.List;
import java.util.Map;

/**
* @author zf
* @date 2019-11-06
*/
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountMapper accountMapper;

    @Override
    public Map<String,Object> queryAll(AccountQueryCriteria criteria, Pageable pageable){
        Page<Account> page = accountRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(accountMapper::toDto));
    }

    @Override
    public List<AccountDTO> queryAll(AccountQueryCriteria criteria){
        return accountMapper.toDto(accountRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    public AccountDTO findById(Long accountKey) {
        Optional<Account> account = accountRepository.findById(accountKey);
        ValidationUtil.isNull(account,"Account","accountKey",accountKey);
        return accountMapper.toDto(account.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AccountDTO create(Account resources) {
        Snowflake snowflake = IdUtil.createSnowflake(1, 1);
        resources.setAccountKey(snowflake.nextId());
        return accountMapper.toDto(accountRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Account resources) {
        Optional<Account> optionalAccount = accountRepository.findById(resources.getAccountKey());
        ValidationUtil.isNull( optionalAccount,"Account","id",resources.getAccountKey());
        Account account = optionalAccount.get();
        account.copy(resources);
        accountRepository.save(account);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long accountKey) {
        accountRepository.deleteById(accountKey);
    }
}
