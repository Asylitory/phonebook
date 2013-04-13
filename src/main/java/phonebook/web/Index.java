package phonebook.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import phonebook.logic.Person;
import phonebook.logic.PhonebookManager;
import phonebook.logic.interfaces.IPhonebook;
import phonebook.web.form.Form;

public class Index extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static IPhonebook phonebook = PhonebookManager.getInstance();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		processRequest(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		processRequest(req, resp);
	}

	public void setPhonebook(IPhonebook phonebook) {
		Index.phonebook = phonebook;
	}
	
	private void processRequest(HttpServletRequest req, HttpServletResponse resp) 
			throws IOException, ServletException {
		Form form = new Form();
		Person person = null;
		int id;
		
		try {
			req.setCharacterEncoding("UTF-8");
			switch (checkAction(req)) {
			case 1:
				id = Integer.parseInt(req.getParameter("id"));
				person = phonebook.getPersonById(id);
				form.setPerson(person);
				form.setAction("SHOW");
				form.setTitle(person.getLastname() + " " + person.getFirstname());
				break;
			case 2:
				person = new Person();
				form.setPerson(person);
				form.setTitle("Создать новую запись");
				break;
			case 3:
				id = Integer.parseInt(req.getParameter("id"));
				person = phonebook.getPersonById(id);
				form.setPerson(person);
				form.setTitle("Редактировать запись: " + person.getLastname() + " " + person.getFirstname());
				break;
			case 4:
				id = Integer.parseInt(req.getParameter("id"));
				person = phonebook.getPersonById(id);
				form.setPerson(person);
				form.setTitle("Удалить запись: " + person.getLastname() + " " + person.getFirstname());
				form.setAction("DELETE");
				break;
			case 5:
				person = new Person();
				person.setId(Integer.parseInt(req.getParameter("id")));
				person.setLastname(req.getParameter("lastname"));
				person.setFirstname(req.getParameter("firstname"));
				person.setPatronymic(req.getParameter("patronymic"));
				person.setPhone_mobile(req.getParameter("phone_mobile"));
				person.setPhone_home(req.getParameter("phone_home"));
				person.setAddress(req.getParameter("address"));
				person.setEmail(req.getParameter("email"));
				List<String> errors;
				if (person.getId() == 0) {
					errors = phonebook.insertPerson(person);
					form.setTitle("Добавить новую запись");
				} else {
					errors = phonebook.updatePerson(person);
					form.setTitle("Редактировать запись: " + person.getLastname() + " " + person.getFirstname());
				}
				if (errors != null && errors.size() > 0) {
					form.setErrors(errors);
					form.setPerson(person);
				} else {
					resp.sendRedirect("index");
					return;
				}
				break;
			case 6:
				person = new Person();
				person.setId(Integer.parseInt(req.getParameter("id")));
				phonebook.deletePerson(person);
				resp.sendRedirect("index");
				return;
			case 7:
				form.setPersonsList(new ArrayList<Person>());
				for (Person p: phonebook.getAllPersons()) {
					if (req.getParameter("id=" + p.getId()) != null) {
						form.getPersonsList().add(p);
					}
				}
					
				if (form.getPersonsList().size() > 0) {
					form.setTitle("Массовое удаление записей");
					form.setAction("DELETE_ALL");
				} else {
					form.setPersonsList(phonebook.getAllPersons());
					form.setTitle("Телефонная книга");
				}
				break;				
			case 8:
				List<Person> personsToDelete = new ArrayList<Person>();
				for (Person p: phonebook.getAllPersons()) {
					if (req.getParameter("id=" + p.getId()) != null) {
						personsToDelete.add(p);
					}
				}
				
				for (Person p: personsToDelete) {
					phonebook.deletePerson(p);
				}
				
				resp.sendRedirect("index");
				return;
			default:
				form.setPersonsList(phonebook.getAllPersons());
				form.setTitle("Телефонная книга");
				break;
			}
		} catch (SQLException sqlException) {
			throw new IOException(sqlException.getMessage());
		}
		req.setAttribute("form", form);
		getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
	}
	
	private int checkAction(HttpServletRequest req) {
		if (req.getParameter("SHOW") != null) {
			return 1;
		}
		if (req.getParameter("NEW") != null) {
			return 2;
		}
		if (req.getParameter("EDIT") != null) {
			return 3;
		}
		if (req.getParameter("DELETE") != null) {
			return 4;
		}
		if (req.getParameter("CONFIRM") != null) {
			return 5;
		}
		if (req.getParameter("CONFIRM_DELETE") != null) {
			return 6;
		}
		if (req.getParameter("DELETE_ALL") != null) {
			return 7;
		}
		if (req.getParameter("CONFIRM_DELETE_ALL") != null) {
			return 8;
		}
		return 0;
	}
}
