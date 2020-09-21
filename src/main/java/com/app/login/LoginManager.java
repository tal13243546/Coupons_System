package com.app.login;

import com.app.beans.ClientType;
import com.app.facade.AdminFacade;
import com.app.facade.ClientFacade;
import com.app.facade.CompanyFacade;
import com.app.facade.CustomerFacade;

public class LoginManager {

	/**
	 * Singleton class which check the login method for each ClientType. 
	 */
	private static LoginManager instance = null;

	private LoginManager() {

	}

	public static ClientFacade login(String email, String password, ClientType cType) throws Exception {
		if (cType.equals(ClientType.ADMINISTRATOR)) {
			AdminFacade adminFacade = new AdminFacade();
			boolean login = adminFacade.login(email, password); // login = true -> If succeed to login.
			if (login) { 
				return adminFacade;
			}
		} else if (cType.equals(ClientType.COMPANY)) {
			CompanyFacade companyFacade = new CompanyFacade();
			boolean login = companyFacade.login(email, password); // login = true -> If succeed to login.
			if (login) {
				return companyFacade;
			}
		} else if (cType.equals(ClientType.CUSTOMER)) {
			CustomerFacade customerFacade = new CustomerFacade();
			boolean login = customerFacade.login(email, password); // login = true -> If succeed to login.
			if (login) {
				return customerFacade;
			}
		}
		return null;
	}

	public static LoginManager getInstance() {
		if (instance == null) {
			synchronized (LoginManager.class) {
				if (instance == null) {
					instance = new LoginManager();
				}
			}
		}
		return instance;
	}

}
