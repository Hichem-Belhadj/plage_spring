package fr.orsys.plage.service;

import java.time.LocalDateTime;
import java.util.List;

import fr.orsys.plage.business.File;
import fr.orsys.plage.business.Location;
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
	
	List<List<Integer>> recupererParasolDisponibleSurDuree(String dateDebut, String dateFin);

	double calculPrixParasol(Location locationModifiee, LocalDateTime dateHeureDebut, LocalDateTime dateHeureFin);
	
}
