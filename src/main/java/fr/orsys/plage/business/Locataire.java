package fr.orsys.plage.business;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter

@FieldDefaults(level = AccessLevel.PRIVATE)
public class Locataire extends Utilisateur {

	LocalDateTime dateHeureInscription;
	
	@JsonIgnore
	@OneToMany(mappedBy = "locataire")
	List<Location> locations;
	
	@ManyToOne
	LienDeParente lienDeParente;
	
	@JsonIgnore
	@ManyToOne
	@NotNull(message = "Veuillez indiquer votre pays !")
	Pays pays;

	public Locataire() {
		dateHeureInscription=LocalDateTime.now();
	}
	
	
}
