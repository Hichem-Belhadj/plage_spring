package fr.orsys.plage.dto;

import java.time.LocalDateTime;
import java.util.List;

import fr.orsys.plage.business.Concessionnaire;
import fr.orsys.plage.business.DemandeReservation;
import fr.orsys.plage.business.Locataire;
import fr.orsys.plage.business.Parasol;
import fr.orsys.plage.business.Statut;
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
	
	Long id;
	
	LocalDateTime dateHeureDebut;

	LocalDateTime dateHeureFin;
	
	double montantARegler;
	
	String remarque;
	
	List<Parasol> parasols;
	
	Concessionnaire concessionnaire;
	
	List<DemandeReservation> demandeReservations;
	
	Statut statut;
	
	Locataire locataire;
	
}
