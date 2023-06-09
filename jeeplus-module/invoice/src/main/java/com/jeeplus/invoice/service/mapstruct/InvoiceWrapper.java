package com.jeeplus.invoice.service.mapstruct;

import com.jeeplus.core.mapstruct.EntityWrapper;
import com.jeeplus.invoice.domain.Invoice;
import com.jeeplus.invoice.service.dto.InvoiceDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {} )
public interface InvoiceWrapper extends EntityWrapper<InvoiceDTO, Invoice> {

    InvoiceWrapper INSTANCE = Mappers.getMapper(InvoiceWrapper.class);
    @Mappings({
            @Mapping(source = "invoiceSequenceSetupId", target = "invoiceSequenceSetupId"),
            @Mapping(source = "numberSequenceCode", target = "numberSequenceCode"),
            @Mapping(source = "hadGenerated", target = "hadGenerated"),
            @Mapping(source = "createBy.id", target = "createBy"),
            @Mapping (source = "updateBy.id", target = "updateBy")})
     Invoice toEntity(InvoiceDTO dto);


    @Mappings({
            @Mapping (source = "createBy", target = "createBy.id"),
            @Mapping (source = "updateBy", target = "updateBy.id")})
    InvoiceDTO toDTO(Invoice entity);
}