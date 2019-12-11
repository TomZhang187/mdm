package com.hqhop.modules.material.domain;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import lombok.Data;

import javax.persistence.*;

/**
 * 物料属性中间管理类
 */
@Data
@Entity
@Table(name = "material_attribute")
public class MaterialAttribute {
    @Id //在表中的名称
    @GeneratedValue(strategy = GenerationType.AUTO) //自动增长
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "material_id")
    private Material material;
    @ManyToOne
    @JoinColumn(name = "attribute_id")
    private Attribute attribute;
    private String attributeValue;
    public void copy(Attribute source) {
        BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
    }
    public MaterialAttribute(Material material, Attribute attribute, String attributeValue) {
        this.material = material;
        this.attribute = attribute;
        this.attributeValue = attributeValue;
    }
    public MaterialAttribute(){}
}
