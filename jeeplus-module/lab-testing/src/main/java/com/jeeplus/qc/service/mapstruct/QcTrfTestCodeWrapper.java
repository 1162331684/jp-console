/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.qc.service.mapstruct;


import com.jeeplus.core.mapstruct.EntityWrapper;
import com.jeeplus.qc.service.dto.QcTrfTestCodeDTO;
import com.jeeplus.qc.domain.QcTrfTestCode;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 *  QcTrfTestCodeWrapper
 * @author max teng
 * @version 2022-11-01
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {} )
public interface QcTrfTestCodeWrapper extends EntityWrapper<QcTrfTestCodeDTO, QcTrfTestCode> {

    QcTrfTestCodeWrapper INSTANCE = Mappers.getMapper(QcTrfTestCodeWrapper.class);
     @Mappings({
            @Mapping(source = "createBy.id", target = "createBy"),
            @Mapping (source = "updateBy.id", target = "updateBy")})
    QcTrfTestCode toEntity(QcTrfTestCodeDTO dto);


    @Mappings({
            @Mapping (source = "createBy", target = "createBy.id"),
            @Mapping (source = "updateBy", target = "updateBy.id")})
    QcTrfTestCodeDTO toDTO(QcTrfTestCode entity);
}

