package fr.orsys.plage.securite;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import lombok.AllArgsConstructor;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SpringSecuriteConfig {

	private final AuthenticationConfiguration authenticationConfiguration;
	
	/**
	 * HttpSecurity http
	 * @param http
	 * @return
	 * @throws Exception
	 */
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		SpringAuthentificationConfig springAuthentificationConfig = new SpringAuthentificationConfig(authenticationManager(authenticationConfiguration));
		springAuthentificationConfig.setFilterProcessesUrl("/api/v1/login");
		http.csrf().disable();
		http.cors();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.authorizeRequests(authorize -> authorize.
    		antMatchers(HttpMethod.GET, "/api/v1/login").permitAll()
    		.antMatchers(HttpMethod.GET, "/h2-console/**").permitAll()
    		.antMatchers(HttpMethod.PATCH, "/api/v1/statut/**").permitAll()
    		.antMatchers(HttpMethod.POST, "/api/v1/utilisateur/ajout").permitAll()
    		.antMatchers(HttpMethod.GET, "/api/v1/utilisateur/lienDeParente").permitAll()
    		.antMatchers(HttpMethod.GET, "/api/v1/utilisateur/listePays").permitAll()
    		.antMatchers(HttpMethod.GET, "/api/v1/reservations/**").permitAll()
    		.antMatchers(HttpMethod.GET, "/api/v1/statut/**").permitAll()
    		.antMatchers(HttpMethod.GET, "/api/v1/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_USER")
    		.antMatchers(HttpMethod.POST, "/api/v1/location/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_USER")
    		.antMatchers(HttpMethod.POST, "/api/v1/utilisateur/**").hasAnyAuthority("ROLE_ADMIN")
    		.antMatchers(HttpMethod.GET, "/api/v1/reservations/parasolParStatut/**").hasAnyAuthority("ROLE_ADMIN")
    		.antMatchers(HttpMethod.PATCH, "/api/v1/reservations/parasol/**").hasAnyAuthority("ROLE_ADMIN")
    		.anyRequest().authenticated()
	    );
		http.addFilter(springAuthentificationConfig);
		http.addFilterBefore(new SpringAutorisationConfig(), UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}
	
//	@Bean
//	public void filterChain(WebSecurity web) throws Exception {
//		web
//        .ignoring()
//        .antMatchers("/h2-console/**");
//	}
	
	 @Bean
	 public CorsConfigurationSource corsConfigurationSource() {
		 CorsConfiguration configuration = new CorsConfiguration();
		 configuration.setAllowedOrigins(Arrays.asList("*"));
		 configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
		 configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type", "x-auth-token"));
		 configuration.setExposedHeaders(Arrays.asList("x-auth-token"));
		 UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		 source.registerCorsConfiguration("/**", configuration);
		 return source;
	 }
	 
	 @Bean
	 	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	 }
	 
	 @Bean
	 public AuthenticationManager authenticationManager(
		 AuthenticationConfiguration authenticationConfiguration) throws Exception {
		 return authenticationConfiguration.getAuthenticationManager();
    }
	
}
