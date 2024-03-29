package fr.orsys.plage.dao;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import fr.orsys.plage.business.Parasol;

@Repository
public interface ParasolDao extends JpaRepository<Parasol, Long> {

	List<Parasol>findByFileNumero(byte numero);
	
	/**
	 * Récupère la liste des parasol avec le statut pour un jour donné
	 * Le premier champ est le numero d'emplacement
	 * le second l'id de la file,
	 * l'id de la location,
	 * le statut 
	 * @param dateRecherche
	 * @return
	 */
	@Query(value = """
			SELECT T1.num_emplacement as "tableId", T1.file_id, T2.location_id, T2.statut_id FROM parasol T1 LEFT JOIN 
			(SELECT lo.id AS location_id, lp.parasols_id AS parasol_id, lo.statut_id AS statut_id, pa.file_id AS file_id 
			FROM location lo Inner JOIN location_parasols lp ON lp.locations_id = lo.id inner JOIN parasol pa ON pa.id = lp.parasols_id 
			WHERE statut_id=2 
			AND lo.date_heure_debut <= :dateRecherche 
			AND lo.date_heure_fin >= :dateRecherche) 
			T2 ON T2.parasol_id = T1.id
			""", nativeQuery = true)
	List<List<Integer>>findByDateAndSatatutConfirme(LocalDateTime dateRecherche);
	
	/**
	 * Récupère la liste des parasols disponible pendant une durée variable
	 * le premier champs est l'id du parasol
	 * le second est le numero d'emplacement
	 * le troisième est l'id de la file
	 * 
	 * @param dateDebut
	 * @param dateFin
	 * @return
	 */
	@Query(value = """
			SELECT t1.id, t1.num_emplacement, t1.file_id FROM parasol t1 
			LEFT JOIN ( 
			SELECT * FROM location lo 
			JOIN location_parasols lp ON lp.locations_id = lo.id 
			JOIN parasol pa ON pa.id = lp.parasols_id 
			WHERE date_heure_debut >= :dateDebut 
			and date_heure_debut <= :dateFin 
			) ade ON ade.parasols_id = t1.id 
			WHERE ade.montantaregler IS NULL 
			""", nativeQuery = true)
	List<List<Integer>>findByDateAndSatatutDisponible(LocalDateTime dateDebut, LocalDateTime dateFin);
}
