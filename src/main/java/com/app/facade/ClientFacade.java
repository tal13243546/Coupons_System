package com.app.facade;

import com.app.dao.CompaniesDAO;
import com.app.dao.CouponsDAO;
import com.app.dao.CustomersDAO;
import com.app.dbdao.CompaniesDBDAO;
import com.app.dbdao.CouponsDBDAO;
import com.app.dbdao.CustomersDBDAO;

public abstract class ClientFacade {

	protected CompaniesDAO companiesDAO;
	protected CouponsDAO couponsDAO;
	protected CustomersDAO customersDAO;

	public ClientFacade() {
		this.companiesDAO = CompaniesDBDAO.getInstance();
		this.couponsDAO = CouponsDBDAO.getInstance();
		this.customersDAO = CustomersDBDAO.getInstance();
	}

	public CompaniesDAO getCompaniesDAO() {
		return companiesDAO;
	}

	public CouponsDAO getCouponsDAO() {
		return couponsDAO;
	}

	public CustomersDAO getCustomersDAO() {
		return customersDAO;
	}

	public abstract boolean login(String email, String password) throws Exception;

}
