package fr.orsys.plage.service.impl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
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
import fr.orsys.plage.service.LocationService;
import fr.orsys.plage.service.ParasolService;
import fr.orsys.plage.util.GestionDate;
import lombok.AllArgsConstructor;


@Service
@Transactional
@AllArgsConstructor
public class ParasolServiceImpl implements ParasolService{

	private final ParasolDao parasolDao;
	private final LocationDao locationDao;
	private final LocationService locationService;
	
	
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
	public List<Parasol> ajouterParasolALocation(List<Parasol> parasols, Location location) {
		LocalDateTime dateDebut=location.getDateHeureDebut();
		LocalDateTime dateFin=location.getDateHeureFin();
		List<Parasol>parasolLocationPossible=new ArrayList<>();
		List<Parasol>parasolLocationImPossible=new ArrayList<>();
		
		System.out.println("debuuuuuuuut"+dateDebut);
		System.out.println("ffffffffffin"+dateFin);
		for (Parasol parasol : parasols) {
				System.out.println("parasollll="+parasol);
			for (Location locationParasol : parasol.getLocations()) {
				boolean parasolEstLoue=verifierSiParasolEstLouePlage(parasol, dateDebut, dateFin, locationParasol.getDateHeureDebut(), locationParasol.getDateHeureFin());
				if(parasolEstLoue) {
					parasolLocationImPossible.add(parasol);
					
				}else {
					parasolLocationPossible.add(parasol);
					
				}
				}
			}
			System.out.println(parasolLocationImPossible);
			System.out.println(parasolLocationPossible);
		return parasolLocationPossible;
	}



	@Override
	public boolean verifierSiParasolEstLouePlage(Parasol parasol,LocalDateTime dateDebutLocation,LocalDateTime dateFinLocation, LocalDateTime dateDebutPlage, LocalDateTime dateFinPlage) {
		List<Location>locations=parasol.getLocations();
		
		if(locations.isEmpty()) {
			return false;
		}else {
			for (Location location : locations) {
				GestionDate gestionDate=new GestionDate();
				if(!gestionDate.verifierSiDateComprise(dateDebutPlage, dateFinLocation, dateDebutLocation)
						&& !gestionDate.verifierSiDateComprise(dateDebutPlage, dateFinPlage, dateFinLocation)
						&& (dateDebutLocation.isAfter(dateFinPlage)|| dateFinLocation.isBefore(dateDebutPlage)))
						{
					return false;
				}
					return true;
				}
			}
			
		
		return false;
	}

	/**
	 * permet de calculer le prix de la location 
	 * @param Location location
	 * @param LocalDateTime dateDebut
	 * @param LocalDateTime dateFin
	 */
	@Override
	public double calculPrixParasol(Location location, LocalDateTime dateDebut, LocalDateTime dateFin) {
		
		long nbreJoursLocation=ChronoUnit.DAYS.between(dateDebut, dateFin);
		float coef=location.getLocataire().getLienDeParente().getCoefficient();
		
		List<Parasol>parasols=location.getParasols();
		double montantJournalierTotalLocation=0;
		double montantTotalLocation=0;
		for (Parasol parasol : parasols) {
			montantJournalierTotalLocation+=parasol.getFile().getPrixJournalier();
		}
		montantTotalLocation=montantJournalierTotalLocation*nbreJoursLocation;
	
		return  Math.round(montantTotalLocation)-(Math.round(montantTotalLocation)*coef);
	}

}
