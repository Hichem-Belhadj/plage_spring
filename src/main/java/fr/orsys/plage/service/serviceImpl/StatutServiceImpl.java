package fr.orsys.plage.service.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.orsys.plage.business.Statut;
import fr.orsys.plage.dao.StatutDao;
import fr.orsys.plage.exception.NotExistingStatutException;
import fr.orsys.plage.service.StatutService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Service
public class StatutServiceImpl implements StatutService {


	@Autowired
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
				()->new NotExistingStatutException("Ce statut est inexistant!"));
		statutAModifier.setNom(statut.getNom());
		return statutAModifier;
	}

	@Override
	public boolean supprimerStatut(Long id) {
		Statut statut=statutDao.findById(id).get();
		if(statut==null) {
			return false;
		}
		statutDao.delete(statut);
		return true;
	}

	@Override
	public Statut recupererStatutParId(Long id) {
		
		return statutDao.findById(id).orElseThrow(
				()->new NotExistingStatutException("Ce statut est inexistant!"));
	}

	@Override
	public Statut recupererStatutParNom(String nom) {
		Statut statut=statutDao.findByNom(nom);
		if(statut==null) {
			throw new NotExistingStatutException("Ce statut n'exite pas!");
		}
		return statut;
	}

}
