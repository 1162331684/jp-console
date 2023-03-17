/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.qc.service.mapstruct;


import com.jeeplus.core.mapstruct.EntityWrapper;
import com.jeeplus.qc.service.dto.QcTrfTestConditionDTO;
import com.jeeplus.qc.domain.QcTrfTestCondition;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 *  QcTrfTestConditionWrapper
 * @author max teng
 * @version 2022-11-01
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {} )
public interface QcTrfTestConditionWrapper extends EntityWrapper<QcTrfTestConditionDTO, QcTrfTestCondition> {

    QcTrfTestConditionWrapper INSTANCE = Mappers.getMapper(QcTrfTestConditionWrapper.class);
     @Mappings({
             @Mapping(source = "testCode.id", target = "testCodeId"),
             @Mapping(source = "testContent.id", target = "testContentId"),
            @Mapping(source = "createBy.id", target = "createBy"),
            @Mapping (source = "updateBy.id", target = "updateBy")})
    QcTrfTestCondition toEntity(QcTrfTestConditionDTO dto);


    @Mappings({
            @Mapping(source = "testCodeId", target = "testCode.id"),
            @Mapping(source = "testContentId", target = "testContent.id"),
            @Mapping (source = "createBy", target = "createBy.id"),
            @Mapping (source = "updateBy", target = "updateBy.id")})
    QcTrfTestConditionDTO toDTO(QcTrfTestCondition entity);
}

