package com.hqhop.modules.material.utils;

import com.hqhop.modules.material.domain.MaterialType;
import com.hqhop.modules.material.repository.MaterialTypeRepository;
import com.hqhop.modules.material.service.MaterialTypeService;
import com.hqhop.modules.system.domain.Dept;
import com.hqhop.modules.system.service.DeptService;
import com.hqhop.modules.system.service.RoleService;
import com.hqhop.modules.system.service.UserService;
import com.hqhop.modules.system.service.dto.RoleSmallDTO;
import com.hqhop.modules.system.service.dto.UserDTO;
import com.hqhop.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 用于数据获取
 * @author Zheng Jie
 * @date 2019-4-1
 */
@Component
public class DataUtil {

    private final String[] scopeType = {"全部","本级","自定义"};

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private DeptService deptService;

    @Autowired
    private MaterialTypeService materialTypeService;

    @Autowired
    private MaterialTypeRepository materialTypeRepository;

    public Set<Long> getMaterialTypeIds() {

        Set<Long> materialTypeIds = new HashSet<>();

        List<MaterialType> types = materialTypeRepository.findAll();
        for (MaterialType materialType : types) {
            materialTypeIds.add(materialType.getId());
            List<MaterialType> deptChildren = materialTypeService.findByPid(materialType.getId());
            if (deptChildren != null && deptChildren.size() != 0) {
                materialTypeIds.addAll(getMaterialTypeChildren(deptChildren));
            }
        }
        return materialTypeIds;
    }


    public List<Long> getMaterialTypeChildren(List<MaterialType> materialTypeList) {
        List<Long> list = new ArrayList<>();
        materialTypeList.forEach(materialType -> {
                    if (materialType!=null ){
                        List<MaterialType> materialTypes = materialTypeService.findByPid(materialType.getId());
                        if(materialTypeList!=null && materialTypeList.size()!=0){
                            list.addAll(getMaterialTypeChildren(materialTypes));
                        }
                        list.add(materialType.getId());
                    }
                }
        );
        return list;
    }
}
