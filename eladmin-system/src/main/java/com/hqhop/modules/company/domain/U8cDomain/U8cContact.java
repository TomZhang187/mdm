package com.hqhop.modules.company.domain.U8cDomain;

import com.hqhop.modules.company.domain.Contact;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
public class U8cContact implements Serializable {

    //收货地址
    private  String  addrname;


    //联系人
    private String  linkman ;

    //手机号
    private  String phone;

    //是否默认
    private String  defaddrflag;

    //所属地区
    private String  pk_areacl ;

    //联系人主键
    private  String cs_addr_id;


    public static List<U8cContact> getListBySetContact(Set<Contact> contacts,String belognArea){

        List<U8cContact> list = new ArrayList<>();
        if(contacts.size() !=0){
            for (Contact contact : contacts) {

                U8cContact u8cContact = new U8cContact();
                u8cContact.setAddrname(contact.getDeliveryAddress());
                u8cContact.setLinkman(contact.getContactName());
                u8cContact.setPhone(contact.getPhone());
                u8cContact.setPk_areacl(belognArea);
                if(contact.getIsDefaultAddress()!=null){
                    u8cContact.setDefaddrflag(contact.getIsDefaultAddress()!=1?"N":"Y");
                }
                u8cContact.setCs_addr_id(contact.getContactKey().toString());
                list.add(u8cContact);
            }

        }


        return  list;
    }



}


