/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.qc.service.mapstruct;


import com.jeeplus.core.mapstruct.EntityWrapper;
import com.jeeplus.qc.service.dto.QcTrfItemSpecimenTestingDTO;
import com.jeeplus.qc.domain.QcTrfItemSpecimenTesting;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 *  QcTrfItemSpecimenTestingWrapper
 * @author Lewis
 * @version 2021-12-17
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {} )
public interface QcTrfItemSpecimenTestingWrapper extends EntityWrapper<QcTrfItemSpecimenTestingDTO, QcTrfItemSpecimenTesting> {

    QcTrfItemSpecimenTestingWrapper INSTANCE = Mappers.getMapper(QcTrfItemSpecimenTestingWrapper.class);
     @Mappings({
            @Mapping(source = "qcTrfItemSpecimen.id", target = "qcTrfItemSpecimenId"),
            @Mapping(source = "tester.id", target = "tester"),
            @Mapping(source = "createBy.id", target = "createBy"),
            @Mapping (source = "updateBy.id", target = "updateBy")})
    QcTrfItemSpecimenTesting toEntity(QcTrfItemSpecimenTestingDTO dto);


    @Mappings({
            @Mapping(source = "qcTrfItemSpecimenId", target = "qcTrfItemSpecimen.id"),
            @Mapping(source = "tester", target = "tester.id"),
            @Mapping (source = "createBy", target = "createBy.id"),
            @Mapping (source = "updateBy", target = "updateBy.id")})
    QcTrfItemSpecimenTestingDTO toDTO(QcTrfItemSpecimenTesting entity);
}

