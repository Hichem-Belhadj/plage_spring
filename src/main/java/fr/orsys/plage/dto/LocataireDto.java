package fr.orsys.plage.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import fr.orsys.plage.business.LienDeParente;
import fr.orsys.plage.business.Location;
import fr.orsys.plage.business.Pays;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonIdentityInfo(
		  generator = ObjectIdGenerators.PropertyGenerator.class, 
		  property = "id")
public class LocataireDto extends UtilisateurDto {
	
	LocalDateTime dateHeureInscription;
	
	List<Location> locations;
	
	LienDeParente lienDeParente;
	
	Pays pays;
}
