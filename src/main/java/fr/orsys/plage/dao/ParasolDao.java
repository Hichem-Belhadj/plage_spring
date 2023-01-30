package fr.orsys.plage.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.orsys.plage.business.Parasol;

@Repository
public interface ParasolDao extends JpaRepository<Parasol, Long> {

	List<Parasol>findByFileNumero(byte numero);
	
}
