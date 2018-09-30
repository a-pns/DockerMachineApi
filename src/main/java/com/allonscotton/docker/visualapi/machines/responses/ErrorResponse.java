package com.allonscotton.docker.visualapi.machines.responses;

/**
 * Error response object that is used to send information back to the client when an error has occured
 * @author allonscotton
 *
 */
public class ErrorResponse {
	
	private int status;
	private String message;
	
	public ErrorResponse(int status, String message)
	{
		this.setStatus(status);
		this.setMessage(message);
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
