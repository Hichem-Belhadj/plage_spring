package fr.orsys.plage.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.orsys.plage.business.Statut;

public interface StatutDao extends JpaRepository<Statut, Long> {

	Statut findByNom(String nom);
	

	
	List<Statut>findAll();

}
