package fr.orsys.plage.initialisation;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.github.javafaker.Faker;

import fr.orsys.plage.business.Concessionnaire;
import fr.orsys.plage.business.File;
import fr.orsys.plage.business.LienDeParente;
import fr.orsys.plage.business.Locataire;
import fr.orsys.plage.business.Location;
import fr.orsys.plage.business.Parasol;
import fr.orsys.plage.business.Pays;
import fr.orsys.plage.business.Statut;
import fr.orsys.plage.business.Utilisateur;
import fr.orsys.plage.dao.ConcessionnaireDao;
import fr.orsys.plage.dao.FileDao;
import fr.orsys.plage.dao.LienDeParenteDao;
import fr.orsys.plage.dao.LocataireDao;
import fr.orsys.plage.dao.LocationDao;
import fr.orsys.plage.dao.ParasolDao;
import fr.orsys.plage.dao.PaysDao;
import fr.orsys.plage.dao.StatutDao;
import fr.orsys.plage.dao.UtilisateurDao;
import fr.orsys.plage.service.LienDeParenteService;
import fr.orsys.plage.service.StatutService;
import fr.orsys.plage.service.UtilisateurService;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class AjoutDonneesInitiales implements CommandLineRunner {

	private final PaysDao paysDao;
	private final LienDeParenteDao lienDeParenteDao;
	private final LienDeParenteService lienDeParenteService;
	private final ConcessionnaireDao concessionnaireDao;
	private final StatutService statutService;
	private final StatutDao statutDao;
	private final LocataireDao locataireDao;
	private final FileDao fileDao;
	private final ParasolDao parasolDao;
	private final LocationDao locationDao;
	private final UtilisateurDao utilisateurDao;
	private final UtilisateurService utilisateurService;

	private static Random random = new Random();
//	private static FakeValuesService fakeValuesService = new FakeValuesService(new Locale("fr-FR"),
//			new RandomService());
	private static Faker faker = new Faker(new Locale("fr-FR"));
//	private static final String DATE_FORMATTER= "dd/MM/yyyy HH:mm:ss";
	

	@Override
	public void run(String... args) throws Exception {
		Date dateHeureDebut = new Date();
		ajouterFiles();

		ajouterLiensDeParente();
		ajouterPays();
		ajouterStatutLocation();
		ajouterConcessionnaire();
		ajouterLocataire();
		ajouterLocation();

		Date dateHeureFin = new Date();
		System.out.println("Données initiales ajoutées en "
				+ String.valueOf(dateHeureFin.getTime() - dateHeureDebut.getTime()) + " ms");

	}

	/**
	 * ajout fonction ajout location aléatoires
	 */
	private void ajouterLocation() {
		if (locationDao.count() == 0) {
			int NB_LOCATIONS=20;
			int NB_PARASOLS=4;
			
			List<Utilisateur> utilisateurs = utilisateurDao.findAll();
			List<Statut> statuts = statutDao.findAll();
			List<Parasol> parasols = parasolDao.findAll();
			List<Locataire> locataires = locataireDao.findAll();
			List<Concessionnaire> concessionnaires = concessionnaireDao.findAll();

			for (int index = 0; index < NB_LOCATIONS; index++) {

				Location location = new Location();

//			creation des dates 

				Calendar calendar1 = new GregorianCalendar(2023, Calendar.JUNE, 01);
				Date dateDebut = calendar1.getTime();
//			System.out.println(dateDebut);
				Calendar calendar2 = new GregorianCalendar(2023, Calendar.SEPTEMBER, 15);
				Date dateFin = calendar2.getTime();

				Date dateAleatoire1 = faker.date().between(dateDebut, dateFin);
				Date dateAleatoire2 = faker.date().between(dateDebut, dateFin);

				do {
					dateAleatoire2 = faker.date().between(dateDebut, dateFin);
//					System.out.println("date1=" + dateAleatoire1 + "/// date2=" + dateAleatoire2);
//					System.out.println(dateAleatoire2.before(dateAleatoire1));
				} while (dateAleatoire2.before(dateAleatoire1));

				
				//conversion en LocalDateTime
				LocalDateTime dateHeureDebut = LocalDateTime.ofInstant(dateAleatoire1.toInstant(),
						ZoneId.systemDefault());
				LocalDateTime dateHeureFin = LocalDateTime.ofInstant(dateAleatoire2.toInstant(),
						ZoneId.systemDefault());
				
				System.out.println("nbre d'heures de differences="+Duration.between(dateHeureDebut, dateHeureFin).toHours());
				//conversion en string pour formater (dd/MM/yyyy)
//				DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMATTER);
//				String dateHeureDebutFormate = dateHeureDebut.format(formatter);
//		        String dateHeureFinFormate = dateHeureFin.format(formatter);
		        
		      
			
				// ajout 4 parasols
				List<Parasol> listParasols = new ArrayList<>();
				for (long i = 1; i < NB_PARASOLS; i++) {
					
					Parasol parasol = parasolDao.findById(i).get();
					listParasols.add(parasol);
					
				}
				// initialisation location

				location.setDateHeureDebut(dateHeureDebut);
				location.setDateHeureFin(dateHeureFin);
				location.setMontantARegler(Math.round(random.nextDouble(30))+1);
				location.setRemarque(faker.lorem().paragraph());
				location.setStatut(statuts.get(random.nextInt(statuts.size())));
				location.setLocataire(locataires.get(random.nextInt(locataires.size())));
				location.setConcessionnaire(concessionnaires.get(random.nextInt(concessionnaires.size())));
				location.setParasols(listParasols);

				// sauvegarde en base
				locationDao.save(location);

			}
		}
	}

	/**
	 * fonction d'ajout de locataire
	 */
	
	private void ajouterLocataire() {
		
		if (locataireDao.count() == 0) {
			
			int NB_LOCATAIRES=10;
			List<Pays> pays = paysDao.findAll();
			List<LienDeParente> liendeParenteList = lienDeParenteDao.findAll();
			for (int i = 0; i < NB_LOCATAIRES; i++) {
				
				Locataire locataire = new Locataire();
				locataire.setNom(faker.name().lastName());
				locataire.setPrenom(faker.name().firstName());
				locataire.setEmail(faker.internet().emailAddress());
				locataire.setMotDePasse(faker.internet().password(8, 16));
				locataire.setPays(pays.get(random.nextInt(pays.size())));
				locataire.setLienDeParente(liendeParenteList.get(random.nextInt(liendeParenteList.size())));
				locataire.setDateHeureInscription(LocalDateTime.ofInstant(faker.date().past(100, TimeUnit.DAYS).toInstant(), ZoneId.systemDefault()));
				locataireDao.save(locataire);
			}
		}

	}

	/**
	 * fonction d'ajout d'un concessionnaire
	 */
	private void ajouterConcessionnaire() {
		
		if (concessionnaireDao.count() == 0) {
			
			utilisateurService.ajouterConcessionnaireDetail("3912345678", faker.name().lastName(),
					faker.name().firstName(), "peppe@orsys.fr", "12345678");
		}

	}

	/**
	 * fonction d'ajout des 3 statuts
	 */
	private void ajouterStatutLocation() {
		
		if (statutDao.count() == 0) {
			
			statutService.ajouterStatut("à traiter");
			statutService.ajouterStatut("confirmée");
			statutService.ajouterStatut("refusée");
		}

	}

	/**
	 * fonction d'ajout de 5 pays aléatoirement
	 */
	private void ajouterPays() {
		
		if (paysDao.count() == 0) {
			
			int NB_PAYS=5;
			for (int i = 0; i < NB_PAYS; i++) {
				
				Pays pays = new Pays();
				String code = faker.address().countryCode();
				Locale L = new Locale("", code);
				String country = L.getDisplayCountry();
				pays.setCode(code);
				pays.setNom(country);
				paysDao.save(pays);

			}
		}

	}

	// findbynom
	// mettre en parametre nom et coeff
	// recupererLiendeparente

	/**
	 * fonction d'ajout de 3 liens de parenté
	 */
	private void ajouterLiensDeParente() {
		
		lienDeParenteService.ajouterLienDeParente("frere/soeur", (float) 0.05);
		lienDeParenteService.ajouterLienDeParente("cousin/cousine", (float) 0.025);
		lienDeParenteService.ajouterLienDeParente("aucun", 1);

	}

	

	/**
	 * fonction d'ajout de  8 files aléatoires et 9 parasoles par file
	 */
	private void ajouterFiles() {
		
		if (fileDao.count() == 0) {
			
			double prixMax=100;
			double prixAleatoire=110;
			
			//boucle pour création des 8 files
			for (byte i = 1; i <= 8; i++) {
				
				List<Parasol> fileParasolList = new ArrayList<>();
				
				do {
					
					prixAleatoire=30+random.nextInt(100);	
					
				}while(prixAleatoire>prixMax);
				
				prixMax=prixAleatoire;
				File file = new File(i,prixAleatoire);
				fileDao.save(file);
				
				//boucle pour création de 9 parasols par files
				
				for (byte j = 1; j <= 9; j++) {
					
					Parasol parasol = new Parasol();
					parasol.setFile(file);
					parasol.setNumEmplacement(j);
					parasolDao.save(parasol);
					fileParasolList.add(parasol);

				}

				file.setParasols(fileParasolList);
				
			}

		}
	
	}

}
