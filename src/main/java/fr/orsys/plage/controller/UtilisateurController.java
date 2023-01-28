package fr.orsys.plage.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.orsys.plage.business.Utilisateur;
import fr.orsys.plage.service.UtilisateurService;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/utilisateur")
@CrossOrigin("http://localhost:4200")
@AllArgsConstructor
public class UtilisateurController {
	
	private final UtilisateurService utilisateurService;

	@GetMapping(value = "/locataire")
	public ResponseEntity<Map<String, Object>> getAllUsers(
	        @RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "3") int taille,
	        @RequestParam(defaultValue = "id") String filtrerPar,
	        @RequestParam(defaultValue = "desc") String trierPar,
	        Authentication authentication
	      ) {
		
		String userEmail = authentication.getName();
		Utilisateur utilisateur = utilisateurService.recupererUtilisateurParEmail(userEmail);
		return utilisateurService.recupererUtilisateurPagination(page, taille, filtrerPar, trierPar, utilisateur);
	}
	
	@DeleteMapping("/supprimer/{id}")
	public boolean deleteUtilisateur(@PathVariable Long id) {
		return utilisateurService.supprimerUtilisateur(id);
	}
	
}
