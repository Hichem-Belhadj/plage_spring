package fr.orsys.plage.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.orsys.plage.business.Concessionnaire;

public interface ConcessionnaireDao extends JpaRepository<Concessionnaire, Long> {

}
