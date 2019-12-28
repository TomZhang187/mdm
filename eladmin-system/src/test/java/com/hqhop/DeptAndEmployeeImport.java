package com.hqhop;


import com.alibaba.fastjson.JSON;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiUserGetOrgUserCountRequest;
import com.dingtalk.api.response.*;
import com.hqhop.common.dingtalk.DingTalkUtils;
import com.hqhop.modules.company.domain.Account;
import com.hqhop.modules.company.domain.CompanyInfo;
import com.hqhop.modules.company.domain.Contact;
import com.hqhop.modules.company.domain.U8cDomain.U8cAccount;
import com.hqhop.modules.company.domain.U8cDomain.U8cContact;
import com.hqhop.modules.company.repository.AccountRepository;
import com.hqhop.modules.company.repository.CompanyInfoRepository;
import com.hqhop.modules.company.repository.ContactRepository;
import com.hqhop.modules.system.repository.DeptRepository;
import com.hqhop.modules.system.repository.EmployeeRepository;
import com.hqhop.modules.system.service.DeptDingService;
import com.hqhop.modules.system.service.EmployeeDingService;
import com.hqhop.modules.system.service.EmployeeService;
import com.hqhop.utils.HttpUtil;
import com.taobao.api.ApiException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DeptAndEmployeeImport {

    @Autowired
    private DeptDingService deptDingService;

    @Autowired
    private EmployeeDingService employeeDingService;

    @Autowired
    private DeptRepository deptRepository;


    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private CompanyInfoRepository companyInfoRepository;

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private AccountRepository accountRepository;


    //部门导入
    @Test
    public void test1() {

        try {
            deptDingService.syncDeptToDingDept();
        } catch (ApiException e) {
            e.printStackTrace();
        }


    }

    //员工导入导入
    @Test
    public void test2() {

        try {
            employeeDingService.syncDingUser();
        } catch (ApiException e) {
            e.printStackTrace();
        }


    }



    @Test
    public void test3() throws
            ApiException {

        int i = 0;
        List<OapiDepartmentListResponse.Department> deptsLists = deptDingService.getDeptsLists(null);
        for (OapiDepartmentListResponse.Department deptsList : deptsLists) {

            List<OapiUserListbypageResponse.Userlist> list = employeeDingService.getDeptUserDetails(deptsList.getId());
                for (OapiUserListbypageResponse.Userlist userlist : list) {
                    i++;
                }
        }
        System.out.println("统计" + i);
    }


     //通过excel员工导入
     @Test
    public void test4() throws
            ApiException {
        try {
            employeeDingService.syncDingUser("D:\\easyExcel\\通讯录.xlsx");
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test6() throws
            ApiException {


        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/get_org_user_count");
        OapiUserGetOrgUserCountRequest request = new OapiUserGetOrgUserCountRequest();
        request.setOnlyActive(0L);
        request.setHttpMethod("GET");
        OapiUserGetOrgUserCountResponse response = client.execute(request,  DingTalkUtils.getAccessToken());
        System.out.println("企业人数"+response.getCount());


    }




    //客商基本档案
    @Test
    public void test7() throws
            ApiException {

        CompanyInfo companyInfo = companyInfoRepository.findByCompanyKey(50539L);
        String url = "http://119.6.33.92:8087/service/AddCubasdocData";
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LinkedMultiValueMap<Object, Object> map = new LinkedMultiValueMap<>();
        map.add("custcode", companyInfo.getTaxId()!=null?companyInfo.getTaxId():"");////客商编码
        map.add("custname", companyInfo.getCompanyName()!=null?companyInfo.getCompanyName():"");//客商名称
        map.add("custshortname", companyInfo.getCompanyShortName()!=null?companyInfo.getCompanyShortName():"");//简称名称

        CompanyInfo byCompanyKey = companyInfoRepository.findByCompanyKey(companyInfo.getParentCompanyId());
        map.add("pk_cubasdoc1",byCompanyKey!=null?byCompanyKey.getTaxId():""); //客商总公司编码
        map.add("custprop", companyInfo.getCustomerType());//客商类型
        map.add("custflag", companyInfo.getCompanyType());//客商属性

        map.add("pk_corp1", "4".equals(companyInfo.getCustomerType())?"":companyInfo.getBelongCompany());//对应公司

        map.add("pk_areacl", companyInfo.getBelongArea());//所属地区

        map.add("pk_crop",companyInfo.getBelongCompany()); //组织
        map.add("csid",companyInfo.getCompanyKey().toString());        //客商id   `


        Set<Contact> contacts= contactRepository.findByCompanyKey(companyInfo.getCompanyKey());
        Set<Account> accounts = accountRepository.findByCompanyKey(companyInfo.getCompanyKey());
        map.add("addrlist",  JSON.toJSONString(U8cContact.getListBySetContact(contacts,companyInfo.getBelongArea())));  //联系人集合
        map.add("banklist", JSON.toJSONString(U8cAccount.getListBySetAccount(accounts)));//银行信息
        if(companyInfo.getIsSynergyPay() != null){
            map.add("cooperateflag",companyInfo.getIsSynergyPay()!=1?"N":"Y"); //是否收付协同
        }else {
            map.add("cooperateflag",companyInfo.getIsSynergyPay()); //是否收付协同
        }
       map.add("pk_salestru",companyInfo.getBelongCompany());   //销售组织
        map.add("pk_calbody",companyInfo.getBelongCompany());    //库存组织
        map.add("pk_respdept1",companyInfo.getChargeDepartment());  //专管部门
        map.add("pk_resppsn1",companyInfo.getProfessionSalesman()); //专管业务员
        map.add("creditlevel",companyInfo.getCreditRating());                    //行用等级
        map.add("sealflag",companyInfo.getIsDisable()!=1?"N":"Y");           //是否封存


        map.add("creator", companyInfo.getCreateMan());  //创建人
        map.add("createtime", df.format(LocalDateTime.now()));  //创建时间
        map.add("modifier", companyInfo.getUpdateMan());    //修改人
        map.add("modifytime", df.format(LocalDateTime.now()));  //修改时间
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
        String s = HttpUtil.postRequest(url, map);


        System.out.println(s);

    }



}










