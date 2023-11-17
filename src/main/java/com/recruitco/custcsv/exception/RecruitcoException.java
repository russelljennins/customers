package com.recruitco.custcsv.exception;

public class RecruitcoException extends Exception {
	private static final long serialVersionUID = 4445663325366652922L;
	private String message;
	private Exception exception;

	public RecruitcoException(String message) {
		this.message = message;
	}

	public RecruitcoException(String message, Exception e) {
		this.message = message;
		this.exception = e;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Exception getException() {
		return exception;
	}

	public void setException(Exception exception) {
		this.exception = exception;
	}
}
