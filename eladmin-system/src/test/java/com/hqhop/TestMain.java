package com.hqhop;
import com.alibaba.fastjson.JSON;
import com.hqhop.easyExcel.excelread.CustomerExcelUtils;
import com.hqhop.easyExcel.model.IncCustomer;
import com.hqhop.modules.company.domain.Account;
import com.hqhop.modules.company.domain.CompanyInfo;
import com.hqhop.modules.company.domain.Contact;
import com.hqhop.modules.company.domain.U8cDomain.U8cAccount;
import com.hqhop.modules.company.domain.U8cDomain.U8cContact;
import com.hqhop.modules.company.repository.AccountRepository;
import com.hqhop.modules.company.repository.CompanyInfoRepository;
import com.hqhop.modules.company.repository.ContactRepository;
import com.hqhop.modules.company.service.mapper.CompanyU8cService;
import com.hqhop.modules.system.repository.*;
import com.hqhop.modules.system.service.UserService;
import com.hqhop.utils.HttpUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

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

        CompanyInfo companyInfo = companyInfoRepository.findByTaxIdAndBelongCompany("91510100740341476L", "1007");
        companyInfo.setCustomerProp("3");
        companyU8cService.updateToU8C(companyInfo,"107677");
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        String url = "http://119.6.33.92:8087/service/UpdateCubasdocData";
        LinkedMultiValueMap<Object, Object> map = new LinkedMultiValueMap<>();
        map.add("custcode", "91130100104327075G");////客商编码
        map.add("custname", companyInfo.getCompanyName()!=null?companyInfo.getCompanyName():"");//客商名称
        map.add("custshortname", companyInfo.getCompanyShortName()!=null?companyInfo.getCompanyShortName():"");//简称名称

        CompanyInfo byCompanyKey = companyInfoRepository.findByCompanyKey(companyInfo.getParentCompanyId());

        map.add("pk_cubasdoc1",byCompanyKey!=null?byCompanyKey.getTaxId():""); //客商总公司编码
        map.add("custprop", Optional.ofNullable(companyInfo.getCustomerType().toString()).orElse(""));//客商类型
        map.add("custflag", Optional.ofNullable(companyInfo.getCustomerProp()).orElse(""));//客商属性


        map.add("pk_areacl", companyInfo.getBelongArea());//所属地区

        map.add("pk_corp","10"); //组织
        map.add("csid", "107677");        //客商id   `


        Set<Contact> contacts= contactRepository.findByCompanyKey(companyInfo.getCompanyKey());
        Set<Account> accounts = accountRepository.findByCompanyKey(companyInfo.getCompanyKey());
        map.add("addrlist",  JSON.toJSONString(U8cContact.getListBySetContact(contacts,companyInfo.getBelongArea())));  //联系人集合
        map.add("banklist", JSON.toJSONString(U8cAccount.getListBySetAccount(accounts)));//银行信息

        if(companyInfo.getCustomerProp().equals("1")){
            map.add("custflaglist", "[{\"custflag\":\"0\"},{\"custflag\":\"4\"}]");//客商属性
        }else  if(companyInfo.getCustomerProp().equals("2")){
            map.add("custflaglist", "[{\"custflag\":\"1\"},{\"custflag\":\" \"}]");//客商属性
        }else  if(companyInfo.getCustomerProp().equals("3")){
            map.add("custflaglist", "[{\"custflag\":\"2\"},{\"custflag\":\"3\"}]");//客商属性
        }
        map.add("cooperateflag","N"); //是否收付协同

        if(companyInfo.getCompanyState().equals("4") && companyInfo.getIsDisable() == 1) {
            map.add("custstate", "1");   //客商状态
        } else if(companyInfo.getCompanyState().equals("4") && companyInfo.getIsDisable() == 1){
            map.add("custstate", "2");   //客商状态
        } else {
            map.add("custstate", "0");   //客商状态
        }


        if(companyInfo.getBelongCompany().equals("100301") || companyInfo.getBelongCompany().equals("1003") || companyInfo.getBelongCompany().equals("100503")
                || companyInfo.getBelongCompany().equals("1007") || companyInfo.getBelongCompany().equals("1008") ||
                companyInfo.getBelongCompany().equals("1009") ||  companyInfo.getBelongCompany().equals("1010") || companyInfo.getBelongCompany().equals("1001")  ){

//            map.add("pk_salestru",Optional.ofNullable(companyInfo.getBelongCompany()).orElse(""));   //销售组织
//            map.add("pk_calbody",companyInfo.getBelongCompany());    //库存组织

        }else {
            map.add("pk_salestru",Optional.ofNullable(companyInfo.getBelongCompany()).orElse(""));   //销售组织
            map.add("pk_calbody",companyInfo.getBelongCompany());    //库存组织

        }
        map.add("pk_respdept1",Optional.ofNullable(companyInfo.getChargeDepartment()).orElse(""));  //专管部门
        map.add("pk_resppsn1",Optional.ofNullable(companyInfo.getProfessionSalesman()).orElse("")); //专管业务员
        map.add("creditlevel",companyInfo.getCreditRating());                    //信用等级
        map.add("sealflag",companyInfo.getIsDisable()!=1?"N":"Y");           //是否封存


        map.add("creator", Optional.ofNullable(companyInfo.getCreateMan()).orElse(""));  //创建人
        map.add("createtime",Optional.ofNullable(df.format(LocalDateTime.now())).orElse("") );  //创建时间
        map.add("modifier", Optional.ofNullable(companyInfo.getUpdateMan()).orElse(""));    //修改人
        map.add("modifytime",  Optional.ofNullable(df.format(LocalDateTime.now())).orElse(""));  //修改时间

        map.add("iscanpurchased", "Y");  //
        map.add("iscansold","Y");  //
        ResponseEntity<String> stringResponseEntity = HttpUtil.postRequest(url, map);
        if(stringResponseEntity.getBody().contains("成功")){
            System.out.println("u8c修改客商成功"+stringResponseEntity.getBody());
        }else {
            System.out.println(stringResponseEntity.getBody());
        }


    }

    @Test
    public void Test2() {

        CompanyInfo companyInfo = companyInfoRepository.findByTaxIdAndBelongCompany("91510100740341476L", "10");
          companyU8cService.updateToU8C( companyInfo ,"107677");
    }


}
