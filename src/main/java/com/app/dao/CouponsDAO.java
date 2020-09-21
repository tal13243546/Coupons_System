package com.app.dao;

import java.util.List;

import com.app.beans.Coupon;
import com.app.beans.CustomerVsCoupon;

public interface CouponsDAO {

	void addCoupon(Coupon coupon);

	void updateCoupon(Coupon coupon);

	void deleteCoupon(int couponID);

	List<Coupon> getAllCoupons();

	Coupon getOneCoupon(int couponID);

	void addCouponPurchase(int customerID, int couponID);

	void deleteCouponPurchase(int customerID, int couponID);

	//vvv My Own Methods vvv
	
	void deleteCouponPurchaseByCoupon(int id);

	void deleteCouponPurchaseByCustomer(int customerID);

	List<CustomerVsCoupon> getCvcByCustomer(int customerID);

	Coupon getOneCouponByTitleAndComID(String title, int companyID);

	List<CustomerVsCoupon> getAllCvc();

}
