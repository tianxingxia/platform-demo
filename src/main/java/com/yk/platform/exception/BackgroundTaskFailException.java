package com.yk.platform.exception;

public class BackgroundTaskFailException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public BackgroundTaskFailException() {
		// TODO Auto-generated constructor stub
	}

	public BackgroundTaskFailException(String message) {
		this.message = message;
	}

}
