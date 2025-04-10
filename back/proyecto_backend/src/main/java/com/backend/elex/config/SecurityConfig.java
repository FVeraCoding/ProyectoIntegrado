package com.backend.elex.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.backend.elex.repository.UserRepository;
import com.backend.elex.security.JwtAuthFilter;
import com.backend.elex.security.JwtUtil;

@Configuration
public class SecurityConfig {


	 @Bean
	    public UserDetailsService userDetailsService(UserRepository userRepository) {
	        return username -> userRepository.findByEmail(username)
	                .map(user -> User.builder()
	                        .username(user.getEmail())
	                        .password(user.getPassword())
	                        .roles("USER")
	                        .build())
	                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));
	    }
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(UserDetailsService userDetailsService) { 
	    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
	    authProvider.setUserDetailsService(userDetailsService);
	    authProvider.setPasswordEncoder(passwordEncoder());
	    return new ProviderManager(authProvider);
	}
	

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http, UserDetailsService userDetailsService, JwtUtil jwtUtil) throws Exception {
	    http.csrf(csrf -> csrf.disable())
	        .cors(cors -> cors.configure(http))
	        .authorizeHttpRequests(auth -> auth
	            .requestMatchers(HttpMethod.POST, "/backendelex/users").permitAll()
	            .requestMatchers(HttpMethod.GET, "/backendelex/users").authenticated()
	            .requestMatchers("/backendelex/users/auth/login").permitAll()
	            .requestMatchers("/backendelex/users/**").authenticated()
	        )
	        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
	        .addFilterBefore(new JwtAuthFilter(jwtUtil, userDetailsService), UsernamePasswordAuthenticationFilter.class);

	    return http.build();
	}

	
}
