package com.app.dailyJobs;

import java.sql.Date;
import java.time.LocalDate;

import com.app.beans.Coupon;
import com.app.dao.CouponsDAO;
import com.app.dbdao.CouponsDBDAO;
import com.app.facade.CompanyFacade;

/**
 * @author θμ μειο
 * A daily thread that delete expired coupons.
 * It has a quit attribute which indicates when to stop the thread.
 */
public class CouponExpirationDailyJob implements Runnable {

	private CouponsDAO couponsDAO;
	private boolean quit;

	public CouponExpirationDailyJob() {
		this.couponsDAO = CouponsDBDAO.getInstance();
		this.quit = false;
	}
	
	public boolean isQuit() {
		return quit;
	}

	public void run() {
//		int roundCounter = 0;
		do {
			boolean couponsDeleted = false;
			for (Coupon coupon : couponsDAO.getAllCoupons()) {
				if (coupon.getEndDate().before(Date.valueOf(LocalDate.now()))) {
					CompanyFacade companyFacade = new CompanyFacade();
					companyFacade.deleteCoupon(coupon.getId());
					System.out.println("Expired coupon deleted!");
					couponsDeleted = true;
				}
			}
			if (!couponsDeleted) {
				System.out.println("No expired coupon found..");
			}
//			roundCounter++;
			try {
				for (int i = 0; i < 30 && !quit; i++) {
					Thread.sleep(1000);
				}
			} catch (InterruptedException e) {
				System.out.println(e.getMessage());
			}
//			if (roundCounter == 3) {
//				Table100.print(couponsDAO.getAllCoupons());
//				stop();
//				try {
//					ConnectionPool.getInstance().closeAllConnection();
//					System.out.println("Connection closed!");
//				} catch (InterruptedException e) {
//					System.out.println(e.getMessage());
//				}
//			}
		} while (!quit);
	}

	public void stop() { // Stops the run method.
		this.quit = !quit;
	}

}
