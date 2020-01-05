package com.hqhop.modules.company.repository;


import com.hqhop.modules.company.domain.CompanyUpdate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
* @author zf
* @date 2019-11-07
*/
public interface CompanyUpdateRepository extends JpaRepository<CompanyUpdate, Long>, JpaSpecificationExecutor {


    CompanyUpdate findByOperateKey(Long key);


    //通过公司主键和公司状态查询修改记录
    List<CompanyUpdate> findAllByCompanyKeyAndCompanyState(Long key,String state);

     //通过审批实例ID查询记录
    CompanyUpdate findByProcessId(String prcoessId);


    //通过审批实例ID加审批结果
    CompanyUpdate findByProcessIdAndApproveResult(String prcoessId,String result);

    //通过公司主键加用户ID加审批结果的数据
    CompanyUpdate findByCompanyKeyAndUserIdAndApproveResult(Long companyKey,String userId,String approveResult);

    //通过公司主键加用户ID加审批结果加操作类型的数据
    CompanyUpdate findByCompanyKeyAndUserIdAndApproveResultAndOperationType(Long companyKey,String userId,String approveResult,String type);

    //通过公司主键加用户ID查审批结果未知的数据
    @Query(value = "select * from company_update where approve_result != ?1",nativeQuery = true)
     List<CompanyUpdate> findByApproveResultNotIs(String result);

    //通过审批实例ID和操作类型查找记录
    CompanyUpdate findByProcessIdAndOperationType(String processId,String operationType);


    //通过联系人主键加用户ID加审批结果的数据
    CompanyUpdate findByContactKeyAndUserIdAndApproveResult(Long contactKey,String userId,String approveResult);

    //通过账户主键加用户ID加审批结果的数据
    CompanyUpdate findByAccountKeyAndUserIdAndApproveResult(Long contactKey,String userId,String approveResult);





}