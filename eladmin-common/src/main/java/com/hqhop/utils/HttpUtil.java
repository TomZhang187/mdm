package com.hqhop.utils;

import com.alibaba.fastjson.JSON;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


public class HttpUtil {
    public static String postRequest(String url, MultiValueMap params) {

        RestTemplate client = getRestTemplate("UTF-8");
        //新建Http头，add方法可以添加参数
        HttpHeaders headers = new HttpHeaders();
        //设置请求发送方式
        HttpMethod method = HttpMethod.POST;

//        method.setRequestHeader("content-type", "application/json;charset=utf-8");
        // 以表单的方式提交
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        //将请求头部和参数合成一个请求
        HttpEntity<MultiValueMap> requestEntity= new HttpEntity<>(params, headers);

        //执行HTTP请求，将返回的结构使用String 类格式化（可设置为对应返回值格式的类）
        ResponseEntity<String> response = client.exchange(url, method, requestEntity, String.class);



        return response.getBody();
    }

    /**
     * @param url
     * @param params
     * @return
     */
    public static String getRequest(String url, MultiValueMap<String, String> params) {
        RestTemplate client = getRestTemplate("UTF-8");

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


    public static RestTemplate getRestTemplate(String charset){
        RestTemplate restTemplate = new RestTemplate();
        List<HttpMessageConverter<?>> list = restTemplate.getMessageConverters();
        for (HttpMessageConverter<?> httpMessageConverter : list) {
            if (httpMessageConverter instanceof StringHttpMessageConverter) {
                ((StringHttpMessageConverter) httpMessageConverter).setDefaultCharset(Charset.forName(charset));
                break;
            }
        }
        return restTemplate;
    }

    /**
     * 发送xml格式的post请求
     * @param url
     * @param xmlString
     * @return
     */
    public static String xmlPostRequest( String url,String xmlString) {

        RestTemplate client = getRestTemplate("UTF-8");
        //新建Http头，add方法可以添加参数
        HttpHeaders headers = new HttpHeaders();
        //设置请求发送方式
        HttpMethod method = HttpMethod.POST;
        // 以表单的方式提交
        headers.setContentType(MediaType.APPLICATION_XML);
        //将请求头部和参数合成一个请求
        HttpEntity<String> requestEntity = new HttpEntity<>(xmlString, headers);
        //执行HTTP请求，将返回的结构使用String 类格式化（可设置为对应返回值格式的类）
        ResponseEntity<String> response = client.exchange(url, method, requestEntity, String.class);
        System.out.println(response.getBody());

        try {
            StringReader sr = new StringReader(response.getBody());

            InputSource is = new InputSource(sr);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(is);
            Element documentElement = doc.getDocumentElement();

            String resultcode = documentElement.getAttribute("resultcode");
            String successful = documentElement.getAttribute("successful");

            NodeList resultdescription = documentElement.getElementsByTagName("resultdescription");
            String nodeValue = resultdescription.item(0).getNodeValue();
            System.out.println(nodeValue);
            NodeList resultcode1 = documentElement.getElementsByTagName("resultcode");
            System.out.println(resultcode1);
            System.out.println(resultdescription);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
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
