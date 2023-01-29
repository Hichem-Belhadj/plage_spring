package fr.orsys.plage.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.orsys.plage.business.Concessionnaire;
import fr.orsys.plage.dao.ConcessionnaireDao;
import fr.orsys.plage.exception.NotExistingConcessionnaireException;
import fr.orsys.plage.service.ConcessionnaireService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class ConcessionnaireServiceImpl implements ConcessionnaireService {

	private ConcessionnaireDao concessionnaireDao;
	
	@Override
	public Concessionnaire recupererConcessionnaire(Long id) {
		return concessionnaireDao.findById(id).orElseThrow(
				()->new NotExistingConcessionnaireException("Ce concessionnaire n'exite pas !"));
	}

	@Override
	public List<Concessionnaire> recupererConcessionnaires() {
		return concessionnaireDao.findAll();
	}

}
