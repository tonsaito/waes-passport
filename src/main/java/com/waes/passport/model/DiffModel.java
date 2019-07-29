package com.waes.passport.model;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DiffModel {

	@NotBlank(message = "required field encoded_data")
	@JsonProperty("encoded_data")
	private String encodedData;

	public String getEncodedData() {
		return encodedData;
	}

	public void setEncodedData(String encodedData) {
		this.encodedData = encodedData;
	}
}
