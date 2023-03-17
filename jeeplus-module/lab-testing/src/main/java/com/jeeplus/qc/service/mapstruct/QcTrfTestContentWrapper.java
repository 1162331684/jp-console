/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.qc.service.mapstruct;


import com.jeeplus.core.mapstruct.EntityWrapper;
import com.jeeplus.qc.domain.QcTrfTestContent;
import com.jeeplus.qc.service.dto.QcTrfTestContentDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 *  QcTrfTestContentWrapper
 * @author max teng
 * @version 2022-11-01
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {} )
public interface QcTrfTestContentWrapper extends EntityWrapper<QcTrfTestContentDTO, QcTrfTestContent> {

    QcTrfTestContentWrapper INSTANCE = Mappers.getMapper(QcTrfTestContentWrapper.class);
     @Mappings({
            @Mapping(source = "testCode.id", target = "testCodeId"),
            @Mapping(source = "createBy.id", target = "createBy"),
            @Mapping (source = "updateBy.id", target = "updateBy")})
     QcTrfTestContent toEntity(QcTrfTestContentDTO dto);


    @Mappings({
            @Mapping(source = "testCodeId", target = "testCode.id"),
            @Mapping (source = "createBy", target = "createBy.id"),
            @Mapping (source = "updateBy", target = "updateBy.id")})
    QcTrfTestContentDTO toDTO(QcTrfTestContent entity);
}

