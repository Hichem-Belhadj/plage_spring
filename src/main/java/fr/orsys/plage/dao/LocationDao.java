package fr.orsys.plage.dao;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import fr.orsys.plage.business.Locataire;
import fr.orsys.plage.business.Location;
import fr.orsys.plage.business.Statut;

@Repository
public interface LocationDao extends JpaRepository<Location, Long> {

	List<Location>findByIdBetween(LocalDateTime dateHeureDebut,LocalDateTime dateHeureFin);
	
	List<Location>findByStatut(Statut statut);
	
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

	@Query( value =
		"""
		SELECT l.id, u.nom, u.prenom FROM location l
		JOIN utilisateur u ON u.id = l.locataire_id
		WHERE u.id=:id
		""",
		nativeQuery = true
	)
	Page<Location> findLocationsByUtilisateurId(Pageable paging, Long id);
	
}
