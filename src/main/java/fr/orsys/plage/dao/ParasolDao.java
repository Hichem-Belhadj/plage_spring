package fr.orsys.plage.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import fr.orsys.plage.business.Parasol;

public interface ParasolDao extends JpaRepository<Parasol, Long> {

	List<Parasol>findByFileNumero(byte numero);
	
	List<Parasol>findAll();
	
	
	
	/**
	 * permet de recuperer la liste des parasols (numero) et leurs prix
	 * @return list:parasols
	 */
	@Query(value="select CONCAT(p.num_emplacement,'F' ,f.numero) as identifiant ,f.prix_journalier"
			+ " from parasol  p left join file as f"
			+ " on p.file=f.id",nativeQuery = true)
	List<Parasol>getParasolesAndPrices();

	
	
		//statut parasol a une date (loué ou non)
	
	//liste des parasol d une date
	
	//parasol avec locataire
	
	//parasol sans locataire
	
	//nom du parasol 
	
	
}
