package ru.netology.dto.response;

import java.util.Objects;

public class FileRS {

	private String filename;
	private Long size;

	public FileRS() {
		// TODO Auto-generated constructor stub
	}

	public FileRS(String filename, Long size) {
		this.filename = filename;
		this.size = size;
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

	@Override
	public int hashCode() {
		return Objects.hash(filename, size);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FileRS other = (FileRS) obj;
		return Objects.equals(filename, other.filename) && Objects.equals(size, other.size);
	}

}