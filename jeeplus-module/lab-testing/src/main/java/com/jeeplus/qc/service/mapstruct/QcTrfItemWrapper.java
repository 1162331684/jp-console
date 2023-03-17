/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.qc.service.mapstruct;


import com.jeeplus.core.mapstruct.EntityWrapper;
import com.jeeplus.qc.service.dto.QcTrfItemDTO;
import com.jeeplus.qc.domain.QcTrfItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 *  QcTrfItemWrapper
 * @author Lewis
 * @version 2021-11-18
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {} )
public interface QcTrfItemWrapper extends EntityWrapper<QcTrfItemDTO, QcTrfItem> {

    QcTrfItemWrapper INSTANCE = Mappers.getMapper(QcTrfItemWrapper.class);
     @Mappings({
            @Mapping(source = "qcTrf.id", target = "qcTrfId"),
            @Mapping(source = "createBy.id", target = "createBy"),
            @Mapping (source = "updateBy.id", target = "updateBy")})
    QcTrfItem toEntity(QcTrfItemDTO dto);


    @Mappings({
            @Mapping(source = "qcTrfId", target = "qcTrf.id"),
            @Mapping (source = "createBy", target = "createBy.id"),
            @Mapping (source = "updateBy", target = "updateBy.id")})
    QcTrfItemDTO toDTO(QcTrfItem entity);
}

