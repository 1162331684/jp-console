package com.jeeplus.qc.configuration.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jeeplus.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_line")
public class QcLineConfiguration extends BaseEntity {
    private static final long serialVersionUID = 1L;
    public  String id;
    public  String lineNo;
    public  String factory;
    public  String building;
    public  String floor;
    public  String productGroup;
    public  String type;
    public  String status;
    public  String remarks;
    public  String show_line_no;
}
