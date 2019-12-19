package com.hqhop.utils;

import com.alibaba.excel.EasyExcelFactory;
import org.apache.poi.sl.usermodel.Sheet;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.mock.web.MockMultipartFile;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import static org.junit.Assert.*;

public class FileUtilTest {

    @Test
    public void testToFile() {
        long retval = FileUtil.toFile(new MockMultipartFile("foo", (byte[]) null)).getTotalSpace();
        assertEquals(500695072768L, retval);
    }

    @Test
    public void testGetExtensionName() {
        Assert.assertEquals("foo", FileUtil.getExtensionName("foo"));
        Assert.assertEquals("exe", FileUtil.getExtensionName("bar.exe"));
    }

    @Test
    public void testGetFileNameNoEx() {
        Assert.assertEquals("foo", FileUtil.getFileNameNoEx("foo"));
        Assert.assertEquals("bar", FileUtil.getFileNameNoEx("bar.txt"));
    }

    @Test
    public void testGetSize() {
        Assert.assertEquals("1000B   ", FileUtil.getSize(1000));
        Assert.assertEquals("1.00KB   ", FileUtil.getSize(1024));
        Assert.assertEquals("1.00MB   ", FileUtil.getSize(1048576));
        Assert.assertEquals("1.00GB   ", FileUtil.getSize(1073741824));
    }


    @Test
    public void contextLoads() {

        // 读取 excel 表格的路径
        try {
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream("D:/1.xlsx"));
            List<Object> data = EasyExcelFactory.read(bis, new com.alibaba.excel.metadata.Sheet(1, 0));
            data.forEach(System.out::println);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }

}
