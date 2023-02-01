package fr.orsys.plage.service;

import fr.orsys.plage.business.DemandeReservation;
import fr.orsys.plage.business.Location;

public interface DemandeReservationService {
	DemandeReservation ajouterDemandeReservation(byte numeroDeFile, Location location);
}
