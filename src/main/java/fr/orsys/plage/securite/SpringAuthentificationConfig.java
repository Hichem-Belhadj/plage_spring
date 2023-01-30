package fr.orsys.plage.securite;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StreamUtils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.orsys.plage.exception.InformationDeConnexionException;
import fr.orsys.plage.util.TokenAlgorithm;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@AllArgsConstructor
@Log4j2
public class SpringAuthentificationConfig extends UsernamePasswordAuthenticationFilter {
	
	private final AuthenticationManager authenticationManager;
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
		
		try {
			byte[] inputStreamBytes = StreamUtils.copyToByteArray(request.getInputStream());
			
			@SuppressWarnings("unchecked")
			Map<String, String> jsonRequest = new ObjectMapper().readValue(inputStreamBytes, Map.class);

			// Connexion alternative en passant les information via l'url
			// String username = request.getParameter("email");
			// String password = request.getParameter("motDePasse");
			String username = jsonRequest.get("email");
			String password = jsonRequest.get("motDePasse");
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username.toLowerCase(), password);
			return authenticationManager.authenticate(authenticationToken);
			
		} catch (IOException e) {
			log.error("Les informations de connexion ne sont pas reconnus !");
			e.printStackTrace();
		}
		throw new InformationDeConnexionException("Les informations de connexion ne sont pas reconnus !");
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

		User user = (User)authResult.getPrincipal();
		Algorithm algorithm = TokenAlgorithm.getTokenAlgorithm();
		Date expireAt = new Date(System.currentTimeMillis() + 20*60*1000);
		String accessToken = JWT.create()
				.withSubject(user.getUsername())
				.withExpiresAt(expireAt)
				.withIssuer(request.getRequestURI())
				.withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
				.sign(algorithm);
		
		Map<String, String> tokens = new HashMap<>();
		tokens.put("access_token", accessToken);
		tokens.put("expire_at", String.valueOf(expireAt.getTime()));
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		new ObjectMapper().writeValue(response.getOutputStream(), tokens);
	}
	
}
