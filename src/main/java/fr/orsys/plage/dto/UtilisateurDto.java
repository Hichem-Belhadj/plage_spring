package fr.orsys.plage.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
	
	String nom;
	
	String prenom;
	
	String email;
	
	@JsonIgnore
	String motDePasse;
}
