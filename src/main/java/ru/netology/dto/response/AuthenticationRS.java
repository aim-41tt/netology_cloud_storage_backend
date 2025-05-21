package ru.netology.dto.response;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthenticationRS {

	@JsonProperty("auth-token")
	private String authToken;

	public AuthenticationRS(String authToken) {
		this.authToken = authToken;
	}

	@Override
	public int hashCode() {
		return Objects.hash(authToken);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AuthenticationRS other = (AuthenticationRS) obj;
		return Objects.equals(authToken, other.authToken);
	}

}