package com.app.dbdao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.app.beans.Customer;
import com.app.dao.CustomersDAO;
import com.app.utils.ConnectionPool;

/**
 * @author θμ μειο
 * Singleton class with ConnectionPool instance.
 * this class let the program make CRUD actions on the DataBase Customers table.
 */
public class CustomersDBDAO implements CustomersDAO {

	private static CustomersDBDAO instance = null;
	private ConnectionPool connectionPool = ConnectionPool.getInstance();

	private static final String QUERY_GET_ALL = "SELECT * FROM `jb project`.customers;";
	private static final String QUERY_INSERT = "INSERT INTO `jb project`.`customers` (`first_name`, `last_name`, `email`, `password`) VALUES (?, ?, ?, ?);";
	private static final String QUERY_DELETE = "DELETE FROM `jb project`.`customers` WHERE (`id` = ?);";
	private static final String QUERY_UPDATE = "UPDATE `jb project`.`customers` SET `first_name` = ?, `last_name` = ?, `email` = ?, `password` = ? WHERE (`id` = ?);";
	private static final String QUERY_GET_ONE = "SELECT * FROM `jb project`.`customers` WHERE (`id` = ?);";

	private CustomersDBDAO() {

	}

	public static CustomersDBDAO getInstance() {
		if (instance == null) {
			synchronized (CustomersDBDAO.class) {
				if (instance == null) {
					instance = new CustomersDBDAO();
				}
			}
		}
		return instance;
	}

	public boolean isCustomerExist(String email, String password) {
		Connection conn = null;
		try {
			conn = connectionPool.getConnection();
			PreparedStatement statement = conn.prepareStatement(QUERY_GET_ALL);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				if (resultSet.getString(4).equals(email) && resultSet.getString(5).equals(password)) {
					return true;
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			connectionPool.returnConnection(conn);
		}
		return false;
	}

	public void addCustomer(Customer customer) {
		Connection conn = null;
		try {
			conn = connectionPool.getConnection();
			PreparedStatement statement = conn.prepareStatement(QUERY_INSERT);
			statement.setString(1, customer.getFirstName());
			statement.setString(2, customer.getLastName());
			statement.setString(3, customer.getEmail());
			statement.setString(4, customer.getPassword());
			statement.execute();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			connectionPool.returnConnection(conn);
		}

	}

	public void updateCustomer(Customer customer) {
		Connection conn = null;
		try {
			conn = connectionPool.getConnection();
			PreparedStatement statement = conn.prepareStatement(QUERY_UPDATE);
			statement.setString(1, customer.getFirstName());
			statement.setString(2, customer.getLastName());
			statement.setString(3, customer.getEmail());
			statement.setString(4, customer.getPassword());
			statement.setInt(5, customer.getId());
			statement.execute();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			connectionPool.returnConnection(conn);
		}
	}

	public void deleteCustomer(int customerID) {
		Connection conn = null;
		try {
			conn = connectionPool.getConnection();
			PreparedStatement statement = conn.prepareStatement(QUERY_DELETE);
			statement.setInt(1, customerID);
			statement.execute();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			connectionPool.returnConnection(conn);
		}

	}

	public List<Customer> getAllCustomers() {
		List<Customer> customers = new ArrayList<Customer>();
		Connection conn = null;
		try {
			conn = connectionPool.getConnection();
			PreparedStatement statement = conn.prepareStatement(QUERY_GET_ALL);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				int id = resultSet.getInt(1);
				String firstName = resultSet.getString(2);
				String lastName = resultSet.getString(3);
				String email = resultSet.getString(4);
				String password = resultSet.getString(5);
				Customer c1 = new Customer(firstName, lastName, email, password);
				c1.setId(id);
				customers.add(c1);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			connectionPool.returnConnection(conn);
		}
		return customers;
	}

	public Customer getOneCustomer(int customerID) {
		Customer customer = null;
		Connection conn = null;
		try {
			conn = connectionPool.getConnection();
			PreparedStatement statement = conn.prepareStatement(QUERY_GET_ONE);
			statement.setInt(1, customerID);
			ResultSet resultSet = statement.executeQuery();
			resultSet.next();
			String firstName = resultSet.getString(2);
			String lastName = resultSet.getString(3);
			String email = resultSet.getString(4);
			String password = resultSet.getString(5);
			customer = new Customer(firstName, lastName, email, password);
			customer.setId(customerID);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			connectionPool.returnConnection(conn);
		}
		return customer;
	}

}
