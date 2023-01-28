package fr.orsys.plage.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import fr.orsys.plage.business.Utilisateur;
import fr.orsys.plage.dto.UtilisateurDto;
import fr.orsys.plage.service.UtilisateurService;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin("http://localhost:4200")
@AllArgsConstructor
public class AuthController {
	
	private final UtilisateurService utilisateurService;

	@GetMapping(value = "/utilisateurCourrant")
	@ResponseBody
    public UtilisateurDto utilisateurCourrant(Authentication authentication) {
		String userEmail = authentication.getName();
		Utilisateur utilisateur = utilisateurService.recupererUtilisateurParEmail(userEmail);
		return utilisateurService.recupererUtilisateurDto(utilisateur);
    }
	
}
