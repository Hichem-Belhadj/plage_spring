package fr.orsys.plage.service.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Service;

import fr.orsys.plage.business.File;
import fr.orsys.plage.business.Location;
import fr.orsys.plage.business.Parasol;
import fr.orsys.plage.dao.FileDao;
import fr.orsys.plage.dao.LocationDao;
import fr.orsys.plage.dao.ParasolDao;
import fr.orsys.plage.exception.NotExistingFileException;
import fr.orsys.plage.exception.NotExistingLocationException;
import fr.orsys.plage.exception.ParasolNotFoundException;
import fr.orsys.plage.service.ParasolService;
import lombok.AllArgsConstructor;


@Service
@AllArgsConstructor
public class ParasolServiceImpl implements ParasolService{

	private ParasolDao parasolDao;
	private LocationDao locationDao;
	private FileDao fileDao;
	
	@Override
	public List<Parasol> recupererParasols() {
		return parasolDao.findAll() ;
	}

	@Override
	public List<Parasol> recupererParasols(byte numeroFile) {
		
		return parasolDao.findByFileNumero(numeroFile);
	}


	@Override
	public List<Parasol> recupererParosols(Long idLocation) {
		Location location=locationDao.findById(idLocation).orElseThrow(
				()->new NotExistingLocationException("La location recherchée n'existe pas!"));
		
		return location.getParasols();
	}

	@Override
	public Parasol recupererParasol(Long id) {
		
		return parasolDao.findById(id).orElseThrow(
				()->new ParasolNotFoundException("Ce parasol n'exite pas!"));
		
	}

	//parametre file et numero d'emplacement a mettre + travailler avec setter
	@Override
	public Parasol ajouterParasol(byte numeroEmplacement) {
		
		return parasolDao.save(new Parasol(null, numeroEmplacement, null, null));
	}

	@Override
	public Parasol supprimerParasol(Long id) {
		Parasol parasol=parasolDao.findById(id).orElseThrow(
				()->new ParasolNotFoundException("ce parasol n'existe pas!"));
		return parasolDao.save(parasol);
	}

}
