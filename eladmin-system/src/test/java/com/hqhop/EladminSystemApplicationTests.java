package com.hqhop;

import com.hqhop.modules.company.repository.CompanyInfoRepository;
import com.hqhop.modules.company.repository.ContactRepository;
import com.hqhop.modules.material.domain.Attribute;
import com.hqhop.modules.material.domain.MaterialAttribute;
import com.hqhop.modules.material.repository.AttributeRepository;
import com.hqhop.modules.material.repository.MaterialAttributeRepository;
import com.hqhop.modules.material.repository.MaterialTypeRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigInteger;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EladminSystemApplicationTests {

    @Autowired
    CompanyInfoRepository companyInfoRepository;

    @Autowired
    ContactRepository contactRepository;
    @Autowired
    private AttributeRepository attributeRepository;
    @Autowired
    private MaterialAttributeRepository materialTypeRepository;
    @Test
    public void contextLoads() {

        List<BigInteger>  list = contactRepository.findCompany_keyByLikeName("联系人2555");
        for (BigInteger bigInteger : list) {
            System.out.println("公司ID为"+bigInteger);
        }

    }
    @Test
    public void test2(){
        List<Attribute> byMaterialTypeId = attributeRepository.findByMaterialTypeId(5L);
        for (Attribute attribute : byMaterialTypeId) {
            System.out.println(attribute.getAttributeName());
        }
    }
    @Test
    public void test3(){

    }

}

