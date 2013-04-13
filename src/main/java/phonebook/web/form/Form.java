package phonebook.web.form;

import java.util.List;

import phonebook.logic.Person;

public class Form {
	private Person person;
	private List<Person> personsList;
	private List<String> errors;
	private String action; 
	private String title;
	
	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}
	public List<Person> getPersonsList() {
		return personsList;
	}
	public void setPersonsList(List<Person> personsList) {
		this.personsList = personsList;
	}
	public List<String> getErrors() {
		return errors;
	}
	public void setErrors(List<String> errors) {
		this.errors = errors;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
}
