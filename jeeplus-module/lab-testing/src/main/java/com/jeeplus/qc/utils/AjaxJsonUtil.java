package com.jeeplus.qc.utils;

import com.jeeplus.common.utils.ResponseUtil;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;

public class AjaxJsonUtil extends ResponseUtil {
    public AjaxJsonUtil(){
        super.add("startTime",ApiCommonUtils.getUTCTimeByString());
        super.add("endTime","");
        super.add("success",true);
        super.add("records", new ArrayList<>());
        super.add("code", 200);
        super.add("msg", "操作成功");
    }
    public void setSuccess(boolean success) {
        super.add("success",success);
    }
    public void setRecord(Object records) {
        super.add("records",records);
    }
    public void close(){
        super.add("endTime",ApiCommonUtils.getUTCTimeByString());
    }
    public ResponseEntity ok() {
        this.close();
        return ResponseEntity.ok(this);
    }

    public ResponseEntity error() {
        this.close();
        return ResponseEntity.badRequest().body(this);
    }

    public ResponseEntity ok(String msg) {
        this.close();
        this.put("msg", msg);
        return ResponseEntity.ok(this);
    }

    public ResponseEntity error(String msg) {
        this.close();
        this.put("msg", msg);
        return ResponseEntity.badRequest().body(this);
    }

}
