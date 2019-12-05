package com.hqhop.modules.system.service.impl;

import com.hqhop.exception.BadRequestException;
import com.hqhop.modules.system.domain.Dept;
import com.hqhop.modules.system.service.dto.DeptQueryCriteria;
import com.hqhop.modules.system.service.DeptDingService;
import com.hqhop.utils.QueryHelp;
import com.hqhop.utils.ValidationUtil;
import com.hqhop.modules.system.repository.DeptRepository;
import com.hqhop.modules.system.service.DeptService;
import com.hqhop.modules.system.service.dto.DeptDTO;
import com.hqhop.modules.system.service.mapper.DeptMapper;
import com.taobao.api.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import java.util.*;
import java.util.stream.Collectors;

/**
* @author Zheng Jie
* @date 2019-03-25
*/
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DeptServiceImpl implements DeptService {

    @Autowired
    private DeptRepository deptRepository;

    @Autowired
    private DeptDingService deptDingService;

    @Autowired
    private DeptMapper deptMapper;

    @Override
    public List<DeptDTO> queryAll(DeptQueryCriteria criteria) {

        return deptMapper.toDto(deptRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    public DeptDTO findById(Long id) {
        Optional<Dept> dept = deptRepository.findById(id);
        ValidationUtil.isNull(dept,"Dept","id",id);
        return deptMapper.toDto(dept.get());
    }

    @Override
    public List<Dept> findByPid(long pid) {
        return deptRepository.findByPid(pid);
    }

    @Override
    public Set<Dept> findByRoleIds(Long id) {
        return deptRepository.findByRoles_Id(id);
    }

    @Override
    public Object buildTree(List<DeptDTO> deptDTOS) {



        Set<DeptDTO> trees = new LinkedHashSet<>();         //树集合
        Set<DeptDTO> depts= new LinkedHashSet<>();          //部门集合

        List<String> deptNames = deptDTOS.stream().map(DeptDTO::getName).collect(Collectors.toList());  //部门名字集合
        Boolean isChild;     //判断是否为子部门

        for (DeptDTO deptDTO : deptDTOS) {     //遍历传过来的部门集合
            isChild = false;
            if ("0".equals(deptDTO.getPid().toString())) {     //如果父节点为0，放入trees集合
                trees.add(deptDTO);
            }
            for (DeptDTO it : deptDTOS) { //再次遍历传过来的部门集合
                isChild = false;
                if (it.getPid().equals(deptDTO.getId())) {      //如果父节点与当前节点相同
                    isChild = true;
                    if (deptDTO.getChildren() == null) {        //判断子节点是否为空
                        deptDTO.setChildren(new ArrayList<DeptDTO>());      //为空新建集合
                    }
                    deptDTO.getChildren().add(it); //加入子节点中
                }
            }
            if(isChild)//有子节点
                depts.add(deptDTO);//depts集合加入当前节点
            else if(!deptNames.contains(deptRepository.findNameById(deptDTO.getPid())))  //非根节点
                depts.add(deptDTO);
        }

        if (CollectionUtils.isEmpty(trees)) {
            trees = depts;
        }

        Integer totalElements = deptDTOS!=null?deptDTOS.size():0;

        Map map = new HashMap();
        map.put("totalElements",totalElements);
        map.put("content",CollectionUtils.isEmpty(trees)?deptDTOS:trees);
        return map;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Dept create(Dept resources) throws
            ApiException {
       Dept dept = deptRepository.save(resources);
        //钉钉新建部门
       String dingId = deptDingService.createDignDept(dept);
       dept.setDingId(dingId);
        return deptRepository.save(dept);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Dept resources) throws
            ApiException{
        if(resources.getId().equals(resources.getPid())) {
            throw new BadRequestException("上级不能为自己");
        }
        if(resources.getDingId() == null){
            Dept dept = deptRepository.getOne(resources.getId());
            resources.setDingId(dept.getDingId());
        }
        Optional<Dept> optionalDept = deptRepository.findById(resources.getId());
        ValidationUtil.isNull( optionalDept,"Dept","id",resources.getId());
        Dept dept = optionalDept.get();
        resources.setId(dept.getId());
        Dept dept1 = deptRepository.save(resources);
        deptDingService.updateDingDept(dept1);
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id)throws
            ApiException {
        Dept dept = deptRepository.getOne(id);
        deptDingService.deleteDingDept(dept);
        deptRepository.deleteById(id);
    }



}
