package fr.orsys.plage.business;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@FieldDefaults(level = AccessLevel.PROTECTED)
public abstract class Utilisateur {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	
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
	
	@JsonIgnore
	@NotNull
	@ManyToMany(fetch = FetchType.EAGER)
	Set<Role> roles = new HashSet<>();
	
}
