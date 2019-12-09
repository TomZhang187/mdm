package com.hqhop.config.dingtalk.dingtalkVo;

import lombok.Data;

/**
 * @author ：张丰
 * @date ：Created in 2019/12/9 0009 9:36
 * @description：钉钉附件对象
 * @modified By：
 * @version: $
 */
@Data
public class accessoryVo {

    //企业ID
   public String corpId;

   //空间ID
    public String spaceId;

    //文件ID
    public String fileId;

    //文件名
    public String fileName;

    //文件大小
    public String fileSize;

    //文件类型
    public String fileType;

}
