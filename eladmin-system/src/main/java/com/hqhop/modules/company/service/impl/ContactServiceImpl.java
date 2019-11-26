package com.hqhop.modules.company.service.impl;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.hqhop.config.dingtalk.dingtalkVo.DingUser;
import com.hqhop.modules.company.domain.CompanyInfo;
import com.hqhop.modules.company.domain.CompanyUpdate;
import com.hqhop.modules.company.domain.Contact;
import com.hqhop.modules.company.repository.CompanyUpdateRepository;
import com.hqhop.modules.company.repository.ContactRepository;
import com.hqhop.modules.company.service.ContactService;
import com.hqhop.modules.company.service.dto.ContactDTO;
import com.hqhop.modules.company.service.dto.ContactQueryCriteria;
import com.hqhop.modules.company.service.mapper.ContactMapper;
import com.hqhop.utils.PageUtil;
import com.hqhop.utils.QueryHelp;
import com.hqhop.utils.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
* @author zf
* @date 2019-11-07
*/
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ContactServiceImpl implements ContactService {

    @Autowired
    private ContactRepository contactRepository;
    @Autowired
    private CompanyUpdateRepository companyUpdateRepository;

    @Autowired
    private ContactMapper contactMapper;

    @Override
    public Map<String,Object> queryAll(ContactQueryCriteria criteria, Pageable pageable){
        Page<Contact> page = contactRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page);
    }

    @Override
    public List<ContactDTO> queryAll(ContactQueryCriteria criteria){
        return contactMapper.toDto(contactRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    public ContactDTO findById(Long contactKey) {
        Optional<Contact> contact = contactRepository.findById(contactKey);
        ValidationUtil.isNull(contact,"Contact","contactKey",contactKey);
        return contactMapper.toDto(contact.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ContactDTO create(Contact resources) {
        Snowflake snowflake = IdUtil.createSnowflake(1, 1);
        resources.setContactKey(snowflake.nextId());
        //1 新增状态 2 新增审批中 3 驳回 4 审批通过5变更审批  字典为准
        resources.setContactState(1);
        return contactMapper.toDto(contactRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Contact resources) {

        if(resources.getContactKey() != null && !"".equals(resources.getContactKey())){
            contactRepository.save(resources);
        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long contactKey) {
        contactRepository.deleteById(contactKey);
    }

    /**
     * 获取改联系人审批链接
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String getDingUrl(Contact resources, DingUser dingUser) {

        CompanyUpdate companyUpdate =companyUpdateRepository.findByContactKeyAndUserIdAndApproveResult(resources.getContactKey(),dingUser.getUserid(),"未知");
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
