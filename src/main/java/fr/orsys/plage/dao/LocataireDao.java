package fr.orsys.plage.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.orsys.plage.business.Locataire;

@Repository
public interface LocataireDao extends JpaRepository<Locataire, Long> {

	Locataire findByEmail(String email);
	
	boolean existsByEmail(String email); 
}
