package fr.orsys.plage.service.impl;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import fr.orsys.plage.business.DemandeReservation;
import fr.orsys.plage.business.Location;
import fr.orsys.plage.dao.DemandeReservationDao;
import fr.orsys.plage.service.DemandeReservationService;
import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class DemandeReservationServiceImpl implements DemandeReservationService {
	
	private final DemandeReservationDao demandeReservationDao;

	@Override
	public DemandeReservation ajouterDemandeReservation(byte numeroDeFile, Location location) {
		DemandeReservation demandeReservation = new DemandeReservation();
		
		demandeReservation.setNumeroFile(numeroDeFile);
		demandeReservation.setLocation(location);
		return demandeReservationDao.save(demandeReservation);
	}

}
