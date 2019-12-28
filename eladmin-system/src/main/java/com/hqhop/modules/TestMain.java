package com.hqhop.modules;

import ch.qos.logback.core.joran.spi.XMLUtil;
import cn.hutool.core.io.resource.ClassPathResource;
import com.sun.xml.internal.ws.util.xml.XmlUtil;

import java.io.*;
import java.net.*;

public class TestMain {
    public static void main(String[] args) {
        String url = "http://119.6.33.92:8087/service/XChangeServlet?account=U8cloud&receiver=0001";
        URL readURL = null;
        OutputStreamWriter out = null;
        InputStreamReader input = null;
        try {
            readURL = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) readURL.openConnection();
            connection.setRequestProperty("Content-type", "text/xml");
            connection.setRequestMethod("POST");
            // 将Document对象写入连接的输出流中
            File file = new File("/home/apple/Desktop/user.xml");
            out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
            input = new InputStreamReader(new FileInputStream(file));
            int length;
            char[] buffer = new char[1000];
            while ((length = input.read(buffer, 0, 1000)) != -1) {
                out.write(buffer, 0, length);
            }

            // 从连接的输入流中取得回执信息
            InputStream inputStream = connection.getInputStream();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
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
    }
}
