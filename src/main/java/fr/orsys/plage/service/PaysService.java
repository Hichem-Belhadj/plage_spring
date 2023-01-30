package fr.orsys.plage.service;

import java.util.List;

import fr.orsys.plage.business.Pays;

public interface PaysService {

	Pays ajouterPays(String code,String nom);
	
	List<Pays>recupererPays();
	
	boolean supprimerPays(String nom);

	Pays ajouterPays(Pays pays);

	Pays recupererPaysParCode(String code);
	
}
