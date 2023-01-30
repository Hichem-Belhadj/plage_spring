package fr.orsys.plage.controller.rest;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.orsys.plage.business.Parasol;
import fr.orsys.plage.dao.ParasolDao;
import fr.orsys.plage.service.ParasolService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/plage/")
public class ParasolRestController {
	private ParasolService parasolService;
	
@GetMapping("parasol")
public List<Parasol>getParasols(){
	return parasolService.recupererParasols();
}
}
