package fr.orsys.plage.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.orsys.plage.business.Pays;

public interface PaysDao extends JpaRepository<Pays, String> {
	
	
	Pays findByNom(String nom);	
}
