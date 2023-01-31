package fr.orsys.plage.service.impl;

import java.time.Duration;
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
import fr.orsys.plage.business.Utilisateur;
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
import fr.orsys.plage.exception.NotExistingUtilisateurException;
import fr.orsys.plage.exception.ParasolNotFoundException;
import fr.orsys.plage.exception.UtilisateurNonAuthorise;
import fr.orsys.plage.service.LocationService;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;

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
	
	

	/**
	 * permet de recuperer toutes les locations
	 * 
	 * @return List:Location
	 */
	@Override
	public List<Location> recupererLocations() {
		return locationDao.findAll();
	}

	/**
	 * permet de modifier une location
	 * 
	 * @param long     id
	 * @param Location location
	 * 
	 * @return Location
	 */
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


	/**
	 * permet de recuperer toutes les locations entre 2 dates
	 * 
	 * @param LocalDateTime dateDebut
	 * @param LocalDateTime dateFin
	 * @return List:Location
	 */
	@Override
	public List<Location> recupererLocations(LocalDateTime dateDebut,LocalDateTime dateFin) {
		return locationDao.findByIdBetween(dateDebut,dateFin);
	}

	/**
	 * permet de recuperer toutes les locations d'un statut
	 * 
	 * @param Statut statut
	 * @return List:Location
	 */
	@Override
	public List<Location> recupererLocations(Statut statut) {
		if (statutDao.findByNom(statut.getNom())==null) {
			throw new NotExistingStatutException("Ce statut n'existe pas !");
		}
		return locationDao.findByStatut(statut);
	}
	
	/**
	 * permet de recuperer toutes les locations d'un locataire
	 * 
	 * @param Locataire locataire
	 * @return List:Location
	 */

	@Override
	public List<Location> recupererLocations(Locataire locataire) {
		if(!locataireDao.existsByEmail(locataire.getEmail())) {
			throw new NotExistingLocataireException("Ce locataire n'a pas de location !");
		}
		return locataire.getLocations();
	}
	
	/**
	 * permet de recuperer toutes les locations d'un parasol
	 * 
	 * @param Long idParasol
	 * @return List:Location
	 */

	@Override
	public List<Location> recupererLocation(Long idParasol) {
		Parasol parasol = parasolDao.findById(idParasol).orElseThrow(
				()->new ParasolNotFoundException("Ce parasol n'exite pas !"));
		return parasol.getLocations();
	}

	
	/**
	 * permet de recuperer une location avec l'id
	 * 
	 * @param Long id
	 * @return Location
	 */
	@Override
	public Location recuperererLocationById(Long id) {
		return locationDao.findById(id).orElseThrow(
				()->new NotExistingLocationException("Cette location n'existe pas !"));
	}
	
	
	
	/**
	 * permet de supprimer une location avec l'id
	 * 
	 * @param Long id
	 * @return boolean
	 */

	@Override
	public boolean supprimerLocation(Long id) {
		// TODO Auto-generated method stub
		return false;
	}
	
	/**
	 * permet de recuperer le montant a payer d'une location avec l'id
	 * 
	 * @param Long id
	 * @return double
	 */

	@Override
	public double recupererMontantARegler(Long id) {
		Location location=locationDao.findById(id).orElseThrow(
			()->new NotExistingLocationException("Cette location n'existe pas !"));
		return location.getMontantARegler();
	}

	//TODO modifier location et verifier statut a traiter (si pas traiter envoyer exception) concatener etat dans exception
	
	/**
	 * permet de modifier le statut d'une location
	 * 
	 * @param Long   id
	 * @param Statut nouveauStatut
	 * @return Location
	 */
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
	/**
	 * permet d ajouter une location
	 * 
	 * @param LocalDateTime   dateDebut
	 * @param LocalDateTime   dateFin
	 * @param double          montantARegler
	 * @param String          remarques
	 * @param List:           parasols
	 * @param Concessionnaire concessionnaire
	 * @param Locataire       locataire
	 * 
	 * @return Location
	 */
	@Override
	public Location ajouterLocation(LocalDateTime dateHeureDebut, LocalDateTime dateHeureFin, String remarques,
			List<Parasol> parasols, Concessionnaire concessionnaire, Locataire locataire) {

		Location location = new Location();

		// limiter les dates de location du 1er juin au 15 sept

		if (verifierDate(dateHeureDebut, dateHeureFin)) {
			location.setDateHeureDebut(dateHeureDebut);
			location.setDateHeureDebut(dateHeureDebut);
		} else {
			throw new InvalidDateException("date non valid!");
		}
		Statut statut = statutDao.findByNom("à traiter");
		location.setStatut(statut);

		// TODO voir si possibilité de calculer le montant a regler en fonction (trouver
		// le nbre de parasol par file,multiplier le prix de la file (nbre de parasol)
		// et on ajoute le prix de chaque file et sur le prix global on applique la
		// reduction)
		// des different parametres
		double montantARegler = calculerMontantLocation(location, dateHeureDebut, dateHeureFin);

		location.setMontantARegler(montantARegler);
		location.setRemarque(remarques);

		location.setParasols(parasols);
		location.setConcessionnaire(concessionnaire);
		location.setLocataire(locataire);

		return locationDao.save(location);
	}

	
	
	/**
	 * permet de recuperer toutels les locations d'une file
	 * 
	 * @param Long idFile
	 * 
	 * @return Location:list
	 */
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

	/**
	 * permet de recuperer la liste de toutes les location du jour
	 * 
	 * @param LocalDateTimejour
	 * 
	 * @return Location:list
	 */
	@Override
	public List<Location> recupererLocationsParJour(LocalDateTime jour) {
		return null;
	}

	
	/**
	 * permet de verifie si la date de la location est comprise dans la periode
	 * d'activité et si la durée de la location est supérieure à une journée
	 * d'activité (8h) durant la même journée
	 * 
	 * @param dateHeureDebut
	 * @param dateHeureFin
	 * @return boolean
	 */
	public boolean verifierDate(LocalDateTime dateHeureDebut, LocalDateTime dateHeureFin) {

		LocalDateTime dateMin = LocalDateTime.of(dateHeureDebut.getYear(), Month.JUNE, 1, 0, 0);
		LocalDateTime dateMax = LocalDateTime.of(dateHeureDebut.getYear(), Month.SEPTEMBER, 16, 0, 0);

		if (dateHeureDebut.isAfter(dateHeureFin) || dateHeureDebut.isBefore(dateMin) || dateHeureDebut.isAfter(dateMax)
				|| dateHeureFin.isBefore(dateMin) || dateHeureFin.isAfter(dateMax)) {
			return false;
		}

		if (dateHeureDebut.getMonth() == dateHeureFin.getMonth()
				&& dateHeureDebut.getDayOfMonth() == dateHeureFin.getDayOfMonth()
				&& Duration.between(dateHeureDebut, dateHeureFin).toHours() < 8) {
			return false;
		}

		return true;
	}

	/**
	 * permet de calculer le montant a payer pour une location en prenant en compte
	 * la remise du lien de parenté
	 * 
	 * @param location
	 * @param dateDebut
	 * @param dateFin
	 * @return double
	 */
	public double calculerMontantLocation(Location location, LocalDateTime dateDebut, LocalDateTime dateFin) {

		File file = null;
		double montantTotalPrix = 0;
		double coef = 1;
		if (location.getId() > 0) {
			List<Parasol> parasols = location.getParasols();
			coef = location.getLocataire().getLienDeParente().getCoefficient();
			for (Parasol parasol : parasols) {
				file = parasol.getFile();
				montantTotalPrix += file.getPrixJournalier();
			}
		}
		return montantTotalPrix * coef;
	}

	
	
	@Override
	public Location ajouterLocation(Location location,Locataire locataire) {
		if (verifierDate(location.getDateHeureDebut(), location.getDateHeureFin())) {
			System.out.println("date verifiee---------------------------");
		} else {
			throw new InvalidDateException("date non valid!");
		}
		Statut statut = statutDao.findByNom("à traiter");
		location.setStatut(statut);
		
//		Locataire locataire=new Locataire();
//		locataire.setNom(location.getLocataire().getNom());
//		locataire.setPrenom(locataire.getPrenom());
//		locataire.setDateHeureInscription(location.getLocataire().getDateHeureInscription());
//		locataire.setPays(location.getLocataire().getPays());
//		locataire.getLocations().add(location);
//		locataireDao.save(locataire);
		location.setLocataire(locataire);

				System.out.println("statut modifié---------------------------");
		double montantARegler = calculerMontantLocation(location, location.getDateHeureDebut(),
				location.getDateHeureFin());
		System.out.println("montant calculé---------------------------");
		location.setMontantARegler(montantARegler);
		System.out.println("montant enregistré---------------------------");
		System.out.println("sauvegarde location---------------------------");
		return locationDao.save(location);

	}

	@Override
	public ResponseEntity<Map<String, Object>> recupererLocationPagination(int page, int taille, String filtrerPar,
			String trierPar, Utilisateur utilisateur) {
		
//		if ( utilisateur instanceof Locataire ) {
//			throw new UtilisateurNonAuthorise("Vous n'êtes pas authorisé à afficher ces donées !");
//		}
		try {	
			Pageable paging = trierPar.equals("desc") ?
	    			PageRequest.of(page, taille, Sort.by(filtrerPar).descending()):
	    			PageRequest.of(page, taille, Sort.by(filtrerPar).ascending());
	    	Page<Location> pageLocation = locationDao.findAll(paging);
	    	
	    	if (utilisateur instanceof Locataire) {
	    		pageLocation = locationDao.findLocationsByUtilisateurId(paging, utilisateur.getId());
			}
	    	
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

	@Override
	public Location ajouterLocation(LocalDateTime dateHeureDebut, LocalDateTime dateHeureFin, double montantARegler,
			String remarques, List<Parasol> parasols, Concessionnaire concessionnaire, Locataire locataire) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Location ajouterLocation(Location location) {
		
		return locationDao.save(location);
	}

}