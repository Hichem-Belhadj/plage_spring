package fr.orsys.plage.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.orsys.plage.business.Pays;
import fr.orsys.plage.dao.PaysDao;
import fr.orsys.plage.service.PaysService;
import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class PaysServiceImpl implements PaysService {

	private final PaysDao paysDao;
		
	@Override
	public Pays ajouterPays(String code, String nom) {
		Pays pays=new Pays();
		pays.setNom(nom);
		pays.setCode(code);
		
		return paysDao.save(pays);
	}

	@Override
	public List<Pays> recupererPays() {
		return paysDao.findAll();
	}

	@Override
	public boolean supprimerPays(String nom) {
		Pays pays=paysDao.findByNom(nom);
		if(pays != null) {
			paysDao.delete(pays);
			return true;
		}
		return false;
	}

	@Override
	public Pays ajouterPays(Pays pays) {
		// TODO Auto-generated method stub
		return paysDao.save(pays);
	}

}
