package com.hqhop.modules.material.service.vo;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class TypeAttributeVo {
    @NotNull
    private Long typeId;

    //物料属性的id
    @NotNull
    @Size(min = 1)
    private Long[] attributes;
}
