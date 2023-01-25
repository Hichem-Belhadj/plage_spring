package fr.orsys.plage.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.orsys.plage.business.Location;

public interface LocationDao extends JpaRepository<Location, Long> {

}
