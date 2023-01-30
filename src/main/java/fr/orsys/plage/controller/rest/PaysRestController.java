package fr.orsys.plage.controller.rest;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.orsys.plage.business.Pays;
import fr.orsys.plage.service.PaysService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/plage/")
public class PaysRestController {

	
	private PaysService paysService;
	
	
	@GetMapping("pays")
	public List<Pays>getPays(){
		return paysService.recupererPays();
	}
	
}
