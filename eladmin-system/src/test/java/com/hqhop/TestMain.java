package com.hqhop;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiCallBackGetCallBackFailedResultRequest;
import com.dingtalk.api.request.OapiCallBackGetCallBackRequest;
import com.dingtalk.api.request.OapiProcessinstanceCreateRequest;
import com.dingtalk.api.response.OapiCallBackGetCallBackFailedResultResponse;
import com.dingtalk.api.response.OapiCallBackGetCallBackResponse;
import com.dingtalk.api.response.OapiProcessinstanceCreateResponse;
import com.hqhop.common.dingtalk.DingTalkConstant;
import com.hqhop.common.dingtalk.DingTalkUtils;
import com.hqhop.modules.company.domain.CompanyInfo;
import com.hqhop.modules.company.repository.AccountRepository;
import com.hqhop.modules.company.repository.CompanyInfoRepository;
import com.hqhop.modules.company.repository.ContactRepository;
import com.hqhop.modules.company.service.impl.CompanyDingServiceImpl;
import com.hqhop.modules.system.repository.DictDetailRepository;
import com.hqhop.modules.system.repository.EmployeeRepository;
import com.hqhop.modules.system.service.UserService;
import com.hqhop.modules.system.service.impl.DeptDingServiceImpl;
import com.hqhop.modules.system.service.impl.EmployeeDingServiceImpl;
import com.taobao.api.ApiException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestMain {

    @Autowired
    private UserService userService;

    @Autowired
    CompanyDingServiceImpl companyDingService;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeDingServiceImpl employeeDingServic;

    @Autowired
    private DeptDingServiceImpl deptDingService;


    @Autowired
    private CompanyInfoRepository companyInfoRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private DictDetailRepository dictDetailRepository;


//    @Transactional
    @Test
    public void contextLoads() {

        List<CompanyInfo> alls = companyInfoRepository.findByCustomerPropAndBelongCompany("1", "10");

//        List<DictDetail> allByDictId = dictDetailRepository.findAllByDictId(5L);
//        for (DictDetail dictDetail : allByDictId) {
//              if(dictDetail.getValue().equals("10")){
//                  continue;
//              }
        for (CompanyInfo all : alls) {
            CompanyInfo companyInfo = companyInfoRepository.findByCustomerPropAndBelongCompanyAndTaxId("1", "1010", all.getTaxId());
            if(companyInfo != null){
                continue;
            }

          CompanyInfo companyInfo1 = new CompanyInfo();

            companyInfo1.getBasicAttribute(all);
            companyInfo1.setBelongCompany("1010");
            companyInfo1.setCompanyKey(null);
            companyInfo1.setCustomerProp("1");
          companyInfoRepository.save(companyInfo1);
        }


//        }
    }


    @Test
    public void Test1() {


        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        int succeed = 0;
        int failure = 0;

//       CompanyInfo companyInfo = companyInfoRepository.findByCompanyKey(1505565L);



      int one = 0;
      int two = 0;
      int threee = 0;
        String belongCompanuy = null;
        List<CompanyInfo> all = companyInfoRepository.findByBelongCompany("1003");
        for (CompanyInfo companyInfo2 : all) {

            List<CompanyInfo> alls = companyInfoRepository.findByTaxIdAndBelongCompanys(companyInfo2.getTaxId(), "10");
            for (CompanyInfo companyInfo : alls) {
                if(companyInfo.getCustomerProp().equals("1")){
                    one++;
            }else if(companyInfo.getCustomerProp().equals("2")) {
                    two++;
                }else if(companyInfo.getCustomerProp().equals("3")){
                    threee++;
                }
            }
        }
        System.out.println("客户"+one);
        System.out.println("供应商"+two);
        System.out.println("客商"+threee);
    }



    @Test
    public void Test() throws
            ApiException {
        System.out.println(DingTalkUtils.getAccessToken());
        DefaultDingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/processinstance/create");
        OapiProcessinstanceCreateRequest request = new OapiProcessinstanceCreateRequest();
        request.setProcessCode(DingTalkConstant.PROCESSCODE_CUSTOMER_TEST);
        request.setOriginatorUserId("manager1142");
        request.setDeptId(1L);
        List<OapiProcessinstanceCreateRequest.FormComponentValueVo> listForm = new ArrayList<OapiProcessinstanceCreateRequest.FormComponentValueVo>();
        // 单行输入框
        OapiProcessinstanceCreateRequest.FormComponentValueVo input = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        input.setName("名字");
        //1 新增 2 修改 3停用 4启用....更多对照字典
        input.setValue("张丰");
            listForm.add(input);
        request.setFormComponentValues(listForm);
        OapiProcessinstanceCreateResponse response = client.execute(request,DingTalkUtils.getAccessToken());
        System.out.println("发起结果"+response.getErrmsg());
    }


    //查询事件回调接口
    @Test
    public void Test2() throws
            ApiException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/call_back/get_call_back");
        OapiCallBackGetCallBackRequest request = new OapiCallBackGetCallBackRequest();
        request.setHttpMethod("GET");
        OapiCallBackGetCallBackResponse response = client.execute(request,DingTalkUtils.getAccessToken());
        String url = response.getUrl();
        List<String> callBackTag = response.getCallBackTag();
        System.out.println("监听事件类型"+callBackTag);
        System.out.println("回调接口地址"+url);
    }


    //回到失败结果
    @Test
    public void Test3() throws
            ApiException {
        DingTalkClient  client = new DefaultDingTalkClient("https://oapi.dingtalk.com/call_back/get_call_back_failed_result");
        OapiCallBackGetCallBackFailedResultRequest request = new OapiCallBackGetCallBackFailedResultRequest();
        request.setHttpMethod("GET");
        OapiCallBackGetCallBackFailedResultResponse response = client.execute(request,DingTalkUtils.getAccessToken());
        System.out.println(response.getErrmsg());
    }



    //客商审批测试
    @Test
    public void Test4() throws
            ApiException {

        CompanyInfo companyInfo = companyInfoRepository.getByTaxId("91610132766990300F");
//         companyDingService.addApprovel(companyInfo)
    }








}
