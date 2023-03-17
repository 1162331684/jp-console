package com.jeeplus.qc.invoice.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jeeplus.core.domain.BaseEntity;
import com.jeeplus.sys.service.dto.UserDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

    @Data
    @EqualsAndHashCode(callSuper = false)
    @TableName("invoice")
    public class Invoice extends BaseEntity {
        private static final long serialVersionUID = 1L;
        public   String id;
        public String invoiceNumber;
        public String name;
        public  int nextNumber;


    }

