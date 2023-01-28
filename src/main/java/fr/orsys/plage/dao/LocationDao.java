package fr.orsys.plage.dao;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.orsys.plage.business.Locataire;
import fr.orsys.plage.business.Location;

public interface LocationDao extends JpaRepository<Location, Long> {

	List<Location>findByIdBetween(LocalDateTime dateHeureDebut,LocalDateTime dateHeureFin);
	
	List<Location>findByStatut(String statut);
	
	List<Location>findByLocataire(Locataire locataire);
	
	
}
