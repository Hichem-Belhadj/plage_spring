package fr.orsys.plage.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import fr.orsys.plage.business.Locataire;
import fr.orsys.plage.dto.LocataireDto;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LocataireMapper {
	
	LocataireMapper INSTANCE = Mappers.getMapper(LocataireMapper.class);
	
	Locataire toEntity(LocataireDto locataireDto);
	
	LocataireDto toDto(Locataire locataireire);
}
