package com.app.exceptions;

public class AddCustomerException extends Exception {

	public AddCustomerException() {
		super("There already exist customer with this email, please try other email.");
		// TODO Auto-generated constructor stub
	}

}
