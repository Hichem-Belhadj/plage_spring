package fr.orsys.plage.service.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import fr.orsys.plage.business.Pays;
import fr.orsys.plage.dao.PaysDao;
import fr.orsys.plage.service.PaysService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PaysServiceImpl implements PaysService {

		private PaysDao paysDao;
		
		
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
		if(pays!=null) {
			paysDao.delete(pays);
			return true;
		}
		return false;
	}

}
