package fr.orsys.plage.business;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
public class LienDeParente {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	
	@NotNull(message = "Veuillez renseigner votre nom !")
	@Size(max = 150)
	String nom;
	
	float coefficient;
}
