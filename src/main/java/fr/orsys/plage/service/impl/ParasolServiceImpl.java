package fr.orsys.plage.service.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.orsys.plage.business.File;
import fr.orsys.plage.business.Location;
import fr.orsys.plage.business.Parasol;
import fr.orsys.plage.dao.LocationDao;
import fr.orsys.plage.dao.ParasolDao;
import fr.orsys.plage.exception.NotExistingLocationException;
import fr.orsys.plage.exception.ParasolNotFoundException;
import fr.orsys.plage.service.ParasolService;
import lombok.AllArgsConstructor;


@Service
@Transactional
@AllArgsConstructor
public class ParasolServiceImpl implements ParasolService{

	private final ParasolDao parasolDao;
	private final LocationDao locationDao;
	
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
				()->new NotExistingLocationException("La location recherchée n'existe pas !"));
		
		return location.getParasols();
	}

	@Override
	public Parasol recupererParasol(Long id) {
		return parasolDao.findById(id).orElseThrow(
			()->new ParasolNotFoundException("Ce parasol n'exite pas !"));
	}

	//parametre file et numero d'emplacement a mettre + travailler avec setter
	@Override
	public Parasol ajouterParasol(byte numeroEmplacement, File file) {
		Parasol parasol = new Parasol();
		parasol.setFile(file);
		parasol.setNumEmplacement(numeroEmplacement);
		return parasolDao.save(parasol);
	}

	@Override
	public Parasol supprimerParasol(Long id) {
		Parasol parasol=parasolDao.findById(id).orElseThrow(
				()->new ParasolNotFoundException("ce parasol n'existe pas !"));
		return parasolDao.save(parasol);
	}

	@Override
	public Parasol ajouterParasol(Parasol parasol) {
		return parasolDao.save(parasol);
	}

	@Override
	public List<List<Integer>> recupererParasolParJourAvecEtatConfirme(String dateRecherche) {
		String strDate = dateRecherche; //"2023-06-23 12:30"
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		LocalDateTime dateHeure = LocalDateTime.parse(strDate, formatter);
		return parasolDao.findByDateAndSatatutConfirme(dateHeure);
	}

	/**
	 * permet de calculer le prix de la location 
	 * @param Location location
	 * @param LocalDateTime dateDebut
	 * @param LocalDateTime dateFin
	 */
	@Override
	public double calculPrixParasol(Location locationModifiee, LocalDateTime dateHeureDebut,
			LocalDateTime dateHeureFin) {
		long nbreJoursLocation=ChronoUnit.DAYS.between(dateHeureDebut, dateHeureFin);
		float coef=locationModifiee.getLocataire().getLienDeParente().getCoefficient();
		
		List<Parasol>parasols=locationModifiee.getParasols();
		double montantJournalierTotalLocation=0;
		double montantTotalLocation=0;
		for (Parasol parasol : parasols) {
			montantJournalierTotalLocation+=parasol.getFile().getPrixJournalier();
		}
		montantTotalLocation=montantJournalierTotalLocation*nbreJoursLocation;
	
		return  Math.round(montantTotalLocation)-(Math.round(montantTotalLocation)*coef);
	}

	@Override
	public List<List<Integer>> recupererParasolDisponibleSurDuree(String dateDebut, String dateFin) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
		LocalDateTime dateHeureDebut = LocalDateTime.parse(dateDebut, formatter);
		LocalDateTime dateHeureFin = LocalDateTime.parse(dateFin, formatter);
		return parasolDao.findByDateAndSatatutDisponible(dateHeureDebut, dateHeureFin);
	}

}
