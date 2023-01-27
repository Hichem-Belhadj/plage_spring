package fr.orsys.plage.service.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Service;

import fr.orsys.plage.business.Concessionnaire;
import fr.orsys.plage.dao.ConcessionnaireDao;
import fr.orsys.plage.exception.NotExistingConcessionnaireException;
import fr.orsys.plage.service.ConcessionnaireService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ConcessionnaireServiceImpl implements ConcessionnaireService {

	private ConcessionnaireDao concessionnaireDao;
	
	@Override
	public Concessionnaire recupererConcessionnaire(Long id) {
		Concessionnaire concessionnaire=concessionnaireDao.findById(id).orElseThrow(
				()->new NotExistingConcessionnaireException("Ce concessionnaire n'exite pas!"));
		return concessionnaire;
	}

	@Override
	public List<Concessionnaire> recupererConcessionnaires() {
		
		return concessionnaireDao.findAll();
	}

}
