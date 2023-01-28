package fr.orsys.plage.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Component;

@Component
public class GestionDate {

	public Date dateAleatoireEntre(Date dateDebut, Date dateFin) {
	    long debutMillis = dateDebut.getTime();
	    long finMillis = dateFin.getTime();
	    long dateAleatoire = ThreadLocalRandom
	      .current()
	      .nextLong(debutMillis, finMillis);

	    return new Date(dateAleatoire);
	}
	
	public Date dateDepuisFormat(String format, String dateAFormater) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.FRANCE); 
		return formatter.parse(dateAFormater);
	}
	
	public LocalDateTime convertirVersLocalDateTime(Date dateAConvertir) {
	    return Instant.ofEpochMilli(dateAConvertir.getTime())
	      .atZone(ZoneId.systemDefault())
	      .toLocalDateTime();
	}
	
}
