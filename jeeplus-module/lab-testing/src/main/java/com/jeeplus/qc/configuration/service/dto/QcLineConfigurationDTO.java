package com.jeeplus.qc.configuration.service.dto;

import com.google.common.collect.Lists;
import com.jeeplus.core.query.Query;
import com.jeeplus.core.query.QueryType;
import com.jeeplus.core.service.dto.BaseDTO;
import com.jeeplus.sys.service.dto.UserDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;


    @Data
    @EqualsAndHashCode(callSuper = false)
    public class QcLineConfigurationDTO extends BaseDTO {

        private static final long serialVersionUID = 1L;

        public  String id;
        public  String lineNo;
      @Query(tableColumn = "factory", javaField = "factory", type = QueryType.EQ)
        public  String factory;
        @Query(tableColumn = "building", javaField = "building", type = QueryType.EQ)

        public  String building;
        @Query(tableColumn = "floor", javaField = "floor", type = QueryType.EQ)

        public  String floor;
        public  String productGroup;
        public  String type;
        public  String status;
        public  String remarks;

        public  String show_line_no;

//        private List<QcLineConfigurationDTO> QcLineConfigurationDTOList = Lists.newArrayList();
}
