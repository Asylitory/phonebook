package phonebook.logic;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class TestPhonebookManager {
	private PhonebookManager testPhonebookManager;
	private Person person;
	private int expected;
	
	@Parameters	
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] {
				{new String[] {"Иванов","Иван","Иванович","+38(555)7557575","+38(0642)123123",
					"Украина, Луганск, ул. Оборонная 6","te-st@testmail.com"}, 0},
				{new String [] {"Иванов","Иван","Иванович","+38(555)7557575","",
					"Украина, Луганск, ул. Оборонная 6","te.st@testmail.com"}, 0},
				{new String [] {"И","Иван","Иванович","+38(555)7557575","+38(0642)123123",
						"Украина, Луганск, ул. Оборонная 6","test@testmail.com"}, 1},
				{new String [] {"Иванов","И","Иванович","+38(555)7557575","+38(0642)123123",
						"Украина, Луганск, ул. Оборонная 6","test@testmail.com"}, 1},
				{new String [] {"Иванов","Иван","И","+38(555)7557575","+38(0642)123123",
						"Украина, Луганск, ул. Оборонная 6","test@testmail.com"}, 1},
				{new String [] {"Иванов","Иван","Иванович","+385","+38(0642)123123",
						"Украина, Луганск, ул. Оборонная 6","test@testmail.com"}, 1},
				{new String [] {"Иванов","Иван","Иванович","+38(555)7557575","+383123",
						"Украина, Луганск, ул. Оборонная 6","test@testmail.com"}, 1},
				{new String [] {"Иванов","Иван","Иванович","+38(555)7557575","+38(0642)123123",
						"Украина, Луганск, ул. Оборонная 6","test"}, 1},
				{new String [] {"И","И","Иванович","+38(555)7557575","+38(0642)123123",
						"Украина, Луганск, ул. Оборонная 6","test@testmail.com"}, 2},
				{new String [] {"И","И","И","+38(555)7557575","+38(0642)123123",
						"Украина, Луганск, ул. Оборонная 6","test@testmail.com"}, 3},
				{new String [] {"И","И","И","+38","+38(0642)123123",
						"Украина, Луганск, ул. Оборонная 6","test@testmail.com"}, 4},
				{new String [] {"И","И","И","+38","+38",
						"Украина, Луганск, ул. Оборонная 6","test@testmail.com"}, 5},
				{new String [] {"И","И","И","+38","3",
						"Украина, Луганск, ул. Оборонная 6","test"}, 6}
		});
		}
	
	public TestPhonebookManager(String[] params, int expected) {
		person = new Person();
		
		person.setLastname(params[0].toString());
		person.setFirstname(params[1].toString());
		person.setPatronymic(params[2].toString());
		person.setPhone_mobile(params[3].toString());
		person.setPhone_home(params[4].toString());
		person.setAddress(params[5].toString());
		person.setEmail(params[6].toString());
		
		this.expected = expected;
		
		PhonebookManager.setPhonebook(mock(Phonebook.class));
		testPhonebookManager = PhonebookManager.getInstance();
	}
	
	@Test
	public void TestInsert() throws SQLException {
			assertEquals(testPhonebookManager.insertPerson(person).size(), expected);
	}
	
	@Test
	public void TestUpdate() throws SQLException {
			assertEquals(testPhonebookManager.updatePerson(person).size(), expected);
	}
}
