package fr.orsys.plage.business;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Positive;

import org.hibernate.validator.constraints.Range;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class File {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	
	@Range(min = 1, max = 8, message = "Le numéro de file doit être compris entre ${min} et ${max}")
	byte numero;
	
	@Positive(message = "Veuillez renseigner un prix!")
	double prixJournalier;
	
	@OneToMany(mappedBy = "file")
	List<Parasol> parasols;
}
