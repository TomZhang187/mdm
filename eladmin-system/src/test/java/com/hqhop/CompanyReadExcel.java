package com.hqhop;


import com.alibaba.fastjson.JSON;
import com.hqhop.easyExcel.excelread.CustomerExcelUtils;
import com.hqhop.easyExcel.model.IncClient;
import com.hqhop.easyExcel.model.IncSupplier;
import com.hqhop.modules.company.domain.Account;
import com.hqhop.modules.company.domain.CompanyInfo;
import com.hqhop.modules.company.domain.Contact;
import com.hqhop.modules.company.domain.U8cDomain.U8cAccount;
import com.hqhop.modules.company.domain.U8cDomain.U8cContact;
import com.hqhop.modules.company.repository.AccountRepository;
import com.hqhop.modules.company.repository.CompanyInfoRepository;
import com.hqhop.modules.company.repository.ContactRepository;
import com.hqhop.modules.system.domain.DictDetail;
import com.hqhop.modules.system.repository.DictDetailRepository;
import com.hqhop.utils.HttpUtil;
import com.taobao.api.ApiException;
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
public class CompanyReadExcel {

    @Autowired
    private CompanyInfoRepository companyInfoRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private DictDetailRepository dictDetailRepository;




    //客户档案读取

    @Test
    public void Test1(){
        List<IncClient> incClients = CustomerExcelUtils.readClitentExcel("D:\\easyExcel\\客户档案读取.xlsx");
        for (IncClient incClient : incClients) {

            CompanyInfo companyInfo = new CompanyInfo();
            DictDetail dictDetail = dictDetailRepository.findByLabelAndDict_Id(incClient.getBelongCompany() , 5L);
            if (dictDetail != null) {
                companyInfo.setBelongCompany(dictDetail.getValue());
            }

            companyInfo.setTaxId(incClient.getCustomerCode());
            companyInfo.setCompanyName(incClient.getCustomerNmae());
            companyInfo.setCompanyShortName(incClient.getCustomerShortName());
            DictDetail dictDetail1 = null;
            if (incClient.getBelongArea() != null) {
                dictDetail1 = dictDetailRepository.findLikeLabelAndDict_Id(incClient.getBelongArea(), 16L);
            }
            if (dictDetail1 != null) {
                companyInfo.setBelongArea(dictDetail1.getValue());
            }

            DictDetail dictDetail2 = dictDetailRepository.findByLabelAndDict_Id(incClient.getEconomyType() != null ? incClient.getEconomyType() : "无", 6L);
            if (dictDetail2 != null) {
                companyInfo.setCompanyProp(dictDetail2.getValue());
            }


            //4 外部单位
            companyInfo.setCustomerType("4");
            companyInfo.setCustomerProp("1");
            companyInfo.setRemark(incClient.getRemark());
            companyInfo.setContactAddress(incClient.getContactAddress());


            if (companyInfo.getBelongCompany() != null) {

                //1 启用
                companyInfo.setIsDisable(1);
                //4 审批通过
                companyInfo.setCompanyState(4);

                CompanyInfo companyInfo1 = companyInfoRepository.findByTaxIdAndBelongCompany(companyInfo.getTaxId(), companyInfo.getBelongCompany());
                if(companyInfo1 == null ){

                    companyInfo.setCustomerProp("1");
                    companyInfoRepository.save(companyInfo);

                } else if(companyInfo1 != null && companyInfo1.getCustomerProp().equals("2")){
                    //客商
                    companyInfo1.setCustomerProp("3");
                    companyInfoRepository.save(companyInfo1);
                }
            }

        }
    }


    //供应商档案读取
    @Test
    public void Test2(){

        List<IncSupplier> incSuppliers = CustomerExcelUtils.readSupplierExcel("D:\\easyExcel\\供应商读取.xlsx");
        for (IncSupplier incSupplier : incSuppliers) {
            CompanyInfo companyInfo = new CompanyInfo();

            DictDetail dictDetail = dictDetailRepository.findByLabelAndDict_Id(incSupplier.getBelongCompany()!=null?incSupplier.getBelongCompany():"无",8L);
            if(dictDetail!=null){
                companyInfo.setBelongCompany(dictDetail.getValue());
            }
            companyInfo.setCreateMan(incSupplier.getNetMan());
            companyInfo.setTaxId(incSupplier.getCustomerCode());
            companyInfo.setCompanyName(incSupplier.getCustomerName());
            companyInfo.setCompanyShortName(incSupplier.getCustomerShortName());
            companyInfo.setContactAddress(incSupplier.getContactAddress());
            //4 外部单位
            companyInfo.setCustomerType("4");

            //1 启用
            companyInfo.setIsDisable(1);
            //4 审批通过
            companyInfo.setCompanyState(4);

            DictDetail dictDetail1 = null;
            if(incSupplier.getBelongArea()!=null){
                dictDetail1 = dictDetailRepository.findLikeLabelAndDict_Id(incSupplier.getBelongArea(),10L);
            }
            if(dictDetail1!=null ){
                companyInfo.setBelongArea(dictDetail1.getValue());
            }


            companyInfo.setContactAddress(incSupplier.getContactAddress());
            companyInfo.setChargeDepartment(incSupplier.getChargeDepartment());
            companyInfo.setProfessionSalesman(incSupplier.getProfessionSalesman());
            companyInfo.setDefaultPaymentAgreement(incSupplier.getDefaultPaymentAgreement());

            CompanyInfo companyInfo1 = companyInfoRepository.save(companyInfo);


            if(incSupplier.getPhoneOne()!=null && incSupplier.getContactOne()!= null){
                Contact contact = new Contact();
                contact.setPhone(incSupplier.getPhoneOne());
                contact.setContactName(incSupplier.getContactOne());
                contact.setDeliveryAddress(companyInfo.getContactAddress());
                contact.setCompanyKey(companyInfo1.getCompanyKey());
                if(companyInfo1.getCustomerProp()!=null){
                    contact.setContactType(companyInfo1.getCustomerProp());
                }

                contact.setContactState(4);
                contactRepository.save(contact);



                if(incSupplier.getPager() != null && incSupplier.getPhone()!=null){
                    Contact contact2 = new Contact();
                    contact2.setPhone(incSupplier.getPhone());
                    contact2.setContactName(incSupplier.getPager());
                    contact2.setDeliveryAddress(companyInfo.getContactAddress());
                    contact2.setCompanyKey(companyInfo1.getCompanyKey());
                    if(companyInfo1.getCustomerProp()!=null){
                        contact2.setContactType(companyInfo1.getCustomerProp());
                    }
                    contact2.setContactState(4);
                    contactRepository.save(contact2);
                }else if(incSupplier.getContactOne()!= null && incSupplier.getPhone()!=null ){
                    Contact contact2 = new Contact();
                    contact2.setPhone(incSupplier.getPhone());
                    contact2.setContactName(incSupplier.getContactOne());
                    contact2.setDeliveryAddress(companyInfo.getContactAddress());
                    contact2.setCompanyKey(companyInfo1.getCompanyKey());
                    if(companyInfo1.getCustomerProp()!=null){
                        contact2.setContactType(companyInfo1.getCustomerProp());
                    }
                    contact2.setContactState(4);
                    contactRepository.save(contact2);
                }
            }
        }
    }




    //客商基本档案
    @Test
    public void test7() throws
            ApiException {

        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        int succeed = 0;
        int failure = 0;

//       CompanyInfo companyInfo = companyInfoRepository.findByCompanyKey(1505565L);

        String url = "http://119.6.33.92:8088/service/AddCubasdocData";
        String csid = null;
        String belongCompanuy = null;
        List<CompanyInfo> all = companyInfoRepository.findByBelongCompany("1003");
        for (CompanyInfo companyInfo2 : all) {
            CompanyInfo companyInfo3 = companyInfoRepository.findByTaxIdAndBelongCompany(companyInfo2.getTaxId(), "10");
            csid=companyInfo3.getCompanyKey().toString();

            List<CompanyInfo> alls = companyInfoRepository.findByTaxIdAndBelongCompanys(companyInfo2.getTaxId(), "1001");



            for (CompanyInfo companyInfo : alls) {

        LinkedMultiValueMap<Object, Object> map = new LinkedMultiValueMap<>();
        map.add("custcode", companyInfo.getTaxId()!=null?companyInfo.getTaxId():"");////客商编码
        map.add("custname", companyInfo.getCompanyName()!=null?companyInfo.getCompanyName():"");//客商名称
        map.add("custshortname", companyInfo.getCompanyShortName()!=null?companyInfo.getCompanyShortName():"");//简称名称

        CompanyInfo byCompanyKey = companyInfoRepository.findByCompanyKey(companyInfo.getParentCompanyId());
        map.add("pk_cubasdoc1",byCompanyKey!=null?byCompanyKey.getTaxId():""); //客商总公司编码
        map.add("custprop", Optional.ofNullable(companyInfo.getCustomerType().toString()).orElse(""));//客商类型
        map.add("custflag", Optional.ofNullable(companyInfo.getCustomerProp()).orElse(""));//客商属性


        map.add("pk_areacl", companyInfo.getBelongArea());//所属地区

        map.add("pk_corp",companyInfo.getBelongCompany()); //组织
        map.add("csid", csid);        //客商id   `


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
        map.add("creditlevel",companyInfo.getCreditRating());                    //行用等级
        map.add("sealflag",companyInfo.getIsDisable()!=1?"N":"Y");           //是否封存


        map.add("creator", Optional.ofNullable(companyInfo.getCreateMan()).orElse(""));  //创建人
        map.add("createtime",Optional.ofNullable(df.format(LocalDateTime.now())).orElse("") );  //创建时间
        map.add("modifier", Optional.ofNullable(companyInfo.getUpdateMan()).orElse(""));    //修改人
        map.add("modifytime",  Optional.ofNullable(df.format(LocalDateTime.now())).orElse(""));  //修改时间

        map.add("iscanpurchased", "Y");  //
        map.add("iscansold","Y");  //
            ResponseEntity<String> stringResponseEntity = HttpUtil.postRequest(url, map);
            System.out.println(stringResponseEntity.getBody());
        System.out.println(stringResponseEntity.getStatusCode().value());
        if(stringResponseEntity.getBody().contains("成功")){
            succeed++;
        }else {
            failure++;
        }
        }

       }
        System.out.println("成功"+succeed+"  "+"失败"+failure);

    }






   @Test
   public void Test12(){


       List<CompanyInfo> companyInfos = companyInfoRepository.findByCustomerProp("1");
       for (CompanyInfo companyInfo : companyInfos) {

           if(companyInfo.getBelongCompany().equals("10")){
               continue;
           }

           CompanyInfo byTaxIdAndBelongCompany = companyInfoRepository.findByTaxIdAndBelongCompany(companyInfo.getTaxId(), "10");
           if(byTaxIdAndBelongCompany !=null){

               continue;
           }

           CompanyInfo companyInfo1=new CompanyInfo();
           companyInfo1.setTaxId(companyInfo.getTaxId());
           companyInfo1.setCompanyName(companyInfo.getCompanyName());
           companyInfo1.setCompanyShortName(companyInfo.getCompanyShortName());
           companyInfo1.setParentCompanyId(companyInfo.getParentCompanyId());
           companyInfo1.setCustomerProp("1");
           companyInfo1.setCreditRating(companyInfo.getCreditRating());
           companyInfo1.setCreateMan(companyInfo.getCreateMan());
           companyInfo1.setCustomerType(companyInfo.getCustomerType());
           companyInfo1.setBelongArea(companyInfo.getBelongArea());
           companyInfo1.setContactAddress(companyInfo.getContactAddress());
           companyInfo1.setIsDisable(1);
           companyInfo1.setCompanyState(4);
           companyInfo1.setProfessionSalesman(null);
           companyInfo1.setCreateTime(new Timestamp(new Date().getTime()));
           companyInfo1.setBelongCompany("10");
           CompanyInfo save = companyInfoRepository.save(companyInfo1);
       }

   }




    @Test
    public void test15() throws
            ApiException {

        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


//       CompanyInfo companyInfo = companyInfoRepository.findByCompanyKey(1505565L);

        List<String> listResult = new  ArrayList();

        String url = "http://119.6.33.92:8088/service/AddCubasdocData";
        String csid = null;
        String belongCompanuy = null;

        List<String> list = new ArrayList();
        list.add("100501");
        list.add("100502");
        list.add("100503");
        list.add("1006");
        list.add("1007");
        list.add("1008");
        list.add("1009");
        list.add("1010");
        list.add("1099");

        for (String s : list) {

            int succeed = 0;
            int failure = 0;


        List<CompanyInfo> all = companyInfoRepository.findByCustomerPropAndBelongCompany("1","10");
        for (CompanyInfo companyInfo : all) {
//            csid=companyInfo2.getCompanyKey().toString();
//            List<CompanyInfo> alls = companyInfoRepository.findByTaxIdAndBelongCompanys(companyInfo2.getTaxId(), "1002");
//            for (CompanyInfo companyInfo : alls) {
            companyInfo.setBelongCompany(s);

                LinkedMultiValueMap<Object, Object> map = new LinkedMultiValueMap<>();
                map.add("custcode", companyInfo.getTaxId()!=null?companyInfo.getTaxId():"");////客商编码
                map.add("custname", companyInfo.getCompanyName()!=null?companyInfo.getCompanyName():"");//客商名称
                map.add("custshortname", companyInfo.getCompanyShortName()!=null?companyInfo.getCompanyShortName():"");//简称名称

                CompanyInfo byCompanyKey = companyInfoRepository.findByCompanyKey(companyInfo.getParentCompanyId());
                map.add("pk_cubasdoc1",byCompanyKey!=null?byCompanyKey.getTaxId():""); //客商总公司编码
                map.add("custprop", Optional.ofNullable(companyInfo.getCustomerType().toString()).orElse(""));//客商类型
                map.add("custflag", Optional.ofNullable(companyInfo.getCustomerProp()).orElse(""));//客商属性


                map.add("pk_areacl", companyInfo.getBelongArea());//所属地区

                map.add("pk_corp",companyInfo.getBelongCompany()); //组织
                map.add("csid", companyInfo.getCompanyKey().toString());        //客商id   `


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
                System.out.println(stringResponseEntity.getBody());
                System.out.println(stringResponseEntity.getStatusCode().value());
                if(stringResponseEntity.getBody().contains("成功")){
                    succeed++;
                }else {
                    failure++;
                }
            }

//        }

            listResult.add(s+"  "+"成功"+succeed+"  "+"失败"+failure);
            System.out.println("成功"+succeed+"  "+"失败"+failure);
        }
        for (String s : listResult) {
            System.out.println(s);
        }

    }










    //客商管理档案

    @Test
    public void test8() throws
            ApiException {


        String url = "http://119.6.33.92:8087/service/AddCubasdocData";
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LinkedMultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("custcode", "91610000681557372F");//客商编码
        map.add("custname", "测试公司名");//客商名称
        map.add("custshortname", "91130100754027891A");//客商简称
        map.add("pk_cubasdoc1", "91130100754027891A"); //客商总公司编码
        map.add("custprop", "0");//客商类型
//        map.add("pk_corp1", "1099");//对应公司
        map.add("pk_areacl", "0201");//所属地区
        map.add("creator", "ALHP0001");  //创建人
        map.add("createtime", df.format(LocalDateTime.now()));  //创建时间
        map.add("modifier", "ALHP0001");    //修改人
        map.add("modifytime", df.format(LocalDateTime.now()));  //修改时间
        map.add("pk_corp", "1099"); //组织
        map.add("csid", "1232236");        //客商id   `


//        [{"addrname":"123","linkman":"123","phone":"123","defaddrflag":"Y","pk_areacl":"0201","cs_addr_id":"76"}]
//        [{"accname":"123","account":"123456789","pk_currtype":"CNY","defflag":"Y","cs_bank_id":"76"}]
        Set<Contact> contacts= contactRepository.findByCompanyKey(35463L);
        Set<Account> accounts = accountRepository.findByCompanyKey(35463L);
        map.add("addrlist", "[{\"addrname\":\"123\",\"linkman\":\"123\",\"phone\":\"123\",\"defaddrflag\":\"Y\",\"pk_areacl\":\"0201\",\"cs_addr_id\":\"76\"}]");  //联系人集合
        map.add("banklist", "[{\"accname\":\"123\",\"account\":\"123456789\",\"pk_currtype\":\"CNY\",\"defflag\":\"Y\",\"cs_bank_id\":\"76\"}]");//银行信息

        map.add("custflaglist", "[{\"custflag\":\"2\"},{\"custflag\":\"3\"}]");//客商属性
        map.add("cooperateflag", "N"); //是否收付协同

//        map.add("pk_accbank", "sfsf"); //是否收付协同

        map.add("pk_salestru", "1001");   //销售组织
        map.add("pk_calbody", "1001");    //库存组织
        map.add("pk_respdept1", "09");  //专管部门
        map.add("pk_resppsn1", "ALHP0001"); //专管业务员
        map.add("creditlevel", "01");                    //行用等级
        map.add("sealflag", "N");                    //是否封存
        map.add("csmid", "100000");           //是否封存
        String s = HttpUtil.getRequest(url, map);


        System.out.println(s);

    }


//   @Transactional
     @Test
    public void  test12() {

//         CompanyInfo byCompanyKey = companyInfoRepository.findByCompanyKey(107675L);
//         CompanyInfo companyInfo = new CompanyInfo();
//         companyInfo.copy(byCompanyKey);
//         companyInfo.setCompanyKey(null);
//         Set<Contact> contacts = contactRepository.findByCompanyKey(byCompanyKey.getCompanyKey());
//         for (Contact contact : contacts) {
//             contact.setContactKey(null);
//         }
//         companyInfo.setContacts(contacts);
//         System.out.println(companyInfo);


//         List<Contact> contacts2 = contactRepository.findByNotBelongCompany("10");
//         for (Contact contact : contacts2) {
//             contactRepository.deleteByContactKey(contact.getContactKey());
//         }
//
//         List<CompanyInfo> all2 = companyInfoRepository.findByNotBelongCompany("10");
//         for (CompanyInfo companyInfo : all2) {
//             companyInfoRepository.deleteById(companyInfo.getCompanyKey());
//         }
         List<CompanyInfo> all = companyInfoRepository.findByBelongCompany("10");
                 for (CompanyInfo companyInfo : all) {
                     List<CompanyInfo> byTaxIdAndBelongCompany = companyInfoRepository.findByTaxIdAndBelongCompanys(companyInfo.getTaxId(), "1008");
                     if (byTaxIdAndBelongCompany!=null && byTaxIdAndBelongCompany.size()>0) {
                         for (CompanyInfo info : byTaxIdAndBelongCompany) {
                             info.setProfessionSalesman(null);
                             companyInfoRepository.save(info);
                         }

                     }
                     CompanyInfo companyInfo1=new CompanyInfo();
//                     BeanUtils.copyProperties(companyInfo,companyInfo1);
                     companyInfo1.setTaxId(companyInfo.getTaxId());
                    companyInfo1.setCompanyName(companyInfo.getCompanyName());
                    companyInfo1.setCompanyShortName(companyInfo.getCompanyShortName());
                    companyInfo1.setParentCompanyId(companyInfo.getParentCompanyId());
                    companyInfo1.setCustomerProp("2");
                    companyInfo1.setCreditRating(companyInfo.getCreditRating());
                    companyInfo1.setCreateMan(companyInfo.getCreateMan());
                    companyInfo1.setCustomerType(companyInfo.getCustomerType());
                    companyInfo1.setBelongArea(companyInfo.getBelongArea());
                    companyInfo1.setContactAddress(companyInfo.getContactAddress());
                    companyInfo1.setIsDisable(1);
                    companyInfo1.setCompanyState(4);
                     companyInfo1.setProfessionSalesman(null);
                     companyInfo1.setCreateTime(new Timestamp(new Date().getTime()));
                     companyInfo1.setBelongCompany("1008");
                     CompanyInfo save = companyInfoRepository.save(companyInfo1);

//                     Set<Contact> contacts = contactRepository.findByCompanyKey(companyInfo.getCompanyKey());
//                     if(contacts !=null && contacts.size()!=0){
//                         for (Contact contact : contacts) {
//                             if(contact.getPhone() != null && contact.getContactName() != null){
//                                 Contact contact1 = new Contact();
//                                 contact1.setContactName(contact.getContactName());
//                                 contact1.setContactAddress(contact.getContactAddress());
//                                 contact1.setDeliveryAddress(contact.getDeliveryAddress());
//                                 contact1.setIsDefaultAddress(contact.getIsDefaultAddress());
//                                 contact1.setPhone(contact.getPhone());
//                                 contact1.setCompanyKey(save.getCompanyKey());
//                                 contact1.setContactState(4);
//                                 contact1.setContactType("2");
//                                 contactRepository.findBy
//
//                                 contactRepository.save(contact1);
//                             }
//                         }
//
//                     }

                 }
     }





}
