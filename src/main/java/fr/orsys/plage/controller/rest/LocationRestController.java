package fr.orsys.plage.controller.rest;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import fr.orsys.plage.business.Location;
import fr.orsys.plage.business.Statut;
import fr.orsys.plage.exception.NotExistingLocationException;
import fr.orsys.plage.service.LocationService;
import fr.orsys.plage.service.StatutService;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/plage/")
public class LocationRestController {

	
	private final LocationService locationService;
	private StatutService statutService;
	
	
	@GetMapping("location")
	public List<Location>getLocations(){
		return  locationService.recupererLocations();
		
	}
	
	@GetMapping("location/{id}")
	public Location getLocationById(@PathVariable Long id) {
		if(locationService.recuperererLocationById(id)!=null) {
			return locationService.recuperererLocationById(id);
		}else {
			return null;
		}
	}
	
	
	@GetMapping("location/statut/{idStatut}")
	public List<Location> getLocationsByStatut(@PathVariable Long idStatut ) {
		Statut statut=statutService.recupererStatutParId(idStatut);
		if(locationService.recupererLocations(statut)!=null) {
			return locationService.recupererLocations(statut);
		}else {
			return null;
		}
	}
	
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
	
	@ExceptionHandler(NotExistingLocationException.class)
	@ResponseStatus(code=HttpStatus.EXPECTATION_FAILED)
	public String traiterLocationInexitante(Exception exception) {
		return exception.getMessage();
	}
	
}
