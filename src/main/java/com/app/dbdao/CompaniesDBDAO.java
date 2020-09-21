package com.app.dbdao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.app.beans.Company;
import com.app.dao.CompaniesDAO;
import com.app.utils.ConnectionPool;

/**
 * @author θμ μειο
 * Singleton class with ConnectionPool instance.
 * this class let the program make CRUD actions on the DataBase Companies table.
 */
public class CompaniesDBDAO implements CompaniesDAO {

	private static CompaniesDBDAO instance = null;
	private ConnectionPool connectionPool = ConnectionPool.getInstance();

	private static final String QUERY_GET_ALL = "SELECT * FROM `jb project`.companies;";
	private static final String QUERY_INSERT = "INSERT INTO `jb project`.`companies` (`name`, `email`, `password`) VALUES (?, ?, ?);";
	private static final String QUERY_DELETE = "DELETE FROM `jb project`.`companies` WHERE (`id` = ?);";
	private static final String QUERY_UPDATE = "UPDATE `jb project`.`companies` SET `name` = ?, `email` = ?, `password` = ? WHERE (`id` = ?);";
	private static final String QUERY_GET_ONE = "SELECT * FROM `jb project`.companies WHERE (`id` = ?);";

	private CompaniesDBDAO() {
	
	}

	public static CompaniesDBDAO getInstance() {
		if (instance == null) {
			synchronized (CompaniesDBDAO.class) {
				if (instance == null) {
					instance = new CompaniesDBDAO();
				}
			}
		}
		return instance;
	}

	public boolean isCompanyExist(String email, String password) {
		Connection conn = null;
		try {
			conn = connectionPool.getConnection();
			PreparedStatement statement = conn.prepareStatement(QUERY_GET_ALL);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				if (resultSet.getString(3).equals(email) && resultSet.getString(4).equals(password)) {
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

	public void addCompany(Company company) {
		Connection conn = null;
		try {
			conn = connectionPool.getConnection();
			PreparedStatement statement = conn.prepareStatement(QUERY_INSERT);
			statement.setString(1, company.getName());
			statement.setString(2, company.getEmail());
			statement.setString(3, company.getPassword());
			statement.execute();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			connectionPool.returnConnection(conn);
		}
	}

	public void updateCompany(Company company) {
		Connection conn = null;
		try {
			conn = connectionPool.getConnection();
			PreparedStatement statement = conn.prepareStatement(QUERY_UPDATE);
			statement.setString(1, company.getName());
			statement.setString(2, company.getEmail());
			statement.setString(3, company.getPassword());
			statement.setInt(4, company.getId());
			statement.execute();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			connectionPool.returnConnection(conn);
		}
	}

	public void deleteCompany(int companyID) {
		Connection conn = null;
		try {
			conn = connectionPool.getConnection();
			PreparedStatement statement = conn.prepareStatement(QUERY_DELETE);
			statement.setInt(1, companyID);
			statement.execute();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			connectionPool.returnConnection(conn);
		}
	}

	public List<Company> getAllCompanies() {
		List<Company> companies = new ArrayList<Company>();
		Connection conn = null;
		try {
			conn = connectionPool.getConnection();
			PreparedStatement statement = conn.prepareStatement(QUERY_GET_ALL);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				int id = resultSet.getInt(1);
				String name = resultSet.getString(2);
				String email = resultSet.getString(3);
				String password = resultSet.getString(4);
				Company c1 = new Company(name, email, password);
				c1.setId(id);
				companies.add(c1);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			connectionPool.returnConnection(conn);
		}
		return companies;
	}

	public Company getOneCompany(int companyID) {
		Company company = null;
		Connection conn = null;
		try {
			conn = connectionPool.getConnection();
			PreparedStatement statement = conn.prepareStatement(QUERY_GET_ONE);
			statement.setInt(1, companyID);
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next()) {
				throw new Exception("There are no companies with this id.");
			}
			String name = resultSet.getString(2);
			String email = resultSet.getString(3);
			String password = resultSet.getString(4);
			company = new Company(name, email, password);
			company.setId(companyID);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			connectionPool.returnConnection(conn);
		}
		return company;
	}

}
