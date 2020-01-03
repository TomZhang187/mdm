package com.hqhop.modules.company.service.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hqhop.annotation.Query;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Set;

/**
 * @author zf
 * @date 2019-10-22
 */
@Data
public class CompanyInfoQueryCriteria {

    // 客商ID
    // 处理精度丢失问题
    @JsonSerialize(using = ToStringSerializer.class)
    private Long companyKey;


    @Query(type = Query.Type.IN, propName="companyKey")
    private Set<Long> keys;



    // 批准时间
    private Timestamp approveTime;

    // 精确
    @Query
    private Integer belongArea;

    // 精确
    @Query
    private Integer belongCompany;


    // 精确
    @Query
    private Integer  operationType;

    // 精确
    @Query
    private Integer companyProp;

    // 模糊
    @Query(type = Query.Type.INNER_LIKE)
    private String companyShortName;

    // 精确
    @Query
    private Integer companyState;

    // 精确
    @Query
    private String customerProp;

    // 模糊
    @Query(type = Query.Type.INNER_LIKE)
    private String compayName;

    // 模糊
    @Query(type = Query.Type.INNER_LIKE)
    private String contactAddress;

    // 精确
    @Query
    private Integer economicType;

    // 模糊
    @Query(type = Query.Type.INNER_LIKE)
    private String foreignName;

    // 精确
    @Query
    private Integer isDisable;

    // 精确
    @Query
    private Integer isRetai;

    // 模糊
    @Query(type = Query.Type.INNER_LIKE)
    private String legalbody;

    // 精确
    @Query
    private Long parentCompanyId;

    // 模糊
    @Query(type = Query.Type.INNER_LIKE)
    private String postalCode;

    // 模糊
    @Query(type = Query.Type.INNER_LIKE)
    private String remark;

    // 模糊
    @Query(type = Query.Type.INNER_LIKE)
    private String taxId;

    // 精确
    @Query
    private Integer trade;

    //联系人的名字 模糊查询
    private String contactName;

    public void setCompanyKey(Long companyKey) {
        this.companyKey = companyKey;
    }

    public void setApproveTime(Timestamp approveTime) {
        this.approveTime = approveTime;
    }

    public void setBelongArea(Integer belongArea) {
        this.belongArea = belongArea;
    }

    public void setBelongCompany(Integer belongCompany) {
        this.belongCompany = belongCompany;
    }

    public void setCompanyProp(Integer companyProp) {
        this.companyProp = companyProp;
    }

    public void setCompanyShortName(String companyShortName) {
        this.companyShortName = companyShortName;
    }

    public void setCompanyState(Integer companyState) {
        this.companyState = companyState;
    }

    public void setCompanyType(String customerProp) {
        this.customerProp = customerProp;
    }

    public void setCompayName(String compayName) {
        this.compayName = compayName;
    }

    public void setContactAddress(String contactAddress) {
        this.contactAddress = contactAddress;
    }

    public void setEconomicType(Integer economicType) {
        this.economicType = economicType;
    }

    public void setForeignName(String foreignName) {
        this.foreignName = foreignName;
    }

    public void setIsDisable(Integer isDisable) {
        this.isDisable = isDisable;
    }

    public void setIsRetai(Integer isRetai) {
        this.isRetai = isRetai;
    }

    public void setLegalbody(String legalbody) {
        this.legalbody = legalbody;
    }

    public void setParentCompanyId(Long parentCompanyId) {
        this.parentCompanyId = parentCompanyId;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setTaxId(String taxId) {
        this.taxId = taxId;
    }

    public void setTrade(Integer trade) {
        this.trade = trade;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }



    public void setKeys(Set<Long> keys) {

        if(keys!=null && keys.size()!=0){
            this.keys = keys;
        }else {
            this.keys.add(0L);
        }

    }
}