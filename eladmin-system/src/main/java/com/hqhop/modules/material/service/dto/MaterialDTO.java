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

    // ID
    private Long id;

    // 创建时间
    private Timestamp createTime;


    // 名称
    private String name;

    //标识
    private String remark;

    //分类编码(001.0001)
    private String classifyNum;

    //大类
    private String bigType;

    //是否应税劳务
    private Boolean isTaxable;

    //流水码
    private String flowCode;

    //新物料代码
    private String newCode;

    //原物料代码1
    private String oldCode1;

    //原物料代码2
    private String oldCode2;

    //原物料代码3
    private String oldCode3;



    //规格型号(图纸明细栏规格型号)
    private String specifications;

    //创建人
    private String creator;

    //修改人
    private String modifier;

    //修改时间
    private Timestamp modifiedTime;


    //单位
    private String unit;

    //物料类型(物料种类小类型)
    private MaterialType type;



}