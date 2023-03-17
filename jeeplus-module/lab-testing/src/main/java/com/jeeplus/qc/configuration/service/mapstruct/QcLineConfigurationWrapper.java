/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.qc.configuration.service.mapstruct;


import com.jeeplus.core.mapstruct.EntityWrapper;
import com.jeeplus.qc.configuration.domain.QcLineConfiguration;
import com.jeeplus.qc.configuration.domain.QcTrfTestConfiguration;
import com.jeeplus.qc.configuration.service.dto.QcLineConfigurationDTO;
import com.jeeplus.qc.configuration.service.dto.QcTrfTestConfigurationDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 *  QcLineConfigurationWrapper
 * @author
 * @version
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {} )
public interface QcLineConfigurationWrapper extends EntityWrapper<QcLineConfigurationDTO, QcLineConfiguration> {

    QcLineConfigurationWrapper INSTANCE = Mappers.getMapper(QcLineConfigurationWrapper.class);
     @Mappings({
            @Mapping(source = "createBy.id", target = "createBy"),
            @Mapping (source = "updateBy.id", target = "updateBy")})
    QcLineConfiguration toEntity(QcLineConfigurationDTO dto);


    @Mappings({
            @Mapping (source = "createBy", target = "createBy.id"),
            @Mapping (source = "updateBy", target = "updateBy.id")})
    QcLineConfigurationDTO toDTO(QcLineConfiguration entity);
}

