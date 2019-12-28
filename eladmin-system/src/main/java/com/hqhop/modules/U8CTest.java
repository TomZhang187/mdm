package com.hqhop.modules;

import com.hqhop.modules.material.domain.Material;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class U8CTest {
    public static void main(String[] args) throws MalformedURLException {
//        u8cMaHttpClient();
        String url = "http://119.6.33.92:8087/service/XChangeServlet?account=U8cloud&receiver=0001";
//
//        HttpRequest httpRequest = HttpUtil.createPost(url);
//        httpRequest.contentType("text/xml");
//
//        StringBuffer xmlString = new StringBuffer();

        Material ma = new Material();
        String params = buildXml(ma);


        RestTemplate client = new RestTemplate();
        //新建Http头，add方法可以添加参数
        HttpHeaders headers = new HttpHeaders();
        //设置请求发送方式
        HttpMethod method = HttpMethod.POST;
        // 以表单的方式提交
        headers.setContentType(MediaType.APPLICATION_XML);
        //将请求头部和参数合成一个请求
        HttpEntity<String> requestEntity = new HttpEntity<>(params, headers);
        //执行HTTP请求，将返回的结构使用String 类格式化（可设置为对应返回值格式的类）
        ResponseEntity<String> response = client.exchange(url, method, requestEntity, String.class);
        System.out.println(response.getBody());

//        DocumentBuilderFactory domfac = DocumentBuilderFactory.newInstance();
//        DocumentBuilder documentBuilder = null;
        try {
            StringReader sr = new StringReader(response.getBody());
            InputSource is = new InputSource(sr);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(is);
            Element documentElement = doc.getDocumentElement();

            String resultcode = documentElement.getAttribute("resultcode");
            String successful = documentElement.getAttribute("successful");

//            String resultdescription = documentElement.getTagName("resultdescription");
            NodeList resultdescription = documentElement.getElementsByTagName("resultdescription");
            String nodeValue = resultdescription.item(0).getNodeValue();
            System.out.println(nodeValue);
            NodeList resultcode1 = documentElement.getElementsByTagName("resultcode");
            System.out.println(resultcode1);
            System.out.println(resultdescription);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }



//        Document xml = XmlUtil.createXml(response.getBody());
//        org.dom4j.Element rootElement = XmlUtil.getRootElement(response.getBody());

//        String escape = XmlUtil.escape(response.getBody());
//        org.dom4j.Element rootElement = XmlUtil.getRootElement(response.getBody());
//        rootElement.get
//        Element documentElement = xml.getDocumentElement();
//        String resultcode = documentElement.getAttribute("resultcode");
//        String resultdescription = documentElement.getAttribute("resultdescription");
        return;


    }

    private static String buildXml(Material ma) {
        StringBuilder builder = new StringBuilder();
        builder.append("<?xml version=\"1.0\" encoding='UTF-8'?>\n" +
                "<ufinterface account=\"U8cloud\" billtype=\"defStock\" isexchange=\"Y\" proc=\"add\" receiver=\"0001\" replace=\"Y\" roottag=\"\" sender=\"Test\" subbilltype=\"\">")
                .append("  <bill id=\"\">\n <billhead>\n")
                // 存货基本档案（集团）
                // 来源,主数据单据id(若要传存货基本档案 则必填)最大长度为64,类型为:String
                .append("<a_lyid>abcd1</a_lyid>")
// 公司,固定为集团编码 最大长度为64,类型为:String
                .append("<a_pk_corp>0001</a_pk_corp>")
// 创建时间,格式（YYYY-MM-DD HH:mm:ss）最大长度为64,类型为:String
                .append("<a_createtime>2019-12-26 15:01:00</a_createtime>")
// 创建人,最大长度为64,类型为:String
                .append("<a_creator>中文HhLLO</a_creator>")
// 存货编码,必填唯一 最大长度为64,类型为:String
                .append("<a_invcode>Abb</a_invcode>")
// 存货名称,必填 最大长度为64,类型为:String
                .append("<a_invname>测试123-,</a_invname>")
// 型号,最大长度为64,类型为:String
                .append("<a_invtype>9992</a_invtype>")
// 是否应税劳务（Y/N）,最大长度为64,类型为:String
                .append("<a_laborflag>N</a_laborflag>")
// 修改人,最大长度为64,类型为:String
                .append("<a_modifier></a_modifier>")
// 修改时间,最大长度为64,类型为:String
                .append("<a_modifytime></a_modifytime>")
// 存货分类,必填 对应U8C存货分类档案编码 最大长度为64,类型为:String
                .append("<a_pk_invcl>001001</a_pk_invcl>")
// 主计量单位 必填 对应U8C计量档案编码 ,最大长度为64,类型为:String
                .append("<a_pk_measdoc>07</a_pk_measdoc>")
// 税目,对应U8C税目档案编码 最大长度为64,类型为:String
                .append("<a_pk_taxitems>6%</a_pk_taxitems>")
// 存货管理档案
// 来源id,主数据单据id (若要传存货管理档案 则必填)最大长度为64,类型为:String
                .append("<b_lyid>abcd1</b_lyid>")
// 创建时间,格式（YYYY-MM-DD HH:mm:ss）最大长度为64,类型为:String
                .append("<b_createtime>2019-12-25 19:01:00</b_createtime>")
// 创建人,最大长度为64,类型为:String
                .append("<b_creator>HLLO</b_creator>")
// 自定义项（物料类型）,最大长度为64,类型为:String
                .append("<b_free1></b_free1>")
// 自定义项（采购员）,最大长度为64,类型为:String
                .append("<b_free2></b_free2>")
// 自定义项（货位）,最大长度为64,类型为:String
                .append("<b_free3></b_free3>")
// 需求管理,最大长度为64,类型为:String
                .append("<b_issalable>N</b_issalable>")
// 是否虚项,最大长度为64,类型为:String
                .append("<b_isvirtual>N</b_isvirtual>")
// 修改人,最大长度为64,类型为:String
                .append("<b_modifier></b_modifier>")
// 修改时间,最大长度为64,类型为:String
                .append("<b_modifytime></b_modifytime>")
// 出库跟踪入库,最大长度为64,类型为:String
                .append("<b_outtrackin>1</b_outtrackin>")
// 公司,必填 对应U8C公司目录档案编码 最大长度为64,类型为:String
                .append("<b_pk_corp>1002</b_pk_corp>")
// 默认工厂,最大长度为64,类型为:String
                .append("<b_pk_dftfactory></b_pk_dftfactory>")
// 封存时间,最大长度为64,类型为:String
                .append("<b_sealdate></b_sealdate>")
// 是否封存,最大长度为64,类型为:String
                .append("<b_sealflag>Y</b_sealflag>")
// 是否进行序列号管理,最大长度为64,类型为:String
                .append("<b_serialmanaflag>N</b_serialmanaflag>")
// 是否批次管理,最大长度为64,类型为:String
                .append("<b_wholemanaflag>N</b_wholemanaflag>")
// 物料生产档案（公司）案
// 来源id,主数据单据id (若要传物料生产档案 则必填)最大长度为64,类型为:String
                .append("<c_lyid></c_lyid>")
// 是否免检,最大长度为64,类型为:String
                .append("<c_chkfreeflag>N</c_chkfreeflag>")
// 是否需求合并,最大长度为64,类型为:String
                .append("<c_combineflagc>N</c_combineflagc>")
// 创建日期,最大长度为64,类型为:String
                .append("<c_createtimec></c_createtimec>")
// 创建人,最大长度为64,类型为:String
                .append("<c_creator>HLLO</c_creator>")
// 是否按生产订单核算成本,最大长度为64,类型为:String
                .append("<c_iscostbyorder>N</c_iscostbyorder>")
// c_isctoutput,最大长度为64,类型为:String
                .append("<c_isctoutput>N</c_isctoutput>")
// 是否发料,最大长度为64,类型为:String
                .append("<c_issend>N</c_issend>")
// 是否出入库,最大长度为64,类型为:String
                .append("<c_isused>N</c_isused>")
// 最低库存,最大长度为64,类型为:String
                .append("<c_lowstocknum>21</c_lowstocknum>")
// 物料型态,最大长度为64,类型为:String
                .append("<c_materstate>02</c_materstate>")
// 物料类型,最大长度为64,类型为:String
                .append("<c_matertyp>02</c_matertyp>")
// 最高库存,最大长度为64,类型为:String
                .append("<c_maxstornum>3</c_maxstornum>")
// 修改人,最大长度为64,类型为:String
                .append("<c_modifier></c_modifier>")
// 修改时间,最大长度为64,类型为:String
                .append("<c_modifytime></c_modifytime>")
// 委外类型,最大长度为64,类型为:String
                .append("<c_outtype>1</c_outtype>")
// 公司,必填 对应U8C公司目录档案编码 最大长度为64,类型为:String
                .append("<c_pk_corp>1002</c_pk_corp>")
// 生产部门,最大长度为64,类型为:String
                .append("<c_pk_deptdoc3>01</c_pk_deptdoc3>")
// 生产业务员,最大长度为64,类型为:String
                .append("<c_pk_psndoc3c></c_pk_psndoc3c>")
// 封存人,最大长度为64,类型为:String
                .append("<c_pk_sealuser></c_pk_sealuser>")
// 计价方式,最大长度为64,类型为:String
                .append("<c_pricemethod>1</c_pricemethod>")
// 安全库存,最大长度为64,类型为:String
                .append("<c_safetystocknum>1</c_safetystocknum>")
// 计划属性,最大长度为64,类型为:String
                .append("<c_scheattr>>MRP</c_scheattr>")
// 封存时间,最大长度为64,类型为:String
                .append("<c_sealdate></c_sealdate>")
// 封存标志,最大长度为64,类型为:String
                .append("<c_sealflag>N</c_sealflag>")
// 是否成本对象,最大长度为64,类型为:String
                .append("<c_sfcbdx>N</c_sfcbdx>")
// 是否批次核算,最大长度为64,类型为:String
                .append("<c_sfpchs>N</c_sfpchs>")
// 是否根据检验结果入库,最大长度为64,类型为:String
                .append("<c_stockbycheck>N</c_stockbycheck>")
// 是否虚项,最大长度为64,类型为:String
                .append("<c_virtualflag>N</c_virtualflag>")
// 再定货点,最大长度为64,类型为:String
                .append("<c_zdhd>1</c_zdhd>")
// 库存组织主键,最大长度为64,类型为:String
                .append("<c_pk_calbody>1002</c_pk_calbody>")
// 供应类型,最大长度为64,类型为:String)
                .append("<c_supplytype>0</c_supplytype>")
                .append(" </billhead>\n</bill>\n")
                .append("</ufinterface>");


//        builder.append("<?xml version=\"1.0\" encoding=\\\"utf-8\\\"?>\n<resultInfo>\n<result >");
//        builder.append(map.get("result")).append("</ result>\n");
//        builder.append("<resultcode>").append(map.get("resultcode")).append("</resultcode>\n");
//        builder.append("<curBalance>").append(map.get("curBalance")).append("</curBalance>\n");
//        builder.append("<sign>").append(map.get("sign")).append("</sign>\n");
//        builder.append("</resultInfo>");
        return builder.toString();
    }

    private static void u8cMaHttpClient() throws MalformedURLException {
//        String url = "http://119.6.33.92:8087/service/XChangeServlet?account=U8cloud&receiver=0001";
        String url = "http://119.6.33.92:8087/service/XChangeServlet?account=U8cloud&receiver=0001";
        URL realURL = new URL(url);
        HttpURLConnection connection = null;
        OutputStreamWriter out = null;
        InputStreamReader input = null;
        try {
            connection = (HttpURLConnection) realURL.openConnection();
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-type", "text/xml");
            connection.setRequestMethod("POST");
            // 将Document对象写入连接的输出流中
//            File file = new File("/Users/apple/workspace/comGitRepo/mdm/mdm/eladmin-system/src/main/resources/u8cxml/存货档案样本数据.xml");
            Material material = new Material();
            String s = buildXml(material);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(s.getBytes());

            out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
            input = new InputStreamReader(byteArrayInputStream, "UTF-8");
            out.write(new String(s.getBytes("UTF-8")));
//            int length;
//            char[] buffer = new char[1000];
//            while ((length = input.read(buffer, 0, 1000)) != -1) {
//                out.write(buffer, 0, length);
//            }
        } catch (IOException e) {

            e.printStackTrace();
        } finally {
            try {
                input.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // 从连接的输入流中取得回执信息
        InputStream inputStream = null;
        try {
            inputStream = connection.getInputStream();

            DocumentBuilderFactory domfac = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = domfac.newDocumentBuilder();
            Document resDoc = documentBuilder.parse(inputStream);
            Element documentElement = resDoc.getDocumentElement();
            System.out.printf("ssss");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
