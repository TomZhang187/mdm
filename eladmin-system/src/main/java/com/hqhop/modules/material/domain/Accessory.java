package com.hqhop.modules.material.domain;

import lombok.Data;

import javax.persistence.*;

/**
 * @author ：张丰
 * @date ：Created in 2019/12/9 0009 14:45
 * @description：附件信息
 * @modified By：
 * @version: $
 */
@Entity
@Data
@Table(name = "accessory")
public class Accessory {

    // 主键
    @Id
    @Column(name = "key")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Key;

    //企业ID
    @Column(name = "corp_id")
    public String corpId;

    //空间ID
    @Column(name = "space_id")
    public String spaceId;

    //文件ID
    @Column(name = "file_id")
    public String fileId;

    //文件名
    @Column(name = "file_name")
    public String fileName;

    //文件大小
    @Column(name = "file_size")
    public String fileSize;

    //文件类型
    @Column(name = "file_type")
    public String fileType;

}
