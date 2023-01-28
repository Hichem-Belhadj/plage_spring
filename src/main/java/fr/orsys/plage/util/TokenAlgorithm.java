package fr.orsys.plage.util;

import com.auth0.jwt.algorithms.Algorithm;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenAlgorithm {

	public static Algorithm getTokenAlgorithm() {
		return Algorithm.HMAC256("sercet".getBytes());
	}
	
}
