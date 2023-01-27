package fr.orsys.plage.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.orsys.plage.business.LienDeParente;

public interface LienDeParenteDao extends JpaRepository<LienDeParente, Long> {

	LienDeParente findByNom(String nom);
}
