package fr.orsys.plage.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.orsys.plage.business.Pays;

public interface PaysDao extends JpaRepository<Pays, String> {
	
}
