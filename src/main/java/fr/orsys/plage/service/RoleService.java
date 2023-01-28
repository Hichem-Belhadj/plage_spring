package fr.orsys.plage.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import fr.orsys.plage.business.Role;

public interface RoleService {
	Role ajouterRole(Role role);
	
	Role recupererRoleParId(Long roleId);
	
	Role mettreAJourRole(Long id, Role role);
	
	boolean upprimerRole(Long roleId);
	
	List<Role> recupererRoles();
	
	ResponseEntity<Map<String, Object>> recupererRolePagination(
			int page,
			int size
	);
}
