package com.app.beans;

import java.util.ArrayList;
import java.util.List;

import com.app.dbdao.CouponsDBDAO;

public class Customer {

	private int id;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private List<Coupon> coupons;

	public Customer() {
	}

	public Customer(String firstName, String lastName, String email, String password) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.coupons = new ArrayList<Coupon>();
	}

	public int getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public List<Coupon> getCoupons() {
		return coupons;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Initializing the customer coupons list.
	 */
	public void setCoupons() {
		for (CustomerVsCoupon cvc : CouponsDBDAO.getInstance().getCvcByCustomer(this.id)) {
			coupons.add(CouponsDBDAO.getInstance().getOneCoupon(cvc.getCouponID()));
		}
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", password=" + password + ", coupons=" + coupons + "]";
	}

}
