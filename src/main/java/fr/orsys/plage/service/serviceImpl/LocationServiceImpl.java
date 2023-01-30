package fr.orsys.plage.service.serviceImpl;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.loader.plan.exec.process.internal.AbstractRowReader;
import org.springframework.stereotype.Service;

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
import fr.orsys.plage.exception.NotExistingParasolException;
import fr.orsys.plage.exception.NotExistingStatutException;
import fr.orsys.plage.service.LocationService;
import fr.orsys.plage.service.ParasolService;
import lombok.AllArgsConstructor;
import net.bytebuddy.asm.Advice.Return;

@Service
@AllArgsConstructor
public class LocationServiceImpl implements LocationService {

	private LocationDao locationDao;
	private ParasolService parasolService;
	// possibilité d'affilier le statut 'a traiter' dans le constructeur

	// TODO trouver les location d'une file

	private StatutDao statutDao;
	private LocataireDao locataireDao;
	private ParasolDao parasolDao;
	private FileDao fileDao;

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
		Location locationAModifier = locationDao.findById(id)
				.orElseThrow(() -> new NotExistingLocationException("location inexistante"));
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
	public List<Location> recupererLocations(LocalDateTime dateDebut, LocalDateTime dateFin) {

		return locationDao.findByIdBetween(dateDebut, dateFin);

	}

	/**
	 * permet de recuperer toutes les locations d'un statut
	 * 
	 * @param Statut statut
	 * @return List:Location
	 */
	@Override
	public List<Location> recupererLocations(Statut statut) {

		if (statutDao.findByNom(statut.getNom()) == null) {
			throw new NotExistingStatutException("Ce statut n'existe pas!");
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
		if (!locataireDao.existsByEmail(locataire.getEmail())) {
			throw new NotExistingLocataireException("Ce locataire n'a pas de location!");
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
		Parasol parasol = parasolDao.findById(idParasol).get();
		if (parasol == null) {
			throw new NotExistingParasolException("Ce parasol n'existe pas!");
		}
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
		Location location = locationDao.findById(id)
				.orElseThrow(() -> new NotExistingLocationException("Cette location n'existe pas!"));
		return location;
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

		Location location = locationDao.findById(id)
				.orElseThrow(() -> new NotExistingLocationException("Cette location n'existe pas!"));

		return location.getMontantARegler();
	}

	// TODO modifier location et verifier statut a traiter (si pas traiter envoyer
	// exception) concatener etat dans exception

	/**
	 * permet de modifier le statut d'une location
	 * 
	 * @param Long   id
	 * @param Statut nouveauStatut
	 * @return Location
	 */
	@Override
	public Location modifierStatutLocation(Long id, Statut nouveauStatut) {
		Location location = locationDao.findById(id)
				.orElseThrow(() -> new NotExistingLocationException("Cette location n'existe pas!"));

		if (location.getStatut().getNom() == "à traiter") {
			location.setStatut(nouveauStatut);
		} else {
			throw new NotAllowedLocationException("impossible de passer au statut " + nouveauStatut.getNom());
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
		File file = fileDao.findById(idFile).orElseThrow(() -> new NotExistingFileException("File introuvable!"));
		List<Location> LocationList = new ArrayList<>();
		for (Parasol parasol : file.getParasols()) {
			for (Location location : parasol.getLocations()) {
				if (LocationList.indexOf(location) <= 0) {
					LocationList.add(location);
				}
			}

		}
		return LocationList;

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
}
