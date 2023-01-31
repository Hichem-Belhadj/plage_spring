package fr.orsys.plage.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import fr.orsys.plage.business.LienDeParente;
import fr.orsys.plage.business.Locataire;
import fr.orsys.plage.business.Location;
import fr.orsys.plage.business.Pays;
import fr.orsys.plage.business.Utilisateur;
import fr.orsys.plage.dto.LocataireDto;
import fr.orsys.plage.service.LienDeParenteService;
import fr.orsys.plage.service.LocationService;
import fr.orsys.plage.service.PaysService;
import fr.orsys.plage.service.UtilisateurService;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/utilisateur")
@CrossOrigin("http://localhost:4200")
@AllArgsConstructor
public class UtilisateurController {
	
	private final UtilisateurService utilisateurService;
	private final LienDeParenteService lienDeParenteService;
	private final PaysService paysService;
	private final LocationService locationService;

	@GetMapping(value = "/locataire")
	public ResponseEntity<Map<String, Object>> getAllUsers(
	        @RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "3") int taille,
	        @RequestParam(defaultValue = "id") String filtrerPar,
	        @RequestParam(defaultValue = "desc") String trierPar,
	        @RequestParam(defaultValue = "") String valeur,
	        Authentication authentication
	      ) {
		
		String userEmail = authentication.getName();
		Utilisateur utilisateur = utilisateurService.recupererUtilisateurParEmail(userEmail);
		return utilisateurService.recupererUtilisateurPagination(page, taille, filtrerPar, trierPar, utilisateur, valeur);
	}
	
	@GetMapping(value = "/listePays")
	public List<Pays> recupererListePays() {
		return paysService.recupererPays();
	}
	
	@GetMapping(value = "/lienDeParente")
	public List<LienDeParente> recupererLienDeParente() {
		return lienDeParenteService.recupererLienDeParente();
	}
	
	@DeleteMapping("/supprimer/{id}")
	public boolean supprimerUtilisateur(@PathVariable Long id) {
		Utilisateur utilisateur = utilisateurService.recupererUtilisateur(id);
		List<Location> locationConfirmee = locationService.recupererLocationsParlocataireEtStatut((Locataire)utilisateur, "confirmée");
		if (utilisateur==null || !locationConfirmee.isEmpty()) {
			return false;
		}
		return utilisateurService.supprimerUtilisateur(utilisateur);
	}
	
	@PostMapping(value = "/ajout")
	@ResponseStatus(code=HttpStatus.CREATED)
	public Locataire ajouterLocataire(@RequestBody @Valid LocataireDto locataireDto) {
		return utilisateurService.ajouterLocataireDto(locataireDto);
	}
	
}
