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

    //联系人类型
    @Column(name = "contact_type")
    private Integer contactType;

    //客商收发货地址名称
    @Column(name = "delivery_address")
    private String deliveryAddress;

    //是否默认地址
    @Column(name = "is_default_address")
    private Integer isDefaultAddress;

    //所属公司
    @Column(name = "company_key")
    private Long companyKey;

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
                ", contactType=" + contactType +
                ", deliveryAddress='" + deliveryAddress + '\'' +
                ", isDefaultAddress=" + isDefaultAddress +
                '}';
    }

    public Long getCompanyKey() {
        return companyKey;
    }

    public void setCompanyKey(Long companyKey) {
        this.companyKey = companyKey;
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

    public Integer getContactType() {
        return contactType;
    }

    public void setContactType(Integer contactType) {
        this.contactType = contactType;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public Integer getIsDefaultAddress() {
        return isDefaultAddress;
    }

    public void setIsDefaultAddress(Integer isDefaultAddress) {
        this.isDefaultAddress = isDefaultAddress;
    }


}