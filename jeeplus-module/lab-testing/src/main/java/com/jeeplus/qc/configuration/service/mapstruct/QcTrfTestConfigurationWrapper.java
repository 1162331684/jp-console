/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.qc.configuration.service.mapstruct;


import com.jeeplus.core.mapstruct.EntityWrapper;
import com.jeeplus.qc.configuration.service.dto.QcTrfTestConfigurationDTO;
import com.jeeplus.qc.configuration.domain.QcTrfTestConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 *  QcTrfTestConfigurationWrapper
 * @author maxteng
 * @version 2022-11-25
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {} )
public interface QcTrfTestConfigurationWrapper extends EntityWrapper<QcTrfTestConfigurationDTO, QcTrfTestConfiguration> {

    QcTrfTestConfigurationWrapper INSTANCE = Mappers.getMapper(QcTrfTestConfigurationWrapper.class);
     @Mappings({
            @Mapping(source = "createBy.id", target = "createBy"),
            @Mapping (source = "updateBy.id", target = "updateBy")})
    QcTrfTestConfiguration toEntity(QcTrfTestConfigurationDTO dto);


    @Mappings({
            @Mapping (source = "createBy", target = "createBy.id"),
            @Mapping (source = "updateBy", target = "updateBy.id")})
    QcTrfTestConfigurationDTO toDTO(QcTrfTestConfiguration entity);
}

