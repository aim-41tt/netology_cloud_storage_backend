package ru.netology.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "users")
public class User implements UserDetails, Serializable {

	private static final long serialVersionUID = 6606772991426123350L;

	@Id
	@Column(nullable = false, unique = true)
	private String username;

	@Column(nullable = false)
	private String password;

//	@OneToMany(cascade = CascadeType.ALL)
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<StorageFile> userFiles;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of();
	}



	@Override
	public String toString() {
		return "User [username=" + username + "]";
	}



	@Override
	public int hashCode() {
		return Objects.hash(password, userFiles, username);
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(password, other.password) && Objects.equals(userFiles, other.userFiles)
				&& Objects.equals(username, other.username);
	}



	public User() {
		// TODO Auto-generated constructor stub
	}

	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public User(String username, String password, List<StorageFile> userFiles) {
		this.username = username;
		this.password = password;
		this.userFiles = userFiles;
	}

	/**
	 * @return the userFiles
	 */
	public List<StorageFile> getUserFiles() {
		return userFiles;
	}

	/**
	 * @param userFiles the userFiles to set
	 */
	public void setUserFiles(List<StorageFile> userFiles) {
		this.userFiles = userFiles;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}