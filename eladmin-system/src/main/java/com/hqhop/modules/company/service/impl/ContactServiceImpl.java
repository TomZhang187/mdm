package com.hqhop.modules.company.service.impl;


import com.hqhop.modules.company.domain.Contact;
import com.hqhop.modules.company.repository.ContactRepository;
import com.hqhop.modules.company.service.ContactService;
import com.hqhop.modules.company.service.dto.ContactDTO;
import com.hqhop.modules.company.service.dto.ContactQueryCriteria;
import com.hqhop.modules.company.service.mapper.ContactMapper;
import com.hqhop.utils.PageUtil;
import com.hqhop.utils.QueryHelp;
import com.hqhop.utils.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;

/**
* @author zf
* @date 2019-10-22
*/
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ContactServiceImpl implements ContactService {


    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private ContactMapper contactMapper;

    @Override
    public Map<String,Object> queryAll(ContactQueryCriteria criteria, Pageable pageable){
        Page<Contact> page = contactRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(contactMapper::toDto));
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
        return contactMapper.toDto(contactRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Contact resources) {
        Optional<Contact> optionalContact = contactRepository.findById(resources.getContactKey());
        ValidationUtil.isNull( optionalContact,"Contact","id",resources.getContactKey());
        Contact contact = optionalContact.get();
        contact.copy(resources);
        contactRepository.save(contact);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long contactKey) {
        contactRepository.deleteById(contactKey);
    }
}