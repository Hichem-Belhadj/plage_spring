package fr.orsys.plage.business;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Locataire extends Utilisateur {

	LocalDateTime dateHeureInscription;
	
	@OneToMany(mappedBy = "locataire")
	List<Location> locations;
	
	@ManyToOne
	LienDeParente lienDeParente;
	
	@ManyToOne
	@NotNull(message = "Veuillez indiquer votre pays !")
	Pays pays;
}
