package fr.orsys.plage.dao;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.github.javafaker.File;

import fr.orsys.plage.business.Locataire;
import fr.orsys.plage.business.Location;
import fr.orsys.plage.business.Statut;

public interface LocationDao extends JpaRepository<Location, Long> {


	
	
	List<Location>findByIdBetween(LocalDateTime dateHeureDebut,LocalDateTime dateHeureFin);
	
	List<Location>findByStatut(Statut statut);
	
	List<Location>findByLocataire(Locataire locataire);
	
	
}
