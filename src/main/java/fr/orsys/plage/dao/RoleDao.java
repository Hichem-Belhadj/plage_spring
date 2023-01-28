package fr.orsys.plage.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.orsys.plage.business.Role;
import fr.orsys.plage.enums.Roles;

@Repository
public interface RoleDao extends JpaRepository<Role, Long> {
	Role findByName(Roles role);
}
