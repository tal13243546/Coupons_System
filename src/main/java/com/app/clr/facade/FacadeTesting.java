package com.app.clr.facade;

import java.sql.Date;

import javax.security.auth.login.LoginException;

import com.app.beans.Category;
import com.app.beans.Company;
import com.app.beans.Coupon;
import com.app.beans.Customer;
import com.app.dbdao.CouponsDBDAO;
import com.app.exceptions.AddCompanyException;
import com.app.exceptions.AddCustomerException;
import com.app.exceptions.UpdateCompanyException;
import com.app.exceptions.purchaseCouponException;
import com.app.facade.AdminFacade;
import com.app.facade.CompanyFacade;
import com.app.facade.CustomerFacade;
import com.app.utils.ArtUtil;
import com.app.utils.Table100;

/**
 * Tests all the Facade Classes, their methods and their logic.
 * Table100 - prints table of list or entity.
 * @author θμ μειο
 *
 */
public class FacadeTesting {

	public static void adminFacadeTesting(AdminFacade admin) {
		ArtUtil.adminFacadeTesting();
		AdminFacade adminFac = admin;
		System.out.println("===========================Admin Login Test=========================");
		try {

			System.out.println("---------ERROR--------");
			System.out.println(adminFac.login("amin@amin.com", "amin")); // Should provide an error (wrong password or
																			// email)
		} catch (LoginException e) {
			System.out.println(e.getMessage());
			System.out.println("---------ERROR--------");
		}
		System.out.println();

		System.out.println("===========================Add Company Test=========================");
		Company com1 = new Company("Tal", "t@gmail.com", "1324");
		try {
			adminFac.addCompany(com1); // Should add the company
			adminFac.addCompany(new Company("JB", "jb@gmail.com", "jb123"));
			System.out.println("---------ERROR--------");
			adminFac.addCompany(new Company("Tal", "t@gmail.com", "1324")); // should provide an error (Same name or
																			// email)
		} catch (AddCompanyException e) {
			System.out.println(e.getMessage());
			System.out.println("---------ERROR--------");
		}
		System.out.println();
		Table100.print(adminFac.getAllCompanies());

		System.out.println("===========================Update Company (Password) Test=========================");
		com1 = adminFac.getOneCompany(1);
		com1.setPassword("4231");
		try {
			adminFac.updateCompany(com1); // Should update "Tal" password
			com1.setName("moshe");
			System.out.println("---------ERROR--------");
			adminFac.updateCompany(com1); // Should provide an error (Can't update company name)
		} catch (UpdateCompanyException e) {
			System.out.println(e.getMessage());
			System.out.println("---------ERROR--------");
		}
		System.out.println();
		Table100.print(adminFac.getAllCompanies());

		System.out.println("===========================Get All Companies Test=========================");
		System.out.println();
		Table100.print(adminFac.getAllCompanies());

		System.out.println("===========================Get One Company Test=========================");
		System.out.println();
		Table100.print(adminFac.getOneCompany(com1.getId()));

		System.out.println("===========================Delete Tal Company Test=========================");
		adminFac.deleteCompany(com1.getId());
		System.out.println();
		Table100.print(adminFac.getAllCompanies());

		System.out.println("===========================Add Customer Test=========================");
		Customer custom1 = new Customer("Tom", "Cat", "tc@gmail.com", "tc123");
		try {
			adminFac.addCustomer(custom1); // Should add customer
			adminFac.addCustomer(new Customer("Tal", "Levin", "tl@gmail.com", "tl123")); // Should add customer
			System.out.println("---------ERROR--------");
			adminFac.addCustomer(new Customer("kobi", "kobi", "tc@gmail.com", "kk123")); // Should provide an error
																							// (Same Email)
		} catch (AddCustomerException e) {
			System.out.println(e.getMessage());
			System.out.println("---------ERROR--------");
		}
		System.out.println();
		Table100.print(adminFac.getAllCustomers());

		System.out.println("===========================Update Customer Tom Password Test=========================");
		custom1 = adminFac.getOneCustomer(1);
		custom1.setPassword("tomcat13");
		adminFac.updateCustomer(custom1);
		System.out.println("VVV Resaults Down VVV");
		System.out.println();

		System.out.println("===========================Get One Customer (Tom) Test=========================");
		System.out.println();
		Table100.print(adminFac.getOneCustomer(custom1.getId()));

		System.out.println("===========================Get All Customers Test=========================");
		System.out.println();
		Table100.print(adminFac.getAllCustomers());

		System.out.println("===========================Delete Customer Tom Test=========================");
		adminFac.deleteCustomer(custom1.getId());
		System.out.println();
		Table100.print(adminFac.getAllCustomers());
	}

	public static void companyFacadeTesting(CompanyFacade company) {
		ArtUtil.companyFacadeTesting();
		CompanyFacade comFac = company;
		System.out.println("===========================Company Login Test=========================");
		try {
			System.out.println("---------ERROR--------");
			System.out.println(comFac.login("aaa@gmail.com", "13bb24")); // Should provide an error (wrong password or
																			// email)
		} catch (LoginException e) {
			System.out.println(e.getMessage());
			System.out.println("---------ERROR--------");
		}
		System.out.println();

		System.out.println("===========================Add Coupon Test=========================");
		Date startDate = Date.valueOf("2020-08-14");
		Date endDate = Date.valueOf("2020-10-14");
		Coupon coup1 = new Coupon(2, Category.FOOD, "Hamburg", "2 Hamburgers 150gr", startDate, endDate, 10, 89.90,
				"image.png");
		Coupon coup2 = new Coupon(2, Category.RESTAURANT, "BBB", "Hamburger Katzavim + Fries + Drink", startDate, endDate,
				10, 99.90, "image.png");
		Coupon coup3 = new Coupon(2, Category.VACATION, "Eilat", "Half Pension Malkat Shva", startDate, endDate, 10,
				499.90, "image.png");
		Coupon coup4 = new Coupon(2, Category.ELECTRICITY, "Mahsanei Hashmal", "Iphone XS Max", startDate, endDate, 10,
				1099.90, "image.png");
		try {
			comFac.addCoupon(coup1);
			comFac.addCoupon(coup2);
			comFac.addCoupon(coup3);
			comFac.addCoupon(coup4);
			System.out.println("---------ERROR--------");
			comFac.addCoupon(coup1); // Should provide an error (Can't add coupons with the same title)
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("---------ERROR--------");
		}
		System.out.println();
		Table100.print(comFac.getCompanyCoupons());

		System.out.println("===========================Update Coupon Hamburg (Amount) Test=========================");
		coup1 = comFac.getCouponsDAO().getOneCouponByTitleAndComID(coup1.getTitle(), coup1.getCompanyId());
		coup2 = comFac.getCouponsDAO().getOneCouponByTitleAndComID(coup2.getTitle(), coup2.getCompanyId());
		coup2.setEndDate(Date.valueOf("2020-09-14"));
		coup1.setAmount(20);
		try {
			comFac.updateCoupon(coup1); // Should update the coupon (Hamburg) amount
			comFac.updateCoupon(coup2); // Should update the coupon (BBB) endDate
			System.out.println("---------ERROR--------");
			coup1.setCompanyId(1);
			comFac.updateCoupon(coup1); // Should provide an error (can't update coupons companyId)
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("---------ERROR--------");
		}
		System.out.println();
		Table100.print(comFac.getCompanyCoupons());

		System.out.println("===========================Get JB Company Coupons Test=========================");
		System.out.println();
		Table100.print(comFac.getCompanyCoupons());

		System.out.println("=======================Get Company Coupons By Category (FOOD) Test=====================");
		System.out.println();
		Table100.print(comFac.getCompanyCoupons(Category.FOOD));
		System.out.println("=======================Get Company Coupons By Max Price (100.0) Test=====================");
		System.out.println();
		Table100.print(comFac.getCompanyCoupons(100.0));

		System.out.println("===========================Get JB Company Details Test=========================");
		System.out.println();
		Table100.print(comFac.getCompanyDetails());

		System.out.println("===========================Delete Hamburg Coupon Test=========================");
		comFac.deleteCoupon(coup1.getId());
		System.out.println();
		Table100.print(comFac.getCompanyCoupons());
	}

	public static void customerFacadeTesting(CustomerFacade customer) {
		ArtUtil.customerFacadeTesting();
		CustomerFacade customFac = customer;

		System.out.println("===========================Customer Login Test=========================");
		try {
			System.out.println("---------ERROR--------");
			customFac.login("abc@gmail.co.il", "abc123"); // Should provide an error (wrong password or email)
		} catch (LoginException e) {
			System.out.println(e.getMessage());
			System.out.println("---------ERROR--------");
		}
		System.out.println();

		System.out.println("===========================Purchase Coupon Test=========================");
		Coupon coup2 = CouponsDBDAO.getInstance().getOneCoupon(2);
		Coupon coup3 = CouponsDBDAO.getInstance().getOneCoupon(3);
		Coupon coup4 = CouponsDBDAO.getInstance().getOneCoupon(4);
		coup4.setAmount(0);
		try {
			System.out.println(customFac.purchaseCoupon(coup3)); // Should return true
			System.out.println("---------ERROR--------");
			customFac.purchaseCoupon(coup3); // Should provide an error (Can't Buy Twice)
		} catch (purchaseCouponException e) {
			System.out.println(e.getMessage());
			System.out.println("---------ERROR--------");
		}
		try {
			System.out.println("---------ERROR--------");
			customFac.purchaseCoupon(coup4); // Should provide an error (Amount = 0 = Sold Out)
		} catch (purchaseCouponException e) {
			System.out.println(e.getMessage());
			System.out.println("---------ERROR--------");
		}
		try {
			System.out.println("---------ERROR--------");
			customFac.purchaseCoupon(coup2); // Should provide an error (Coupon Expired)
		} catch (purchaseCouponException e) {
			System.out.println(e.getMessage());
			System.out.println("---------ERROR--------");
		}
		System.out.println();

		System.out.println("===========================Get Customer Coupons Test=========================");
		System.out.println("Coupons purchased by the customer:");
		System.out.println();
		Table100.print(customFac.getCustomerCoupons());

		System.out.println(
				"======================Get Customer Coupons By Category (Vacation) Test========================");
		System.out.println();
		Table100.print(customFac.getCustomerCoupons(Category.VACATION)); // Should return the customer coupon
		System.out.println(customFac.getCustomerCoupons(Category.FOOD)); // Should return nothing
		System.out.println();

		System.out.println(
				"=========================Get Customer Coupons By Max Price (500) Test=======================");
		System.out.println();
		Table100.print(customFac.getCustomerCoupons(500.0)); // Should return the customer coupon
		System.out.println(customFac.getCustomerCoupons(90.0)); // Should return nothing
		System.out.println();

		System.out.println("===========================Get Customer Details Test=========================");
		System.out.println();
		Table100.print(customFac.getCustomerDetails());
	}
}
