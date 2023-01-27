package fr.orsys.plage.service;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import fr.orsys.plage.business.Locataire;
import lombok.AllArgsConstructor;


public interface LocataireService {
	
	List<Locataire>recupererLocataires();

}
