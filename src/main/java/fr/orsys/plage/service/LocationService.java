package fr.orsys.plage.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import fr.orsys.plage.business.Concessionnaire;
import fr.orsys.plage.business.Locataire;
import fr.orsys.plage.business.Location;
import fr.orsys.plage.business.Parasol;
import fr.orsys.plage.business.Statut;

public interface LocationService {
	
	List<Location>recupererLocations();
	
	// TODO liste de parasol et statut par rapport a une date
	
	Location ajouterLocation(LocalDateTime dateHeureDebut,LocalDateTime dateHeureFin,double montantARegler,String remarques,List<Parasol>parasols,Concessionnaire concessionnaire,Locataire locataire);
	
	Location ajouterLocation(Location location);
	
	Location modifierLocation(Long id,Location location);
	
	List<Location> recupererLocations(LocalDateTime datedebut,LocalDateTime dateFin);
	
	//liste des location par jour (en parametre un jour)
	
	List<Location> recupererLocations(Statut status);
	
	List<Location> recupererLocations (Locataire locataire);
	
	List<Location> recupererLocation(Long idParasol);
	
	List<Location> recupererLocationParFile(Long idFile);
	
	List<Location>recupererLocationsParJour(LocalDateTime jour);
	
	List<Location>recupererLocationsParlocataireEtStatut(Locataire locataire, String statut);
	
	Location recuperererLocationById(Long id);
	
	boolean supprimerLocation(Long id);
	
	double recupererMontantARegler(Long id);
	
	Location modifierStatutLocation(Long id,Statut nouveauStatut);
	
	ResponseEntity<Map<String, Object>> recupererLocationPagination(
			int page,
			int taille,
			String filtrerPar,
			String trierPar
	);
}
