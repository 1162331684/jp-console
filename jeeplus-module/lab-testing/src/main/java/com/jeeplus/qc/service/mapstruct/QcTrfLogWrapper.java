/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.qc.service.mapstruct;


import com.jeeplus.core.mapstruct.EntityWrapper;
import com.jeeplus.qc.domain.QcTrfLog;
import com.jeeplus.qc.service.dto.QcTrfLogDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {} )
public interface QcTrfLogWrapper extends EntityWrapper<QcTrfLogDTO, QcTrfLog> {

    QcTrfLogWrapper INSTANCE = Mappers.getMapper(QcTrfLogWrapper.class);
}

