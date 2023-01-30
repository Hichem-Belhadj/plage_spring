package fr.orsys.plage.business;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Parasol {

	

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	
	@NotNull(message = "Veuillez renseigner un numéro d'emplacement!")
	byte numEmplacement;
	
	@JsonIgnore
	@ManyToOne
	@NotNull
	File file;
	
	@JsonIgnore
	@ManyToMany(mappedBy = "parasols")
	List<Location> locations;
	
	
	//constructeur pour la classe ServiceParasolImpl
	
	public Parasol(byte numeroEmplacement) {
		this.numEmplacement=numeroEmplacement;
	}
}
