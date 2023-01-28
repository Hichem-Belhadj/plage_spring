package fr.orsys.plage.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.orsys.plage.business.Parasol;

public interface ParasolDao extends JpaRepository<Parasol, Long> {

	List<Parasol>findByFileNumero(byte numero);
	
}
