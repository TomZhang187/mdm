package com.hqhop.modules.system.service.dto;

import com.hqhop.modules.system.domain.Dept;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.util.HashSet;
import java.util.Set;

/**
 * @author ：张丰
 * @date ：Created in 2019/12/3 0003 14:30
 * @description：员工简略DTO
 * @modified By：
 * @version: $
 */

@Data
public class EmployeeSmallDTO {

    /**
     * ID
     */
    private Long id;

    // 工号
    private String employeeCode;

    // 姓名
    private String employeeName;

    //页面显示格式所属部门
    private String pageBelongDepts;


    //钉钉格式所属部门
    private String dingBelongDepts;

    // 钉钉ID
    private String dingId;

}
