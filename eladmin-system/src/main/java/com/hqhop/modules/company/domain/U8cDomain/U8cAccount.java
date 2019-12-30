package com.hqhop.modules.company.domain.U8cDomain;


import com.hqhop.modules.company.domain.Account;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
public class U8cAccount implements Serializable {

    //开户银行
    private String pk_accbank;


    //名称
    private String  accname;


    // 账户
    private String   account ;


    //币种主键
    private String   pk_currtype;


    //  是否默认
    private String   defflag;



    public static   List<U8cAccount> getListBySetAccount(Set<Account > accounts){
        List<U8cAccount> list = new ArrayList<>();
        if(accounts.size()!=0){
            for (Account account : accounts) {
                U8cAccount u8cAccount = new U8cAccount();
                u8cAccount.setPk_accbank(account.getAccountBlank());
                u8cAccount.setAccname(account.getAccountName());
                u8cAccount.setAccount(account.getAccount());
                if(account.getIsDefalut()!=null){
                    u8cAccount.setDefflag(account.getIsDefalut()!=1?"N":"Y");
                }
                u8cAccount.setPk_currtype(account.getCurrency());
                list.add(u8cAccount);
            }
        }

         return  list;

    }




}
