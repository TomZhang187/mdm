package com.hqhop.modules.company.service.impl;

import com.alibaba.fastjson.JSON;
import com.hqhop.modules.company.domain.Account;
import com.hqhop.modules.company.domain.CompanyInfo;
import com.hqhop.modules.company.domain.Contact;
import com.hqhop.modules.company.domain.U8cDomain.U8cAccount;
import com.hqhop.modules.company.domain.U8cDomain.U8cContact;
import com.hqhop.modules.company.repository.AccountRepository;
import com.hqhop.modules.company.repository.CompanyInfoRepository;
import com.hqhop.modules.company.repository.ContactRepository;
import com.hqhop.modules.company.service.CompanyU8cService;
import com.hqhop.utils.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class CompanyU8cServiceImpl implements CompanyU8cService {

    @Autowired
    private CompanyInfoRepository companyInfoRepository;

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private AccountRepository accountRepository;

    //u8c新增客商接口
    @Override
    public void addToU8C(CompanyInfo companyInfo, String csid){

        companyInfo.setCreateMan("HQHP0933");
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        String url = "http://119.6.33.92:8088/service/AddCubasdocData";
        String belongCompanuy = null;
                LinkedMultiValueMap<Object, Object> map = new LinkedMultiValueMap<>();
                map.add("custcode", companyInfo.getTaxId()!=null?companyInfo.getTaxId():"");////客商编码
                map.add("custname", companyInfo.getCompanyName()!=null?companyInfo.getCompanyName():"");//客商名称
                map.add("custshortname", companyInfo.getCompanyShortName()!=null?companyInfo.getCompanyShortName():"");//简称名称

                CompanyInfo byCompanyKey = companyInfoRepository.findByCompanyKey(companyInfo.getParentCompanyId());

                map.add("pk_cubasdoc1",byCompanyKey!=null?byCompanyKey.getTaxId():""); //客商总公司编码
                map.add("custprop", Optional.ofNullable(companyInfo.getCustomerType()).orElse(""));//客商类型
                map.add("custflag", Optional.ofNullable(companyInfo.getCustomerProp()).orElse(""));//客商属性


                map.add("pk_areacl", companyInfo.getBelongArea());//所属地区

                map.add("pk_corp",companyInfo.getBelongCompany()); //组织
                map.add("csid", csid);        //客商id   `


//                Set<Contact> contacts= contactRepository.findByCompanyKey(companyInfo.getCompanyKey());
//                Set<Account> accounts = accountRepository.findByCompanyKey(companyInfo.getCompanyKey());
                 Set<Contact> contacts= new HashSet<>();
                  Set<Account> accounts = new HashSet<>();
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
                   companyInfo.setIsSyncU8c(1);
                   companyInfoRepository.save(companyInfo);
                      System.out.println("u8c新增客商成功"+stringResponseEntity.getBody());
                       }else {
                   companyInfo.setIsSyncU8c(0);
                   companyInfoRepository.save(companyInfo);
                      System.out.println(stringResponseEntity.getBody());
                 }

    }





    //u8c新增客商测试接口
    @Override
    public void addToU8CTest(CompanyInfo companyInfo, String csid){

        companyInfo.setCreateMan("HQHP0933");
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        String url = "http://119.6.33.92:8087/service/AddCubasdocData";
        String belongCompanuy = null;
        LinkedMultiValueMap<Object, Object> map = new LinkedMultiValueMap<>();
        map.add("custcode", companyInfo.getTaxId()!=null?companyInfo.getTaxId():"");////客商编码
        map.add("custname", companyInfo.getCompanyName()!=null?companyInfo.getCompanyName():"");//客商名称
        map.add("custshortname", companyInfo.getCompanyShortName()!=null?companyInfo.getCompanyShortName():"");//简称名称

        CompanyInfo byCompanyKey = companyInfoRepository.findByCompanyKey(companyInfo.getParentCompanyId());

        map.add("pk_cubasdoc1",byCompanyKey!=null?byCompanyKey.getTaxId():""); //客商总公司编码
        map.add("custprop", Optional.ofNullable(companyInfo.getCustomerType()).orElse(""));//客商类型
        map.add("custflag", Optional.ofNullable(companyInfo.getCustomerProp()).orElse(""));//客商属性


        map.add("pk_areacl", companyInfo.getBelongArea());//所属地区

        map.add("pk_corp",companyInfo.getBelongCompany()); //组织
        map.add("csid", csid);        //客商id   `


                Set<Contact> contacts= contactRepository.findByCompanyKey(companyInfo.getCompanyKey());
//                Set<Account> accounts = accountRepository.findByCompanyKey(companyInfo.getCompanyKey());
//        Set<Contact> contacts= new HashSet<>();
        Set<Account> accounts = new HashSet<>();
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
            companyInfo.setIsSyncU8c(1);
            companyInfoRepository.save(companyInfo);
            System.out.println("u8c新增客商成功"+stringResponseEntity.getBody());
        }else {
            companyInfo.setIsSyncU8c(0);
            companyInfoRepository.save(companyInfo);
            System.out.println(stringResponseEntity.getBody());
        }

    }


    //u8c修改客商接口
    @Override
    public void updateToU8C(CompanyInfo companyInfo,CompanyInfo updateData, String csid){

             companyInfo.dataCompare(updateData);

        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        String url = "http://119.6.33.92:8087/service/UpdateCubasdocData";
        LinkedMultiValueMap<Object, Object> map = new LinkedMultiValueMap<>();


        if(companyInfo.getTaxId()!=null){
            map.add("custcode", companyInfo.getTaxId()!=null?companyInfo.getTaxId():"");////客商编码
        }
      if(companyInfo.getCompanyName()!=null){
          map.add("custname", companyInfo.getCompanyName()!=null?companyInfo.getCompanyName():"");//客商名称
      }
     if(companyInfo.getCompanyShortName()!=null){
         map.add("custshortname", companyInfo.getCompanyShortName()!=null?companyInfo.getCompanyShortName():"");//简称名称
     }

     if(companyInfo.getParentCompanyId()!=null){
         CompanyInfo byCompanyKey = companyInfoRepository.findByCompanyKey(companyInfo.getParentCompanyId());
         map.add("pk_cubasdoc1",byCompanyKey!=null?byCompanyKey.getTaxId():""); //客商总公司编码
     }
      if(companyInfo.getCustomerType()!=null){
          map.add("custprop", Optional.ofNullable(companyInfo.getCustomerType().toString()).orElse(""));//客商类型
      }
  if(companyInfo.getCustomerProp()!=null){
      map.add("custflag", Optional.ofNullable(companyInfo.getCustomerProp()).orElse(""));//客商属性
  }

  if(companyInfo.getBelongArea()!=null){
      map.add("pk_areacl", companyInfo.getBelongArea());//所属地区
  }

if(companyInfo.getBelongCompany()!=null){
    map.add("pk_corp",companyInfo.getBelongCompany()); //组织
}

        map.add("csid", csid);        //客商id   `

        if(companyInfo.getCustomerProp()!=null){
            if(companyInfo.getCustomerProp().equals("1")){
           map.add("custflaglist", "[{\"custflag\":\"0\"},{\"custflag\":\"4\"}]");//客商属性
      }else  if(companyInfo.getCustomerProp().equals("2")){
            map.add("custflaglist", "[{\"custflag\":\"1\"},{\"custflag\":\" \"}]");//客商属性
        }else  if(companyInfo.getCustomerProp().equals("3")){
            map.add("custflaglist", "[{\"custflag\":\"2\"},{\"custflag\":\"3\"}]");//客商属性
        }
        }

       map.add("cooperateflag","N"); //是否收付协同

        if(companyInfo.getCompanyState()!=null){
            if(companyInfo.getCompanyState().equals("4") && companyInfo.getIsDisable() == 1) {
                map.add("custstate", "1");   //客商状态
            } else if(companyInfo.getCompanyState().equals("4") && companyInfo.getIsDisable() == 1){
                map.add("custstate", "2");   //客商状态
            } else {
                map.add("custstate", "0");   //客商状态
            }
        }


         if(companyInfo.getBelongCompany()!=null){
             if(companyInfo.getBelongCompany().equals("100301") || companyInfo.getBelongCompany().equals("1003") || companyInfo.getBelongCompany().equals("100503")
                     || companyInfo.getBelongCompany().equals("1007") || companyInfo.getBelongCompany().equals("1008") ||
                     companyInfo.getBelongCompany().equals("1009") ||  companyInfo.getBelongCompany().equals("1010") || companyInfo.getBelongCompany().equals("1001")  ){

//            map.add("pk_salestru",Optional.ofNullable(companyInfo.getBelongCompany()).orElse(""));   //销售组织
//            map.add("pk_calbody",companyInfo.getBelongCompany());    //库存组织

             }else {
                 map.add("pk_salestru",Optional.ofNullable(companyInfo.getBelongCompany()).orElse(""));   //销售组织
                 map.add("pk_calbody",companyInfo.getBelongCompany());    //库存组织

             }
         }

        if(companyInfo.getChargeDepartment()!=null){
            map.add("pk_respdept1",Optional.ofNullable(companyInfo.getChargeDepartment()).orElse(""));  //专管部门
        }
      if(companyInfo.getProfessionSalesman()!=null){
          map.add("pk_resppsn1",Optional.ofNullable(companyInfo.getProfessionSalesman()).orElse("")); //专管业务员
      }
       if(companyInfo.getCreditRating()!=null){
           map.add("creditlevel",companyInfo.getCreditRating());                    //信用等级
       }
       if(companyInfo.getIsDisable()!=null){
           map.add("sealflag",companyInfo.getIsDisable()!=1?"N":"Y");           //是否封存
       }

      if(companyInfo.getUpdateMan()!=null){
          map.add("modifier", Optional.ofNullable(companyInfo.getUpdateMan()).orElse(""));    //修改人
      }
       if(companyInfo.getUpdateTime()!=null){
           map.add("modifytime",  Optional.ofNullable(df.format((TemporalAccessor) companyInfo.getUpdateTime())).orElse(""));  //修改时间
       }
        ResponseEntity<String> stringResponseEntity = HttpUtil.postRequest(url, map);
        if(stringResponseEntity.getBody().contains("成功")){
            CompanyInfo byCompanyKey = companyInfoRepository.findByCompanyKey(companyInfo.getCompanyKey());

            byCompanyKey.setIsSyncU8c(1);
            companyInfoRepository.save(byCompanyKey);
            System.out.println("u8c修改客商成功"+stringResponseEntity.getBody());
        }else {
            CompanyInfo byCompanyKey = companyInfoRepository.findByCompanyKey(companyInfo.getCompanyKey());

            byCompanyKey.setIsSyncU8c(0);
            companyInfoRepository.save(byCompanyKey);
            System.out.println(stringResponseEntity.getBody());
        }

    }




//
//    //u8c修改客商接口
//    @Override
//    public void updateContactToU8C(CompanyInfo companyInfo,String csid, Contact contact){
//
//
//
//
//    }

    public CompanyInfo dataCompare(CompanyInfo companyInfo,CompanyInfo updateData){








             return  null;
    }


}












