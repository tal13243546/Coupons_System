package com.app.main;

import com.app.clr.facade.FacadeTesting;
import com.app.clr.login.LoginTest;
import com.app.dailyJobs.CouponExpirationDailyJob;
import com.app.db.DBManager;
import com.app.dbdao.CouponsDBDAO;
import com.app.facade.AdminFacade;
import com.app.facade.CompanyFacade;
import com.app.facade.CustomerFacade;
import com.app.utils.ConnectionPool;
import com.app.utils.Table100;

public class Test {

	public static void testAll() {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			DBManager.dropCreateTables();
			System.out.println("Tables were Created!");
			System.out.println("==========================================");
			CouponExpirationDailyJob dailyJob = new CouponExpirationDailyJob();
			Thread t1 = new Thread(dailyJob);
			t1.start();

			AdminFacade admin = LoginTest.adminLog();
			if (admin != null) {
				FacadeTesting.adminFacadeTesting(admin);
			}
			CompanyFacade company = LoginTest.companyLog();
			if (company != null || admin != null) {
				FacadeTesting.companyFacadeTesting(company);
			}
			CustomerFacade customer = LoginTest.customerLog();
			if (customer != null || company != null || admin != null) {
				FacadeTesting.customerFacadeTesting(customer);
			}

			Thread.sleep(1000 * 61);
			Table100.print(CouponsDBDAO.getInstance().getAllCoupons());
			dailyJob.stop();
			ConnectionPool.getInstance().closeAllConnection();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
