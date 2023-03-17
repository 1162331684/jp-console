/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.qc.service.mapstruct;


import com.jeeplus.core.mapstruct.EntityWrapper;
import com.jeeplus.qc.service.dto.QcT2FilesDTO;
import com.jeeplus.qc.domain.QcT2Files;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 *  QcT2FilesWrapper
 * @author zhimi
 * @version 2021-12-21
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {} )
public interface QcT2FilesWrapper extends EntityWrapper<QcT2FilesDTO, QcT2Files> {

    QcT2FilesWrapper INSTANCE = Mappers.getMapper(QcT2FilesWrapper.class);
}

