package com.waes.passport.model;

/**
 * ResponseModel for basic response on APIs requests
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
