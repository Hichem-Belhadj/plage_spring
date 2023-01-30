	package fr.orsys.plage.business;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Location {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	
	LocalDateTime dateHeureDebut;
	
	LocalDateTime dateHeureFin;
	
	@Positive(message = "Veuillez renseigner un prix!")
	double montantARegler;
	
//	@Lob
	String remarque;
	
	@JsonIgnore
	@ManyToMany
	@NotEmpty(message = "Veuillez choisir au moins un parassol !")
	List<Parasol> parasols;
	
	@ManyToOne
	@NotNull
	Concessionnaire concessionnaire;
	
	@ManyToOne
	@NotNull
	Statut statut;
	
	@JsonIgnore
	@ManyToOne
	@NotNull
	Locataire locataire;
	
	public Location() {
		Statut statut=new Statut((long)1,"à traiter");
		this.statut=statut;
	}
}
