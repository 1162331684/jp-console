/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.qc.optlog.service.mapstruct;


import com.jeeplus.core.mapstruct.EntityWrapper;
import com.jeeplus.qc.optlog.service.dto.QcTrfItemSpecimenOptlogDTO;
import com.jeeplus.qc.optlog.domain.QcTrfItemSpecimenOptlog;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 *  QcTrfItemSpecimenOptlogWrapper
 * @author max teng
 * @version 2022-11-25
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {} )
public interface QcTrfItemSpecimenOptlogWrapper extends EntityWrapper<QcTrfItemSpecimenOptlogDTO, QcTrfItemSpecimenOptlog> {

    QcTrfItemSpecimenOptlogWrapper INSTANCE = Mappers.getMapper(QcTrfItemSpecimenOptlogWrapper.class);
     @Mappings({
            @Mapping(source = "createBy.id", target = "createBy"),
            @Mapping (source = "updateBy.id", target = "updateBy")})
    QcTrfItemSpecimenOptlog toEntity(QcTrfItemSpecimenOptlogDTO dto);


    @Mappings({
            @Mapping (source = "createBy", target = "createBy.id"),
            @Mapping (source = "updateBy", target = "updateBy.id")})
    QcTrfItemSpecimenOptlogDTO toDTO(QcTrfItemSpecimenOptlog entity);
}

