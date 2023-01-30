package fr.orsys.plage.service.serviceImpl;

import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.introspect.TypeResolutionContext.Empty;

import fr.orsys.plage.business.File;
import fr.orsys.plage.business.Location;
import fr.orsys.plage.business.Parasol;
import fr.orsys.plage.business.Statut;
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

	

	@Override
	public Parasol supprimerParasol(Long id) {
		Parasol parasol=parasolDao.findById(id).orElseThrow(
				()->new ParasolNotFoundException("ce parasol n'existe pas!"));
		return parasolDao.save(parasol);
	}

	// TODO liste des parasols (parasols,id,statut,id reservation)
	@Override
	public List<Parasol> recupererParasolsParDate(LocalDateTime date) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * permet de recuperer les parasols a une date selon un statut
	 * @param LocalDateTime
	 * @param Statut
	 * @return list:Parasol
	 */
	@Override
	public List<Parasol> recupererParasolsParDateAndStatut(LocalDateTime date, Statut statut) {
		List<Parasol>parasolsAAfficher=new ArrayList();
		List<File> files=fileDao.findAll();
		//pas besoin de recuperer toutes les files
		for (File file : files) {
			for (Parasol parasol : file.getParasols()) {
				for (Location location : parasol.getLocations()) {
					if(location.getDateHeureDebut().isAfter(date) 
							&& location.getDateHeureFin().isBefore(date)
							//verifier si statut=statut
							&& location.getStatut().getNom().equals(statut.getNom())) {
						
						parasolsAAfficher.add(parasol);
					}
				}
			}
		}
		return parasolsAAfficher;
	}

	/**
	 * permet d'ajouter une liste de parasol a une location
	 * @param List:Parasol
	 * @param Location
	 * 
	 */
	@Override
	public List<Parasol> ajouterParasolsALocation(List<Parasol> parasolsAAjouter,Location location) {
		if(parasolsAAjouter.isEmpty()) {
			return null;
		}else {
			for (Parasol parasol : parasolsAAjouter) {
				location.getParasols().add(parasol);
				
			}
			location.setParasols(parasolsAAjouter);
		}
		
		return location.getParasols();
	}

	
	@Override
	public Parasol creerParasol(byte numeroEmplacement, File file) {
	
		Parasol parasol=new Parasol();
		parasol.setNumEmplacement(numeroEmplacement);
		parasol.setFile(file);
		file.getParasols().add(parasol);
		
		return parasolDao.save(parasol);
	}

	@Override
	public List<Parasol> recupererParasolsParStatut(Statut statut) {
		
		return null;
	}

}
