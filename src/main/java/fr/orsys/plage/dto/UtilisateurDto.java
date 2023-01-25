package fr.orsys.plage.dto;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

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
	
	@NotNull(message = "Veuillez renseigner votre nom !")
	@Size(max = 150)
	String nom;
	
	@NotNull(message = "Veuillez renseigner votre prenom !")
	@Size(max = 150)
	String prenom;
	
	@Email(message = "Merci de préciser l'adresse email au bon format !")
	@NotBlank(message = "Merci de préciser votre adresse email !")
	@Column(unique = true)
	String email;
	
	@NotNull(message = "Veuillez renseigner votre mot de passe !")
	@Size(min = 8, message = "Veuillez renseigner au moins ${min} caractères")
	@JsonProperty(access = Access.WRITE_ONLY)
	String motDePasse;
}