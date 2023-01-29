package fr.orsys.plage.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import fr.orsys.plage.business.Utilisateur;

public interface UtilisateurDao extends JpaRepository<Utilisateur, Long> {
	
	boolean existsByEmail(String email);
	
	Utilisateur findByEmail(String email);
	
	/**
	 * Cette requette permet de récupérer un pagnation
	 * contenant uniquement des locataires
	 * 
	 * @param pageable
	 * @return
	 */
	@Query( value =
		"""
		SELECT l, lp
		FROM Locataire l
		LEFT JOIN l.roles lr
		LEFT JOIN l.pays lp
		WHERE lr.name = 'ROLE_USER'
		AND LOWER(lp.nom)
		LIKE LOWER(:valeur)
		"""
	)
	Page<Utilisateur> findLocatairePagination(Pageable pageable, String valeur);
	
	@Query(
		"""
		FROM Utilisateur
		WHERE dtype=:dtype
		"""
	)
	List<Utilisateur> findUserByType(@Param("dtype") String dtype);
	

}
