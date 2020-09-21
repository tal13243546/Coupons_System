package com.app.dao;

import java.util.List;

import com.app.beans.Company;

public interface CompaniesDAO {

	boolean isCompanyExist(String email, String password);

	void addCompany(Company company);

	void updateCompany(Company company);

	void deleteCompany(int companyID);

	List<Company> getAllCompanies();

	Company getOneCompany(int companyID);

}
