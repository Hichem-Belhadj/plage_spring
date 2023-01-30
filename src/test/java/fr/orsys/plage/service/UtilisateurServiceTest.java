package fr.orsys.plage.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.hamcrest.collection.IsEmptyCollection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import fr.orsys.plage.business.Concessionnaire;
import fr.orsys.plage.business.Role;
import fr.orsys.plage.business.Utilisateur;
import fr.orsys.plage.enums.Roles;
import fr.orsys.plage.exception.NotExistingUtilisateurException;

@SpringBootTest
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UtilisateurServiceTest {
	
	@Autowired
	private UtilisateurService utilisateurService;
	@Autowired
	private RoleService roleService;

	@Test
	@DisplayName("Teste l'ajout d'un concessionnaire")
	@Order(1)
	void testAjouterConcessionnaire() {
		Utilisateur utilisateur = new Concessionnaire();
		Role role = new Role();
		
		utilisateur.setNom("BEL");
		utilisateur.setPrenom("Hich");
		utilisateur.setEmail("hi@hi.hi");
		utilisateur.setMotDePasse("1234");
		
		role.setName(Roles.ROLE_ADMIN);
		role = roleService.ajouterRole(role);
		
		Utilisateur utilisateurEnBase = utilisateurService.ajouterUtilisateur(utilisateur);
		
		utilisateurService.ajouterRoleAUtilisateur(utilisateurEnBase.getId(), role.getId());
		assertNotNull(utilisateurEnBase);
	}

	@Test
	@DisplayName("Teste la récupération de tous les utilisateur")
	@Order(2)
	void testRecupererTousUtilisateurs() {
		List<Utilisateur> concessionnaireList = utilisateurService.recupererTousUtilisateurs();
		assertNotNull(concessionnaireList);
	}

	@Test
	@DisplayName("Teste la récupération d'un utilisateur par son ID")
	@Order(3)
	void testRecupererUtilisateur() {
		Exception exception = assertThrows(NotExistingUtilisateurException.class, () -> {
			Utilisateur utilisateur = utilisateurService.recupererUtilisateur(254L);
	    });
		
		String expectedMessage = "Cet utilisateur n'existe pas !";
	    String actualMessage = exception.getMessage();

	    assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	@DisplayName("Teste la suppression d'un utilisateur utilisateur")
	@Order(4)
	void testSupprimerUtilisateur() {
		boolean estSupprime = utilisateurService.supprimerUtilisateur(1L);
		// Utilisateur utilisateur = utilisateurService.recupererUtilisateur(1L);
		assertTrue(estSupprime);
	}

}
