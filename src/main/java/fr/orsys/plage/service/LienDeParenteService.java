package fr.orsys.plage.service;

import java.util.List;

import fr.orsys.plage.business.LienDeParente;

public interface LienDeParenteService {

	LienDeParente ajouterLienDeParente(String nom,float coefficient);
	
	List<LienDeParente> recupererLienDeParente();
	
	LienDeParente recupererLienDeParenteParId(Long id);
	
	float coefficientParLienDeParente(String nom);
	
	boolean supprimerLienDeParente(Long id);
	
	LienDeParente modifierLienDeParente(Long id,LienDeParente lienDeParente);
}
