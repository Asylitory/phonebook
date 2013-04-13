package phonebook.logic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import phonebook.logic.interfaces.IPhonebook;

public class Phonebook implements IPhonebook {
	private static Phonebook instance;
	private static Connection connection;
	private static DataSource dataSource;
	
	private Phonebook(){}
	
	public static synchronized Phonebook getInstance() {
		if (null == instance) {
			try {
				instance = new Phonebook();
				Context context = new InitialContext();
				dataSource = (DataSource) context.lookup("java:comp/env/jdbc/PhonebookDS");
				connection = dataSource.getConnection();
			} catch (SQLException sql_e) {
				sql_e.printStackTrace();
			} catch (NamingException n_e) {
				n_e.printStackTrace();
			}
		}
		return instance;
	}

	public List<Person> getAllPersons() throws SQLException {
		List<Person> persons = new ArrayList<Person>();
		
		Statement stmt = null;
		ResultSet rs = null;
			
		try {
			connection.createStatement().execute("SET NAMES cp1251");
			stmt = connection.createStatement();
			rs = stmt.executeQuery(
					"SELECT id, lastname, firstname, patronymic, " +
					"phone_mobile, phone_home, address, email " +
					"FROM person");
			
			while (rs.next()) {
				Person person = new Person();
				
				person.setId(rs.getInt(1));
				person.setLastname(new String(rs.getString(2)));
				person.setFirstname(rs.getString(3));
				person.setPatronymic(rs.getString(4));
				person.setPhone_mobile(rs.getString(5));
				person.setPhone_home(rs.getString(6));
				person.setAddress(rs.getString(7));
				person.setEmail(rs.getString(8));
				
				persons.add(person);
			}
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (stmt != null) {
				stmt.close();
			}
		}

		return persons;
	}

	public Person getPersonById(int personId) throws SQLException {
		Person person = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = connection.prepareStatement(
					"SELECT id, lastname, firstname, patronymic, " +
					"phone_mobile, phone_home, address, email " +
					"FROM person " +
					"WHERE id=?");
			stmt.setInt(1, personId);
			
			rs = stmt.executeQuery();
			
			if (rs.next()) {
				person = new Person();
				
				person.setId(rs.getInt(1));
				person.setLastname(rs.getString(2));
				person.setFirstname(rs.getString(3));
				person.setPatronymic(rs.getString(4));
				person.setPhone_mobile(rs.getString(5));
				person.setPhone_home(rs.getString(6));
				person.setAddress(rs.getString(7));
				person.setEmail(rs.getString(8));
			}
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (stmt != null) {
				stmt.close();
			}
		}
		return person;
	}

	public List<String> insertPerson(Person person) throws SQLException {
		PreparedStatement stmt = null;
		
		try {
			stmt = connection.prepareStatement(
					"INSERT INTO person " + 
					"(lastname, firstname, patronymic, " + 
					"phone_mobile, phone_home, address, email) " +
					"VALUES (?, ?, ?, ?, ?, ?, ?)");
			
			stmt.setString(1, person.getLastname());
			stmt.setString(2, person.getFirstname());
			stmt.setString(3, person.getPatronymic());
			stmt.setString(4, person.getPhone_mobile());
			stmt.setString(5, person.getPhone_home());
			stmt.setString(6, person.getAddress());
			stmt.setString(7, person.getEmail());
			
			stmt.execute();
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		return null;
	}

	public List<String> updatePerson(Person person) throws SQLException {
		PreparedStatement stmt = null;
		
		try {
			stmt = connection.prepareStatement(
					"UPDATE person SET " + 
					"lastname=?, firstname=?, patronymic=?, " + 
					"phone_mobile=?, phone_home=?, address=?, email=? " + 
					"WHERE id=?");
			
			stmt.setString(1, person.getLastname());
			stmt.setString(2, person.getFirstname());
			stmt.setString(3, person.getPatronymic());
			stmt.setString(4, person.getPhone_mobile());
			stmt.setString(5, person.getPhone_home());
			stmt.setString(6, person.getAddress());
			stmt.setString(7, person.getEmail());
			stmt.setInt(8, person.getId());
			
			stmt.execute();
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		return null;
	}

	public void deletePerson(Person person) throws SQLException {
		PreparedStatement stmt = null;
		
		try {
			stmt = connection.prepareStatement(
					"DELETE FROM person " + 
					"WHERE id=?");
			stmt.setInt(1, person.getId());
			
			stmt.execute();
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
	}
	
}
