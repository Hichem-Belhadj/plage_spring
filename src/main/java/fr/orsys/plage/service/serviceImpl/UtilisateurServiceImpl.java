package fr.orsys.plage.service.serviceImpl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import fr.orsys.plage.business.Concessionnaire;
import fr.orsys.plage.business.Locataire;
import fr.orsys.plage.business.Utilisateur;
import fr.orsys.plage.dao.UtilisateurDao;
import fr.orsys.plage.dto.UtilisateurDto;
import fr.orsys.plage.exception.NotExistingUtilisateurException;
import fr.orsys.plage.service.UtilisateurService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UtilisateurServiceImpl  implements UtilisateurService{

	//supprimer utilisateur et verifier si il n'a pas de location a l'etat validé (exception)
	private UtilisateurDao utilisateurDao;
	@Override
	public Concessionnaire ajouterConcessionnaireDetail(String numeroDeTelephone, String nom, String prenom,
			String email, String motDePasse) {
		Concessionnaire concessionnaire=new Concessionnaire();
		concessionnaire.setNumeroDeTelephone(numeroDeTelephone);
		concessionnaire.setNom(nom);
		concessionnaire.setPrenom(prenom);
		concessionnaire.setEmail(email);
		concessionnaire.setMotDePasse(motDePasse);
		return utilisateurDao.save(concessionnaire);
	}

	@Override
	public Concessionnaire ajouterConcessionnaire(String numeroDeTelephone, Utilisateur utilisateur) {
		Concessionnaire concessionnaire=new Concessionnaire(numeroDeTelephone);
		concessionnaire.setNom(utilisateur.getNom());
		concessionnaire.setPrenom(utilisateur.getPrenom());
		concessionnaire.setEmail(utilisateur.getEmail());
		concessionnaire.setMotDePasse(utilisateur.getMotDePasse());
		return concessionnaire;
		
	}

	@Override
	public Concessionnaire ajouterConcessionnaireDto(String numeroDeTelephone, UtilisateurDto utilisateurDto) {
		
		return null;
	}

	@Override
	public Locataire ajouterLocataire( String nom, String prenom, String email,
			String motDePasse) {
		
		Locataire locataire=new Locataire();
		locataire.setNom(nom);
		locataire.setPrenom(prenom);
		locataire.setEmail(email);
		locataire.setMotDePasse(motDePasse);
		return locataire;
	}

	@Override
	public Locataire ajouterLocataire( Utilisateur utilisateur) {

		Locataire locataire=new Locataire();
		locataire.setNom(utilisateur.getNom());
		locataire.setPrenom(utilisateur.getPrenom());
		locataire.setEmail(utilisateur.getEmail());
		locataire.setMotDePasse(utilisateur.getMotDePasse());
		return locataire;
	}

	@Override
	public Locataire ajouterLocataireDto( UtilisateurDto utilisateurDto) {
		
		return null;
	}

	@Override
	public Utilisateur recupererUtilisateur(Long idUtilisateur) {
		Utilisateur utilisateur=utilisateurDao.findById(idUtilisateur).orElseThrow(
				()->new NotExistingUtilisateurException("Cet utilisateur n'existe pas!"));
		return utilisateur;
	}
	

	@Override
	public List<Utilisateur> recupererTousUtilisateurs() {
		
		return utilisateurDao.findAll();
	}

	@Override
	public List<Utilisateur>recupererUtilisateurParType(String type) {
		
		return utilisateurDao.findUserByType(type);
	}



	

}
