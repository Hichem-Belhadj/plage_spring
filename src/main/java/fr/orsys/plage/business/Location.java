package fr.orsys.plage.business;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Location {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	
	LocalDateTime dateHeureDebut;
	
	LocalDateTime dateHeureFin;
	
	double montantARegler;
	
	@Type(type = "org.hibernate.type.TextType") 
	String remarque;
	
	@JsonIgnore
	@ManyToMany
	List<Parasol> parasols;
	
	@ManyToOne
	@NotNull
	Concessionnaire concessionnaire;
	
	@NotNull
	@JsonIgnore
	@OneToMany(mappedBy = "location", cascade = CascadeType.PERSIST)
	List<DemandeReservation> demandeReservations;
	
	@ManyToOne
	@NotNull
	Statut statut;
	
	@ManyToOne
	@NotNull
	Locataire locataire;
}
