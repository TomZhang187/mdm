package com.hqhop.modules.material.repository;

import com.hqhop.modules.material.domain.MaterialOperationRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
* @author zf
* @date 2019-12-09
*/
public interface MaterialOperationRecordRepository extends JpaRepository<MaterialOperationRecord, Long>, JpaSpecificationExecutor {


    //通过审批实例ID加审批结果查找记录
     MaterialOperationRecord findByProcessIdAndApproveResult(String prcoessId,String result);


    //通过id加审批结果查找记录
    MaterialOperationRecord findByIdAndApproveResult(Long id,String result);


    //通过审批实例ID加操作类型
    MaterialOperationRecord findByProcessIdAndOperationType(String prcoessId,String type);

   //通过关联物料ID和用户钉钉ID和审批结果查找记录
   MaterialOperationRecord findByIdAndUserIdAndApproveResult(Long id,String dingId,String result);

   //通过物料主键和用户名和操作类型查找数据
    @Query(value = "select * from material_operation_record where id=?1 and creator=?2 and operation_type=?3", nativeQuery = true)
    MaterialOperationRecord findByIdAndCreatorAndOperationType(Long id,String name,String type);

    //通过临时数据主键和用户名和操作类型查找数据
    MaterialOperationRecord findByTemporaryIdAndCreatorAndOperationType(Long id,String name,String type);







}