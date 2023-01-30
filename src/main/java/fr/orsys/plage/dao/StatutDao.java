package fr.orsys.plage.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.orsys.plage.business.Statut;

@Repository
public interface StatutDao extends JpaRepository<Statut, Long> {

	Statut findByNom(String nom);

}
