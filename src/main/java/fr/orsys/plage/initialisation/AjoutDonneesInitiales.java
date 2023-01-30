package fr.orsys.plage.initialisation;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

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
import fr.orsys.plage.business.Role;
import fr.orsys.plage.business.Statut;
import fr.orsys.plage.business.Utilisateur;
import fr.orsys.plage.dao.ConcessionnaireDao;
import fr.orsys.plage.dao.FileDao;
import fr.orsys.plage.dao.LocataireDao;
import fr.orsys.plage.dao.LocationDao;
import fr.orsys.plage.dao.PaysDao;
import fr.orsys.plage.dao.StatutDao;
import fr.orsys.plage.enums.Roles;
import fr.orsys.plage.service.ConcessionnaireService;
import fr.orsys.plage.service.FileService;
import fr.orsys.plage.service.LienDeParenteService;
import fr.orsys.plage.service.LocataireService;
import fr.orsys.plage.service.LocationService;
import fr.orsys.plage.service.ParasolService;
import fr.orsys.plage.service.PaysService;
import fr.orsys.plage.service.RoleService;
import fr.orsys.plage.service.StatutService;
import fr.orsys.plage.service.UtilisateurService;
import fr.orsys.plage.util.GestionDate;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Component
@AllArgsConstructor
@Log4j2
public class AjoutDonneesInitiales implements CommandLineRunner {

	private final PaysDao paysDao;
	private final LienDeParenteService lienDeParenteService;
	private final ConcessionnaireDao concessionnaireDao;
	private final StatutService statutService;
	private final StatutDao statutDao;
	private final LocataireDao locataireDao;
	private final FileDao fileDao;
	private final LocationDao locationDao;
	private final UtilisateurService utilisateurService;
	private final GestionDate gestionDate;
	private final ParasolService parasolService;
	private final LocataireService locataireService;
	private final ConcessionnaireService concessionnaireService;
	private final LocationService locationService;
	private final PaysService paysService;
	private final FileService fileService;
	private final RoleService roleService;

	private static Random random = new Random();
	private static Faker faker = new Faker(new Locale("fr-FR"));
	
	private static final String DATE_FORMAT = "dd-MM-yyy";

	@Override
	public void run(String... args) throws Exception {
		Date dateHeureDebut = new Date();
//		ajouterFiles();
//		ajouterLiensDeParente();
//		ajouterPays();
//		ajouterStatutLocation();
//		ajouterConcessionnaire();
//		ajouterLocataire();
//		ajouterLocation();
//		ajoutUtilisateurs();

		Date dateHeureFin = new Date();
		log.info("Données initiales ajoutées en {} ms",
				String.valueOf(dateHeureFin.getTime() - dateHeureDebut.getTime()));
	}

	private void ajouterLocation() throws ParseException {
		if (locationDao.count() == 0) {

			List<Statut> statuts = statutService.recupererStatut();
			List<Locataire> locataires = locataireService.recupererLocataires();
			List<Concessionnaire> concessionnaires = concessionnaireService.recupererConcessionnaires();
			
			log.info("Ajout de 20 locations en base");
			for (int index = 0; index < 20; index++) {
				Location location = new Location();

				Date limiteDateInf = gestionDate.dateDepuisFormat(DATE_FORMAT, "01-06-2023");
				Date limiteDateSup = gestionDate.dateDepuisFormat(DATE_FORMAT, "15-09-2023");
				
				Date dateDebut = gestionDate.dateAleatoireEntre(limiteDateInf, limiteDateSup);
				Date dateFin = gestionDate.dateAleatoireEntre(dateDebut, limiteDateSup);

				LocalDateTime dateHeureDebut = gestionDate.convertirVersLocalDateTime(dateDebut);
				LocalDateTime dateHeureFin = gestionDate.convertirVersLocalDateTime(dateFin);
				
				// ajout 4 parasols
				List<Parasol> parasols = new ArrayList<>();
				for (long i = 1; i < 4; i++) {
					Parasol parasol = parasolService.recupererParasol(i);
					if(parasol != null) {
						parasols.add(parasol);
					}
				}

				// initialisation location
				location.setDateHeureDebut(dateHeureDebut);
				location.setDateHeureFin(dateHeureFin);
				location.setMontantARegler(30);
				location.setRemarque(faker.lorem().paragraph());
				location.setStatut(statuts.get(random.nextInt(statuts.size())));
				location.setLocataire(locataires.get(random.nextInt(locataires.size())));
				location.setConcessionnaire(concessionnaires.get(random.nextInt(concessionnaires.size())));
				location.setParasols(parasols);

				// sauvegarde en base
				locationService.ajouterLocation(location);
			}
		}
	}

	private void ajouterLocataire() throws ParseException {
		if (locataireDao.count() == 0) {
			
			List<Pays> pays = paysService.recupererPays();
			List<LienDeParente> liendeParenteList = lienDeParenteService.recupererLienDeParente();
			
			log.info("Ajout de 10 locataires en base");
			for (int i = 0; i < 10; i++) {
				Role roleLocataire = new Role();
				roleLocataire.setName(Roles.ROLE_USER);
				roleLocataire = roleService.ajouterRole(roleLocataire);
				
				Date limiteDateInf = gestionDate.dateDepuisFormat(DATE_FORMAT, "01-01-2023");
				Date limiteDateSup = gestionDate.dateDepuisFormat(DATE_FORMAT, "15-09-2023");
				Date dateInscription = gestionDate.dateAleatoireEntre(limiteDateInf, limiteDateSup);
				LocalDateTime dateHeureInscription = gestionDate.convertirVersLocalDateTime(dateInscription);

				Utilisateur locataire = new Locataire();
				locataire.setNom(faker.name().lastName());
				locataire.setPrenom(faker.name().firstName());
				locataire.setEmail(faker.internet().emailAddress());
				locataire.setMotDePasse(faker.internet().password(8, 16));
				((Locataire)locataire).setLienDeParente(liendeParenteList.get(random.nextInt(liendeParenteList.size())));
				((Locataire)locataire).setDateHeureInscription(dateHeureInscription);
				((Locataire)locataire).setPays(pays.get(random.nextInt(pays.size())));
				log.info("*****************************{}*****{}", locataire.getEmail(), locataire.getMotDePasse());
				locataire = utilisateurService.ajouterUtilisateur(locataire);
				utilisateurService.ajouterRoleAUtilisateur(locataire.getId(), roleLocataire.getId());
			}
		}
	}

	private void ajouterConcessionnaire() {
		log.info("Ajout d'un concessionnaire en base");
		if (concessionnaireDao.count() == 0) {
			Role roleConcessionnaire = new Role();
			roleConcessionnaire.setName(Roles.ROLE_ADMIN);
			roleConcessionnaire = roleService.ajouterRole(roleConcessionnaire);
			
			Utilisateur concessionnaire = new Concessionnaire();
			concessionnaire.setNom(faker.name().lastName());
			concessionnaire.setPrenom(faker.name().firstName());
			concessionnaire.setEmail("peppe@orsys.fr");
			concessionnaire.setMotDePasse("12345678");
			((Concessionnaire)concessionnaire).setNumeroDeTelephone("3912345678");
			concessionnaire = utilisateurService.ajouterUtilisateur(concessionnaire);
			utilisateurService.ajouterRoleAUtilisateur(concessionnaire.getId(), roleConcessionnaire.getId());
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
				Locale locale = new Locale("", code);
				String country = locale.getDisplayCountry();
				pays.setCode(code);
				pays.setNom(country);
				paysService.ajouterPays(pays);
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

	// probleme avec le prix pour commencer
	private void ajouterFiles() {
		if (fileDao.count() == 0) {
			Double prixJournalier = 60d;

			for (byte i = 1; i <= 8; i++) {
				List<Parasol> fileParasolList = new ArrayList<>();
				prixJournalier = (prixJournalier-5) > 0 ? (prixJournalier-5) : prixJournalier;
				
				File file = new File(i, prixJournalier);
				fileService.ajouterFile(file);
				for (byte j = 1; j <= 9; j++) {
					Parasol parasol = new Parasol();

					parasol.setFile(file);
					parasol.setNumEmplacement(j);
					parasol = parasolService.ajouterParasol(parasol);
					fileParasolList.add(parasol);
				}

				file.setParasols(fileParasolList);
			}

		}
	
	}
	
	public void ajoutUtilisateurs() {
		List<Pays> pays = paysService.recupererPays();
		List<LienDeParente> liendeParenteList = lienDeParenteService.recupererLienDeParente();
		Utilisateur concessionnaire = new Concessionnaire();
		Utilisateur concessionnaire1 = new Concessionnaire();
		Utilisateur locataire = new Locataire();
		Role roleConcessionnaire = new Role();
		Role roleLocataire = new Role();

//		concessionnaire.setNom("BEL-HADJ");
//		concessionnaire.setPrenom("Hichem");
//		concessionnaire.setEmail("hichem.belhadj7@gmail.com");
//		concessionnaire.setMotDePasse("1234");
//		
//		concessionnaire.setNom("doe");
//		concessionnaire.setPrenom("john");
//		concessionnaire.setEmail("j.doe@gmail.com");
//		concessionnaire.setMotDePasse("12345678");

//		locataire.setNom("Idoubrahim");
//		locataire.setPrenom("Chafek");
//		locataire.setEmail("cafir@hotmail.fr");
//		locataire.setMotDePasse("12345678");
//		((Locataire)locataire).setPays(pays.get(random.nextInt(pays.size())));
//		((Locataire)locataire).setLienDeParente(liendeParenteList.get(random.nextInt(liendeParenteList.size())));
		
		roleConcessionnaire.setName(Roles.ROLE_ADMIN);
		roleLocataire.setName(Roles.ROLE_USER);

		roleConcessionnaire = roleService.ajouterRole(roleConcessionnaire);
		roleLocataire = roleService.ajouterRole(roleLocataire);

		concessionnaire = utilisateurService.ajouterUtilisateur(concessionnaire);
		locataire = utilisateurService.ajouterUtilisateur(locataire);

		utilisateurService.ajouterRoleAUtilisateur(concessionnaire.getId(), roleConcessionnaire.getId());
		utilisateurService.ajouterRoleAUtilisateur(locataire.getId(), roleLocataire.getId());
	}

}
