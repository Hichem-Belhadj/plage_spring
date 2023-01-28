package fr.orsys.plage.service;

import java.util.List;

import fr.orsys.plage.business.Role;

public interface RoleService {
	Role ajouterRole(Role role);
	
	Role recupererRoleParId(Long roleId);
	
	List<Role> recupererRoles();
}
