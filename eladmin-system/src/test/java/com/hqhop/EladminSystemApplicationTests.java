package com.hqhop;

import com.hqhop.modules.company.repository.CompanyInfoRepository;
import com.hqhop.modules.company.repository.ContactRepository;
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

    @Test
    public void contextLoads() {

        List<BigInteger>  list = contactRepository.findCompany_keyByLikeName("联系人2555");
        for (BigInteger bigInteger : list) {
            System.out.println("公司ID为"+bigInteger);
        }

    }

}

