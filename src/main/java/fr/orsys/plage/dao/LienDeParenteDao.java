package fr.orsys.plage.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.orsys.plage.business.LienDeParente;

@Repository
public interface LienDeParenteDao extends JpaRepository<LienDeParente, Long> {

	LienDeParente findByNom(String nom);
}
