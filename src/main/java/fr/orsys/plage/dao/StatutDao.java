package fr.orsys.plage.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.orsys.plage.business.Statut;

public interface StatutDao extends JpaRepository<Statut, Long> {

}
