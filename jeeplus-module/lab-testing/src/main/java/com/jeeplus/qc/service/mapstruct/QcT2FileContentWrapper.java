/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.qc.service.mapstruct;


import com.jeeplus.core.mapstruct.EntityWrapper;
import com.jeeplus.qc.service.dto.QcT2FileContentDTO;
import com.jeeplus.qc.domain.QcT2FileContent;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 *  QcT2FileContentWrapper
 * @author Lewis
 * @version 2022-01-04
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {} )
public interface QcT2FileContentWrapper extends EntityWrapper<QcT2FileContentDTO, QcT2FileContent> {

    QcT2FileContentWrapper INSTANCE = Mappers.getMapper(QcT2FileContentWrapper.class);
}

