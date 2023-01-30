package fr.orsys.plage.controller.rest;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import fr.orsys.plage.business.Concessionnaire;
import fr.orsys.plage.business.Locataire;
import fr.orsys.plage.business.Location;
import fr.orsys.plage.business.Statut;
import fr.orsys.plage.business.Utilisateur;
import fr.orsys.plage.exception.NotAutorizedUtilisateurException;
import fr.orsys.plage.exception.NotExistingLocataireException;
import fr.orsys.plage.exception.NotExistingLocationException;
import fr.orsys.plage.exception.NotExistingUtilisateurException;
import fr.orsys.plage.service.LocationService;
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
	
	@GetMapping("reservations")
	public List<Location>getLocations(Authentication authentification){
		String userEmail=authentification.getName();
		Utilisateur utilisateur=utilisateurService.recupererUtilisateurParEmail(userEmail);
		if(utilisateur instanceof Concessionnaire) {
			return  locationService.recupererLocations();
		}else if(utilisateur instanceof Locataire) {
			Long id=utilisateur.getId();
			Locataire locataire= (Locataire) utilisateurService.recupererUtilisateur(id);

			return locationService.recupererLocations(locataire);
			 
		}else {
			 throw new NotExistingUtilisateurException("Vous n'avez pas de compte, enregistrez vous!") ;
		}
		
	}
	
	@GetMapping(value = "/reservations/page")
	public ResponseEntity<Map<String, Object>> getLocationsPage(
	        @RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "3") int taille,
	        @RequestParam(defaultValue = "id") String filtrerPar,
	        @RequestParam(defaultValue = "desc") String trierPar,
	        Authentication authentication
	      ) {
		
		String userEmail = authentication.getName();
		Utilisateur utilisateur = utilisateurService.recupererUtilisateurParEmail(userEmail);
		return locationService.recupererLocationPagination(page, taille, filtrerPar, trierPar, utilisateur);
	}
	
	@GetMapping("reservations/{id}")
	public Location getLocationById(@PathVariable Long id) {
		if(locationService.recuperererLocationById(id)!=null) {
			return locationService.recuperererLocationById(id);
		}else {
			throw new NotExistingLocationException("Cette location n'exite pas!");
		}
	}
	
	
	
	
	//TODO mettre en request et verifier si concessionnaire
	@GetMapping("reservations/locataire/{locataireId}")
	public List<Location>getLocationByLocataireId(@PathVariable Long locataireId){
		Locataire locataire=(Locataire) utilisateurService.recupererUtilisateur(locataireId);
		if(locataire instanceof Locataire && locataire!=null) {
			return locationService.recupererLocations(locataire);
		}else {
			throw new NotExistingLocataireException("Ce locataire n'existe pas");
		}
	}
	
	@GetMapping("reservations/statut/{idStatut}")
	public List<Location> getLocationsByStatut(@PathVariable Long idStatut ) {
		Statut statut=statutService.recupererStatutParId(idStatut);
		if(locationService.recupererLocations(statut)!=null) {
			return locationService.recupererLocations(statut);
		}else {
			throw new NotExistingLocationException("Aucune location pour le statut "+statut.getNom()+"!");
		}
	}
	
	@PatchMapping("reservations/statut/{id}/{nouveauStatutId}")
	@ResponseStatus(code=HttpStatus.OK)
	public Location patchLocationStatut(@PathVariable Long id,@PathVariable Long nouveauStatutId) {
		if(locationService.recuperererLocationById(id)!=null) {
			
			
			Location location=locationService.recuperererLocationById(id);
			Statut statut=statutService.recupererStatutParId(nouveauStatutId);
			locationService.modifierStatutLocation(location.getId(), statut);
			return location;
		}else {
			throw new NotExistingLocationException("Cette location n'exite pas!");
		}
		 
	}
	
	
	//TODO A FAIRE
	@PostMapping(value = "location")
	@ResponseStatus(code=HttpStatus.CREATED)
	public Location ajouterLocation(@RequestBody @Valid Location location, BindingResult result) {

		if (result.hasErrors()) {
			return null;
		}
		else {
			return locationService.ajouterLocation(location, null);
		}
	}
	
	@ExceptionHandler(NotAutorizedUtilisateurException.class)
	@ResponseStatus(code=HttpStatus.UNAUTHORIZED)
	public String traiterLocationInexitante(Exception exception) {
		return exception.getMessage();
	}
}
