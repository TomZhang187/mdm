package com.hqhop.utils;

import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class HttpUtil {
    public static String postRequest(String url, MultiValueMap params) {
        RestTemplate client = new RestTemplate();
        //新建Http头，add方法可以添加参数
        HttpHeaders headers = new HttpHeaders();
        //设置请求发送方式
        HttpMethod method = HttpMethod.POST;
        // 以表单的方式提交
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        //将请求头部和参数合成一个请求
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);
        //执行HTTP请求，将返回的结构使用String 类格式化（可设置为对应返回值格式的类）
        ResponseEntity<String> response = client.exchange(url, method, requestEntity, String.class);
        return response.getBody();
    }

    /**
     *
     * @param url
     * @param params
     * @return
     */
    public static String getRequest(String url, MultiValueMap<String, String> params) {
        RestTemplate client = new RestTemplate();
        HttpMethod method = HttpMethod.GET;
        HttpHeaders headers = new HttpHeaders();
        // 以表单的方式提交
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        //将请求头部和参数合成一个请求
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);
        //执行HTTP请求，将返回的结构使用String 类格式化
        ResponseEntity<String> response = client.exchange(url, method, requestEntity, String.class);
        return response.getBody();
    }

    public static void main(String[] args) {
        String url = "http://119.6.33.92:8087/service/AddCubasdocData";
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LinkedMultiValueMap<Object, Object> map = new LinkedMultiValueMap<>();
        map.add("custcode", "9001");////客商编码
        map.add("custname", "客商名称接口测试1122");//客商名称
        map.add("custshortname", "123");//客商名称
        map.add("pk_cubasdoc1", "客商名称接口测试1122"); //客商总公司编码
        map.add("custprop", "2");//客商类型
        map.add("pk_corp1", "1017");//对应公司
        map.add("pk_areacl", "0001F810000000000MSL");//所属地区
        map.add("creator", "0001F8100000000004O0");
        map.add("createtime", df.format(LocalDateTime.now()));
        map.add("modifier", "0001F8100000000004O0");
        map.add("modifytime", df.format(LocalDateTime.now()));
        HttpUtil.postRequest(url, map);
    }
}
