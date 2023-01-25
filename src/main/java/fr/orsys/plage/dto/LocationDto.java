package fr.orsys.plage.dto;

import java.time.LocalDateTime;

import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

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
	
	@Positive(message = "Veuillez renseigner un prix!")
	double montantARegler;
	
	@Lob
	String remarque;
	
	@ManyToOne
	@NotNull
	ConcessionnaireDto concessionnaireDto;
	
	// TODO Status DTO
	
	@NotNull
	LocataireDto locataireDto;
	
}
