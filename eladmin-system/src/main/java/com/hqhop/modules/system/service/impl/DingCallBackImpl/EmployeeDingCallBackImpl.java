package com.hqhop.modules.system.service.impl.DingCallBackImpl;

import com.hqhop.common.dingtalk.dingtalkVo.ApprovalCallbackVo;
import com.hqhop.modules.system.domain.Employee;
import com.hqhop.modules.system.repository.EmployeeRepository;
import com.hqhop.modules.system.service.EmployeeDingCallBackService;
import com.hqhop.modules.system.service.EmployeeDingService;
import com.taobao.api.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author ：张丰
 * @date ：Created in 2019/11/29 0029 15:59
 * @description：员工钉钉回调业务类
 * @modified By：
 * @version: $
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class EmployeeDingCallBackImpl implements EmployeeDingCallBackService {

    @Autowired
    private EmployeeRepository employeeRepository;


    @Autowired
    private EmployeeDingService employeeDingService;


    //入职审批回调
    @Override
    public void entryApprovalBack(ApprovalCallbackVo callback) {

        try {
            Employee employee = employeeDingService.getDingUserDetails(callback.getStaffId());
            employeeRepository.save(employee);
        } catch (ApiException e) {
            e.printStackTrace();
        }


    }

    //离职审批回调
    @Override
    public  void dimissionApprovalBack(ApprovalCallbackVo callback){


        Employee employee = employeeRepository.findByDingId(callback.getStaffId());
        employee.setEnabled(false);
        employeeRepository.save(employee);




    }



}
