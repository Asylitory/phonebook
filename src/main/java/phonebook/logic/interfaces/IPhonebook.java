package phonebook.logic.interfaces;

import java.sql.SQLException;
import java.util.List;

import phonebook.logic.Person;

public interface IPhonebook {
	List<Person> getAllPersons() throws SQLException;
	Person getPersonById(int id) throws SQLException;
	List<String> insertPerson(Person person) throws SQLException;
	List<String> updatePerson(Person person) throws SQLException;
	void deletePerson(Person person) throws SQLException;
}
