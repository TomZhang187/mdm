package com.hqhop;

import cn.hutool.json.JSONObject;
import com.dingtalk.api.response.OapiProcessinstanceGetResponse;
import com.hqhop.config.dingtalk.DingTalkUtils;
import com.hqhop.config.dingtalk.dingtalkVo.DingUser;
import com.hqhop.modules.company.domain.CompanyInfo;
import com.hqhop.modules.company.domain.CompanyUpdate;
import com.hqhop.modules.company.repository.CompanyInfoRepository;
import com.hqhop.modules.company.repository.CompanyUpdateRepository;
import com.hqhop.modules.company.repository.ContactRepository;
import com.hqhop.modules.company.service.CompanyDingService;
import com.hqhop.modules.system.domain.Dict;
import com.hqhop.modules.system.repository.DictDetailRepository;
import com.hqhop.modules.system.repository.DictRepository;
import com.hqhop.modules.system.service.DictDetailService;
import com.taobao.api.ApiException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
public class EladminSystemApplicationTests {

    @Autowired
    CompanyInfoRepository companyInfoRepository;

    @Autowired
    CompanyUpdateRepository companyUpdateRepository;

    @Autowired
    ContactRepository contactRepository;

    @Autowired
    private CompanyDingService companyDingService;

    @Autowired
    private DictDetailService dictDetailService;

    @Autowired
    private DictDetailRepository dictDetailRepository;

    @Autowired
    private DictRepository dictRepository;

    @Transactional
    @Test
    public void contextLoads() throws
            ApiException {

//              String dicName ="inside_company";
//              Integer now = 10;
////       companyDingService.terminateAddApproval("e64e1531-77b6-4773-b109-35ce290de1c1");
//        CompanyUpdate resouces = companyUpdateRepository.getOne(120L);
//        DingUser dingUser = new DingUser();
//        dingUser.setUserid("15716230092609128");
//        dingUser.setDepteId(1);
//       companyDingService.updateApproval(resouces,  dingUser);
        CompanyInfo companyInfo = companyInfoRepository.getOne(1L);
        String str = "45.050";
        if(str.indexOf(".") > 0){
            str = str.replaceAll("0+?$", "");//去掉多余的0
            str = str.replaceAll("[.]$", "");//如最后一位是.则去掉
        }


        System.out.println(companyInfo.getRegisterfund());
        System.out.println(str);

    }

}

