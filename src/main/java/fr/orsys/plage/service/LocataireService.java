package fr.orsys.plage.service;

import java.util.List;

import fr.orsys.plage.business.Locataire;


public interface LocataireService {
	
	List<Locataire>recupererLocataires();
	
	Locataire ajouterLocataire(Locataire locataire);

}
