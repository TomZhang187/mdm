package com.hqhop.modules.material.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import javax.persistence.*;
import java.io.Serializable;

/**
* @author chengy
* @date 2019-10-17
*/
@Entity
@Data
@Table(name="material")
public class Material implements Serializable {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "material_model")
    private String materialModel;

    @Column(name = "material_name")
    private String materialName;

    @Column(name = "material_number")
    private String materialNumber;

    public void copy(Material source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}