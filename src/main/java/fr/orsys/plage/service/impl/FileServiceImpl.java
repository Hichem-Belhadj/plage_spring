package fr.orsys.plage.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.orsys.plage.business.File;
import fr.orsys.plage.dao.FileDao;
import fr.orsys.plage.exception.NotExistingFileException;
import fr.orsys.plage.service.FileService;
import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class FileServiceImpl implements FileService {
	
	private final FileDao fileDao;
	
	@Override
	public List<File> recupererFiles() {
		return fileDao.findAll();
	}

	@Override
	public File recupererFile(Long id) {

		return fileDao.findById(id).orElseThrow(
				()->new NotExistingFileException("Cette file est inexistante"));
	}

	@Override
	public File recupererFile(byte numero) {
		
		return fileDao.findByNumero(numero);
	}

	//travailler par setter et getter
	@Override
	public File ajouterFile(Byte numero, double prixJournalier) {
		
		return fileDao.save(new File(numero,prixJournalier));
	
	}

	@Override
	public boolean supprimerFile(Long id) {
		File file=recupererFile(id);
		
		if (file==null) {
			return false;
		}else {
			fileDao.delete(file);
			return true;
		}
	}
	
	@Override
	public File mettreAJourFile(byte numero, double nouveauPrixJournalier) {
		File file=recupererFile(numero);
		file.setPrixJournalier(nouveauPrixJournalier);
		return file;
	}

	@Override
	public void ajouterFile(File file) {
		fileDao.save(file);
	}

}
