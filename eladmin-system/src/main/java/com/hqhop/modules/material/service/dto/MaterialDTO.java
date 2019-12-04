package com.hqhop.modules.material.service.dto;

import com.hqhop.modules.material.domain.MaterialType;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;


/**
* @author KinLin
* @date 2019-10-30
*/
@Data
public class MaterialDTO implements Serializable {


    private Long id;

    //标识存货编码

    private String remark;

    //存货名称

    private String name;

    //是否应税劳务

    private Boolean isTaxable;

    //规格型号

    private String model;

    //税目
    private String taxRating;

    //创建者
    private String createPerson;
    //创建时间

    private Timestamp createTime;

    //单位
    private String unit;

    //物料类型(物料种类小类型)
    private MaterialType type;




}