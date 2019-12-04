package com.hqhop.modules.company.service.impl;


import com.dingtalk.api.response.OapiProcessinstanceCreateResponse;
import com.hqhop.modules.company.domain.CompanyInfo;
import com.hqhop.modules.company.domain.CompanyUpdate;
import com.hqhop.modules.company.domain.Contact;
import com.hqhop.modules.company.repository.CompanyInfoRepository;
import com.hqhop.modules.company.repository.CompanyUpdateRepository;
import com.hqhop.modules.company.repository.ContactRepository;
import com.hqhop.modules.company.service.CompanyInfoService;
import com.hqhop.modules.company.service.dto.CompanyInfoDTO;
import com.hqhop.modules.company.service.dto.CompanyInfoQueryCriteria;
import com.hqhop.modules.company.service.mapper.CompanyInfoMapper;
import com.hqhop.utils.CompanyQueryHelp;
import com.hqhop.utils.PageUtil;
import com.hqhop.utils.QueryHelp;
import com.hqhop.utils.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author zf`
 * @date 2019-10-22
 */
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class CompanyInfoServiceImpl implements CompanyInfoService {

    @Autowired
    private CompanyInfoRepository companyInfoRepository;

    @Autowired
    private CompanyInfoMapper companyInfoMapper;

    @Autowired
    private ContactRepository contactRepository;





    @Override
    public Map<String, Object> queryAll(CompanyInfoQueryCriteria criteria, Pageable pageable) {


        List<BigInteger> compyKeyList = findCompanykeys(criteria.getContactName());
        if (criteria.getIsDisable() == null || "".equals(criteria.getIsDisable())) {
            criteria.setIsDisable(1);
        }

        Page<CompanyInfo> page = companyInfoRepository.findAll((root, criteriaQuery, criteriaBuilder) -> CompanyQueryHelp.getPredicate(root, criteria, criteriaBuilder, compyKeyList ), pageable);
        return PageUtil.toPage(page);
    }

    @Override
    public List<CompanyInfo> queryAll(CompanyInfoQueryCriteria criteria) {
//        if (criteria.getIsDisable() == null || "".equals(criteria.getIsDisable())) {
//            criteria.setIsDisable(1);
//        }
        return companyInfoRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder));
    }


    @Override
    public CompanyInfo findById(Long companyKey) {
        CompanyInfo companyInfo = companyInfoRepository.getOne(companyKey);
        return companyInfo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CompanyInfo createAndUpadte(CompanyInfo resources) {   //保存和修改

        CompanyInfo companyInfo = companyInfoRepository.findByTaxIdAndBelongCompany(resources.getTaxId(), resources.getBelongCompany());
        if (companyInfo != null) {
         resources.setCompanyKey(companyInfo.getCompanyKey());
            return companyInfoRepository.save(resources);

        } else {
            if ( resources.getCompanyState() == null || "".equals( resources.getCompanyState())) {
                //1 新增状态 2 审批中 3 驳回 4 审批通过
                resources.setCompanyState(1);
            }
            if ( resources.getIsDisable() == null || "".equals( resources.getIsDisable())) {
                //1 启用 0 停用
                resources.setIsDisable(1);
            }
            resources.setCreateTime(new Date());
            return companyInfoRepository.save(resources);
        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(CompanyInfo resources) {   //修改提交审批接口

        Optional<CompanyInfo> optionalCompanyInfo = companyInfoRepository.findById(resources.getCompanyKey());
        ValidationUtil.isNull(optionalCompanyInfo, "CompanyInfo", "id", resources.getCompanyKey());
        CompanyInfo companyInfo = optionalCompanyInfo.get();
        companyInfo.copy(resources);
        companyInfoRepository.save(companyInfo);
    }

    //通过ID删除客商数据
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long companyKey) {
        companyInfoRepository.deleteById(companyKey);
    }


    /*
   添加之前前客商验证
   * */
    @Override
    public CompanyInfo VerifyAdd(CompanyInfoDTO resources) {

        CompanyInfo companyInfo = companyInfoRepository.findByTaxIdAndBelongCompany(resources.getTaxId(), resources.getBelongCompany());
        if (companyInfo != null) {
            return companyInfo;
        }
        List<CompanyInfo> list = companyInfoRepository.findByTaxId(resources.getTaxId());
        if (list.isEmpty()) {
            CompanyInfo companyInfo1 = new CompanyInfo();
            return companyInfo1;
        }
        return list.get(0);
    }


    //名字模糊查询出来的对应公司id集合
    public List<BigInteger> findCompanykeys(String contactsName) {

        List<BigInteger> list = new ArrayList<>();
        if (contactsName != null && !"".equals(contactsName)) {
            list = contactRepository.findCompany_keyByLikeName(contactsName);

            if (list.isEmpty()) {
                list.add(BigInteger.ZERO);
            }
        }
        return list;
    }



}