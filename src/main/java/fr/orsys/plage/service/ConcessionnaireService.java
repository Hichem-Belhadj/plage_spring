package fr.orsys.plage.service;

import java.util.List;

import fr.orsys.plage.business.Concessionnaire;

public interface ConcessionnaireService {
	
	Concessionnaire recupererConcessionnaire(Long id);
	
	List<Concessionnaire>recupererConcessionnaires();

}
