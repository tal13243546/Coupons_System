package com.app.clr.dbdao;

import java.sql.Date;

import com.app.beans.Category;
import com.app.beans.Company;
import com.app.beans.Coupon;
import com.app.beans.Customer;
import com.app.dao.CompaniesDAO;
import com.app.dao.CouponsDAO;
import com.app.dao.CustomersDAO;
import com.app.db.DBManager;
import com.app.dbdao.CompaniesDBDAO;
import com.app.dbdao.CouponsDBDAO;
import com.app.dbdao.CustomersDBDAO;
import com.app.utils.ArtUtil;
import com.app.utils.ConnectionPool;
import com.app.utils.Table100;

/**
 * Tests all the DBDAO Classes and their methods.
 * Table100 - prints table of list or entity.
 * @author θμ μειο
 */
public class DBDAOTesting {

	public static void main(String[] args) {
		DBManager.dropCreateTables();
		System.out.println("Tables were Created!");
		System.out.println("==========================================");
		CompaniesDBDAOTesting();
		CustomersDBDAOTesting();
		CouponsDBDAOTesting();

	}

	public static void CompaniesDBDAOTesting() {
		ArtUtil.companiesDBDAOTesting();
		CompaniesDAO comDBDAO = CompaniesDBDAO.getInstance();
		Company c1 = new Company("Tal", "t@gmail.com", "1324");
		Company c2 = new Company();
		c2.setName("Kobi");
		c2.setEmail("k@gmail.com");
		c2.setPassword("1234");

		System.out.println("===========================ADD COMPANIES===========================");
		comDBDAO.addCompany(c1);
		comDBDAO.addCompany(c2);
		comDBDAO.addCompany(new Company("JB", "jb@gmail.com", "jb123"));
		c1 = comDBDAO.getOneCompany(1);
		c2 = comDBDAO.getOneCompany(2);
		System.out.println();
		Table100.print(comDBDAO.getAllCompanies());
		
		System.out.println("===========================IS COMPANY EXIST===========================");
		System.out.println("Email: h@gmail.com, Pass: 1234? = " + comDBDAO.isCompanyExist("h@gmail.com", "1234"));
		System.out.println("Email: t@gmail.com, Pass: 1324? = " + comDBDAO.isCompanyExist("t@gmail.com", "1324") + "\n");
		
		System.out.println("===========================UPDATE COMPANY NAME===========================");
		c1.setName("Moshe");
		comDBDAO.updateCompany(c1);
		System.out.println();
		Table100.print(comDBDAO.getAllCompanies());
		
		System.out.println("===========================GET ONE COMPANY (Kobi)===========================");
		System.out.println();
		Table100.print(comDBDAO.getOneCompany(c2.getId()));
		
		System.out.println("===========================DELETE COMPANY (Kobi)===========================");
		comDBDAO.deleteCompany(c2.getId());
		System.out.println();
		Table100.print(comDBDAO.getAllCompanies());
		
		System.out.println("===========================GET ALL COMPANIES===========================");
		System.out.println();
		Table100.print(comDBDAO.getAllCompanies());
	}

	public static void CustomersDBDAOTesting() {
		ArtUtil.customersDBDAOTesting();
		CustomersDAO customDBDAO = CustomersDBDAO.getInstance();
		Customer c1 = new Customer("Tal", "Levin", "t@gmail.com", "1324");
		Customer c2 = new Customer("Ofir", "Bennarosh", "o@gmail.com", "1234");

		System.out.println("===========================ADD CUSTOMERS===========================");
		customDBDAO.addCustomer(c1);
		customDBDAO.addCustomer(c2);
		customDBDAO.addCustomer(new Customer("Tom", "Cat", "tomcat@gmail.com", "123456"));
		c1 = customDBDAO.getOneCustomer(1);
		c2 = customDBDAO.getOneCustomer(2);
		System.out.println();
		Table100.print(customDBDAO.getAllCustomers());
		
		System.out.println("===========================IS CUSTOMER EXIST===========================");
		System.out.println("Email: k@gmail.com, Pass: 1234 ? = " + customDBDAO.isCustomerExist("k@gmail.com", "1234"));
		System.out.println("Email: t@gmail.com, Pass: 1324 ? = " + customDBDAO.isCustomerExist("t@gmail.com", "1324"));
		
		System.out.println("===========================UPDATE CUSTOMER NAME===========================");
		c1.setFirstName("Moshe");
		customDBDAO.updateCustomer(c1);
		System.out.println();
		Table100.print(customDBDAO.getAllCustomers());
		
		System.out.println("===========================GET ONE CUSTOMER===========================");
		System.out.println();
		Table100.print(customDBDAO.getOneCustomer(c2.getId()));
		
		System.out.println("===========================DELETE CUSTOMER===========================");
		customDBDAO.deleteCustomer(c2.getId());
		System.out.println();
		Table100.print(customDBDAO.getAllCustomers());
		
		System.out.println("===========================GET ALL CUSTOMERS===========================");
		System.out.println();
		Table100.print(customDBDAO.getAllCustomers());
	}

	public static void CouponsDBDAOTesting() {
		ArtUtil.couponsDBDAOTesting();
		CouponsDAO coupDBDAO = CouponsDBDAO.getInstance();
		Coupon c1 = new Coupon(1, Category.FOOD, "Hamburger", "Free Hamburger", Date.valueOf("2020-08-12"),
				Date.valueOf("2020-08-12"), 10, 99.90, "image.png");
		Coupon c2 = new Coupon(1, Category.RESTAURANT, "BBB", "Free HomeFries", Date.valueOf("2020-08-12"),
				Date.valueOf("2020-08-12"), 10, 89.90, "image.png");
		System.out.println("======================ADD COUPONS======================");
		coupDBDAO.addCoupon(c1);
		coupDBDAO.addCoupon(c2);
		c1 = coupDBDAO.getOneCoupon(1);
		c2 = coupDBDAO.getOneCoupon(2);
		System.out.println();
		Table100.print(coupDBDAO.getAllCoupons());

		System.out.println("======================UPDATE COUPONS PRICE====================");
		c1.setPrice(92.90);
		c2.setPrice(82.90);
		coupDBDAO.updateCoupon(c1);
		coupDBDAO.updateCoupon(c2);
		System.out.println();
		Table100.print(coupDBDAO.getAllCoupons());
		
		System.out.println("======================GET ONE COUPON======================");
		System.out.println();
		Table100.print(coupDBDAO.getOneCoupon(c2.getId()));
		
		System.out.println("======================GET ALL COUPONS======================");
		System.out.println();
		Table100.print(coupDBDAO.getAllCoupons());
		
		System.out.println("======================DELETE COUPON (Hamburger)=====================");
		coupDBDAO.deleteCoupon(c1.getId());
		System.out.println();
		Table100.print(coupDBDAO.getAllCoupons());
		
		System.out.println("======================ADD COUPON PURCHASE======================");
		coupDBDAO.addCouponPurchase(CustomersDBDAO.getInstance().getOneCustomer(1).getId(), c2.getId());
		coupDBDAO.addCouponPurchase(CustomersDBDAO.getInstance().getOneCustomer(3).getId(), c2.getId());
		System.out.println();
		Table100.print(coupDBDAO.getAllCvc());
		
		System.out.println("======================DELETE COUPON PURCHASE=====================");
		coupDBDAO.deleteCouponPurchase(CustomersDBDAO.getInstance().getOneCustomer(1).getId(), c2.getId());
		System.out.println();
		Table100.print(coupDBDAO.getAllCvc());
		try {
			ConnectionPool.getInstance().closeAllConnection();
		} catch (InterruptedException e) {
			System.out.println(e.getMessage());
		}
	}

}
