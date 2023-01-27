package fr.orsys.plage.service.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Service;

import fr.orsys.plage.business.Locataire;
import fr.orsys.plage.dao.LocataireDao;
import fr.orsys.plage.service.LocataireService;
import lombok.AllArgsConstructor;


@Service
@AllArgsConstructor
public class LocataireServiceImpl implements LocataireService{

	//faire le findbyid
	//faire le findparpays
	
	private LocataireDao locataireDao;
	@Override
	public List<Locataire> recupererLocataires() {
	
		return locataireDao.findAll();
	}

}
