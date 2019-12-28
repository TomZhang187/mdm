package com.hqhop.common.dingtalk;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiSmartworkHrmEmployeeListRequest;
import com.dingtalk.api.request.OapiSmartworkHrmEmployeeQueryonjobRequest;
import com.dingtalk.api.response.OapiSmartworkHrmEmployeeListResponse;
import com.dingtalk.api.response.OapiSmartworkHrmEmployeeQueryonjobResponse;
import com.taobao.api.ApiException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 智能人事相关的工具类
 */
public class SmartworkUtils {

    /**
     * 在职人员查询
     *
     * @param statusList 在职员工子状态筛选，其他状态无效。2，试用期；3，正式；5，待离职；-1，无状态
     * @param offSet     分页游标，从0开始
     * @param size       分页大小，最大20
     * @return
     * @throws ApiException
     */
    public static OapiSmartworkHrmEmployeeQueryonjobResponse getEmpsFromDingtalk(String statusList, Long offSet, Long size) throws ApiException {

        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/smartwork/hrm/employee/queryonjob");
        OapiSmartworkHrmEmployeeQueryonjobRequest req = new OapiSmartworkHrmEmployeeQueryonjobRequest();
        req.setStatusList(statusList);
        req.setOffset(offSet);
        req.setSize(size);
        OapiSmartworkHrmEmployeeQueryonjobResponse response = client.execute(req, DingTalkUtils.getAccessToken());
        if (response.getErrcode() == 0){
            OapiSmartworkHrmEmployeeQueryonjobResponse.PageResult result = response.getResult();
        }
        return response;
    }

    /**
     * 遍历查询所有的在职人员
     *
     * @param statusList 在职员工子状态筛选，其他状态无效。2，试用期；3，正式；5，待离职；-1，无状态
     * @return userid的list
     * @throws ApiException
     */
    public static List<String> getEmsAll(String statusList) throws ApiException {
        ArrayList<String> empsList = new ArrayList<>();
        boolean flag = true;
        Long offset = 0L;
        while (flag) {
            OapiSmartworkHrmEmployeeQueryonjobResponse empsFromDingtalk = getEmpsFromDingtalk(statusList, offset, 20L);
            if (empsFromDingtalk.getErrcode() == 0) {
                OapiSmartworkHrmEmployeeQueryonjobResponse.PageResult result = empsFromDingtalk.getResult();
                empsList.addAll(result.getDataList());
                try {
                    Long nextCursor = result.getNextCursor();
                    if (nextCursor != null) {
                        offset = nextCursor;
                    } else {
                        flag = false;
                    }
                } catch (NullPointerException e) {
                    flag = false;
                }
            }
        }
        return empsList;
    }

    /**
     * 查询人员花名册

     * @param userid_list  员工userid列表，最大列表长度：20   实例值：1, 2, 3
     * @param field_filter_list 需要获取的花名册字段列表，最大列表长度：20。具体业务字段的code参见附录，实例值： sys01-name, sys01-dept
     * @return
     * @throws ApiException
     *
     *字段code * * 业务含义
     * sys00-name * * 姓名
     * sys00-email * * 邮箱
     * sys00-dept * * 部门 * * (查该字段时，会返回部门名称列表sys00-dept和部门id列表sys00-deptIds)
     * sys00-mainDept * * 主部门  (查该字段时，会返回主部门名称sys00-mainDept和主部门id sys00-mainDeptId)
     * sys00-position * 职位
     * sys00-mobile * * 手机号
     * sys00-jobNumber * 工号
     * sys00-tel * * 分机号
     * sys00-workPlace * * 办公地点
     * sys00-remark * * 备注
     * sys00-confirmJoinTime * * 入职时间
     * sys01-employeeType * * 员工类型
     * sys01-employeeStatus * * 员工状态
     * sys01-probationPeriodType * * 试用期
     * sys01-regularTime * * 转正日期
     * sys01-positionLevel * * 岗位职级
     * sys02-realName * * 身份证姓名
     * sys02-certNo * * 证件号码
     * sys02-birthTime * * 出生日期
     * sys02-sexType * * 性别
     * sys02-nationType * * 民族
     * sys02-certAddress * * 身份证地址
     * sys02-certEndTime * * 证件有效期
     * sys02-marriage * * 婚姻状况
     * sys02-joinWorkingTime * * 首次参加工作时间
     * sys02-residenceType * * 户籍类型
     * sys02-address * * 住址
     * sys02-politicalStatus * * 政治面貌
     * sys09-personalSi * * 个人社保账号
     * sys09-personalHf * * 个人公积金账号
     * sys03-highestEdu * * 最高学历
     * sys03-graduateSchool * * 毕业院校
     * sys03-graduationTime * * 毕业时间
     * sys03-major * * 所学专业
     * sys04-bankAccountNo * * 银行卡号
     * sys04-accountBank * * 开户行
     * sys05-contractCompanyName * * 合同公司
     * sys05-contractType * * 合同类型
     * sys05-firstContractStartTime * * 首次合同起始日
     * sys05-firstContractEndTime * * 首次合同到期日
     * sys05-nowContractStartTime * * 现合同起始日
     * sys05-nowContractEndTime * * 现合同到期日
     * sys05-contractPeriodType * * 合同期限
     * sys05-contractRenewCount * * 续签次数
     * sys06-urgentContactsName * * 紧急联系人姓名
     * sys06-urgentContactsRelation * * 联系人关系
     * sys06-urgentContactsPhone * * 联系人电话
     * sys07-haveChild * * 有无子女
     * sys07-childName * * 子女姓名
     * sys07-childSex * * 子女性别
     * sys07-childBirthDate * * 子女出生日期
     * customField * * 自定义字段
     *
     */
    public static OapiSmartworkHrmEmployeeListResponse getEmpListByUserids(String userid_list, String field_filter_list) throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/smartwork/hrm/employee/list");
        OapiSmartworkHrmEmployeeListRequest req = new OapiSmartworkHrmEmployeeListRequest();
        req.setUseridList(userid_list);
        req.setFieldFilterList(field_filter_list);
        OapiSmartworkHrmEmployeeListResponse rsp = client.execute(req, DingTalkUtils.getAccessToken());
        System.out.println(rsp.getBody());
        return rsp;
    }


    /**
     * 15716412641139941
     * 15710167477566459
     * 1570687061312427
     * 15708600214878297
     * 15706872662708696
     * 15706847371627610
     * 15705892939462639
     * 15698028074371518
     * 15686730990126310
     * 15686466935364502
     * 15686007869047789
     * 15686013466063228
     * 15675640681554927
     * 15673877657648691
     *
     * @param args
     */


    public static void main(String[] args) {
        try {
            List<String> empsAll = getEmsAll("2");


            OapiSmartworkHrmEmployeeQueryonjobResponse empsFromDingtalk = getEmpsFromDingtalk("2", 0L, 20L);
            String userids = empsFromDingtalk.getResult().getDataList().stream().collect(Collectors.joining(","));

            String field_filter_list = "sys00-name,sys00-jobNumber,sys00-position,sys00-confirmJoinTime,sys01-probationPeriodType,sys01-regularTime";
            //查询所有的用户
            OapiSmartworkHrmEmployeeListResponse empListResponse = getEmpListByUserids(userids, field_filter_list);
            List<OapiSmartworkHrmEmployeeListResponse.EmpFieldInfoVO> result = empListResponse.getResult();
//            result.forEach();
//            empListByUserids
            result.forEach(emp -> {
               String userid = emp.getUserid();
                List<OapiSmartworkHrmEmployeeListResponse.EmpFieldVO> fieldList = emp.getFieldList();

                List<OapiSmartworkHrmEmployeeListResponse.EmpFieldVO> collect = fieldList.stream().filter(e -> "sys01-probationPeriodType".equals(e.getFieldCode()) && "7".equals(e.getValue()))
                        .collect(Collectors.toList());

//                fieldList.stream().forEach( e -> {
//                    String fieldName = e.getFieldName();//
//                    String value = e.getValue();// 入职时间
//                });
            });

        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

}
