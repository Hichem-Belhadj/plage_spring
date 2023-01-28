package fr.orsys.plage.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.orsys.plage.business.Locataire;
import fr.orsys.plage.dao.LocataireDao;
import fr.orsys.plage.service.LocataireService;
import lombok.AllArgsConstructor;


@Service
@Transactional
@AllArgsConstructor
public class LocataireServiceImpl implements LocataireService{

	//faire le findbyid
	//faire le findparpays
	private final LocataireDao locataireDao;
	
	@Override
	public List<Locataire> recupererLocataires() {
		return locataireDao.findAll();
	}

	@Override
	public Locataire ajouterLocataire(Locataire locataire) {
		// TODO Auto-generated method stub
		return locataireDao.save(locataire);
	}

}
