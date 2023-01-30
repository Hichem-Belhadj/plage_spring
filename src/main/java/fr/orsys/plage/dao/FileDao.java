package fr.orsys.plage.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.orsys.plage.business.File;

@Repository
public interface FileDao extends JpaRepository<File, Long> {

	File findByNumero(byte numero);
}
