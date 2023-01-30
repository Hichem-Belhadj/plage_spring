package fr.orsys.plage.service;

import java.time.LocalDateTime;
import java.util.List;

import fr.orsys.plage.business.File;
import fr.orsys.plage.business.Location;
import fr.orsys.plage.business.Parasol;
import fr.orsys.plage.business.Statut;

public interface ParasolService {
	
	List<Parasol>recupererParasols();
	
	List<Parasol>recupererParasols(byte numeroFile);
	
	List<Parasol>recupererParosols(Long idLocation);
	
	List<Parasol>recupererParasolsParDate(LocalDateTime date);
	
	List<Parasol>recupererParasolsParDateAndStatut(LocalDateTime date,Statut statut);//recuperer les parasols loué ou pas
	
	List<Parasol>recupererParasolsParStatut(Statut statut);
	Parasol recupererParasol(Long id);
	
	
	
	
	
	List<Parasol>ajouterParasolsALocation(List<Parasol>parasolsAAjouter,Location location);
	
	Parasol supprimerParasol(Long id);

	Parasol creerParasol(byte numeroEmplacement, File file);

	
	
	
	
}
