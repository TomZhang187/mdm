package com.hqhop;
import com.hqhop.modules.company.domain.CompanyInfo;
import com.hqhop.modules.company.repository.AccountRepository;
import com.hqhop.modules.company.repository.CompanyInfoRepository;
import com.hqhop.modules.company.repository.ContactRepository;
import com.hqhop.modules.company.service.CompanyU8cService;
import com.hqhop.modules.system.repository.DictDetailRepository;
import com.hqhop.modules.system.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.format.DateTimeFormatter;
import java.util.List;

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


    @Autowired
    private CompanyU8cService companyU8cService;

//    @Transactional
    @Test
    public void contextLoads() {

        List<CompanyInfo> alls = companyInfoRepository.findByCustomerPropAndBelongCompany("1", "10");

//        List<DictDetail> allByDictId = dictDetailRepository.findAllByDictId(5L);
//        for (DictDetail dictDetail : allByDictId) {
//              if(dictDetail.getValue().equals("10")){
//                  continue;
//              }
        for (CompanyInfo all : alls) {
            CompanyInfo companyInfo = companyInfoRepository.findByCustomerPropAndBelongCompanyAndTaxId("1", "1010", all.getTaxId());
            if(companyInfo != null){
                continue;
            }

          CompanyInfo companyInfo1 = new CompanyInfo();

            companyInfo1.getBasicAttribute(all);
            companyInfo1.setBelongCompany("1010");
            companyInfo1.setCompanyKey(null);
            companyInfo1.setCustomerProp("1");
          companyInfoRepository.save(companyInfo1);
        }


//        }
    }


    @Test
    public void Test1() {


        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        int succeed = 0;
        int failure = 0;

//       CompanyInfo companyInfo = companyInfoRepository.findByCompanyKey(1505565L);



      int one = 0;
      int two = 0;
      int threee = 0;
        String belongCompanuy = null;
        List<CompanyInfo> all = companyInfoRepository.findByBelongCompany("1003");
        for (CompanyInfo companyInfo2 : all) {

            List<CompanyInfo> alls = companyInfoRepository.findByTaxIdAndBelongCompanys(companyInfo2.getTaxId(), "10");
            for (CompanyInfo companyInfo : alls) {
                if(companyInfo.getCustomerProp().equals("1")){
                    one++;
            }else if(companyInfo.getCustomerProp().equals("2")) {
                    two++;
                }else if(companyInfo.getCustomerProp().equals("3")){
                    threee++;
                }
            }
        }
        System.out.println("客户"+one);
        System.out.println("供应商"+two);
        System.out.println("客商"+threee);




    }



    @Test
    public void Test() {

        List<CompanyInfo> 成都 = companyInfoRepository.findByLikeName("环保");
        for (CompanyInfo companyInfo : 成都) {

            System.out.println(companyInfo.getCompanyName());
        }





    }

//    @Test
//    public void Test2() {
//
//        CompanyInfo companyInfo = companyInfoRepository.findByTaxIdAndBelongCompany("91510100740341476L", "10");
//          companyU8cService.updateToU8C( companyInfo ,"107677");
//    }


}
