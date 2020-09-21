package com.app.facade;

import java.util.List;

import javax.security.auth.login.LoginException;

import com.app.beans.Company;
import com.app.beans.Coupon;
import com.app.beans.Customer;
import com.app.exceptions.AddCompanyException;
import com.app.exceptions.AddCustomerException;
import com.app.exceptions.UpdateCompanyException;

/**
 * @author θμ μειο 
 * Gets DAO attributes from the ClientFacade which are initialized with DBDAO instances. 
 * This class is responsible for the logic of the administrator actions.
 */
public class AdminFacade extends ClientFacade {

	public AdminFacade() {
		super(); // initialize the DBDAO instances in the ClientFacade.
	}

	@Override
	public boolean login(String email, String password) throws LoginException {
		if (email.equals("admin@admin.com") && password.equals("admin")) {
			return true;
		} else {
			throw new LoginException("Login Details Were Wrong, Please Try Again.");
		}
	}

	public void addCompany(Company company) throws AddCompanyException {

		for (Company company2 : companiesDAO.getAllCompanies()) {
			if (company.getName().equalsIgnoreCase(company2.getName())
					|| company.getEmail().equals(company2.getEmail())) { // Name or Email cant be the same!
				throw new AddCompanyException();
			}
		}
			companiesDAO.addCompany(company);
	}

	public void updateCompany(Company company) throws UpdateCompanyException {
		boolean canUpdate = false;
		for (Company company2 : companiesDAO.getAllCompanies()) {
			if (company.getId() == company2.getId() && company.getName().equals(company2.getName())) { // can't update
																										// company name
				canUpdate = true;
			}
		}
		if (canUpdate) {
			companiesDAO.updateCompany(company);
		} else {
			throw new UpdateCompanyException();
		}
	}

	public void deleteCompany(int companyID) {
		for (Coupon coupon : couponsDAO.getAllCoupons()) {
			if (coupon.getCompanyId() == companyID) {
				couponsDAO.deleteCouponPurchaseByCoupon(coupon.getId()); // delete coupon records from
																			// Customers_Vs_Coupons

				couponsDAO.deleteCoupon(coupon.getId()); // delete coupon from the data base
			}
		}
		companiesDAO.deleteCompany(companyID); // delete company from the data base
	}

	public List<Company> getAllCompanies() {
		List<Company> companies = companiesDAO.getAllCompanies();
		for (Company company : companies) {
			company.setCoupons(); //initialize each company coupons list.
		}
		return companies;
	}

	public Company getOneCompany(int companyID) {
		Company company = companiesDAO.getOneCompany(companyID);
		company.setCoupons(); //initialize company coupons list.
		return company;
	}

	public void addCustomer(Customer customer) throws AddCustomerException {

		for (Customer customer2 : customersDAO.getAllCustomers()) {
			if (customer2.getEmail().equals(customer.getEmail())) { // Email can't be the same!
				throw new AddCustomerException();
			}
		}
			customersDAO.addCustomer(customer);
	}

	public void updateCustomer(Customer customer) {
		customersDAO.updateCustomer(customer);
	}

	public void deleteCustomer(int customerID) {
		couponsDAO.deleteCouponPurchaseByCustomer(customerID);
		customersDAO.deleteCustomer(customerID);
	}

	public List<Customer> getAllCustomers() {
		List<Customer> customers = customersDAO.getAllCustomers();
		for (Customer customer : customers) {
			customer.setCoupons(); //initialize each customer coupons list.
		}
		return customers;
	}

	public Customer getOneCustomer(int customerID) {
		Customer customer = customersDAO.getOneCustomer(customerID);
		customer.setCoupons(); //initialize customer coupons list.
		return customer;
	}

}
