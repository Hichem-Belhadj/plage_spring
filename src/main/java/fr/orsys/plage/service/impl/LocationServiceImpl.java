package fr.orsys.plage.service.impl;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.orsys.plage.business.Concessionnaire;
import fr.orsys.plage.business.File;
import fr.orsys.plage.business.Locataire;
import fr.orsys.plage.business.Location;
import fr.orsys.plage.business.Parasol;
import fr.orsys.plage.business.Statut;
import fr.orsys.plage.dao.FileDao;
import fr.orsys.plage.dao.LocataireDao;
import fr.orsys.plage.dao.LocationDao;
import fr.orsys.plage.dao.ParasolDao;
import fr.orsys.plage.dao.StatutDao;
import fr.orsys.plage.exception.InvalidDateException;
import fr.orsys.plage.exception.NotAllowedLocationException;
import fr.orsys.plage.exception.NotExistingFileException;
import fr.orsys.plage.exception.NotExistingLocataireException;
import fr.orsys.plage.exception.NotExistingLocationException;
import fr.orsys.plage.exception.NotExistingStatutException;
import fr.orsys.plage.exception.ParasolNotFoundException;
import fr.orsys.plage.service.LocationService;
import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class LocationServiceImpl implements LocationService {

	private LocationDao locationDao;
	//possibilité d'affilier le statut 'a traiter' dans le constructeur
	
	// TODO trouver les location d'une file

	private final StatutDao statutDao;
	private final LocataireDao locataireDao;
	private final ParasolDao parasolDao;
	private final FileDao fileDao;
	
	@Override
	public List<Location> recupererLocations() {
		return locationDao.findAll();
	}

	@Override
	public Location modifierLocation(Long id, Location location) {
		Location locationAModifier = locationDao.findById(id).orElseThrow(
			()->new NotExistingLocationException("location inexistante"));
		locationAModifier.setDateHeureDebut(location.getDateHeureDebut());
		locationAModifier.setDateHeureFin(location.getDateHeureFin());
		locationAModifier.setMontantARegler(location.getMontantARegler());
		locationAModifier.setRemarque(location.getRemarque());
		return locationAModifier;
	}

	@Override
	public List<Location> recupererLocations(LocalDateTime dateDebut,LocalDateTime dateFin) {
		return locationDao.findByIdBetween(dateDebut,dateFin);
	}

	@Override
	public List<Location> recupererLocations(Statut statut) {
		if (statutDao.findByNom(statut.getNom())==null) {
			throw new NotExistingStatutException("Ce statut n'existe pas !");
		}
		return locationDao.findByStatut(statut.getNom());
	}

	@Override
	public List<Location> recupererLocations(Locataire locataire) {
		if(!locataireDao.existsByEmail(locataire.getEmail())) {
			throw new NotExistingLocataireException("Ce locataire n'a pas de location !");
		}
		return locataire.getLocations();
	}

	@Override
	public List<Location> recupererLocation(Long idParasol) {
		Parasol parasol = parasolDao.findById(idParasol).orElseThrow(
				()->new ParasolNotFoundException("Ce parasol n'exite pas !"));
		return parasol.getLocations();
	}

	@Override
	public Location recuperererLocationById(Long id) {
		return locationDao.findById(id).orElseThrow(
				()->new NotExistingLocationException("Cette location n'existe pas !"));
	}

	@Override
	public boolean supprimerLocation(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public double recupererMontantARegler(Long id) {
		Location location=locationDao.findById(id).orElseThrow(
			()->new NotExistingLocationException("Cette location n'existe pas !"));
		return location.getMontantARegler();
	}

	//TODO modifier location et verifier statut a traiter (si pas traiter envoyer exception) concatener etat dans exception
	@Override
	public Location modifierStatutLocation(Long id, Statut nouveauStatut) {
		Location location=locationDao.findById(id).orElseThrow(
				()->new NotExistingLocationException("Cette location n'existe pas !"));
		
		if(location.getStatut().getNom().equals("à traiter")) {
			location.setStatut(nouveauStatut);
		}else {
			throw new NotAllowedLocationException( String.format("impossible de passer au statut %s", nouveauStatut.getNom()));
		}
		return location;
	}

	@Override
	public Location ajouterLocation(LocalDateTime dateHeureDebut, LocalDateTime dateHeureFin, double montantARegler,
			String remarques, List<Parasol> parasols, Concessionnaire concessionnaire, Locataire locataire) {
		Location location=new Location();
		Statut statut=statutDao.findByNom("à traiter");
		location.setStatut(statut);
		
		// limiter les dates de location du 1er juin au 15 sept
		LocalDateTime dateMin=LocalDateTime.of(dateHeureDebut.getYear(),Month.JUNE,1,0,0);
		LocalDateTime dateMax=LocalDateTime.of(dateHeureDebut.getYear(),Month.SEPTEMBER,16,0,0);
		
		
		if (dateHeureDebut.isBefore(dateHeureFin) ) {
			if(dateHeureDebut.isAfter(dateMin) && dateHeureDebut.isBefore(dateMax)
					&& dateHeureFin.isAfter(dateMin)
					&& dateHeureFin.isBefore(dateMax)) {
				location.setDateHeureDebut(dateHeureDebut);
				location.setDateHeureDebut(dateHeureDebut);
			}
		} else {
			throw new InvalidDateException("les dates saisies sont invalides !");
		}
		
		//TODO voir si possibilité de calculer le montant a regler en fonction (trouver le nbre de parasol par file,multiplier le prix de la file (nbre de parasol) et on ajoute le prix de chaque file et sur le prix global on applique la reduction) 
		//des different parametres
		location.setMontantARegler(montantARegler);
		location.setRemarque(remarques);
		location.setParasols(parasols);
		location.setConcessionnaire(concessionnaire);
		location.setLocataire(locataire);
		
		return locationDao.save(location);
	}

	@Override
	public List<Location> recupererLocationParFile(Long idFile) {
		File file=fileDao.findById(idFile).orElseThrow(
				()->new NotExistingFileException("File introuvable !"));
		
		List<Location> locationList = new ArrayList<>();
		for (Parasol parasol : file.getParasols()) {
			for (Location location : parasol.getLocations()) {
				if (locationList.indexOf(location) <= 0) {
					locationList.add(location);
				}
			}
		}
		return locationList;
	}

	@Override
	public List<Location> recupererLocationsParJour(LocalDateTime jour) {
		return null;
	}

	@Override
	public Location ajouterLocation(Location location) {
		return locationDao.save(location);
	}

	@Override
	public ResponseEntity<Map<String, Object>> recupererLocationPagination(int page, int taille, String filtrerPar,
			String trierPar) {
		try {	    	
	    	Pageable paging = filtrerPar.equals("desc") ?
	    			PageRequest.of(page, taille, Sort.by(trierPar).descending()):
	    			PageRequest.of(page, taille, Sort.by(trierPar).ascending());
	    	Page<Location> pageLocation = locationDao.findAll(paging);
	    	
	    	List<Location> locations = pageLocation.getContent();
	    	
	    	Map<String, Object> response = new HashMap<>();
	    	
	    	response.put("locations", locations);
	    	response.put("pageCourante", pageLocation.getNumber());
	    	response.put("totalElements", pageLocation.getTotalElements());
	    	response.put("totalPages", pageLocation.getTotalPages());
	    	
			return new ResponseEntity<>(response, HttpStatus.OK);
	      
	    } catch (Exception e) {
	      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}

	@Override
	public List<Location> recupererLocationsParlocataireEtStatut(Locataire locataire, String statut) {
		return locationDao.findByLocataireAndStatutName(locataire, statut);
	}

}
