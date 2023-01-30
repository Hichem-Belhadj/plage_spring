package fr.orsys.plage.service;

import java.util.List;

import fr.orsys.plage.business.Statut;

public interface StatutService {

	Statut ajouterStatut(String nom);
	
	List<Statut> recupererStatut();
	
	Statut recupererStatutParId(Long id);
	
	Statut recupererStatutParNom(String nom);
	
	Statut modifierStatut(Long id,Statut statut);
	
	boolean supprimerStatut(Long id);
	
	
	
}
