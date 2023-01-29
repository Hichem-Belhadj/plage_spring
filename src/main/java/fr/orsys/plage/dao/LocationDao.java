package fr.orsys.plage.dao;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import fr.orsys.plage.business.Locataire;
import fr.orsys.plage.business.Location;

public interface LocationDao extends JpaRepository<Location, Long> {

	List<Location>findByIdBetween(LocalDateTime dateHeureDebut,LocalDateTime dateHeureFin);
	
	List<Location>findByStatut(String statut);
	
	List<Location>findByLocataire(Locataire locataire);
	
	/**
	 * Cette requette récupère la liste des location
	 * confirmée pour un locataire
	 * 
	 * @param locataire
	 * @param statut
	 * @return
	 */
	@Query( value =
		"""
		SELECT l
		FROM Location l
		JOIN l.locataire lo
		JOIN l.statut st
		WHERE st.nom =:statut
		AND lo=:locataire
		"""
	)
	List<Location>findByLocataireAndStatutName(@Param("locataire")Locataire locataire, @Param("statut") String statut);
	
}
