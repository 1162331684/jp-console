/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.qc.service.mapstruct;


import com.jeeplus.core.mapstruct.EntityWrapper;
import com.jeeplus.qc.service.dto.QcDataTriggerDTO;
import com.jeeplus.qc.domain.QcDataTrigger;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 *  QcDataTriggerWrapper
 * @author Lewis
 * @version 2021-12-08
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {} )
public interface QcDataTriggerWrapper extends EntityWrapper<QcDataTriggerDTO, QcDataTrigger> {

    QcDataTriggerWrapper INSTANCE = Mappers.getMapper(QcDataTriggerWrapper.class);
}

