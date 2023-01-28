package fr.orsys.plage.dto;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import fr.orsys.plage.business.Role;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PROTECTED)
public abstract class UtilisateurDto {
	
	Long id;
	
	String nom;
	
	String prenom;
	
	String email;
	
	@JsonIgnore
	String motDePasse;
	
	Set<Role> roles;
}
