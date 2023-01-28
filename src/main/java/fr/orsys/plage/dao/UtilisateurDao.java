package fr.orsys.plage.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import fr.orsys.plage.business.Utilisateur;

public interface UtilisateurDao extends JpaRepository<Utilisateur, Long> {
	
	boolean existsByEmail(String email);
	
	Utilisateur findByEmail(String email);
	
	@Query(
		
			"""
			FROM Utilisateur
			WHERE dtype=:dtype
			"""
			)
	List<Utilisateur> findUserByType(@Param("dtype") String dtype);
	

}
