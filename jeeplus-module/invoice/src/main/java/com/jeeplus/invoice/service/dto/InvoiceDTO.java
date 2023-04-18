package com.jeeplus.invoice.service.dto;

import com.jeeplus.core.query.Query;
import com.jeeplus.core.query.QueryType;
import com.jeeplus.core.service.dto.BaseDTO;
import com.jeeplus.sys.service.dto.UserDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.lang.reflect.Array;
import java.math.BigInteger;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
public class InvoiceDTO extends BaseDTO {
    @Query(tableColumn = "invoice_number", javaField = "invoiceNumber", type = QueryType.LIKE)
    public String invoiceNumber;
    @Query(tableColumn = "name", javaField = "name", type = QueryType.LIKE)
    public String name;
    @Query(tableColumn = "number_sequence_code", javaField = "numberSequenceCode", type = QueryType.EQ)
    public String numberSequenceCode;
//    public String factory;
    public  int nextNumber;
    public String lastInvoice;

    public String hadGenerated;
public String invoiceSequenceSetupId;

public String prefix;
public int year;
public int alphanumeric;
public  int smallest;
public int largest;
public String separatorLine;
public int next;
    @Query(tableColumn = "create_by", javaField = "createBy.id", type = QueryType.LIKE)
public UserDTO createBy;
    @Query(tableColumn = "create_date", javaField = "createDate", type = QueryType.BETWEEN)
public Date createDate;

}
