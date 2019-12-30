package com.hqhop;


import com.alibaba.fastjson.JSON;
import com.hqhop.easyExcel.excelRead.CustomerExcelUtils;
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
import com.hqhop.modules.system.domain.Dict;
import com.hqhop.modules.system.domain.DictDetail;
import com.hqhop.modules.system.repository.DictDetailRepository;
import com.hqhop.utils.HttpUtil;
import com.taobao.api.ApiException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;

import javax.persistence.*;
import java.math.BigDecimal;
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
        List<IncClient> incClients = CustomerExcelUtils.readClitentExcel("D:\\easyExcel\\客商档案读取.xlsx");
        for (IncClient incClient : incClients) {

            if (incClient.getCustomerCode() == null) {
                continue;
            }

            CompanyInfo companyInfo = new CompanyInfo();
            DictDetail dictDetail = dictDetailRepository.findByLabelAndDict_Id(incClient.getBelongCompany() != null ? incClient.getBelongCompany() : "无", 8L);

            CompanyInfo byTaxIdAndBelongCompany = null;
            if (dictDetail != null) {
                companyInfo.setBelongCompany(dictDetail.getValue());
                 byTaxIdAndBelongCompany = companyInfoRepository.findByTaxIdAndBelongCompany(incClient.getCustomerCode(), dictDetail.getValue());
            }


            if(byTaxIdAndBelongCompany != null){
                continue;
            }
            companyInfo.setTaxId(incClient.getCustomerCode());
            companyInfo.setCompanyName(incClient.getCustomerNmae());
            companyInfo.setCompanyShortName(incClient.getCustomerShortName());
            DictDetail dictDetail1 = null;
            if (incClient.getBelongArea() != null) {
                dictDetail1 = dictDetailRepository.findLikeLabelAndDict_Id(incClient.getBelongArea(), 10L);
            }
            if (dictDetail1 != null) {
                companyInfo.setBelongArea(dictDetail1.getValue());
            }

            DictDetail dictDetail2 = dictDetailRepository.findByLabelAndDict_Id(incClient.getEconomyType() != null ? incClient.getEconomyType() : "无", 11L);
            if (dictDetail2 != null) {
                companyInfo.setCompanyProp(dictDetail2.getValue());
            }


            //4 外部单位
            companyInfo.setCustomerType("4");
            companyInfo.setCustomerProp("1");

            companyInfo.setRemark(incClient.getRemark());
            companyInfo.setContactAddress(incClient.getContactAddress());


            if (companyInfo.getBelongCompany() != null) {
                CompanyInfo companyInfo1 = companyInfoRepository.findByTaxIdAndBelongCompany(incClient.getCustomerCode(), companyInfo.getBelongCompany());
                //1 启用
                companyInfo.setIsDisable(1);
                //4 审批通过
                companyInfo.setCompanyState(4);
                companyInfoRepository.save(companyInfo);

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

       CompanyInfo companyInfo = companyInfoRepository.findByCompanyKey(107669L);

        String url = "http://119.6.33.92:8087/service/AddCubasdocData";

        LinkedMultiValueMap<Object, Object> map = new LinkedMultiValueMap<>();
        map.add("custcode", companyInfo.getTaxId()!=null?companyInfo.getTaxId():"");////客商编码
        map.add("custname", companyInfo.getCompanyName()!=null?companyInfo.getCompanyName():"");//客商名称
        map.add("custshortname", companyInfo.getCompanyShortName()!=null?companyInfo.getCompanyShortName():"");//简称名称

        CompanyInfo byCompanyKey = companyInfoRepository.findByCompanyKey(companyInfo.getParentCompanyId());
        map.add("pk_cubasdoc1",byCompanyKey!=null?byCompanyKey.getTaxId():""); //客商总公司编码
        map.add("custprop", Optional.ofNullable(companyInfo.getCustomerType().toString()).orElse(""));//客商类型
        map.add("custflag", Optional.ofNullable(companyInfo.getCustomerProp()).orElse(""));//客商属性

        map.add("pk_corp1", companyInfo.getBelongCompany());//对应公司

        map.add("pk_areacl", companyInfo.getBelongArea());//所属地区

        map.add("pk_corp",companyInfo.getBelongCompany()); //组织
        map.add("csid","35467");        //客商id   `


        Set<Contact> contacts= contactRepository.findByCompanyKey(companyInfo.getCompanyKey());
        Set<Account> accounts = accountRepository.findByCompanyKey(companyInfo.getCompanyKey());
        map.add("addrlist",  JSON.toJSONString(U8cContact.getListBySetContact(contacts,companyInfo.getBelongArea())));  //联系人集合
        map.add("banklist", JSON.toJSONString(U8cAccount.getListBySetAccount(accounts)));//银行信息
        map.add("custflaglist", "[{\"custflag\":\"2\"},{\"custflag\":\"3\"}]");//客商属性
        map.add("cooperateflag","N"); //是否收付协同

        map.add("pk_salestru",Optional.ofNullable(companyInfo.getBelongCompany()).orElse(""));   //销售组织
        map.add("pk_calbody",companyInfo.getBelongCompany());    //库存组织
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
        String s = HttpUtil.postRequest(url, map);
        System.out.println(s);
        System.out.println(companyInfo.toString());

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
        map.add("pk_corp1", "1099");//对应公司
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



     @Test
    public void  test12() {


         CompanyInfo byCompanyKey = companyInfoRepository.findByCompanyKey(107675L);
         CompanyInfo companyInfo = new CompanyInfo();
         companyInfo.copy(byCompanyKey);
         companyInfo.setCompanyKey(null);
         Set<Contact> contacts = byCompanyKey.getContacts();
         for (Contact contact : contacts) {
             contact.setContactKey(null);
         }
         companyInfo.setContacts(contacts);
         System.out.println(companyInfo);




//         List<DictDetail> allByDictId = dictDetailRepository.findAllByDictId(5L);
//         for (DictDetail dictDetail : allByDictId) {
//             System.out.println(dictDetail);
//         }
//         for (DictDetail dictDetail : allByDictId) {
//             if(!dictDetail.getValue().equals("10")){
//
//                 List<CompanyInfo> all = companyInfoRepository.findAll();
//                 for (CompanyInfo companyInfo : all) {
//                     CompanyInfo companyInfo1 = new CompanyInfo();
//                     companyInfo1.copy(companyInfo);
//                     companyInfo1.getContacts().addAll(companyInfo.getContacts());
//                 }
//
//
//             }
//         }
     }





}
