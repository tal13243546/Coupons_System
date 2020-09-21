package com.app.exceptions;

public class AddCompanyException extends Exception {

	public AddCompanyException() {
		super("There already exist company with this name or email, please change details and try again.");
	}

}
