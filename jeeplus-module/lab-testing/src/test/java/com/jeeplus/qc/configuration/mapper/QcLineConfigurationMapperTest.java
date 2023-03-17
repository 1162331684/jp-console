package com.jeeplus.qc.configuration.mapper;

import com.jeeplus.qc.configuration.service.dto.QcLineConfigurationDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class QcLineConfigurationMapperTest {
@Autowired
private QcLineConfigurationMapper qcLineConfigurationMapper;

    @org.junit.jupiter.api.Test
    void findById() {
        QcLineConfigurationDTO qcLineConfigurationDTO=qcLineConfigurationMapper.findById("1");
    }

    @org.junit.jupiter.api.Test
    void findList() {
//        QcLineConfigurationDTO qcLineConfigurationDTO=qcLineConfigurationMapper.findList();
    }

    @Test
    void updateByIds() {
        int ret= qcLineConfigurationMapper.updateByIds("1","520");
    }

    @Test
    void testFindById() {
        QcLineConfigurationDTO qcLineConfigurationDTO=qcLineConfigurationMapper.findById("1");
        Assertions.assertNotNull(qcLineConfigurationDTO);
    }

}