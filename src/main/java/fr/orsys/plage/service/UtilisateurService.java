package fr.orsys.plage.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import fr.orsys.plage.business.Concessionnaire;
import fr.orsys.plage.business.Locataire;
import fr.orsys.plage.business.Utilisateur;
import fr.orsys.plage.dto.UtilisateurDto;

public interface UtilisateurService {
	
	/**
	 * 
	 *TODO: Pas besoin de de classe suplémentaire pour ajouter des Concessionnaires ou des locataires,
	 * on peu utiliser la classe mère avec le principe de polymorphisme :
	 * Utilisateur locataire = new Locataire(...args);
	 * et tu utilise un setter pour les paramètres manquants : locataire.setDateInscription(date)
	 * utilisateurService.ajouterUtilisateur(utilisateur)
	 */
	Concessionnaire ajouterConcessionnaireDetail(String numeroDeTelephone,String nom,String prenom,String email,String motDePasse);

	Concessionnaire ajouterConcessionnaire(String numeroDeTelephone,Utilisateur utilisateur);
	
	Concessionnaire ajouterConcessionnaireDto(String numeroDeTelephone,UtilisateurDto utilisateurDto);
	
	Locataire ajouterLocataire(String nom,String prenom,String email,String motDePasse);
	
	Locataire ajouterLocataire(Utilisateur utilisateur);
	
	Locataire ajouterLocataireDto(UtilisateurDto utilisateurDto);
	
	Utilisateur ajouterUtilisateur(Utilisateur utilisateur);
	
	Utilisateur recupererUtilisateurParEmail(String email);
	
	UtilisateurDto recupererUtilisateurDto(Utilisateur utilisateur);
	
	void ajouterRoleAUtilisateur(Long userId, Long roleId);
	
	List<Utilisateur> recupererTousUtilisateurs();
	
	Utilisateur recupererUtilisateur(Long idUtilisateur);
	
	List<Utilisateur>recupererUtilisateurParType(String type);
	
	boolean supprimerUtilisateur(Long id);
	
	ResponseEntity<Map<String, Object>> recupererUtilisateurPagination(
			int page,
			int taille,
			String filtrerPar,
			String trierPar,
			Utilisateur utilisateur,
			String valeur
	);
	
	//TODO recuperer utilisateur par type 
	
}
