/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.qc.service.mapstruct;


import com.jeeplus.core.mapstruct.EntityWrapper;
import com.jeeplus.qc.service.dto.QcTrfItemSpecimenDTO;
import com.jeeplus.qc.domain.QcTrfItemSpecimen;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 *  QcTrfItemSpecimenWrapper
 * @author Lewis
 * @version 2021-12-17
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {} )
public interface QcTrfItemSpecimenWrapper extends EntityWrapper<QcTrfItemSpecimenDTO, QcTrfItemSpecimen> {

    QcTrfItemSpecimenWrapper INSTANCE = Mappers.getMapper(QcTrfItemSpecimenWrapper.class);
}

