package com.hqhop;

import com.hqhop.easyExcel.excelread.CustomerExcelUtils;
import com.hqhop.easyExcel.model.IncCustomer;
import com.hqhop.modules.company.domain.CompanyInfo;
import com.hqhop.modules.company.repository.AccountRepository;
import com.hqhop.modules.company.repository.CompanyBasicRepository;
import com.hqhop.modules.company.repository.CompanyInfoRepository;
import com.hqhop.modules.company.repository.ContactRepository;
import com.hqhop.modules.company.service.CompanyU8cService;
import com.hqhop.modules.system.repository.DictDetailRepository;
import com.hqhop.modules.system.repository.EmployeeRepository;
import com.hqhop.modules.system.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SyncU8cTest {


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

    @Autowired
    private CompanyBasicRepository companyBasicRepository;

    @Autowired
    private EmployeeRepository employeeRepository;




    @Test
    public void Test1() {


        CompanyInfo save = companyInfoRepository.findByCompanyKey(930234L);

//        CompanyInfo save = companyInfoRepository.findByCompanyKey(929543L);
      companyU8cService.addToU8C(save,"930237");
//                save.setBelongCompany("100501");
//        CompanyBasic companyBasic = companyBasicRepository.findByTaxId(save.getTaxId());
//        if (companyBasic == null) {
//            CompanyBasic companyBasic1 = new CompanyBasic();
//            companyBasic1.setTaxId(save.getTaxId());
//            companyBasic1.setCompanyName(save.getCompanyName());
//            companyBasic1.setBelongArea(save.getBelongArea());
//            companyBasic1.setBelongCompany(save.getBelongCompany());
//            companyBasic1.setCompanyShortName(save.getCompanyShortName());
//            companyBasic1.setCustomerType(save.getCustomerType());
//            companyBasic1.setBelongCompany(save.getBelongCompany());
//
//            CompanyInfo companyInfo1 = companyInfoRepository.findByCompanyKey(save.getParentCompanyId());
//            companyBasic1.setHeadOfficeCode(companyInfo1 != null ? companyInfo1.getTaxId() : null);
//
//            companyBasic1.setCreateMan(save.getCreateMan());
//            companyBasic1.setCreateTime(save.getCreateTime());
//            companyBasic1.setUpdateMan(save.getUpdateMan());
//            companyBasic1.setUpdateTime(save.getUpdateTime());
//
//            CompanyBasic companyBasic2 = companyBasicRepository.save(companyBasic1);
//
//            companyU8cService.addToU8C(save, companyBasic2.getKey().toString());
//
//
//        } else {
//            companyU8cService.addToU8C(save, companyBasic.getKey().toString());
//        }
    }

    @Test
    public void Test2() {

        int t=0;
        List list = new ArrayList();
        List<IncCustomer> incCustomers = CustomerExcelUtils.readIncCustomer("C:\\Users\\Administrator\\Desktop\\数据\\股份公司供应商.xlsx");
        for (IncCustomer incCustomer : incCustomers) {

            CompanyInfo companyInfo = new CompanyInfo();
            companyInfo.setBelongCompany("10");
//            companyInfo.setBelongArea(dictDetailRepository.findLikeLabelAndDict_Id(incCustomer.getBelongArea(),16L).getValue());
            companyInfo.setCompanyName(incCustomer.getCompanyName());
            companyInfo.setTaxId(incCustomer.getTaxId());
            companyInfo.setCompanyShortName(incCustomer.getCompanyShortName());
//            companyInfo.setContactAddress(incCustomer.getContactAddress());
            companyInfo.setCustomerProp("2");
            companyInfo.setIsDisable(1);
            companyInfo.setCompanyState(1);
            companyInfo.setCreateMan("HQHP0933");

//            Employee byEmployeeName = employeeRepository.findByEmployeeName(incCustomer.getProfessionSalesman());
//            if(byEmployeeName !=null){
//                companyInfo.setProfessionSalesman(byEmployeeName.getEmployeeCode());
//            }



            CompanyInfo save = new CompanyInfo();

            CompanyInfo byTaxIdAndBelongCompany = companyInfoRepository.findByTaxIdAndBelongCompany(incCustomer.getTaxId().trim(), "10");
              if(byTaxIdAndBelongCompany != null){


//               save = byTaxIdAndBelongCompany;
              } else {
                  System.out.println("未有编码"+incCustomer.getTaxId());
                  list.add(incCustomer.getTaxId());
                  t++;
//                     companyInfo.setBelongArea("0219");
//                 save = companyInfoRepository.save(companyInfo);
              }


//
//            CompanyBasic companyBasic = companyBasicRepository.findByTaxId(save.getTaxId());
//            if (companyBasic == null) {
//                CompanyBasic companyBasic1 = new CompanyBasic();
//                companyBasic1.setTaxId(save.getTaxId());
//                companyBasic1.setCompanyName(save.getCompanyName());
//                companyBasic1.setBelongArea(save.getBelongArea());
//                companyBasic1.setCompanyShortName(save.getCompanyShortName());
//                companyBasic1.setCustomerType(save.getCustomerType());
//
//                CompanyInfo companyInfo1 = companyInfoRepository.findByCompanyKey(save.getParentCompanyId());
//                companyBasic1.setHeadOfficeCode(companyInfo1 != null ? companyInfo1.getTaxId() : null);
//
//                companyBasic1.setCreateMan(save.getCreateMan());
//                companyBasic1.setCreateTime(save.getCreateTime());
//                companyBasic1.setUpdateMan(save.getUpdateMan());
//                companyBasic1.setUpdateTime(save.getUpdateTime());
//
//                CompanyBasic companyBasic2 = companyBasicRepository.save(companyBasic1);
//
//                companyU8cService.addToU8C(save, companyBasic2.getKey().toString());
//
//
//            } else {
//                companyBasicRepository.save(companyBasic);
//                companyU8cService.addToU8C(save, companyBasic.getKey().toString());
//            }
        }

        System.out.println("未有"+t);
        for (Object o : list) {
            System.out.println(o);
        }
    }


    @Test
    public void Test3() {
        CompanyInfo save = companyInfoRepository.findByCompanyKey(930234L);

        companyU8cService.updateToU8C(save,save,"930237");
    }
}
