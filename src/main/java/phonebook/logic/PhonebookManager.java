package phonebook.logic;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import phonebook.logic.interfaces.IPhonebook;

public class PhonebookManager implements IPhonebook {
	private static PhonebookManager instance;
	private static IPhonebook phonebook;
	
	public static synchronized PhonebookManager getInstance() {
		if (null == instance) {
			instance = new PhonebookManager();
			if (phonebook == null) {
				phonebook = Phonebook.getInstance();
			}
		}
		
		return instance;
	}
	
	public static void setPhonebook(IPhonebook pbook) {
		phonebook = pbook;
	}
	
	private PhonebookManager() {}
	
	private List<String> checkPerson(Person person) {
		List<String> list = new ArrayList<String>();
		Pattern pattern;
		Matcher matcher;
		
		if (person.getLastname().length() > 50) {
			list.add("Фамилия не может содержать более 50 символов");
		}
			
		pattern = Pattern.compile("[а-яА-Я]{2,}+");
		matcher = pattern.matcher(person.getLastname());
		if (!matcher.matches()) {
			list.add("Фамилия должна быть указана на русском языке");
		}
		
		if (person.getFirstname().length() > 30) {
			list.add("Имя не может содержать более 30 символов");
		}
		
		matcher = pattern.matcher(person.getFirstname());
		if (!matcher.matches()) {
			list.add("Имя должно быть указано на русском языке");
		}
		
		if (person.getPatronymic().length() > 30) {
			list.add("Отчество не может быть длиннее 30 символов");
		}
		
		matcher = pattern.matcher(person.getPatronymic());
		if (!matcher.matches()) {
			list.add("Отчество должно быть указано на русском языке");
		}
		
		pattern = Pattern.compile("\\+38\\([0-9]{1,3}\\)[0-9]{3,7}");
		matcher = pattern.matcher(person.getPhone_mobile());
		if (!matcher.matches()) {
			list.add("Мобильный номер должен соответствовать формату '+38(yyy)xxxxxx");
		}
		
		if (person.getPhone_home().length() != 0) {
			pattern = Pattern.compile("\\+38\\([0-9]{1,4}\\)[0-9]{3,7}");
			matcher = pattern.matcher(person.getPhone_home());
			if (!matcher.matches()) {
				list.add("Домашний номер должен соответствовать формату '+38(yyyy)xxxxxxx");
			}
		}
		
		if (person.getAddress().length() > 100) {
			list.add("Длина адреса не может превышать 100 символов");
		}
		
		if (person.getEmail().length() > 30) {
			list.add("Длина e-mail не может превышать 30 символов");
		}
		
		pattern = Pattern.compile("\\w+([-.\\w]+\\w+){0,4}@\\w+.[\\w]{1,3}");
		matcher = pattern.matcher(person.getEmail());
		
		if (person.getEmail().length() != 0 && !matcher.matches()) {
			list.add("Неверный формат e-mail");
		}
					
		return list;
	}
	
	public List<Person> getAllPersons() throws SQLException {
		return phonebook.getAllPersons();
	}

	public Person getPersonById(int id) throws SQLException {
		return phonebook.getPersonById(id);
	}

	public List<String> insertPerson(Person person) throws SQLException {
		List<String> list = checkPerson(person);
		if (list.size() == 0) {
			phonebook.insertPerson(person);
		}
		return list;
	}

	public List<String> updatePerson(Person person) throws SQLException {
		List<String> list = checkPerson(person);
		if (list.size() == 0) {
			phonebook.updatePerson(person);
		}
		return list;
	}

	public void deletePerson(Person person) throws SQLException {
		phonebook.deletePerson(person);
	}
}
