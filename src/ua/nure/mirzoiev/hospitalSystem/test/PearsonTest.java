
package ua.nure.mirzoiev.hospitalSystem.test;


import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import ua.nure.mirzoiev.hospitalSystem.entity.Person;
import ua.nure.mirzoiev.hospitalSystem.entity.Role;


public class PearsonTest {
	static Person person;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		person = new Person();
	}

	@Test
	public void getSetTest() {
		person.setId(1);
		person.setLogin("login");
		person.setPassword("password");
		person.setName("name");
		person.setSurname("surname");
		Role role = Role.getRoleById(1);
		person.setRole(role);
		person.setAdditionalInfo("additionalInfo");
		
		Assert.assertEquals(1, person.getId());
		Assert.assertEquals("login", person.getLogin());
		Assert.assertEquals("password", person.getPassword());
		Assert.assertEquals("name", person.getName());
		Assert.assertEquals("surname", person.getSurname());
		Assert.assertEquals(1, person.getRole().getId());
		Assert.assertEquals("additionalInfo", person.getAdditionalInfo());




	}

}
