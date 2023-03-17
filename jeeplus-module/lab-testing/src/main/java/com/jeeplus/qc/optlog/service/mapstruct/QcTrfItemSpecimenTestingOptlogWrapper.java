/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.qc.optlog.service.mapstruct;


import com.jeeplus.core.mapstruct.EntityWrapper;
import com.jeeplus.qc.optlog.service.dto.QcTrfItemSpecimenTestingOptlogDTO;
import com.jeeplus.qc.optlog.domain.QcTrfItemSpecimenTestingOptlog;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 *  QcTrfItemSpecimenTestingOptlogWrapper
 * @author max teng
 * @version 2022-11-25
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {} )
public interface QcTrfItemSpecimenTestingOptlogWrapper extends EntityWrapper<QcTrfItemSpecimenTestingOptlogDTO, QcTrfItemSpecimenTestingOptlog> {

    QcTrfItemSpecimenTestingOptlogWrapper INSTANCE = Mappers.getMapper(QcTrfItemSpecimenTestingOptlogWrapper.class);
     @Mappings({
            @Mapping(source = "createBy.id", target = "createBy"),
            @Mapping (source = "updateBy.id", target = "updateBy")})
    QcTrfItemSpecimenTestingOptlog toEntity(QcTrfItemSpecimenTestingOptlogDTO dto);


    @Mappings({
            @Mapping (source = "createBy", target = "createBy.id"),
            @Mapping (source = "updateBy", target = "updateBy.id")})
    QcTrfItemSpecimenTestingOptlogDTO toDTO(QcTrfItemSpecimenTestingOptlog entity);
}

