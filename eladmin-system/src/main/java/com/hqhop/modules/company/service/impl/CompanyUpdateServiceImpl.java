package com.hqhop.modules.company.service.impl;

import com.hqhop.common.dingtalk.dingtalkVo.DingUser;
import com.hqhop.modules.company.domain.CompanyUpdate;
import com.hqhop.modules.company.repository.CompanyInfoRepository;
import com.hqhop.utils.CompanyUpdateHelp;
import com.hqhop.utils.ValidationUtil;
import com.hqhop.modules.company.repository.CompanyUpdateRepository;
import com.hqhop.modules.company.service.CompanyUpdateService;
import com.hqhop.modules.company.service.dto.CompanyUpdateDTO;
import com.hqhop.modules.company.service.dto.CompanyUpdateQueryCriteria;
import com.hqhop.modules.company.service.mapper.CompanyUpdateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.hqhop.utils.PageUtil;
import com.hqhop.utils.QueryHelp;
import java.util.List;
import java.util.Map;

/**
* @author zf
* @date 2019-11-07
*/
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class CompanyUpdateServiceImpl implements CompanyUpdateService {

    @Autowired
    private CompanyUpdateRepository companyUpdateRepository;

    @Autowired
    private CompanyInfoRepository companyInfoRepository;


    private CompanyUpdateMapper companyUpdateMapper;

    @Override
    public Map<String,Object> queryAll(CompanyUpdateQueryCriteria criteria, Pageable pageable){
        Page<CompanyUpdate> page = companyUpdateRepository.findAll((root, criteriaQuery, criteriaBuilder) -> CompanyUpdateHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page);
    }

    @Override
    public List<CompanyUpdateDTO> queryAll(CompanyUpdateQueryCriteria criteria){
        return companyUpdateMapper.toDto(companyUpdateRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    public CompanyUpdateDTO findById(Long operateKey) {
        Optional<CompanyUpdate> companyUpdate = companyUpdateRepository.findById(operateKey);
        ValidationUtil.isNull(companyUpdate,"CompanyUpdate","operateKey",operateKey);
        return companyUpdateMapper.toDto(companyUpdate.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CompanyUpdateDTO create(CompanyUpdate resources) {
        Snowflake snowflake = IdUtil.createSnowflake(1, 1);
        resources.setOperateKey(snowflake.nextId());
        return companyUpdateMapper.toDto(companyUpdateRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(CompanyUpdate resources) {
        Optional<CompanyUpdate> optionalCompanyUpdate = companyUpdateRepository.findById(resources.getOperateKey());
        ValidationUtil.isNull( optionalCompanyUpdate,"CompanyUpdate","id",resources.getOperateKey());
        CompanyUpdate companyUpdate = optionalCompanyUpdate.get();
        companyUpdate.copy(resources);
        companyUpdateRepository.save(companyUpdate);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long operateKey) {
        companyUpdateRepository.deleteById(operateKey);
    }


    /*
      查询该客商修改记录
      * */  @Override
    public CompanyUpdate loadUpdateRecord(Long companyKey,String state,String userId,String name) {
           //1 新增 2 审批中 3驳回 4审核通过
        CompanyUpdate companyUpdate = companyUpdateRepository.findByCompanyKeyAndUserIdAndApproveResult(companyKey,userId,"未知");
//          List<CompanyUpdate> companyUpdates = companyUpdateRepository.findAllByCompanyKeyAndCompanyState(companyKey,state);
          if(companyUpdate != null){
              return  companyUpdate;

          }else {
              return  null;
          }

    }
    /**
     * 新增该客商的修改记录
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public CompanyUpdate saveCompanyUpdate(CompanyUpdate resources,DingUser dingUser) {
        resources.setUserId(dingUser.getUserid());
        resources.setCreateMan(dingUser.getName());
        //1 新增 2 审批中 3驳回 4审核通过
        CompanyUpdate companyUpdate = companyUpdateRepository.findByCompanyKeyAndUserIdAndApproveResult(resources.getCompanyKey(),dingUser.getUserid(),"未知");
//        List<CompanyUpdate> companyUpdates = companyUpdateRepository.findAllByCompanyKeyAndCompanyState(resources.getCompanyKey(),resources.getCompanyState());
        if(companyUpdate !=null){
            resources.setOperateKey(companyUpdate.getOperateKey());
            return companyUpdateRepository.save(resources);

        }else {
            return companyUpdateRepository.save(resources);
        }
    }

    /**
     * 获取改客商审批链接
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String getDingUrl(CompanyUpdate resources,DingUser dingUser) {
        CompanyUpdate companyUpdate = null;
        // 1 新增 2 新增审批 3驳回 4审批通过 5变更审批
        if("5".equals(resources.getCompanyState())){
              //操作类型  1新增2修改 3 停用...更多对照字典
            companyUpdate =companyUpdateRepository.findByCompanyKeyAndUserIdAndApproveResultAndOperationType(resources.getCompanyKey(),dingUser.getUserid(),"未知","2");

            if(companyUpdate == null){
                companyUpdate = companyUpdateRepository.findByCompanyKeyAndUserIdAndApproveResult(resources.getCompanyKey(),dingUser.getUserid(),"未知");
            }
        }else {
            companyUpdate = companyUpdateRepository.findByCompanyKeyAndUserIdAndApproveResult(resources.getCompanyKey(),dingUser.getUserid(),"未知");
        }


        if(companyUpdate != null){
            String url = companyUpdate.getDingUrl();
            return  url;
        }else {
            return null;
        }


    }



}
