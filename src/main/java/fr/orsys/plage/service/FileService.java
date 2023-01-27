package fr.orsys.plage.service;

import java.util.List;

import fr.orsys.plage.business.File;

public interface FileService {
	
	List<File> recupererFiles();
	
	File recupererFile(Long id);
	
	File recupererFile(byte numero);
	
	File ajouterFile(Byte numero,double prixJournalier);
	
	boolean supprimerFile(Long id);
	
	File mettreAJourFile(byte numero,double nouveauPrixJournalier);
	
	
	

}
