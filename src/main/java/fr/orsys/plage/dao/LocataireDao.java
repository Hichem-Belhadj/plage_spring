package fr.orsys.plage.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.orsys.plage.business.Locataire;

public interface LocataireDao extends JpaRepository<Locataire, Long> {

}
