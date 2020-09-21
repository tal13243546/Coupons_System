package com.app.beans;

import java.util.ArrayList;
import java.util.List;

import com.app.dbdao.CouponsDBDAO;

public class Company {

	private int id;
	private String name;
	private String email;
	private String password;
	private List<Coupon> coupons;

	public Company() {
	}

	public Company(String name, String email, String password) {
		this.name = name;
		this.email = email;
		this.password = password;
		this.coupons = new ArrayList<Coupon>();
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
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

	public void setName(String name) {
		this.name = name;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Initializing the company coupons list.
	 */
	public void setCoupons() {
		for (Coupon coupon : CouponsDBDAO.getInstance().getAllCoupons()) {
			if (coupon.getCompanyId() == this.id) {
				this.coupons.add(coupon);
			}
		}
	}

	@Override
	public String toString() {
		return "Company [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ", coupons="
				+ coupons + "]";
	}

}
