package com.app.clr.login;

import com.app.beans.ClientType;
import com.app.facade.AdminFacade;
import com.app.facade.CompanyFacade;
import com.app.facade.CustomerFacade;
import com.app.login.LoginManager;

/**
 * Tests the Login Class by client type.
 * @author θμ μειο
 *
 */
public class LoginTest {

	public static AdminFacade adminLog() {
		AdminFacade adminFacade = null;
		try {
			adminFacade = (AdminFacade) LoginManager.login("admin@admin.com", "admin", ClientType.ADMINISTRATOR);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		if (adminFacade != null) {
			System.out.println();
			System.out.println("Admin Login Succesfully!");
			System.out.println();
			return adminFacade;
		} else {
			return null;
		}
	}

	public static CompanyFacade companyLog() {
		CompanyFacade companyFacade = null;
		try {
			companyFacade = (CompanyFacade) LoginManager.login("jb@gmail.com", "jb123", ClientType.COMPANY);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		if (companyFacade != null) {
			System.out.println(companyFacade.getCompanyDetails().getName() + " Company Login Succesfully!");
			System.out.println();
			return companyFacade;
		} else {
			return null;
		}
	}

	public static CustomerFacade customerLog() {
		CustomerFacade customerFacade = null;
		try {
			customerFacade = (CustomerFacade) LoginManager.login("tl@gmail.com", "tl123", ClientType.CUSTOMER);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		if (customerFacade != null) {
			System.out.println(customerFacade.getCustomerDetails().getFirstName() + " Login Succesfully!");
			System.out.println();
			return customerFacade;
		} else {
			return null;
		}
	}
}
