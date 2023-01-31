package fr.orsys.plage.controller.rest;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.hibernate.validator.internal.constraintvalidators.bv.notempty.NotEmptyValidatorForArraysOfLong;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import fr.orsys.plage.business.Concessionnaire;
import fr.orsys.plage.business.File;
import fr.orsys.plage.business.LienDeParente;
import fr.orsys.plage.business.Locataire;
import fr.orsys.plage.business.Location;
import fr.orsys.plage.business.Parasol;
import fr.orsys.plage.business.Statut;
import fr.orsys.plage.business.Utilisateur;
import fr.orsys.plage.dao.FileDao;
import fr.orsys.plage.dao.LocationDao;
import fr.orsys.plage.dao.ParasolDao;
import fr.orsys.plage.exception.NotAllowedLocationException;
import fr.orsys.plage.exception.NotAutorizedUtilisateurException;
import fr.orsys.plage.exception.NotExistingLocataireException;
import fr.orsys.plage.exception.NotExistingLocationException;
import fr.orsys.plage.exception.NotExistingUtilisateurException;
import fr.orsys.plage.service.FileService;
import fr.orsys.plage.service.LocataireService;
import fr.orsys.plage.service.LocationService;
import fr.orsys.plage.service.ParasolService;
import fr.orsys.plage.service.StatutService;
import fr.orsys.plage.service.UtilisateurService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/")
public class LocationRestController {

	private final LocationService locationService;
	private final StatutService statutService;
	private final UtilisateurService utilisateurService;
	private final LocataireService locataireService;
	private final FileService fileService;
	private final ParasolService parasolService;
	

	@GetMapping("reservations")
	public List<Location> getLocations(Authentication authentification) {
		String userEmail = authentification.getName();
		Utilisateur utilisateur = utilisateurService.recupererUtilisateurParEmail(userEmail);
		if (utilisateur instanceof Concessionnaire) {
			return locationService.recupererLocations();
		} else if (utilisateur instanceof Locataire) {
			Long id = utilisateur.getId();
			Locataire locataire = (Locataire) utilisateurService.recupererUtilisateur(id);

			return locationService.recupererLocations(locataire);

		} else {
			throw new NotExistingUtilisateurException("Vous n'avez pas de compte, enregistrez vous!");
		}

	}

	@GetMapping("reservations/{id}")
	public Location getLocationById(@PathVariable Long id, Authentication authentification) {
		String userEmail = authentification.getName();
		Utilisateur utilisateur = utilisateurService.recupererUtilisateurParEmail(userEmail);
		if (utilisateur instanceof Concessionnaire) {
			if (locationService.recuperererLocationById(id) != null) {
				return locationService.recuperererLocationById(id);
			} else {
				throw new NotExistingLocationException("Cette location n'exite pas!");
			}
		} else {
			throw new NotAllowedLocationException("Vous ne disposez pas des droits nécessaires!");
		}
	}

	// TODO mettre en request et verifier si concessionnaire
	@GetMapping("reservations/locataire/{locataireId}")
	public List<Location> getLocationByLocataireId(@PathVariable Long locataireId, Authentication authentification)
	{
		
		String userEmail = authentification.getName();
		Utilisateur utilisateur = utilisateurService.recupererUtilisateurParEmail(userEmail);
		if (utilisateur instanceof Concessionnaire) {
			Locataire locataire = (Locataire) utilisateurService.recupererUtilisateur(locataireId);
			if (locataire != null) {
				return locationService.recupererLocations(locataire);
			} else {
				throw new NotExistingLocataireException("Ce locataire n'existe pas");
			}
		} else {
			throw new NotAllowedLocationException("Vous ne disposez pas des droits nécessaires!");
		}
	}

	@GetMapping("reservations/statut/{idStatut}")
	public List<Location> getLocationsByStatut(@PathVariable Long idStatut,Authentication authentification) {
		String userEmail = authentification.getName();
		Utilisateur utilisateur = utilisateurService.recupererUtilisateurParEmail(userEmail);
		if (utilisateur instanceof Concessionnaire) {
		Statut statut = statutService.recupererStatutParId(idStatut);
			if (locationService.recupererLocations(statut) != null) {
				return locationService.recupererLocations(statut);
			} else {
				throw new NotExistingLocationException("Aucune location pour le statut " + statut.getNom() + "!");
			}
		}else {
			throw new NotAllowedLocationException("vous ne disposez pas des droits nécessaires!");
		}
	}

	@PatchMapping("reservations/statut/{id}/{nouveauStatutId}")
	@ResponseStatus(code = HttpStatus.OK)
	public Location patchLocationStatut(@PathVariable Long id, @PathVariable Long nouveauStatutId,Authentication authentification) {
		
		String userEmail = authentification.getName();
		Utilisateur utilisateur = utilisateurService.recupererUtilisateurParEmail(userEmail);
		if (utilisateur instanceof Concessionnaire) {
		
		if (locationService.recuperererLocationById(id) != null) {

			Location location = locationService.recuperererLocationById(id);
			Statut statut = statutService.recupererStatutParId(nouveauStatutId);
			locationService.modifierStatutLocation(location.getId(), statut);
			return location;
		} else {
			throw new NotExistingLocationException("Cette location n'exite pas!");
		}
		}else {
			throw new NotAllowedLocationException("Vous ne disposez pas des droits nécessaires!");
		}

	}
	
	
	

	
	@PatchMapping("reservations/parasol/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public Location patchLocationParasols(@RequestBody Location location) {
		

		Location locationModifiee=locationService.recuperererLocationById(location.getId());
	
		locationModifiee.setParasols(location.getParasols());
		locationModifiee.setStatut(statutService.recupererStatutParId((long)2));
		locationModifiee.setLocataire(location.getLocataire());
		locationModifiee.setDateHeureDebut(location.getDateHeureDebut());
		locationModifiee.setDateHeureFin(location.getDateHeureFin());
		double montantARegler=parasolService.calculPrixParasol(locationModifiee, locationModifiee.getDateHeureDebut(), locationModifiee.getDateHeureFin());

		locationModifiee.setMontantARegler(montantARegler);
		locationService.ajouterLocation(locationModifiee);

	
		return locationModifiee;
	}

	@ExceptionHandler(NotAutorizedUtilisateurException.class)
	@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
	public String traiterLocationInexitante(Exception exception) {
		return exception.getMessage();
	}
}
