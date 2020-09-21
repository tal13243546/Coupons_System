package com.app.beans;
/**
 * A bean that help to get data from CustomerVsCoupons Table
 * and to make actions with that data on CustomerVsCoupons Table.
 * @author θμ μειο
 */
public class CustomerVsCoupon {

	private int customerID;
	private int couponID;

	public CustomerVsCoupon() {

	}

	public CustomerVsCoupon(int customerID, int couponID) {
		this.customerID = customerID;
		this.couponID = couponID;
	}

	public int getCustomerID() {
		return customerID;
	}

	public int getCouponID() {
		return couponID;
	}

	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}

	public void setCouponID(int couponID) {
		this.couponID = couponID;
	}

	@Override
	public String toString() {
		return "CustomerVsCoupon [customerID=" + customerID + ", couponID=" + couponID + "]";
	}

}
