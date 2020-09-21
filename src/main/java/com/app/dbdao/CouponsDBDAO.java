package com.app.dbdao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.app.beans.Category;
import com.app.beans.Coupon;
import com.app.beans.CustomerVsCoupon;
import com.app.dao.CouponsDAO;
import com.app.utils.ConnectionPool;

/**
 * @author θμ μειο
 * Singleton class with ConnectionPool instance.
 * this class let the program make CRUD actions on the DataBase Coupons and Customers_Vs_Coupons tables.
 */
public class CouponsDBDAO implements CouponsDAO {

	private static CouponsDBDAO instance = null;
	private ConnectionPool connectionPool = ConnectionPool.getInstance();

	private static final String QUERY_GET_ALL = "SELECT * FROM `jb project`.coupons;";
	private static final String QUERY_INSERT = "INSERT INTO `jb project`.`coupons` (`company_id`, `category_id`, `title`, `description`, `start_date`, `end_date`, `amount`, `price`, `image`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
	private static final String QUERY_DELETE = "DELETE FROM `jb project`.`coupons` WHERE (`id` = ?);";
	private static final String QUERY_UPDATE = "UPDATE `jb project`.`coupons` SET `category_id` = ?, `title` = ?, `description` = ?, `start_date` = ?, `end_date` = ?, `amount` = ?, `price` = ?, `image` = ? WHERE (`id` = ?);";
	private static final String QUERY_GET_ONE = "SELECT * FROM `jb project`.coupons WHERE (`id` = ?);";
	private static final String QUERY_GET_ONE_BY_TITLE_AND_comID = "SELECT * FROM `jb project`.coupons WHERE (`title` = ?) AND (`company_id` = ?);";

	private CouponsDBDAO() {
		
	}

	public static CouponsDBDAO getInstance() {
		if (instance == null) {
			synchronized (CouponsDBDAO.class) {
				if (instance == null) {
					instance = new CouponsDBDAO();
				}
			}
		}
		return instance;
	}

	public void addCoupon(Coupon coupon) {
		Connection conn = null;
		try {
			conn = connectionPool.getConnection();
			PreparedStatement statement = conn.prepareStatement(QUERY_INSERT);
			statement.setInt(1, coupon.getCompanyId());

			if (coupon.getCategory() == Category.FOOD) {
				statement.setInt(2, Category.FOOD.ordinal() + 1);
			} else if (coupon.getCategory() == Category.ELECTRICITY) {
				statement.setInt(2, Category.ELECTRICITY.ordinal() + 1);
			} else if (coupon.getCategory() == Category.RESTAURANT) {
				statement.setInt(2, Category.RESTAURANT.ordinal() + 1);
			} else {
				statement.setInt(2, Category.VACATION.ordinal() + 1);
			}
			statement.setString(3, coupon.getTitle());
			statement.setString(4, coupon.getDescription());
			statement.setDate(5, coupon.getStartDate());
			statement.setDate(6, coupon.getEndDate());
			statement.setInt(7, coupon.getAmount());
			statement.setDouble(8, coupon.getPrice());
			statement.setString(9, coupon.getImage());
			statement.execute();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			connectionPool.returnConnection(conn);
		}
	}

	public void updateCoupon(Coupon coupon) {
		Connection conn = null;
		try {
			conn = connectionPool.getConnection();
			PreparedStatement statement = conn.prepareStatement(QUERY_UPDATE);

			if (coupon.getCategory() == Category.FOOD) {
				statement.setInt(1, Category.FOOD.ordinal() + 1);
			} else if (coupon.getCategory() == Category.ELECTRICITY) {
				statement.setInt(1, Category.ELECTRICITY.ordinal() + 1);
			} else if (coupon.getCategory() == Category.RESTAURANT) {
				statement.setInt(1, Category.RESTAURANT.ordinal() + 1);
			} else {
				statement.setInt(1, Category.VACATION.ordinal() + 1);
			}
			statement.setString(2, coupon.getTitle());
			statement.setString(3, coupon.getDescription());
			statement.setDate(4, coupon.getStartDate());
			statement.setDate(5, coupon.getEndDate());
			statement.setInt(6, coupon.getAmount());
			statement.setDouble(7, coupon.getPrice());
			statement.setString(8, coupon.getImage());
			statement.setInt(9, coupon.getId());
			statement.execute();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			connectionPool.returnConnection(conn);
		}
	}

	public void deleteCoupon(int couponID) {
		Connection conn = null;
		try {
			conn = connectionPool.getConnection();
			PreparedStatement statement = conn.prepareStatement(QUERY_DELETE);
			statement.setInt(1, couponID);
			statement.execute();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			connectionPool.returnConnection(conn);
		}
	}

	public List<Coupon> getAllCoupons() {
		List<Coupon> coupons = new ArrayList<Coupon>();
		Connection conn = null;
		try {
			conn = connectionPool.getConnection();
			PreparedStatement statement = conn.prepareStatement(QUERY_GET_ALL);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				int id = resultSet.getInt(1);
				int companyId = resultSet.getInt(2);
				int categoryId = resultSet.getInt(3);
				Category category = null;
				if (categoryId == 1) {
					category = Category.FOOD;
				} else if (categoryId == 2) {
					category = Category.ELECTRICITY;
				} else if (categoryId == 3) {
					category = Category.RESTAURANT;
				} else {
					category = Category.VACATION;
				}
				String title = resultSet.getString(4);
				String description = resultSet.getString(5);
				Date startDate = resultSet.getDate(6);
				Date endDate = resultSet.getDate(7);
				int amount = resultSet.getInt(8);
				double price = resultSet.getDouble(9);
				String image = resultSet.getString(10);
				Coupon c1 = new Coupon(companyId, category, title, description, startDate, endDate, amount, price,
						image);
				c1.setId(id);
				coupons.add(c1);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			connectionPool.returnConnection(conn);
		}
		return coupons;
	}

	public Coupon getOneCoupon(int couponID) {
		Coupon c1 = null;
		Connection conn = null;
		try {
			conn = connectionPool.getConnection();
			PreparedStatement statement = conn.prepareStatement(QUERY_GET_ONE);
			statement.setInt(1, couponID);
			ResultSet resultSet = statement.executeQuery();
			resultSet.next();
			int id = resultSet.getInt(1);
			int companyId = resultSet.getInt(2);
			int categoryId = resultSet.getInt(3);
			Category category = null;
			if (categoryId == 1) {
				category = Category.FOOD;
			} else if (categoryId == 2) {
				category = Category.ELECTRICITY;
			} else if (categoryId == 3) {
				category = Category.RESTAURANT;
			} else {
				category = Category.VACATION;
			}
			String title = resultSet.getString(4);
			String description = resultSet.getString(5);
			Date startDate = resultSet.getDate(6);
			Date endDate = resultSet.getDate(7);
			int amount = resultSet.getInt(8);
			double price = resultSet.getDouble(9);
			String image = resultSet.getString(10);
			c1 = new Coupon(companyId, category, title, description, startDate, endDate, amount, price, image);
			c1.setId(id);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			connectionPool.returnConnection(conn);
		}
		return c1;
	}

	public Coupon getOneCouponByTitleAndComID(String title, int companyID) {
		Coupon c1 = null;
		Connection conn = null;
		try {
			conn = connectionPool.getConnection();
			PreparedStatement statement = conn.prepareStatement(QUERY_GET_ONE_BY_TITLE_AND_comID);
			statement.setString(1, title);
			statement.setInt(2, companyID);
			ResultSet resultSet = statement.executeQuery();
			resultSet.next();
			int id = resultSet.getInt(1);
			int categoryId = resultSet.getInt(3);
			Category category = null;
			if (categoryId == 1) {
				category = Category.FOOD;
			} else if (categoryId == 2) {
				category = Category.ELECTRICITY;
			} else if (categoryId == 3) {
				category = Category.RESTAURANT;
			} else {
				category = Category.VACATION;
			}
			String description = resultSet.getString(5);
			Date startDate = resultSet.getDate(6);
			Date endDate = resultSet.getDate(7);
			int amount = resultSet.getInt(8);
			double price = resultSet.getDouble(9);
			String image = resultSet.getString(10);
			c1 = new Coupon(companyID, category, title, description, startDate, endDate, amount, price, image);
			c1.setId(id);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			connectionPool.returnConnection(conn);
		}
		return c1;
	}

	// cvc= customer_vs_coupons
	private static final String QUERY_INSERT_INTO_CVC = "INSERT INTO `jb project`.`customers_vs_coupons` (`customer_id`, `coupon_id`) VALUES (?, ?);";
	private static final String QUERY_DELETE_FROM_CVC = "DELETE FROM `jb project`.`customers_vs_coupons` WHERE (`customer_id` = ?) AND (coupon_id = ?);";
	private static final String QUERY_DELETE_FROM_CVC_BY_COUPON = "DELETE FROM `jb project`.`customers_vs_coupons` WHERE (`coupon_id` = ?);";
	private static final String QUERY_DELETE_FROM_CVC_BY_CUSTOMER = "DELETE FROM `jb project`.`customers_vs_coupons` WHERE (`customer_id` = ?);";
	private static final String QUERY_GET_FROM_CVC_BY_CUSTOMER = "SELECT * FROM `jb project`.`customers_vs_coupons` WHERE (`customer_id` = ?);";
	private static final String QUERY_GET_ALL_CVC = "SELECT * FROM `jb project`.`customers_vs_coupons`;";

	public void addCouponPurchase(int customerID, int couponID) {
		Connection conn = null;
		try {
			conn = connectionPool.getConnection();
			PreparedStatement statement = conn.prepareStatement(QUERY_INSERT_INTO_CVC);
			statement.setInt(1, customerID);
			statement.setInt(2, couponID);
			statement.execute();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			connectionPool.returnConnection(conn);
		}
	}

	public void deleteCouponPurchase(int customerID, int couponID) {
		Connection conn = null;
		try {
			conn = connectionPool.getConnection();
			PreparedStatement statement = conn.prepareStatement(QUERY_DELETE_FROM_CVC);
			statement.setInt(1, customerID);
			statement.setInt(2, couponID);
			statement.execute();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			connectionPool.returnConnection(conn);
		}
	}

	public void deleteCouponPurchaseByCoupon(int couponID) {
		Connection conn = null;
		try {
			conn = connectionPool.getConnection();
			PreparedStatement statement = conn.prepareStatement(QUERY_DELETE_FROM_CVC_BY_COUPON);
			statement.setInt(1, couponID);
			statement.execute();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			connectionPool.returnConnection(conn);
		}
	}

	public void deleteCouponPurchaseByCustomer(int customerID) {
		Connection conn = null;
		try {
			conn = connectionPool.getConnection();
			PreparedStatement statement = conn.prepareStatement(QUERY_DELETE_FROM_CVC_BY_CUSTOMER);
			statement.setInt(1, customerID);
			statement.execute();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			connectionPool.returnConnection(conn);
		}
	}

	public List<CustomerVsCoupon> getCvcByCustomer(int customerID) {
		List<CustomerVsCoupon> cvc = new ArrayList<CustomerVsCoupon>();
		Connection conn = null;
		try {
			conn = connectionPool.getConnection();
			PreparedStatement statement = conn.prepareStatement(QUERY_GET_FROM_CVC_BY_CUSTOMER);
			statement.setInt(1, customerID);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				int couponID = resultSet.getInt(2);
				cvc.add(new CustomerVsCoupon(customerID, couponID));
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			connectionPool.returnConnection(conn);
		}
		return cvc;
	}

	public List<CustomerVsCoupon> getAllCvc() {
		List<CustomerVsCoupon> cvc = new ArrayList<CustomerVsCoupon>();
		Connection conn = null;
		try {
			conn = connectionPool.getConnection();
			PreparedStatement statement = conn.prepareStatement(QUERY_GET_ALL_CVC);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				int customerID = resultSet.getInt(1);
				int couponID = resultSet.getInt(2);
				cvc.add(new CustomerVsCoupon(customerID, couponID));
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			connectionPool.returnConnection(conn);
		}
		return cvc;
	}
}
