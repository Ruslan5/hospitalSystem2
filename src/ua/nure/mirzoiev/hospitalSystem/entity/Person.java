package ua.nure.mirzoiev.hospitalSystem.entity;

import ua.nure.mirzoiev.hospitalSystem.entity.Role;

/**
 * Person entity.
 * 
 * @author R.Mirzoiev
 * 
 */
public class Person {
	private int id;
	private String login;
	private String password;
	private String name;
	private String surname;
	private Role role_name;
	private String additional_info;
	private int count_patients;

	public Person() {
	}

	public Person(int id, String login, String password, String name, String surname, Role role, String additionalInfo,
			int count_patients) {
		this.id = id;
		this.login = login;
		this.password = password;
		this.name = name;
		this.surname = surname;
		this.role_name = role;
		this.additional_info = additionalInfo;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public Role getRole() {
		return role_name;
	}

	public void setRole(Role role) {
		this.role_name = role;
	}

	public String getAdditionalInfo() {
		return additional_info;
	}

	public void setAdditionalInfo(String additionalInfo) {
		this.additional_info = additionalInfo;
	}

	public int getCount_patients() {
		return count_patients;
	}

	public void setCount_patients(int count_patients) {
		this.count_patients = count_patients;
	}

	public int compareTo(Person person) {
		return this.getName().compareToIgnoreCase(person.getName());
	}

	@Override
	public String toString() {
		return "Person [id=" + id + ", login=" + login + ", password=" + password + ", name=" + name + ", surname="
				+ surname + ", role=" + role_name + ", additional_info=" + additional_info + ", count_patients="
				+ count_patients + "]";
	}
}
