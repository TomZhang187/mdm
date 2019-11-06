package com.hqhop.modules.material.domain;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

/**
 * @author KinLin
 * @date 2019-10-30
 * 物料属性类
 */
@Entity
@Table(name="attribute")
public class Attribute implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attribute_id")
    private Long attributeId;
    /*
    属性名
    */
    @Column(name = "attr_name")
    private String attributeName;

    /*
     属性值
     */
    @Column(name = "attr_value")
    private String attributeValue;

    @Column(name = "create_time")
    @CreationTimestamp
    private Timestamp createTime;


    @ManyToMany(cascade = { CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "attributes","materialTypes" })// 解决循环查找的问题
    @JoinTable(name = "t_type_attr", joinColumns = { @JoinColumn(name = "attribute_id") }, inverseJoinColumns = { @JoinColumn(name = "type_id") })
    private Set<MaterialType> materialTypes;



    public void copy(Attribute source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }

    public Long getAttributeId() {
        return attributeId;
    }

    public void setAttributeId(Long attributeId) {
        this.attributeId = attributeId;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public String getAttributeValue() {
        return attributeValue;
    }

    public void setAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Set<MaterialType> getMaterialTypes() {
        return materialTypes;
    }

    public void setMaterialTypes(Set<MaterialType> materialTypes) {
        this.materialTypes = materialTypes;
    }
}
