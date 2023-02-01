package fr.orsys.plage.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DemandeReservationDao extends JpaRepository<fr.orsys.plage.business.DemandeReservation, Long> {
	
}
