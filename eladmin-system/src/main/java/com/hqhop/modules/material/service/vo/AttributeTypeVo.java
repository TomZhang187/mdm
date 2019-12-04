package com.hqhop.modules.material.service.vo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class AttributeTypeVo {

    private Long typeId;

    //物料属性的id
    private Long attributeId;
}
