package com.waes.passport.model;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * RequestDiffModel to receive the left or right base64 value
 * @author tonsaito
 *
 */
public class RequestDiffModel {

	@NotBlank(message = "required field encoded_data")
	@JsonProperty("encoded_data")
	private String encodedData;
	
	public RequestDiffModel() {}
	
	public RequestDiffModel(String encodedData) {
		this.encodedData = encodedData;
	}

	public String getEncodedData() {
		return encodedData;
	}

	public void setEncodedData(String encodedData) {
		this.encodedData = encodedData;
	}
}
