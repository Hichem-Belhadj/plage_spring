package fr.orsys.plage.service.impl;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import fr.orsys.plage.business.Utilisateur;
import fr.orsys.plage.dao.UtilisateurDao;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@AllArgsConstructor
@Log4j2
public class UtilisateurDetailsService implements UserDetailsService {
	
	private final UtilisateurDao utilisateurDao;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Utilisateur utilisateur = utilisateurDao.findByEmail(username);
		
		if (utilisateur == null) {
			log.error("L'utilisateur {} n'est pas présent en base", username);
			throw new UsernameNotFoundException("Utilisateur introuvable");
		} else {
			log.error("Utilisateur présent en base: {}", username);
			Collection<SimpleGrantedAuthority> authorities  = new ArrayList<>();
			utilisateur.getRoles().forEach(role->authorities.add(new SimpleGrantedAuthority(role.getName().name())));
			return new org.springframework.security.core.userdetails.User(utilisateur.getEmail(), utilisateur.getMotDePasse(), authorities);
		}
	}

}
