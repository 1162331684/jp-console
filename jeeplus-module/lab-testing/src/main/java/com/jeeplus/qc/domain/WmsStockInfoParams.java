package com.jeeplus.qc.domain;

import lombok.Data;

@Data
public class WmsStockInfoParams {
    private String bNo;
    private String barcode;
    private String f1;
    private String f10;
    private String f13;
    private String materialType;
    private String pageNo;
    private String pageSize;
    private String styleNo;
    private String productCode;
    private String result;
    private String factory;
}
