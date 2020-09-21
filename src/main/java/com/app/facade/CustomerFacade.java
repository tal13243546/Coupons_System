package com.app.facade;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.login.LoginException;

import com.app.beans.Category;
import com.app.beans.Coupon;
import com.app.beans.Customer;
import com.app.exceptions.purchaseCouponException;

/**
 * @author θμ μειο 
 * Gets DAO attributes from the ClientFacade which are initialized with DBDAO instances. 
 * This class is responsible for the logic of the customers actions.
 */
public class CustomerFacade extends ClientFacade {

	private int customerID;

	public CustomerFacade() {
		super(); // initialize the DBDAO instances in the ClientFacade.
	}

	@Override
	public boolean login(String email, String password) throws LoginException {
		for (Customer customer : customersDAO.getAllCustomers()) {
			if (customer.getEmail().equals(email) && customer.getPassword().equals(password)) {
				this.customerID = customer.getId(); // initialize the attribute this.customerID with the ID of the customer that logged in.
				return true;
			}
		}
		throw new LoginException("Incorrect email or password, please make sure you are a registered customer.");
	}

	public boolean purchaseCoupon(Coupon coupon) throws purchaseCouponException {
		for (Coupon c1 : getCustomerCoupons()) {
			if (c1.getId() == couponsDAO.getOneCouponByTitleAndComID(coupon.getTitle(), coupon.getCompanyId()) // Can't purchase the same coupon twice (as long it is not used).
					.getId()) {
				throw new purchaseCouponException(
						"Can't purchase the same coupon twice.\nUse the one you bought and try again or purchase different coupons.");
			}
		}
		if (coupon.getAmount() == 0) { // Can't purchase a sold out coupon!
			throw new purchaseCouponException("Sorry this coupon was sold out.");
		} else if (coupon.getEndDate().before(Date.valueOf(LocalDate.now()))) { // Can't purchase an expired coupon!
			throw new purchaseCouponException("Sorry this coupon has expired.");
		} else {
			Coupon c1 = couponsDAO.getOneCouponByTitleAndComID(coupon.getTitle(), coupon.getCompanyId());
			c1.setAmount(c1.getAmount() - 1);
			couponsDAO.updateCoupon(c1); 
			couponsDAO.addCouponPurchase(this.customerID, c1.getId());
			return true;
		}

	}

	public List<Coupon> getCustomerCoupons() {
		return getCustomerDetails().getCoupons();
	}

	public List<Coupon> getCustomerCoupons(Category category) {
		List<Coupon> coupons = new ArrayList<Coupon>();
		for (Coupon coupon : getCustomerCoupons()) {
			if (coupon.getCategory() == category) {
				coupons.add(coupon);
			}
		}
		return coupons;
	}

	public List<Coupon> getCustomerCoupons(double maxPrice) {
		List<Coupon> coupons = new ArrayList<Coupon>();
		for (Coupon coupon : getCustomerCoupons()) {
			if (coupon.getPrice() <= maxPrice) {
				coupons.add(coupon);
			}
		}
		return coupons;
	}

	public Customer getCustomerDetails() {
		Customer c1 = customersDAO.getOneCustomer(this.customerID);
		c1.setCoupons(); //initialize customer coupons list.
		return c1;
	}

}
