package fr.orsys.plage.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import fr.orsys.plage.business.Location;
import fr.orsys.plage.dto.LocationDto;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LocationMapper {
	
	LocationMapper INSTANCE = Mappers.getMapper(LocationMapper.class);
	
	Location toEntity(LocationDto locationDto);
	
	LocationDto toDto(Location location);
}
