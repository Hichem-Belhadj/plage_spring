package fr.orsys.plage.business;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
public class Pays {
	
	@Id
	String code;
	
	@NotNull(message = "Veuillez renseigner un nom !")
	@Size(max = 150)
	String nom;
	
	@OneToMany(mappedBy = "pays")
	List<Locataire> locataires;
}
