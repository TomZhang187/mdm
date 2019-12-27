package com.hqhop;


import com.hqhop.easyExcel.excelRead.CustomerExcelUtils;
import com.hqhop.easyExcel.model.IncClient;
import com.hqhop.easyExcel.model.IncSupplier;
import com.hqhop.modules.company.domain.CompanyInfo;
import com.hqhop.modules.company.domain.Contact;
import com.hqhop.modules.company.repository.AccountRepository;
import com.hqhop.modules.company.repository.CompanyInfoRepository;
import com.hqhop.modules.company.repository.ContactRepository;
import com.hqhop.modules.system.domain.Dict;
import com.hqhop.modules.system.domain.DictDetail;
import com.hqhop.modules.system.repository.DictDetailRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

            if(incClient.getCustomerCode() == null){
                continue;
            }
            CompanyInfo companyInfo =new  CompanyInfo();
            DictDetail dictDetail = dictDetailRepository.findByLabelAndDict_Id(incClient.getBelongCompany()!=null?incClient.getBelongCompany():"无",8L);
            if(dictDetail !=null){
               companyInfo.setBelongCompany(dictDetail.getValue());
            }
            companyInfo.setTaxId(incClient.getCustomerCode());
            companyInfo.setCompanyName(incClient.getCustomerNmae());
            companyInfo.setCompanyShortName(incClient.getCustomerShortName());

            DictDetail dictDetail1 = null;
            if(incClient.getBelongArea()!=null){
                dictDetail1 = dictDetailRepository.findLikeLabelAndDict_Id(incClient.getBelongArea(),10L);
            }
            if(dictDetail1!=null ){
                companyInfo.setBelongArea(dictDetail1.getValue());
            }

            DictDetail dictDetail2 = dictDetailRepository.findByLabelAndDict_Id(incClient.getEconomyType()!=null?incClient.getEconomyType():"无",11L);
            if(dictDetail2!=null){
                companyInfo.setCompanyProp(dictDetail2.getValue());
            }





            //4 外部单位
            companyInfo.setCustomerType("4");

            companyInfo.setRemark(incClient.getRemark());
            companyInfo.setContactAddress(incClient.getContactAddress());




            if(companyInfo.getBelongCompany()!=null ){
                CompanyInfo companyInfo1 = companyInfoRepository.findByTaxIdAndBelongCompany(incClient.getCustomerCode(), companyInfo.getBelongCompany());

                if(companyInfo1 != null && companyInfo.getCompanyType().equals("2") ){
                    //客商
                    companyInfo.setCompanyType("3");
                }else if(companyInfo1 == null ) {
                    //供应商
                    companyInfo.setCompanyType("2");
                }
            }

            if(companyInfo.getCompanyType() == null){
                continue;
            }


                //1 启用
                companyInfo.setIsDisable(1);
                //4 审批通过
                companyInfo.setCompanyState(4);


                companyInfoRepository.save(companyInfo);

        }


        Test2();

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


            if(companyInfo.getBelongCompany()!=null ){
                CompanyInfo companyInfo1 = companyInfoRepository.findByTaxIdAndBelongCompany(incSupplier.getCustomerCode(), companyInfo.getBelongCompany());

                if(companyInfo1 != null && companyInfo.getCompanyType().equals("1") ){
                    //客商
                    companyInfo.setCompanyType("3");
                }else if(companyInfo1 == null ) {
                    //供应商
                    companyInfo.setCompanyType("2");
                }
            }


            if(companyInfo.getCompanyType() == null){
                continue;
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
                if(companyInfo1.getCompanyType()!=null){
                    contact.setContactType(Integer.parseInt(companyInfo1.getCompanyType()));
                }

                contact.setContactState(4);
                contactRepository.save(contact);



                if(incSupplier.getPager() != null && incSupplier.getPhone()!=null){
                    Contact contact2 = new Contact();
                    contact2.setPhone(incSupplier.getPhone());
                    contact2.setContactName(incSupplier.getPager());
                    contact2.setDeliveryAddress(companyInfo.getContactAddress());
                    contact2.setCompanyKey(companyInfo1.getCompanyKey());
                    if(companyInfo1.getCompanyType()!=null){
                        contact2.setContactType(Integer.parseInt(companyInfo1.getCompanyType()));
                    }
                    contact2.setContactState(4);
                    contactRepository.save(contact2);
                }else if(incSupplier.getContactOne()!= null && incSupplier.getPhone()!=null ){
                    Contact contact2 = new Contact();
                    contact2.setPhone(incSupplier.getPhone());
                    contact2.setContactName(incSupplier.getContactOne());
                    contact2.setDeliveryAddress(companyInfo.getContactAddress());
                    contact2.setCompanyKey(companyInfo1.getCompanyKey());
                    if(companyInfo1.getCompanyType()!=null){
                        contact2.setContactType(Integer.parseInt(companyInfo1.getCompanyType()));
                    }
                    contact2.setContactState(4);
                    contactRepository.save(contact2);
                }


            }










        }














    }











}
