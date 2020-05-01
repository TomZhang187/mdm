package com.hqhop.common.dingtalk.dingtalkVo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Table(name = "sys_approve")
public class Approve {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//键值自增
    @NotNull(groups = Approve.Update.class)
    @Column(name = "id")
    private Long id;

    @Column(name = "event_type")
    private String eventType;//事件类型

    @Column(name = "process_instance_id")
    private String processInstanceId;//审批实例id

    @Column(name = "corp_id")
    private String corpId;//审批实例对应的企业

    @Column(name = "create_time")
    private Timestamp createTime;//实例创建时间

    @Column(name = "finish_time")
    private Timestamp finishTime;//实例结束事件

    @Column(name = "title")
    private String title;//实例标题

    @Column(name = "type")
    private String type;//类型 start

    @Column(name = "staff_id")
    private String staffId;//发起审批实例的员工

    @Column(name = "url")
    private String url;//审批实例url，可在钉钉内跳转到审批页面

    @Column(name = "remark")
    private String remark;

    @Column(name = "result")
    private String result;

    @Column(name = "process_code")
    private String processCode;//审批模板的唯一码

    public @interface Update{}

    public Approve(){};

    public Approve(String eventType, String processInstanceId, String corpId, Timestamp createTime, Timestamp finishTime, String title, String type, String staffId, String url, String remark, String result, String processCode) {
        this.eventType = eventType;
        this.processInstanceId = processInstanceId;
        this.corpId = corpId;
        this.createTime = createTime;
        this.finishTime = finishTime;
        this.title = title;
        this.type = type;
        this.staffId = staffId;
        this.url = url;
        this.remark = remark;
        this.result = result;
        this.processCode = processCode;
    }
}
