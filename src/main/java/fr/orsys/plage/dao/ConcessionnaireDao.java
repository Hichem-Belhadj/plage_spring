package fr.orsys.plage.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.orsys.plage.business.Concessionnaire;

@Repository
public interface ConcessionnaireDao extends JpaRepository<Concessionnaire, Long> {

}
