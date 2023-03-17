/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.exception.bom.service.mapstruct;


import com.jeeplus.core.mapstruct.EntityWrapper;
import com.jeeplus.exception.bom.service.dto.ExceptionBomDTO;
import com.jeeplus.exception.bom.domain.ExceptionBom;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 *  ExceptionBomWrapper
 * @author Young
 * @version 2022-11-12
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {} )
public interface ExceptionBomWrapper extends EntityWrapper<ExceptionBomDTO, ExceptionBom> {

    ExceptionBomWrapper INSTANCE = Mappers.getMapper(ExceptionBomWrapper.class);
     @Mappings({
            @Mapping(source = "createBy.id", target = "createBy"),
            @Mapping (source = "updateBy.id", target = "updateBy")})
    ExceptionBom toEntity(ExceptionBomDTO dto);


    @Mappings({
            @Mapping (source = "createBy", target = "createBy.id"),
            @Mapping (source = "updateBy", target = "updateBy.id")})
    ExceptionBomDTO toDTO(ExceptionBom entity);
}

