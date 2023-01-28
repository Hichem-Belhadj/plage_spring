package fr.orsys.plage.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.orsys.plage.business.Statut;
import fr.orsys.plage.dao.StatutDao;
import fr.orsys.plage.exception.NotExistingStatutException;
import fr.orsys.plage.service.StatutService;
import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class StatutServiceImpl implements StatutService {

	private StatutDao statutDao;
	
	
	@Override
	public Statut ajouterStatut(String nom) {
		// TODO verifier si unique dans entité
		Statut statut=new Statut();
		statut.setNom(nom);
		return statutDao.save(statut);
	}

	@Override
	public List<Statut> recupererStatut() {
		return statutDao.findAll();
	}

	//TODO si supprime ou modifier impossible de modifier le statut
	@Override
	public Statut modifierStatut(Long id, Statut statut) {
		Statut statutAModifier=statutDao.findById(id).orElseThrow(
				()->new NotExistingStatutException("Ce statut est inexistant !"));
		statutAModifier.setNom(statut.getNom());
		return statutAModifier;
	}

	@Override
	public boolean supprimerStatut(Long id) {
		Statut statut=statutDao.findById(id).orElseThrow(
				()->new NotExistingStatutException("Ce statut est inexistant !"));
		return statut != null;
	}

}
