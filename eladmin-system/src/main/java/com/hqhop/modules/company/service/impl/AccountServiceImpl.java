package com.hqhop.modules.company.service.impl;

import com.hqhop.common.dingtalk.dingtalkVo.DingUser;
import com.hqhop.modules.company.domain.Account;
import com.hqhop.modules.company.domain.CompanyInfo;
import com.hqhop.modules.company.domain.CompanyUpdate;
import com.hqhop.modules.company.repository.AccountRepository;
import com.hqhop.modules.company.repository.CompanyInfoRepository;
import com.hqhop.modules.company.repository.CompanyUpdateRepository;
import com.hqhop.modules.company.service.AccountService;
import com.hqhop.modules.company.service.dto.AccountDTO;
import com.hqhop.modules.company.service.dto.AccountQueryCriteria;
import com.hqhop.modules.company.service.mapper.AccountMapper;
import com.hqhop.modules.system.repository.EmployeeRepository;
import com.hqhop.utils.PageUtil;
import com.hqhop.utils.QueryHelp;
import com.hqhop.utils.SecurityUtils;
import com.hqhop.utils.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
    private CompanyUpdateRepository companyUpdateRepository;

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private CompanyInfoRepository companyInfoRepository;



    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Map<String,Object> queryAll(AccountQueryCriteria criteria, Pageable pageable){

        Set<Long> keys = employeeRepository.findCompanyKeysByEmployeeKey(SecurityUtils.getEmployeeId());
        if(keys == null || keys.size()==0) {
          criteria.setAccountKey(0L);
        } else {
            Set<Long> contactKeysByCompnayKes = accountRepository.findAccountKeysKeysByCompnayKes(keys.stream().collect(Collectors.toList()));
            criteria.setKeys(contactKeysByCompnayKes);
        }


        Page<Account> page = accountRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);

        for (Account account : page) {

                if(account.getAccountKey() != null){
                    CompanyInfo byCompanyKey = companyInfoRepository.findByCompanyKey(account.getCompanyKey());
                    account.setBelongCompany(byCompanyKey.getCompanyName());
                }
        }

        return PageUtil.toPage(page);
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
    public Account create(Account resources) {

           //1 新增状态 2 新增审批中 3 驳回 4 审批通过5变更审批  字典为准
           resources.setAccountState(1);
           resources.setAccountKey(null);
           return accountRepository.save(resources);


    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Account resources) {

        if(resources.getAccountKey() != null && !"".equals(resources.getAccountKey())){
            accountRepository.save(resources);
        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long accountKey) {
        accountRepository.deleteById(accountKey);
    }

    @Override
    public String getDingUrl(Account resources, DingUser dingUser) {
        CompanyUpdate companyUpdate =companyUpdateRepository.findByAccountKeyAndUserIdAndApproveResult(resources.getAccountKey(),dingUser.getUserid(),"未知");
        if(companyUpdate != null){
            if(companyUpdate.getDingUrl() != null){
                return   companyUpdate.getDingUrl();
           }
           return  null;
        }else {
            return  null;
       }
    }
}



