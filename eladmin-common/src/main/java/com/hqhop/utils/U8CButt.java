package com.hqhop.utils;

import com.alibaba.fastjson.JSON;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class U8CButt {
    public static Object operator(String url, Map<String, Object> parameter) {
        String serviceUrl = "http://119.6.33.92:8087";
        String param = JSON.toJSONString(parameter);
        // 使用U8cloud系统中设置，具体节点路径为：
        // 应用集成 - 系统集成平台 - 系统信息设置
        // 设置信息中具体属性的对照关系如下：
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("trantype", "code"); // 档案翻译方式，枚举值为：编码请录入 code， 名称请录入 name， 主键请录入 pk
        map.put("system", "001"); // 系统编码
        map.put("usercode", "mdm"); // 用户
        //String s1 = EncryptUtils.encryptPassword("123456");
        String s1 = "e10adc3949ba59abbe56e057f20f883e";
        System.out.println("password:" + s1);
        map.put("password", s1); // 密码，需要 MD5 加密后录入
        HttpClient httpClient = new HttpClient();
        PostMethod httpPost = new PostMethod(serviceUrl + url);
        httpPost.setRequestHeader("content-type", "application/json;charset=utf-8");
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            httpPost.setRequestHeader(entry.getKey(), entry.getValue().toString());
        }
        String result = new String();
        try {
            RequestEntity entity = new StringRequestEntity(param,
                    "application/json", "UTF-8");
            httpPost.setRequestEntity(entity);
            httpClient.executeMethod(httpPost);
            result = httpPost.getResponseBodyAsString();
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (result.equals("")) {
            System.out.println(111);
            return null;
        }
        System.out.println(result);
        Map res = (Map) JSON.parse(result);
        if (!(res.get("status").equals("success"))) {
            System.out.println(111);
            return null;
        }
        if ((((Map<String, Object>) JSON.parse(((Map<String, Object>) JSON.parse(result)).get("data").toString())).get("datas")) != null) {
            String datas = (((Map<String, Object>) JSON.parse(((Map<String, Object>) JSON.parse(result)).get("data").toString())).get("datas")).toString();
            List<Map> maps = JSON.parseArray(datas, Map.class);
            return maps;
        } else {
            Map<String, Object> data = (Map<String, Object>) JSON.parse(((Map<String, Object>) JSON.parse(result)).get("data").toString());
            return data;
        }
    }

}
