package ru.netology.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.netology.dto.request.AuthenticationRQ;
import ru.netology.dto.response.AuthenticationRS;
import ru.netology.jwt.JwtTokenUtil;
import ru.netology.repository.AuthenticationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class AuthenticationService {

	private static final Logger log = LoggerFactory.getLogger(AuthenticationService.class);
	private AuthenticationRepository authenticationRepository;
	private AuthenticationManager authenticationManager;
	private JwtTokenUtil jwtTokenUtil;
	private UserService userService;
	
	

	public AuthenticationService(AuthenticationRepository authenticationRepository,
			AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil, UserService userService) {
		this.authenticationRepository = authenticationRepository;
		this.authenticationManager = authenticationManager;
		this.jwtTokenUtil = jwtTokenUtil;
		this.userService = userService;
	}

	public AuthenticationRS login(AuthenticationRQ authenticationRQ) {
		final String username = authenticationRQ.getLogin();
		final String password = authenticationRQ.getPassword();
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password)); 
		final UserDetails userDetails = userService.loadUserByUsername(username);
		final String token = jwtTokenUtil.generateToken(userDetails);
		authenticationRepository.putTokenAndUsername(token, username);
		log.info("User {} authentication. JWT: {}", username, token);
		return new AuthenticationRS(token);
	}

	public void logout(String authToken) {
		final String token = authToken.substring(7);
		final String username = authenticationRepository.getUsernameByToken(token);
		log.info("User {} logout. JWT is disabled.", username);
		authenticationRepository.removeTokenAndUsernameByToken(token);
	}
}