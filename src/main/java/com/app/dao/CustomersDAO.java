package com.app.dao;

import java.util.List;

import com.app.beans.Customer;

public interface CustomersDAO {

	boolean isCustomerExist(String email, String password);

	void addCustomer(Customer customer);

	void updateCustomer(Customer customer);

	void deleteCustomer(int customerID);

	List<Customer> getAllCustomers();

	Customer getOneCustomer(int customerID);

}
