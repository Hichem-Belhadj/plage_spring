package fr.orsys.plage.controller.rest;

import java.text.ParseException;
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
import fr.orsys.plage.business.Utilisateur;
import fr.orsys.plage.dto.LocationDto;
import fr.orsys.plage.exception.NotAutorizedUtilisateurException;
import fr.orsys.plage.exception.NotExistingLocataireException;
import fr.orsys.plage.exception.NotExistingLocationException;
import fr.orsys.plage.exception.NotExistingUtilisateurException;
import fr.orsys.plage.mapper.LocationMapper;
import fr.orsys.plage.service.LocationService;
import fr.orsys.plage.service.ParasolService;
import fr.orsys.plage.service.UtilisateurService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/")
public class LocationRestController {

	private final LocationService locationService;
	private final UtilisateurService utilisateurService;
	private final ParasolService parasolService;
	private final LocationMapper locationMapper;

	/**
	 * Cette fonction permet de récupérer la liste des location.
	 * La liste sera complète pour le concessionnaire et individuelle pour les locataires
	 * @param authentification permet d'autentifier l'autheur de la requette.
	 * @return
	 */
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
	
	/**
	 * Cette fonction permet de récupérer des <strong>pages de location</strong>
	 * @param page
	 * @param taille
	 * @param filtrerPar
	 * @param trierPar
	 * @param authentication
	 * @return
	 */
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
	
	/**
	 * Cette fonction permet de récupérer les détails
	 * d'une réservation en fonction de l'ID
	 * @param id
	 * @return
	 */
	@GetMapping("reservations/{id}")
	public LocationDto getLocationById(@PathVariable Long id) {
		if(locationService.recuperererLocationById(id)!=null) {
			return locationMapper.toDto(locationService.recuperererLocationById(id));
		}else {
			throw new NotExistingLocationException("Cette location n'exite pas!");
		}
	}	
	
	/**
	 * Cette fonction permet de récupérer les information
	 * d'un locataire en fonction de son ID
	 * @param locataireId
	 * @return
	 */
	@GetMapping("reservations/locataire/{locataireId}")
	public List<Location>getLocationByLocataireId(@PathVariable Long locataireId){
		Locataire locataire=(Locataire) utilisateurService.recupererUtilisateur(locataireId);
		if(locataire instanceof Locataire) {
			return locationService.recupererLocations(locataire);
		}else {
			throw new NotExistingLocataireException("Ce locataire n'existe pas");
		}
	}
	
	/**
	 * Cette fonction retourne la liste des parasol dont la réservation est confirmée
	 * La fonction prends en paramètre la date recherchée
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	@GetMapping("reservations/parasol/statut")
	public List<List<Integer>>getLocationsByStatut(@RequestParam String date) {
		return parasolService.recupererParasolParJourAvecEtatConfirme(date);
	}
	
	/**
	 * Cette fonction permet de traiter les nouvelle réservation.
	 * @param authentification
	 * @param locationDto
	 * @param result
	 * @return
	 * @throws ParseException
	 */
	@PostMapping(value = "location")
	@ResponseStatus(code=HttpStatus.CREATED)
	public Location ajouterLocation(Authentication authentification, @RequestBody @Valid LocationDto locationDto, BindingResult result) throws ParseException {

		String userEmail=authentification.getName();
		Utilisateur locataire=  utilisateurService.recupererUtilisateurParEmail(userEmail);
		return locationService.traiterNouvelleReservation(locataire, locationDto);
	}
	
	/**
	 * Cette fonction permet de mettre à jours le statut de la location
	 * après validation par le concessionnaire.
	 * @param locationDto
	 * @return
	 */
	@PatchMapping("reservations/parasol")
	@ResponseStatus(code = HttpStatus.OK)
	public Location patchLocationParasols(@RequestBody LocationDto locationDto) {
		return locationService.MAJReservation(locationDto);
	}
	
	@GetMapping(value = "/reservations/parasol/statut/duree")
	public List<List<Integer>> recupererParasols(@RequestParam String dateDebut, @RequestParam String dateFin) {
		return parasolService.recupererParasolDisponibleSurDuree(dateDebut, dateFin);
	}
	
	/**
	 * Exception sur les utilisateur non authorisés.
	 * @param exception
	 * @return
	 */
	@ExceptionHandler(NotAutorizedUtilisateurException.class)
	@ResponseStatus(code=HttpStatus.UNAUTHORIZED)
	public String traiterLocationInexitante(Exception exception) {
		return exception.getMessage();
	}
}
