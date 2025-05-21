package ru.netology.dto.request;

import javax.validation.constraints.NotBlank;

public class AuthenticationRQ {

	@NotBlank(message = "login must not be null")
	private String login;

	@NotBlank(message = "password must not be null")
	private String password;

	/**
	 * @return the login
	 */
	public String getLogin() {
		return login;
	}

	@Override
	public String toString() {
		return "AuthenticationRQ [login=" + login + ", password=" + password + "]";
	}

	public AuthenticationRQ() {
		// TODO Auto-generated constructor stub
	}

	public AuthenticationRQ(@NotBlank(message = "login must not be null") String login,
			@NotBlank(message = "password must not be null") String password) {
		this.login = login;
		this.password = password;
	}

	/**
	 * @param login the login to set
	 */
	public void setLogin(String login) {
		this.login = login;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
}