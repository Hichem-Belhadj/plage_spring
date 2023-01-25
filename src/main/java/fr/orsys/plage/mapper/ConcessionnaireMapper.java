package fr.orsys.plage.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import fr.orsys.plage.business.Concessionnaire;
import fr.orsys.plage.dto.ConcessionnaireDto;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ConcessionnaireMapper {

	ConcessionnaireMapper INSTANCE = Mappers.getMapper(ConcessionnaireMapper.class);
	
	Concessionnaire toEntity(ConcessionnaireDto concessionnaireDto);
	
	ConcessionnaireDto toDto(Concessionnaire concessionnaire);
	
}
