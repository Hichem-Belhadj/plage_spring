package fr.orsys.plage.service.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Service;

import fr.orsys.plage.business.LienDeParente;
import fr.orsys.plage.dao.LienDeParenteDao;
import fr.orsys.plage.exception.NotExistingLienDeParenteException;
import fr.orsys.plage.service.LienDeParenteService;
import lombok.AllArgsConstructor;


@Service
@AllArgsConstructor
public class LienDeParenteServiceImpl implements LienDeParenteService {

	private LienDeParenteDao lienDeParenteDao;
	
	//verifier qu il existe pas en base
	@Override
	public LienDeParente ajouterLienDeParente(String nom, float coefficient) {
		LienDeParente lienDeParente=new LienDeParente();
		lienDeParente.setNom(nom);
		lienDeParente.setCoefficient(coefficient);
		return lienDeParenteDao.save(lienDeParente);
	}

	@Override
	public List<LienDeParente> recupererLienDeParente() {
	
		return lienDeParenteDao.findAll();
	}

	@Override
	public LienDeParente recupererLienDeParenteParId(Long id) {
		
		return lienDeParenteDao.findById(id).orElseThrow(
				()->new NotExistingLienDeParenteException("Ce lien de parenté n'existe pas!"));
	}

	
	@Override
	public float coefficientParLienDeParente(String nom) {
		LienDeParente lienDeParente=lienDeParenteDao.findByNom(nom);
		if(lienDeParente==null) {
			throw new NotExistingLienDeParenteException("ce lien de parenté n'exite pas!");
		}
		return lienDeParente.getCoefficient();
	}

	@Override
	public boolean supprimerLienDeParente(Long id) {
		//si false modifier avec findById(id).get()
		LienDeParente lienDeParente=lienDeParenteDao.findById(id).orElseThrow(
				()->new NotExistingLienDeParenteException("Ce lien de parenté n'exite pas!"));
		lienDeParenteDao.delete(lienDeParente);
		return true;
	}

	@Override
	public LienDeParente modifierLienDeParente(Long id, LienDeParente lienDeParente) {
		LienDeParente lienDeParenteAModifier=lienDeParenteDao.findById(id).orElseThrow(
				()->new NotExistingLienDeParenteException("Ce lien de parenté n'exite pas!"));
			lienDeParenteAModifier.setNom(lienDeParente.getNom());
			lienDeParenteAModifier.setCoefficient(lienDeParente.getCoefficient());
			return lienDeParenteAModifier;
			
	}

}
