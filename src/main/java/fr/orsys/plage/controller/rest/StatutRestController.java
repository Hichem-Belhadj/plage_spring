package fr.orsys.plage.controller.rest;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import fr.orsys.plage.business.Statut;
import fr.orsys.plage.service.StatutService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/")
public class StatutRestController {

private StatutService statutService;
	
	
	@GetMapping("statut")
	public List<Statut>getStatuts(){
		return statutService.recupererStatut();
	}
	
	@GetMapping("statut/{id}")
	public Statut getStatutsById(@PathVariable Long id){
		return statutService.recupererStatutParId(id);
	}
	
	@PostMapping("statut/{nom}")
	public Statut postStatut(@PathVariable String nom) {
		return statutService.ajouterStatut(nom);
	}
	
	@DeleteMapping("statut/{id}")
	public boolean deleteStatut(Long id) {
		return statutService.supprimerStatut(id);
	}
	@PatchMapping("statut/{id}")
	@ResponseStatus(code=HttpStatus.OK)
	public ResponseEntity<Statut >patchStatut(@PathVariable Long id,@RequestBody Statut statut) {
		if(statutService.recupererStatutParId(id)!=null) {
			
			statutService.modifierStatut(id, statut);
			return ResponseEntity.status(200).body(statut);
		}else {
			return ResponseEntity.status(404).body(null);
		}
		 
	}
}
