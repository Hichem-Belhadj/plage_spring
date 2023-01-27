package fr.orsys.plage.service.serviceImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

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
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class LocationServiceImpl implements LocationService {

	private LocationDao locationDao;
	//possibilité d'affilier le statut 'a traiter' dans le constructeur
	
	// TODO trouver les location d'une file
	
	
	private StatutDao statutDao;
	private LocataireDao locataireDao;
	private ParasolDao parasolDao;
	private FileDao fileDao;
	
	@Override
	public List<Location> recupererLocations() {
		
		return locationDao.findAll();
	}

	

	@Override
	public Location modifierLocation(Long id, Location location) {
		Location locationAModifier=locationDao.findById(id).orElseThrow(
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
		if(statutDao.findByNom(statut.getNom())==null) {
			throw new NotExistingStatutException("Ce statut n'existe pas!");
			
		}
		return locationDao.findByStatut(statut.getNom());
	}

	@Override
	public List<Location> recupererLocations(Locataire locataire) {
		if(!locataireDao.existsByEmail(locataire.getEmail())) {
			throw new NotExistingLocataireException("Ce locataire n'a pas de location!");
		}
		return locataire.getLocations();
	}

	@Override
	public List<Location> recupererLocation(Long idParasol) {
		Parasol parasol=parasolDao.findById(idParasol).get();
		if(parasol==null) {
			throw new NotExistingParasolException("Ce parasol n'existe pas!");
		}
		return parasol.getLocations();
	}

	@Override
	public Location recuperererLocationById(Long id) {
		Location location=locationDao.findById(id).orElseThrow(
				()->new NotExistingLocationException("Cette location n'existe pas!"));
		return location;
	}

	@Override
	public boolean supprimerLocation(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public double recupererMontantARegler(Long id) {
		
				Location location=locationDao.findById(id).orElseThrow(
						()->new NotExistingLocationException("Cette location n'existe pas!"));
				
		return location.getMontantARegler();
	}

	//TODO modifier location et verifier statut a traiter (si pas traiter envoyer exception) concatener etat dans exception
	@Override
	public Location modifierStatutLocation(Long id, Statut nouveauStatut) {
		Location location=locationDao.findById(id).orElseThrow(
				()->new NotExistingLocationException("Cette location n'existe pas!"));
		
		if(location.getStatut().getNom()=="à traiter") {
			location.setStatut(nouveauStatut);
		}else {
			throw new NotAllowedLocationException("impossible de passer au statut "+nouveauStatut.getNom());
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
		
		
		if(dateHeureDebut.isBefore(dateHeureFin) ) {
			if(dateHeureDebut.isAfter(dateMin) && dateHeureDebut.isBefore(dateMax) && dateHeureFin.isAfter(dateMin) && dateHeureFin.isBefore(dateMax)) {
				location.setDateHeureDebut(dateHeureDebut);
				location.setDateHeureDebut(dateHeureDebut);
			}
		}else {
			throw new InvalidDateException("les dates saisies sont invalides");
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
				()->new NotExistingFileException("File introuvable!"));
			List<Location> LocationList=new ArrayList<>();
			for (Parasol parasol : file.getParasols()) {
				for (Location location : parasol.getLocations()) {
					if(LocationList.indexOf(location)<=0) {
						LocationList.add(location);
					}
				}
				
			}
		return LocationList;
			
	}



	@Override
	public List<Location> recupererLocationsParJour(LocalDateTime jour) {
		
		return null;
	}

}
