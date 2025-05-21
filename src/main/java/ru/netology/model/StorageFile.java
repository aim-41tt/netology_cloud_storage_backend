package ru.netology.model;

import javax.persistence.*;

import java.beans.ConstructorProperties;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;

@Entity
@Table(name = "files")
public class StorageFile {
//	create table files (
//		    filename      varchar(255) not null,
//		    date          timestamp(6) not null, 
//		    file_content  bytea not null,
//		    size          bigint not null,
//		    user_username varchar(255),
//		    primary key (filename)
//		);

	@Id
	@Column(nullable = false, unique = true)
	private String filename;

	@Column(nullable = false)
	private LocalDateTime date;

	@Column(nullable = false, name = "file_content") 
	private byte[] fileContent;

	@Column(nullable = false)
	private Long size;

	@ManyToOne
	private User user;

	public StorageFile() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "StorageFile [filename=" + filename + ", date=" + date + ", size=" + size + ", user=" + user + "]";
	}
	
	@ConstructorProperties({"filename", "date", "fileContent", "size", "user"})
	public StorageFile(String filename, LocalDateTime date, byte[] fileContent, Long size, User user) {
		this.filename = filename;
		this.date = date;
		this.fileContent = fileContent;
		this.size = size;
		this.user = user;
	}

	/**
	 * @return the filename
	 */
	public String getFilename() {
		return filename;
	}

	/**
	 * @param filename the filename to set
	 */
	public void setFilename(String filename) {
		this.filename = filename;
	}

	/**
	 * @return the date
	 */
	public LocalDateTime getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	/**
	 * @return the size
	 */
	public Long getSize() {
		return size;
	}

	/**
	 * @param size the size to set
	 */
	public void setSize(Long size) {
		this.size = size;
	}

	/**
	 * @return the fileContent
	 */
	public byte[] getFileContent() {
		return fileContent;
	}

	/**
	 * @param fileContent the fileContent to set
	 */
	public void setFileContent(byte[] fileContent) {
		this.fileContent = fileContent;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(fileContent);
		result = prime * result + Objects.hash(date, filename, size, user);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StorageFile other = (StorageFile) obj;
		return Objects.equals(date, other.date) && Arrays.equals(fileContent, other.fileContent)
				&& Objects.equals(filename, other.filename) && Objects.equals(size, other.size)
				&& Objects.equals(user, other.user);
	}

}