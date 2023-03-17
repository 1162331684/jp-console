/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.exception.access.service.mapstruct;


import com.jeeplus.core.mapstruct.EntityWrapper;
import com.jeeplus.exception.access.service.dto.ExceptionListDTO;
import com.jeeplus.exception.access.domain.ExceptionList;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 *  ExceptionListWrapper
 * @author Young
 * @version 2022-11-12
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {} )
public interface ExceptionListWrapper extends EntityWrapper<ExceptionListDTO, ExceptionList> {

    ExceptionListWrapper INSTANCE = Mappers.getMapper(ExceptionListWrapper.class);
     @Mappings({
            @Mapping(source = "createBy.id", target = "createBy"),
            @Mapping (source = "updateBy.id", target = "updateBy")})
    ExceptionList toEntity(ExceptionListDTO dto);


    @Mappings({
            @Mapping (source = "createBy", target = "createBy.id"),
            @Mapping (source = "updateBy", target = "updateBy.id")})
    ExceptionListDTO toDTO(ExceptionList entity);
}

