package com.hqhop;
import com.alipay.api.domain.ItemDiagnoseType;
import com.hqhop.easyExcel.excelRead.MaterialExcelUtils;
import com.hqhop.easyExcel.model.*;
import com.hqhop.modules.company.domain.CompanyBasic;
import com.hqhop.modules.company.domain.CompanyInfo;
import com.hqhop.modules.company.repository.AccountRepository;
import com.hqhop.modules.company.repository.CompanyInfoRepository;
import com.hqhop.modules.company.repository.ContactRepository;
import com.hqhop.modules.material.domain.*;
import com.hqhop.modules.material.repository.AttributeRepository;
import com.hqhop.modules.material.repository.MaterialProductionRepository;
import com.hqhop.modules.material.repository.MaterialRepository;
import com.hqhop.modules.material.repository.MaterialTypeRepository;
import com.hqhop.modules.material.service.MaterialDingService;
import com.hqhop.modules.material.service.MaterialService;
import com.hqhop.modules.material.service.impl.AttributeServiceImpl;
import com.hqhop.modules.system.domain.*;
import com.hqhop.modules.system.repository.*;
import com.hqhop.modules.system.service.DeptService;
import com.hqhop.modules.system.service.UserService;
import com.hqhop.modules.system.service.dto.UserDTO;
import com.taobao.api.ApiException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestMain {

    @Autowired
    private UserService userService;


    @Autowired
    private CompanyInfoRepository companyInfoRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private DictDetailRepository dictDetailRepository;

//    @Transactional
    @Test
    public void contextLoads() {

        List<CompanyInfo> alls = companyInfoRepository.findByCustomerPropAndBelongCompany("1", "10");

        List<DictDetail> allByDictId = dictDetailRepository.findAllByDictId(5L);
        for (DictDetail dictDetail : allByDictId) {
              if(dictDetail.getValue().equals("10")){
                  continue;
              }
        for (CompanyInfo all : alls) {
            CompanyInfo companyInfo = companyInfoRepository.findByCustomerPropAndBelongCompanyAndTaxId("1", dictDetail.getValue(), all.getTaxId());
            if(companyInfo != null){
                continue;
            }

          CompanyInfo companyInfo1 = new CompanyInfo();

            companyInfo1.getBasicAttribute(all);
            companyInfo1.setBelongCompany(dictDetail.getValue());
            companyInfo1.setCompanyKey(null);
            companyInfo1.setCustomerProp("1");
          companyInfoRepository.save(companyInfo1);
        }


        }
    }
}
