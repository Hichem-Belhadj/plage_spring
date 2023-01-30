package fr.orsys.plage.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import fr.orsys.plage.business.Utilisateur;

@Repository
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
		SELECT new map(
		l.id as id,
		l.nom as nom,
		l.prenom as prenom,
		l.email as email,
		l.dateHeureInscription as dateHeureInscription,
		l.lienDeParente.nom as lienDeParente,
		lp.nom as pays
		)
		FROM Locataire l
		INNER JOIN l.pays lp
		INNER JOIN l.roles lr
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
