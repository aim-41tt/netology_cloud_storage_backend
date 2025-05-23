package ru.netology.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import ru.netology.jwt.JwtAuthenticationEntryPoint;
import ru.netology.jwt.JwtRequestFilter;
import ru.netology.service.UserService;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true, jsr250Enabled = true)
public class CloudStorageConfig extends WebSecurityConfigurerAdapter {

	private final UserService userService;
	private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	private final JwtRequestFilter jwtRequestFilter;

	@Value("${cors.credentials}")
	private Boolean credentials;

	@Value("${cors.origins}")
	private String origins;

	@Value("${cors.methods}")
	private String methods;

	@Value("${cors.headers}")
	private String headers;

	public CloudStorageConfig(UserService userService, JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
			JwtRequestFilter jwtRequestFilter) {
		this.userService = userService;
		this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
		this.jwtRequestFilter = jwtRequestFilter;
	}

	@Bean
	protected CorsConfigurationSource corsConfigurationSource() {
		final CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowCredentials(credentials);
		configuration.setAllowedOrigins(List.of(origins));
		configuration.setAllowedMethods(List.of(methods));
		configuration.setAllowedHeaders(List.of(headers));
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
	}

	@Bean
	protected PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
	    http.cors().and()
	        .csrf(csrf -> csrf.disable())
	        .authorizeRequests()
	            .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
	            .antMatchers("/login").permitAll()
	            .anyRequest().authenticated()
	    .and()
	        .exceptionHandling()
	            .authenticationEntryPoint(jwtAuthenticationEntryPoint)
	    .and()
	        .sessionManagement()
	            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

	    http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
	}


}