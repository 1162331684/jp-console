/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.qc.service.mapstruct;


import com.jeeplus.core.mapstruct.EntityWrapper;
import com.jeeplus.qc.service.dto.QcTrfDTO;
import com.jeeplus.qc.domain.QcTrf;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 *  QcTrfWrapper
 * @author Lewis
 * @version 2021-11-18
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {} )
public interface QcTrfWrapper extends EntityWrapper<QcTrfDTO, QcTrf> {

    QcTrfWrapper INSTANCE = Mappers.getMapper(QcTrfWrapper.class);
}

