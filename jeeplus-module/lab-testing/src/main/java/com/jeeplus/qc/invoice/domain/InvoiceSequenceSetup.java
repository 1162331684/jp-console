package com.jeeplus.qc.invoice.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jeeplus.core.domain.BaseEntity;
import com.jeeplus.core.query.Query;
import com.jeeplus.core.query.QueryType;
import com.jeeplus.sys.service.dto.UserDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("invoice_sequence_setup")
public class InvoiceSequenceSetup extends BaseEntity {
    private static final long serialVersionUID = 1L;
    public   String id;

    public String name;
    public  int nextNumber;
    public String numberSequenceCode;
    public String prefix;
    public String year;

//    public String factory;
    public int alphanumeric;
    public  int smallest;
    public int largest;
    public String separatorLine;
    public int nextId;

}