package ru.netology.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;


public class EditFileNameRQ {

    private String filename;

    @JsonCreator
    public EditFileNameRQ(String filename) {
        this.filename = filename;
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
    
}