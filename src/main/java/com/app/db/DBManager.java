package com.app.db;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.app.beans.Category;
import com.app.utils.ConnectionPool;

public class DBManager {

//	private static ConnectionPool connectionPool = ConnectionPool.getInstance();

	private static final String URL = "jdbc:mysql://localhost:3306/jb project?createDatabaseIfNotExist=TRUE&useTimezone=TRUE&serverTimezone=Asia/Jerusalem";
	private static final String USER = "root";
	private static final String PASS = "132435";

	private static final String CREATE_TABLE_COMPANIES = "CREATE TABLE `jb project`.`companies` (\r\n"
			+ "  `id` INT NOT NULL AUTO_INCREMENT,\r\n" + "  `name` VARCHAR(45) NOT NULL,\r\n"
			+ "  `email` VARCHAR(45) NOT NULL,\r\n" + "  `password` VARCHAR(45) NOT NULL,\r\n"
			+ "  PRIMARY KEY (`id`));";

	private static final String CREATE_TABLE_CUSTOMERS = "CREATE TABLE `jb project`.`customers` (\r\n"
			+ "  `id` INT NOT NULL AUTO_INCREMENT,\r\n" + "  `first_name` VARCHAR(45) NOT NULL,\r\n"
			+ "  `last_name` VARCHAR(45) NOT NULL,\r\n" + "  `email` VARCHAR(45) NOT NULL,\r\n"
			+ "  `password` VARCHAR(45) NOT NULL,\r\n" + "  PRIMARY KEY (`id`));";

	private static final String CREATE_TABLE_CATEGORIES = "\r\n" + "CREATE TABLE `jb project`.`categories` (\r\n"
			+ "  `id` INT NOT NULL AUTO_INCREMENT,\r\n" + "  `name` VARCHAR(45) NOT NULL,\r\n"
			+ "  PRIMARY KEY (`id`));";

	private static final String CREATE_TABLE_COUPONS = "CREATE TABLE `jb project`.`coupons` (\r\n"
			+ "  `id` INT NOT NULL AUTO_INCREMENT,\r\n" + "  `company_id` INT NOT NULL,\r\n"
			+ "  `category_id` INT NOT NULL,\r\n" + "  `title` VARCHAR(45) NOT NULL,\r\n"
			+ "  `description` VARCHAR(45) NOT NULL,\r\n" + "  `start_date` DATE NOT NULL,\r\n"
			+ "  `end_date` DATE NOT NULL,\r\n" + "  `amount` INT NOT NULL,\r\n" + "  `price` DOUBLE NOT NULL,\r\n"
			+ "  `image` VARCHAR(45) NOT NULL,\r\n" + "  PRIMARY KEY (`id`),\r\n"
			+ "  INDEX `company_id_idx` (`company_id` ASC) VISIBLE,\r\n"
			+ "  INDEX `category_id_idx` (`category_id` ASC) VISIBLE,\r\n" + "  CONSTRAINT `company_id`\r\n"
			+ "    FOREIGN KEY (`company_id`)\r\n" + "    REFERENCES `jb project`.`companies` (`id`)\r\n"
			+ "    ON DELETE NO ACTION\r\n" + "    ON UPDATE NO ACTION,\r\n" + "  CONSTRAINT `category_id`\r\n"
			+ "    FOREIGN KEY (`category_id`)\r\n" + "    REFERENCES `jb project`.`categories` (`id`)\r\n"
			+ "    ON DELETE NO ACTION\r\n" + "    ON UPDATE NO ACTION);";

	private static final String CREATE_TABLE_CUSTOMERS_VS_COUPONS = "CREATE TABLE `jb project`.`customers_vs_coupons` (\r\n"
			+ "  `customer_id` INT NOT NULL,\r\n" + "  `coupon_id` INT NOT NULL,\r\n"
			+ "  PRIMARY KEY (`customer_id`, `coupon_id`),\r\n"
			+ "  INDEX `coupon_id_idx` (`coupon_id` ASC) VISIBLE,\r\n" + "  CONSTRAINT `customer_id`\r\n"
			+ "    FOREIGN KEY (`customer_id`)\r\n" + "    REFERENCES `jb project`.`customers` (`id`)\r\n"
			+ "    ON DELETE NO ACTION\r\n" + "    ON UPDATE NO ACTION,\r\n" + "  CONSTRAINT `coupon_id`\r\n"
			+ "    FOREIGN KEY (`coupon_id`)\r\n" + "    REFERENCES `jb project`.`coupons` (`id`)\r\n"
			+ "    ON DELETE NO ACTION\r\n" + "    ON UPDATE NO ACTION);";

	private static final String DROP_TABLE_COMPANIES = "DROP TABLE `jb project`.`companies`;";

	private static final String DROP_TABLE_CUSTOMERS = "DROP TABLE `jb project`.`customers`;";

	private static final String DROP_TABLE_CATEGORIES = "DROP TABLE `jb project`.`categories`;";

	private static final String DROP_TABLE_COUPONS = "DROP TABLE `jb project`.`coupons`;";

	private static final String DROP_TABLE_CUSTOMERS_VS_COUPONS = "DROP TABLE `jb project`.`customers_vs_coupons`;";

	private static final String INSERT_CATEGORY = "INSERT INTO `jb project`.`categories` (`name`) VALUES (?);";

	public static void insertCategory(Category category) {
		Connection conn = null;
		try {
			conn = ConnectionPool.getInstance().getConnection();
			PreparedStatement statement = conn.prepareStatement(INSERT_CATEGORY);
			statement.setString(1, category.toString());
			statement.execute();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			ConnectionPool.getInstance().returnConnection(conn);
		}
	}

	public static void runQuery(String query) {
		Connection conn = null;
		try {
			conn = ConnectionPool.getInstance().getConnection();
			PreparedStatement statement = conn.prepareStatement(query);
			statement.execute();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			ConnectionPool.getInstance().returnConnection(conn);
		}
	}

	public static void createTables() {
		runQuery(CREATE_TABLE_COMPANIES);
		runQuery(CREATE_TABLE_CUSTOMERS);
		runQuery(CREATE_TABLE_CATEGORIES);
		runQuery(CREATE_TABLE_COUPONS);
		runQuery(CREATE_TABLE_CUSTOMERS_VS_COUPONS);
		insertCategory(Category.FOOD);
		insertCategory(Category.ELECTRICITY);
		insertCategory(Category.RESTAURANT);
		insertCategory(Category.VACATION);
	}

	public static void dropTables() {
		runQuery(DROP_TABLE_CUSTOMERS_VS_COUPONS);
		runQuery(DROP_TABLE_COUPONS);
		runQuery(DROP_TABLE_CUSTOMERS);
		runQuery(DROP_TABLE_COMPANIES);
		runQuery(DROP_TABLE_CATEGORIES);

	}

	public static void dropCreateTables() {
		dropTables();
		createTables();
	}

	public static String getUrl() {
		return URL;
	}

	public static String getUser() {
		return USER;
	}

	public static String getPass() {
		return PASS;
	}

}
