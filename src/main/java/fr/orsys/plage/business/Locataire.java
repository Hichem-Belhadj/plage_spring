package fr.orsys.plage.business;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonIdentityInfo(
		  generator = ObjectIdGenerators.PropertyGenerator.class, 
		  property = "id")
public class Locataire extends Utilisateur {

	LocalDateTime dateHeureInscription;
	
	@OneToMany(mappedBy = "locataire", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
	List<Location> locations;
	
	@ManyToOne
	LienDeParente lienDeParente;
	
	@ManyToOne
	//@JsonBackReference(value = "locataires_pays")
	@JsonIgnore
	@NotNull(message = "Veuillez indiquer votre pays !")
	Pays pays;

	public Locataire() {
		dateHeureInscription=LocalDateTime.now();
	}
}
