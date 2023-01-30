package fr.orsys.plage.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import fr.orsys.plage.business.Pays;

@Repository
public interface PaysDao extends JpaRepository<Pays, String> {
	
	Pays findByNom(String nom);
	
	@Query(
		"""
		FROM Pays p
		WHERE p.code=:code
		"""
	)
	Pays findByCode(String code);
}
