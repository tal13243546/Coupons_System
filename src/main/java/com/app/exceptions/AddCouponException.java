package com.app.exceptions;

public class AddCouponException extends Exception {

	public AddCouponException() {
		super("Something went wrong, please make sure you are not trying to add a coupon with a similar title to an existing coupon.");
	}

}
