/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.exception.bom.info.service.mapstruct;


import com.jeeplus.core.mapstruct.EntityWrapper;
import com.jeeplus.exception.bom.info.service.dto.ExceptionBomInfoDTO;
import com.jeeplus.exception.bom.info.domain.ExceptionBomInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 *  ExceptionBomInfoWrapper
 * @author Young
 * @version 2022-11-12
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {} )
public interface ExceptionBomInfoWrapper extends EntityWrapper<ExceptionBomInfoDTO, ExceptionBomInfo> {

    ExceptionBomInfoWrapper INSTANCE = Mappers.getMapper(ExceptionBomInfoWrapper.class);
     @Mappings({
            @Mapping(source = "createBy.id", target = "createBy"),
            @Mapping (source = "updateBy.id", target = "updateBy")})
    ExceptionBomInfo toEntity(ExceptionBomInfoDTO dto);


    @Mappings({
            @Mapping (source = "createBy", target = "createBy.id"),
            @Mapping (source = "updateBy", target = "updateBy.id")})
    ExceptionBomInfoDTO toDTO(ExceptionBomInfo entity);
}

