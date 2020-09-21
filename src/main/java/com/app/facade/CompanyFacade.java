package com.app.facade;

import java.util.ArrayList;
import java.util.List;

import javax.security.auth.login.LoginException;

import com.app.beans.Category;
import com.app.beans.Company;
import com.app.beans.Coupon;
import com.app.exceptions.AddCouponException;
import com.app.exceptions.UpdateCouponException;

/**
 * @author θμ μειο 
 * Gets DAO attributes from the ClientFacade which are initialized with DBDAO instances. 
 * This class is responsible for the logic of the companies actions.
 */
public class CompanyFacade extends ClientFacade {

	private int companyID;

	public CompanyFacade() {
		super(); // initialize the DBDAO instances in the ClientFacade.
	}

	@Override
	public boolean login(String email, String password) throws LoginException {
		for (Company company : companiesDAO.getAllCompanies()) {
			if (company.getEmail().equals(email) && company.getPassword().equals(password)) {
				this.companyID = company.getId(); // initialize the attribute this.companyID with the ID of the company that logged in.
				return true;
			}
		}
		throw new LoginException("Incorrect email or password, please make sure you are a registered company.");
	}

	public void addCoupon(Coupon coupon) throws AddCouponException {

		for (Coupon coupon2 : couponsDAO.getAllCoupons()) {
			if (coupon2.getCompanyId() == this.companyID) { 
				if (coupon2.getTitle().equals(coupon.getTitle())) { // Title can't be the same as an existing coupon title 
					                                                 // if their com_ID is the same.
					throw new AddCouponException();
				}
			}
		}
			couponsDAO.addCoupon(coupon); // add coupon to the data base
	}

	public void updateCoupon(Coupon coupon) throws UpdateCouponException {
		for (Coupon coupon2 : getCompanyCoupons()) {
			if (coupon.getId() == coupon2.getId()) {
				if (coupon.getCompanyId() != coupon2.getCompanyId()) { // Can't update coupon com_ID.
					throw new UpdateCouponException();
				}
			}
		}
		couponsDAO.updateCoupon(coupon); // update the coupon in the Data Base
	}

	public void deleteCoupon(int couponID) {
		couponsDAO.deleteCouponPurchaseByCoupon(couponID); // delete coupon records from Customers_Vs_Coupons
		couponsDAO.deleteCoupon(couponID); // delete coupon from the data base
	}

	public List<Coupon> getCompanyCoupons() {
		return getCompanyDetails().getCoupons();
	}

	public List<Coupon> getCompanyCoupons(Category category) {
		List<Coupon> coupons = new ArrayList<Coupon>();
		for (Coupon coupon : getCompanyCoupons()) {
			if (coupon.getCategory() == category) {
				coupons.add(coupon);
			}
		}
		return coupons;
	}

	public List<Coupon> getCompanyCoupons(double maxPrice) {
		List<Coupon> coupons = new ArrayList<Coupon>();
		for (Coupon coupon : getCompanyCoupons()) {
			if (coupon.getPrice() <= maxPrice) {
				coupons.add(coupon);
			}
		}
		return coupons;
	}

	public Company getCompanyDetails() {
		Company company = companiesDAO.getOneCompany(this.companyID);
		company.setCoupons(); //initialize company coupons list.
		return company;
	}

}
