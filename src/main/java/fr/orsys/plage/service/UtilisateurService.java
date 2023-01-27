package fr.orsys.plage.service;

import java.time.LocalDateTime;
import java.util.List;

import fr.orsys.plage.business.Concessionnaire;
import fr.orsys.plage.business.Locataire;
import fr.orsys.plage.business.Utilisateur;
import fr.orsys.plage.dto.UtilisateurDto;

public interface UtilisateurService {
	
	Concessionnaire ajouterConcessionnaireDetail(String numeroDeTelephone,String nom,String prenom,String email,String motDePasse);

	Concessionnaire ajouterConcessionnaire(String numeroDeTelephone,Utilisateur utilisateur);
	
	Concessionnaire ajouterConcessionnaireDto(String numeroDeTelephone,UtilisateurDto utilisateurDto);
	
	Locataire ajouterLocataire(String nom,String prenom,String email,String motDePasse);
	
	Locataire ajouterLocataire(Utilisateur utilisateur);
	
	Locataire ajouterLocataireDto(UtilisateurDto utilisateurDto);
	
	List<Utilisateur> recupererTousUtilisateurs();
	
	Utilisateur recupererUtilisateur(Long idUtilisateur);
	
	List<Utilisateur>recupererUtilisateurParType(String type);
	
	//TODO recuperer utilisateur par type 
	

	
	
}
