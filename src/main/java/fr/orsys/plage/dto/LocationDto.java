package fr.orsys.plage.dto;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LocationDto {

LocalDateTime dateHeureDebut;
	
	LocalDateTime dateHeureFin;

	double montantARegler;

	String remarque;

	ConcessionnaireDto concessionnaireDto;

	LocataireDto locataireDto;
	
}
