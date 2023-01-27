package fr.orsys.plage.initialisation;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
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
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;

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
	private static FakeValuesService fakeValuesService = new FakeValuesService(new Locale("fr-FR"),
			new RandomService());
	private static Faker faker = new Faker(new Locale("fr-FR"));
	private static final String DATE_FORMATTER= "dd/MM/yyyy HH:mm:ss";
	 private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMATTER);

	@Override
	public void run(String... args) throws Exception {
		Date dateHeureDebut = new Date();
		ajouterFiles();
		ajouterParasols();
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

	private void ajouterLocation() {
		if (locationDao.count() == 0) {

			List<Utilisateur> utilisateurs = utilisateurDao.findAll();
			List<Statut> statuts = statutDao.findAll();
			List<Parasol> parasols = parasolDao.findAll();
			List<Locataire> locataires = locataireDao.findAll();
			List<Concessionnaire> concessionnaires = concessionnaireDao.findAll();

			for (int index = 0; index < 20; index++) {

				Location location = new Location();

//			creation des dates 

				Calendar calendar1 = new GregorianCalendar(2023, Calendar.JUNE, 01);
				Date dateDebut = calendar1.getTime();
//			System.out.println(dateDebut);

				Calendar calendar2 = new GregorianCalendar(2023, Calendar.SEPTEMBER, 15);
				Date dateFin = calendar2.getTime();

//			System.out.println(calendar2.getTime());

				Date dateAleatoire1 = faker.date().between(dateDebut, dateFin);
				Date dateAleatoire2 = faker.date().between(dateDebut, dateFin);

				do {
					dateAleatoire2 = faker.date().between(dateDebut, dateFin);
//					System.out.println("date1=" + dateAleatoire1 + "/// date2=" + dateAleatoire2);
//					System.out.println(dateAleatoire2.before(dateAleatoire1));
				} while (dateAleatoire2.before(dateAleatoire1));

//				String pattern = "dd/MM/yyyy";
//				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
//			System.out.println(simpleDateFormat.format(dateAleatoire1));
//			System.out.println(simpleDateFormat.format(dateAleatoire2));
				
				
				//conversion en LocalDateTime
				LocalDateTime dateHeureDebut = LocalDateTime.ofInstant(dateAleatoire1.toInstant(),
						ZoneId.systemDefault());
				LocalDateTime dateHeureFin = LocalDateTime.ofInstant(dateAleatoire2.toInstant(),
						ZoneId.systemDefault());
				
				//conversion en string pour formater (dd/MM/yyyy)
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMATTER);
				String dateHeureDebutFormate = dateHeureDebut.format(formatter);
		        String dateHeureFinFormate = dateHeureFin.format(formatter);
		        
		        //reconversion en LocalDateTime avec format(dd/MM/yyyy)
		        LocalDateTime dateHeureDebutLocalDateTime=LocalDateTime.parse(dateHeureDebutFormate,FORMATTER);
		        LocalDateTime dateHeureFinLocalDateTime=LocalDateTime.parse(dateHeureFinFormate,FORMATTER);
		        System.out.println("date string===="+dateHeureDebutFormate);
		        System.out.println(dateHeureDebutFormate.getClass().getSimpleName());
		        System.out.println("date datetime===="+LocalDateTime.parse(dateHeureDebutFormate,FORMATTER));
		        System.out.println(LocalDateTime.parse(dateHeureFinFormate,FORMATTER).getClass().getSimpleName());
				

				// ajout 4 parasols
				List<Parasol> listParasols = new ArrayList<>();
				for (long i = 1; i < 4; i++) {
					Parasol parasol = parasolDao.findById(i).get();
					listParasols.add(parasol);
				}

				// initialisation location

				location.setDateHeureDebut(dateHeureDebutLocalDateTime);
				location.setDateHeureFin(dateHeureFinLocalDateTime);
				location.setMontantARegler(Math.round(random.nextDouble(30))+1);
				location.setRemarque(faker.lorem().paragraph());
				location.setStatut(statuts.get(random.nextInt(statuts.size())));
				location.setLocataire(locataires.get(random.nextInt(locataires.size())));
				location.setConcessionnaire(concessionnaires.get(random.nextInt(concessionnaires.size())));
				location.setParasols(listParasols);

				System.out.println("remarque========" + location.getRemarque());

				// sauvegarde en base
				locationDao.save(location);

			}
		}
	}

	private void ajouterLocataire() {
		if (locataireDao.count() == 0) {
			List<Pays> pays = paysDao.findAll();
			List<LienDeParente> liendeParenteList = lienDeParenteDao.findAll();
			for (int i = 0; i < 10; i++) {
				Locataire locataire = new Locataire();
				locataire.setNom(faker.name().lastName());
				locataire.setPrenom(faker.name().firstName());
				locataire.setEmail(faker.internet().emailAddress());
				locataire.setMotDePasse(faker.internet().password(8, 16));
				locataire.setPays(pays.get(random.nextInt(pays.size())));
				locataire.setLienDeParente(liendeParenteList.get(random.nextInt(liendeParenteList.size())));
				//LocalDateTime.ofInstant(dateAleatoire1.toInstant(),ZoneId.systemDefault());
				locataire.setDateHeureInscription(LocalDateTime.ofInstant(faker.date().past(100, TimeUnit.DAYS).toInstant(), ZoneId.systemDefault()));
				locataireDao.save(locataire);
			}
		}

	}

	private void ajouterConcessionnaire() {
		if (concessionnaireDao.count() == 0) {
			utilisateurService.ajouterConcessionnaireDetail("3912345678", faker.name().lastName(),
					faker.name().firstName(), "peppe@orsys.fr", "12345678");
		}

	}

	private void ajouterStatutLocation() {
		if (statutDao.count() == 0) {
			statutService.ajouterStatut("à traiter");
			statutService.ajouterStatut("confirmée");
			statutService.ajouterStatut("refusée");
		}

	}

	private void ajouterPays() {
		if (paysDao.count() == 0) {
			for (int i = 0; i < 5; i++) {
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

	private void ajouterLiensDeParente() {
		// création du lien de parenté frère-soeur
		lienDeParenteService.ajouterLienDeParente("frere/soeur", (float) 0.05);
		// création du lien de parenté cousin-cousine
		lienDeParenteService.ajouterLienDeParente("cousin/cousine", (float) 0.025);
		// création du lien de parenté aucun
		lienDeParenteService.ajouterLienDeParente("aucun", 1);

	}

	private void ajouterParasols() {
		if (parasolDao.count() == 0) {
			Parasol parasol = new Parasol();
		}

	}

	// probleme avec le prix pour commencer
	private void ajouterFiles() {
		if (fileDao.count() == 0) {

			for (byte i = 1; i <= 8; i++) {
				List<Parasol> fileParasolList = new ArrayList<>();
				
				File file = new File(i, random.nextInt(50) + 1);
				fileDao.save(file);
				for (byte j = 1; j <= 9; j++) {
					Parasol parasol = new Parasol();

					parasol.setFile(file);

					byte[] arr = { j, i };

//					System.out.println(Arrays.toString(arr));
					parasol.setNumEmplacement(j);
					parasolDao.save(parasol);
					fileParasolList.add(parasol);

//					System.out.println(parasol);

				}

				file.setParasols(fileParasolList);

			}

		}
	
	}

}
