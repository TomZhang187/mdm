package com.hqhop.modules.company.service.mapper;

import com.hqhop.modules.company.domain.CompanyInfo;

public interface CompanyU8cService {
    //u8c新增客商接口
    void addToU8C(CompanyInfo companyInfo, String csid);

    //u8c修改客商接口
    void updateToU8C(CompanyInfo companyInfo, String csid);
}
