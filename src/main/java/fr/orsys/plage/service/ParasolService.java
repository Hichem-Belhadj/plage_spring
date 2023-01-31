package fr.orsys.plage.service;

import java.util.List;

import fr.orsys.plage.business.File;
import fr.orsys.plage.business.Parasol;

public interface ParasolService {
	
	List<Parasol>recupererParasols();
	
	List<Parasol>recupererParasols(byte numeroFile);
	
	List<Parasol>recupererParosols(Long idLocation);
	
	Parasol recupererParasol(Long id);
	
	Parasol ajouterParasol(byte numeroEmplacement, File file);
	
	Parasol supprimerParasol(Long id);

	Parasol ajouterParasol(Parasol parasol);
	
	List<List<Integer>> recupererParasolParJourAvecEtatConfirme(String dateRecherche);
	
}
