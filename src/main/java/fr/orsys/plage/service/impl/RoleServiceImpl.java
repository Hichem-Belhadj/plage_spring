package fr.orsys.plage.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import fr.orsys.plage.business.Role;
import fr.orsys.plage.dao.RoleDao;
import fr.orsys.plage.service.RoleService;
import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {
	
	private final RoleDao roleDao;

	@Override
	public Role ajouterRole(Role role) {
		return roleDao.save(role);
	}

	@Override
	public Role recupererRoleParId(Long roleId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Role> recupererRoles() {
		// TODO Auto-generated method stub
		return null;
	}

}
