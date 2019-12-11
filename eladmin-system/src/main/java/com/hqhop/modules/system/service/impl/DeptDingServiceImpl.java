package com.hqhop.modules.system.service.impl;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.*;
import com.dingtalk.api.response.*;
import com.hqhop.config.dingtalk.DingTalkUtils;
import com.hqhop.modules.system.domain.dingVo.deptVo.DingDeptDetailVo;
import com.hqhop.modules.system.domain.Dept;
import com.hqhop.modules.system.repository.DeptRepository;
import com.hqhop.modules.system.service.DeptDingService;
import com.taobao.api.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.sound.midi.Soundbank;
import java.util.List;

/**
 * @author ：张丰
 * @date ：Created in 2019/11/27 0027 14:35
 * @description：部门钉钉业务
 * @modified By：
 * @version: $
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DeptDingServiceImpl implements DeptDingService {

    @Autowired
    private DeptRepository deptRepository;




    //创建钉钉部门
    @Override
    public  String createDignDept(Dept resource) throws
            ApiException {

        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/department/create");
        OapiDepartmentCreateRequest request = new OapiDepartmentCreateRequest();
        Dept dept = deptRepository.getOne(resource.getPid());
        request.setParentid(dept.getDingId());
        request.setSourceIdentifier(resource.getId().toString());
        request.setName(resource.getName());
        OapiDepartmentCreateResponse response = client.execute(request,DingTalkUtils.getAccessToken());

        return  response.getId().toString();
    }

   //更新钉钉部门
   @Override
    public void updateDingDept(Dept resource)throws
            ApiException {

        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/department/update");
        OapiDepartmentUpdateRequest request = new OapiDepartmentUpdateRequest();
        request.setId(Long.valueOf(resource.getDingId()));
        Dept dept = deptRepository.getOne(resource.getPid());
        request.setParentid(dept.getDingId());
        request.setName(resource.getName());
        request.setDeptManagerUseridList(resource.getDeptManagerUseridList());
        OapiDepartmentUpdateResponse response = client.execute(request, DingTalkUtils.getAccessToken());

    }

   //删除钉钉部门
   @Override
    public void deleteDingDept(Dept resource)throws
            ApiException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/department/delete");
        OapiDepartmentDeleteRequest request = new OapiDepartmentDeleteRequest();
        request.setId(resource.getDingId());
        request.setHttpMethod("GET");

        OapiDepartmentDeleteResponse response = client.execute(request,DingTalkUtils.getAccessToken());


    }

    //获取部门列表
    @Override
    public List<OapiDepartmentListResponse.Department> getDeptsLists(Dept resource)throws
            ApiException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/department/list");
        OapiDepartmentListRequest request = new OapiDepartmentListRequest();
        request.setId(resource.getDingId());
        request.setFetchChild(true);
        request.setHttpMethod("GET");
        OapiDepartmentListResponse response = client.execute(request, DingTalkUtils.getAccessToken());
        List<OapiDepartmentListResponse.Department> list = response.getDepartment();
        return  list;
    }

    //同步钉钉部门到后台部门
    @Override
    public void syncDeptToDingDept()throws
            ApiException {
        //清空数据
        deptRepository.deleteAll();

         //拿到钉钉数据载入数据库
        Dept dept = new Dept();
        List<OapiDepartmentListResponse.Department> list = getDeptsLists(dept);
        List<Dept> depts = deptRepository.findAll();
        for (OapiDepartmentListResponse.Department department : list) {
            Dept dept1 = new Dept();
            dept1.setDingId(department.getId().toString());
            dept1.setName(department.getName());
            if(department.getParentid() == null){
                dept1.setPid(0L);
            }else {
                dept1.setPid(department.getParentid());
            }
            dept1.setEnabled(true);
            deptRepository.save(dept1);
        }
        //调整数据库中上级部门
        List<Dept> depts1 = deptRepository.findAll();
        for (Dept dept4 : depts1) {
            Dept dept5 = deptRepository.findByDingId(dept4.getPid().toString());
            if(dept5 != null){
                dept4.setPid(dept5.getId());
            }
            deptRepository.save(dept4);
        }

    }

    //获取部门详情
    @Override
    public DingDeptDetailVo getDeptDetails(Dept resource)throws
            ApiException {

        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/department/get");
        OapiDepartmentGetRequest request = new OapiDepartmentGetRequest();
        request.setId(resource.getDingId());
        request.setHttpMethod("GET");
        OapiDepartmentGetResponse response = client.execute(request, DingTalkUtils.getAccessToken());
        DingDeptDetailVo dingDeptDetailVo = new DingDeptDetailVo();
        dingDeptDetailVo.getDataByResponse(response);

        return dingDeptDetailVo;

    }




}
