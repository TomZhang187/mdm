package com.hqhop.modules.company.domain;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author zf
 * @date 2019-10-22
 */
@Entity
@Table(name = "contact")
public class Contact implements Serializable {

    // 联系人主键
    @Id
    @Column(name = "contact_key")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long contactKey;

    // 联系地址
    @Column(name = "contact_address")
    private String contactAddress;

    //  邮件
    @Column(name = "email")
    private String email;

    // 姓名
    @Column(name = "name")
    private String name;

    // 电话
    @Column(name = "phone")
    private String phone;

    // 职务
    @Column(name = "position")
    private String position;

    public void copy(Contact source) {
        BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
    }

    @Override
    public String toString() {
        return "Contact{" +
                "contactKey=" + contactKey +
                ", contactAddress='" + contactAddress + '\'' +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", position='" + position + '\'' +
                '}';
    }



    public Long getContactKey() {

        return contactKey;
    }

    public void setContactKey(Long contactKey) {
        this.contactKey = contactKey;
    }

    public String getContactAddress() {
        return contactAddress;
    }

    public void setContactAddress(String contactAddress) {
        this.contactAddress = contactAddress;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}