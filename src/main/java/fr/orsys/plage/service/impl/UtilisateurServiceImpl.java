package fr.orsys.plage.service.impl;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.orsys.plage.business.Concessionnaire;
import fr.orsys.plage.business.Locataire;
import fr.orsys.plage.business.Role;
import fr.orsys.plage.business.Utilisateur;
import fr.orsys.plage.dao.RoleDao;
import fr.orsys.plage.dao.UtilisateurDao;
import fr.orsys.plage.dto.UtilisateurDto;
import fr.orsys.plage.exception.NotExistingUtilisateurException;
import fr.orsys.plage.mapper.ConcessionnaireMapper;
import fr.orsys.plage.mapper.LocataireMapper;
import fr.orsys.plage.service.UtilisateurService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@AllArgsConstructor
@Transactional
@Log4j2
public class UtilisateurServiceImpl  implements UtilisateurService{

	//supprimer utilisateur et verifier si il n'a pas de location a l'etat validé (exception)
	private final UtilisateurDao utilisateurDao;
	private final RoleDao roleDao;
	private final PasswordEncoder passwordEncoder;
	private LocataireMapper locataireMapper;
	private ConcessionnaireMapper concessionnaireMapper;
	
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
		return utilisateurDao.findById(idUtilisateur).orElseThrow(
				()->new NotExistingUtilisateurException("Cet utilisateur n'existe pas!"));
	}

	@Override
	public List<Utilisateur> recupererTousUtilisateurs() {
		
		return utilisateurDao.findAll();
	}

	@Override
	public List<Utilisateur>recupererUtilisateurParType(String type) {
		
		return utilisateurDao.findUserByType(type);
	}

	@Override
	public Utilisateur ajouterUtilisateur(Utilisateur utilisateur) {
		utilisateur.setMotDePasse(passwordEncoder.encode(utilisateur.getMotDePasse()));
		return utilisateurDao.save(utilisateur);
	}

	@Override
	public void ajouterRoleAUtilisateur(Long userId, Long roleId) {
		Utilisateur utilisateur = utilisateurDao.findById(userId).orElseThrow(
				()->new NotExistingUtilisateurException("Cet utilisateur n'existe pas!"));
		Role role = roleDao.findById(roleId).orElseThrow(
				()->new NotExistingUtilisateurException("Ce rôle n'existe pas!"));
		log.info("------------Ajout role {} à {}", role.getName(), utilisateur.getNom());
		utilisateur.getRoles().add(role);
		utilisateurDao.save(utilisateur);
		
	}

	@Override
	public Utilisateur recupererUtilisateurParEmail(String email) {
		Utilisateur utilisateur =  utilisateurDao.findByEmail(email);
		if (utilisateur == null) {
			throw new NotExistingUtilisateurException("Cet utilisateur n'existe pas!");
		}
		return utilisateur;
	}

	@Override
	public UtilisateurDto recupererUtilisateurDto(Utilisateur utilisateur) {
		if (utilisateur instanceof Locataire) {
			return locataireMapper.toDto((Locataire)utilisateur);
		} else if(utilisateur instanceof Concessionnaire) {
			return concessionnaireMapper.toDto((Concessionnaire)utilisateur);
		}
		return null;
	}

}
