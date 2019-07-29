package com.waes.passport.model;

/**
 * ResponseModel for basic responses on APIs requests
 * @author tonsaito
 *
 */
public class ResponseModel {
	private String message;
	
	public ResponseModel(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
