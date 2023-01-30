package fr.orsys.plage.service;

import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import fr.orsys.plage.business.Concessionnaire;
import fr.orsys.plage.business.Role;
import fr.orsys.plage.business.Utilisateur;
import fr.orsys.plage.enums.Roles;
import lombok.extern.log4j.Log4j2;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Log4j2
//@AllArgsConstructor
class UtilisateurServiceTest2 {

	@Autowired
	private UtilisateurService utilisateurService;
	@Autowired
	private RoleService roleService;

	@Test
	void testAjouterConcessionnaireDetail() {
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
		log.info("khkhggjhgjhgj");
		// assertEquals(4, list.size());
		//assertEquals(17, list.get(2));
		assertNotNull(utilisateurEnBase);
		// assertFalse(list.isEmpty());
	}

//	@Test
//	void testSupprimerUtilisateur() {
//		fail("Not yet implemented");
//	}

}
