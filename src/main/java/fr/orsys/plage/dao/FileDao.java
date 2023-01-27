package fr.orsys.plage.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.orsys.plage.business.File;

public interface FileDao extends JpaRepository<File, Long> {

	File findByNumero(byte numero);
}
