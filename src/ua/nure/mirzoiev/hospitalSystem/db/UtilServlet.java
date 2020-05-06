package ua.nure.mirzoiev.hospitalSystem.db;

import ua.nure.mirzoiev.hospitalSystem.entity.Role;
import ua.nure.mirzoiev.hospitalSystem.entity.Statuses;
import ua.nure.mirzoiev.hospitalSystem.exceptions.DBException;
import ua.nure.mirzoiev.hospitalSystem.tag.CardService;
import ua.nure.mirzoiev.hospitalSystem.entity.Category;
import ua.nure.mirzoiev.hospitalSystem.entity.Person;
import ua.nure.mirzoiev.hospitalSystem.entity.Diagnosis;
import ua.nure.mirzoiev.hospitalSystem.entity.PatientCard;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Util servlet manager. Works with MySQL DB.
 * 
 * @author R.Mirzoiev
 * 
 */
public class UtilServlet {
	// //////////////////////////////////////////////
	// SQL queries
	// /////////////////////////////////////////////
	private static final String CREATE_PERSON = "INSERT INTO person VALUES(DEFAULT, ?, ?, ?, ?, ?, ?, ?)";
	private static final String FIND_PEARSON_BY_LOGIN = "SELECT person.id, person.login, person.password, person.name, person.surname, roles.name role_name, additional_info, count_patients FROM person LEFT JOIN roles ON person.role_id = roles.id WHERE login = ?";
	private static final String FIND_PEARSON_BY_LOGIN_AND_PASSWORD = "SELECT person.id, person.login, person.password, person.name, person.surname, roles.name role_name, additional_info, count_patients FROM person LEFT JOIN roles ON person.role_id = roles.id WHERE login=? AND password=?";
	private static final String FIND_ALL_BY_ROLE = "SELECT person.id, person.login, person.password, person.name, person.surname, roles.name role_name, additional_info, count_patients FROM person LEFT JOIN roles ON person.role_id = roles.id WHERE roles.name=?";
	private static final String FIND_PERSON_BY_ID = "select * from person where id = ?";
	private static final String DELETE_PERSON = "DELETE FROM person where id = ?";

	// //////////////////////////////////////////////
	// Setting acces to DB
	// /////////////////////////////////////////////
	private static String url = "jdbc:mysql://localhost/hospitalTest2?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";
	private static String userName = "root";
	private static String password = "root";

	private static final Logger LOG = Logger.getLogger(UtilServlet.class);
	static ArrayList<Person> persons = new ArrayList<Person>();
	CardService cardService = new CardService();

	// //////////////////////////////////////////////////////////
	// Methods to obtain persons by role
	// //////////////////////////////////////////////////////////
	/**
	 * Returns all categories.
	 * 
	 * @return List of category entities.
	 */
	public static List<Person> getDoctorsList() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con = DriverManager.getConnection(url, userName, password);
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(FIND_ALL_BY_ROLE);

		while (rs.next()) {
			int id = rs.getInt(1);
			String login = rs.getString(2);
			String pass = rs.getString(3);
			String name = rs.getString(4);
			String surname = rs.getString(5);
			Role role = Role.getRoleByName(rs.getString(6));
			String additionalInfo = rs.getString(7);
			int count_patients = rs.getInt(1);
			Person doc = new Person(id, login, pass, name, surname, role, additionalInfo, count_patients);
			persons.add(doc);
		}
		return persons;
	}

	public static void setRequestListForClient(HttpServletRequest req) {
		req.setAttribute("roles", Role.values());
		req.setAttribute("categories", Category.values());
	}

	/**
	 * Method set request list for patient cards.
	 * 
	 */
	public void setRequestListForPatientCard(HttpServletRequest req) throws ClassNotFoundException {
		req.setAttribute("doctors", getAllByRole(Role.DOCTOR));
		req.setAttribute("patients", getAllByRole(Role.PATIENT));
		req.setAttribute("nurses", getAllByRole(Role.NURSE));
		req.setAttribute("statuses", getStatusesList());
		req.setAttribute("diagnoses", getDiagnosesList());
	}

	/**
	 * Get list of diagnosis.
	 * 
	 */
	private List<Diagnosis> getDiagnosesList() {
		return Arrays.asList(Diagnosis.values());
	}

	private List<Statuses> getStatusesList() {
		return Arrays.asList(Statuses.values());

	}

	public PatientCard setPatientCard(HttpServletRequest req) throws ClassNotFoundException {
		PatientCard patientCard = new PatientCard();

		int cardId = checkId(req);

		String nurseLogin = req.getParameter("nurse");

		Person doctor = getPersonByLogin(req.getParameter("doctor"));
		Person patient = getPersonByLogin(req.getParameter("patient"));
		Person nurse = null;
		if (nurseLogin != null) {
			nurse = getPersonByLogin(nurseLogin);
		}
		Statuses patientStatus = Statuses.getStatusByName(req.getParameter("status"));
		Diagnosis currentDiagnosis = Diagnosis.getDiagnosisByName(req.getParameter("diagnosis"));
		Diagnosis finalDiagnosis = Diagnosis.getDiagnosisByName(req.getParameter("finalDiagnosis"));
		String drug = req.getParameter("drug");
		String operation = req.getParameter("operation");
		String procedure = req.getParameter("procedure");

		patientCard.setId(cardId);
		patientCard.setDoctor(doctor);
		patientCard.setPatient(patient);
		patientCard.setNurse(nurse);
		patientCard.setStatus(patientStatus);
		patientCard.setDiagnosis(currentDiagnosis);
		patientCard.setFinalDiagnosis(finalDiagnosis);
		patientCard.setDrugs(PatientCard.parseData(drug));
		patientCard.setOperations(PatientCard.parseData(operation));
		patientCard.setProcedures(PatientCard.parseData(procedure));
		return patientCard;
	}

	public Person getPersonByLogin(String login) throws ClassNotFoundException {
		Person person = null;

		try {
			person = findByLogin(login);
		} catch (DBException e) {
			e.printStackTrace();
		}

		return person;
	}

	public static int checkId(HttpServletRequest req) {
		int id = 0;

		String idReq = req.getParameter("id");

		if (idReq != null && !idReq.isEmpty()) {
			id = Integer.parseInt(req.getParameter("id"));
		}

		return id;
	}
	
	public static int checkCount(HttpServletRequest req) {
		int count = 0;

		String countReq = req.getParameter("count_patients");

		if (countReq != null && !countReq.isEmpty()) {
			count = Integer.parseInt(req.getParameter("count_patients"));
		}

		return count;
	}

	public boolean insertPerson(Person person) throws ClassNotFoundException {
		boolean result = false;

		try {
			result = createPearson(person);
		} catch (DBException e) {
			e.printStackTrace();
		}

		return result;
	}

	public static void close(AutoCloseable closeable) {
		if (closeable != null) {
			try {
				closeable.close();
			} catch (Exception e) {
				LOG.error("Can't close the resource: " + closeable.toString());
			}
		}
	}

	public static Person setPerson(HttpServletRequest req) throws ClassNotFoundException, SQLException {

		Class.forName("com.mysql.cj.jdbc.Driver");

		Connection con = DriverManager.getConnection(url, userName, password);
		System.out.println("Соединение с БД setClient : " + con);
		Statement stmt = con.createStatement();

		int clientId = checkId(req);

		Person person = new Person();
		String login = req.getParameter("login");
		String passwor = req.getParameter("password");
		String name = req.getParameter("name");
		String surname = req.getParameter("surname");
		Role role = Role.getRoleByName(req.getParameter("role"));
		String additionalInfo = req.getParameter("additionalInfo");
		int count = checkCount(req);

		person.setId(clientId);
		person.setLogin(login);
		person.setPassword(passwor);
		person.setName(name);
		person.setSurname(surname);
		person.setRole(role);
		person.setAdditionalInfo(additionalInfo);
		person.setCount_patients(count);

		stmt.close();
		con.close();

		return person;
	}

	public static int insert(Person person) {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
			try (Connection conn = DriverManager.getConnection(url, userName, password)) {
				System.out.println("Соединение с БД insert : " + conn);
				String sql = CREATE_PERSON;
				try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

					preparedStatement.setString(1, person.getLogin());
					preparedStatement.setString(2, person.getPassword());
					preparedStatement.setString(3, person.getName());
					preparedStatement.setString(4, person.getSurname());
					preparedStatement.setString(5, person.getRole().toString());
					preparedStatement.setString(6, person.getAdditionalInfo());
					preparedStatement.setInt(7, person.getCount_patients());

					return preparedStatement.executeUpdate();
				}
			}
		} catch (Exception ex) {
			System.out.println(ex);
		}
		return 0;
	}

	/**
	 * Extract person for a doctords
	 * 
	 */
	public static Person extractPerson(ResultSet resultSet) throws SQLException {
		Person person = new Person();

		person.setId(resultSet.getInt("id"));
		person.setLogin(resultSet.getString("login"));
		person.setPassword(resultSet.getString("password"));
		person.setName(resultSet.getString("name"));
		person.setSurname(resultSet.getString("surname"));
		Role role = Role.getRoleByName((resultSet.getString("role_name")));
		person.setRole(role);
		person.setAdditionalInfo(resultSet.getString("additional_info"));
		
		person.setCount_patients(resultSet.getInt("count_patients"));
		return person;
	}
	/**
	 * Extract person for a patients
	 * 
	 */
	public static Person extractPers(ResultSet resultSet) throws SQLException {
		Person person = new Person();

		person.setId(resultSet.getInt("id"));
		person.setLogin(resultSet.getString("login"));
		person.setPassword(resultSet.getString("password"));
		person.setName(resultSet.getString("name"));
		person.setSurname(resultSet.getString("surname"));
		Role role = Role.getRoleByName((resultSet.getString("role_id")));
		person.setRole(role);
		person.setAdditionalInfo(resultSet.getString("additional_info"));
		person.setCount_patients(resultSet.getInt("count_patients"));
		return person;
	}

	public static Person findByLogin(String login) throws DBException, ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver");

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Person person = null;

		try {
			connection = DriverManager.getConnection(url, userName, password);
			System.out.println("Подключение к БД findByLogin: " + connection);
			preparedStatement = connection.prepareStatement(FIND_PEARSON_BY_LOGIN);
			preparedStatement.setString(1, login);
			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				person = extractPerson(resultSet);
			} else {
				throw new SQLException();
			}
		} catch (SQLException e) {
			LOG.error("Can't find client with login: " + login + e);
			throw new DBException("Can't find client with login:" + login, e);
		} finally {
			close(resultSet);
			close(preparedStatement);
			close(connection);
		}
		return person;
	}

	public boolean createPearson(Person person) throws DBException, ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver");

		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
		boolean result = false;
		int n = 1;

		try {
			connection = DriverManager.getConnection(url, userName, password);
			System.out.println("Соединение с БД createClient: " + connection);
			pstmt = connection.prepareStatement(CREATE_PERSON, Statement.RETURN_GENERATED_KEYS);

			pstmt.setString(n++, person.getLogin());
			System.out.println("client getLogin: " + person.getLogin());
			pstmt.setString(n++, person.getPassword());
			pstmt.setString(n++, person.getName());
			pstmt.setString(n++, person.getSurname());
			pstmt.setInt(n++, person.getRole().getId());
			System.out.println(pstmt);
			pstmt.setString(n++, person.getAdditionalInfo());
			pstmt.setInt(n++, person.getCount_patients());
			if (pstmt.executeUpdate() > 0) {
				resultSet = pstmt.getGeneratedKeys();
				if (resultSet.next()) {
					int generatedClientId = resultSet.getInt(1);
					person.setId(generatedClientId);
					result = true;
				}
			}

		} catch (SQLException e) {
			LOG.error("Can't create client: " + person.getLogin() + e);
			throw new DBException("Can't create client: " + person.getLogin(), e);
		} finally {
			close(resultSet);
			close(pstmt);
			close(connection);
		}
		return result;
	}

	/**
	 * Find person by login and Password.
	 * 
	 */

	public Person findByLoginAndPassword(String login, String pass)
			throws DBException, ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");

		Connection connection = null;

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Person person = null;

		try {
			connection = DriverManager.getConnection(url, userName, password);
			System.out.println("Соединение с БД findByLoginAndPassword : " + connection);

			preparedStatement = connection.prepareStatement(FIND_PEARSON_BY_LOGIN_AND_PASSWORD);
			preparedStatement.setString(1, login);
			preparedStatement.setString(2, pass);
			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				person = extractPerson(resultSet);
				System.out.println("extractClient: " + person);
			} else {
				throw new SQLException();
			}
		} catch (SQLException e) {
			LOG.error("Can't find client with such combination of login and password");
			throw new DBException("Can't find client with such combination of login and password", e);
		} finally {
			close(resultSet);
			close(preparedStatement);
			close(connection);
		}
		return person;
	}

	/**
	 * Get finnded person by login and password.
	 * 
	 * @return Person with entered login and password from person entities.
	 */
	public Person getPearsontByLoginAndPassword(String login, String password)
			throws ClassNotFoundException, SQLException {
		Person person = null;
		try {
			person = findByLoginAndPassword(login, password);
		} catch (DBException e) {
			LOG.error(e.getMessage());
		}
		return person;
	}

	// //////////////////////////////////////////////////////////
	// Entity access methods
	// //////////////////////////////////////////////////////////

	/**
	 * Returns all person by role.
	 * 
	 * @param role.
	 * @return Person entity.
	 * @throws DBException
	 */
	public List<Person> getAllByRole(Role role) throws ClassNotFoundException {
		List<Person> persons = null;

		try {
			persons = findAllByRole(role);
		} catch (DBException e) {
			LOG.error(e.getMessage());
		}

		return persons;
	}

	/**
	 * Find all person by role.
	 * 
	 */
	public List<Person> findAllByRole(Role role) throws DBException, ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		List<Person> persons = new ArrayList<>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = DriverManager.getConnection(url, userName, password);
			preparedStatement = connection.prepareStatement(FIND_ALL_BY_ROLE);
			preparedStatement.setString(1, role.getName());

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				persons.add(extractPerson(resultSet));
			}

			if (persons.size() == 0) {
				LOG.error("Users with this role was not finded: " + role.toString());
				throw new DBException("Can't find any users with role: " + role.toString(), null);
			}
		} catch (SQLException e) {
			LOG.error("Users with this role was not finded " + role.toString() + e);
			throw new DBException("Can't find person with role: " + role.toString(), e);
		} finally {
			close(resultSet);
			close(preparedStatement);
			close(connection);
		}

		return persons;
	}

	/**
	 * Extract data about patient from patient card
	 * 
	 * @return List of patirnt card item entities.
	 */
	public static PatientCard extractCard(ResultSet resultSet) throws SQLException {
		PatientCard patientCard = new PatientCard();
		Person doctor = new Person();
		Person patient = new Person();
		Person nurse = new Person();
		int k = 1;

		patientCard.setId(resultSet.getInt(k++));

		k = extractPearsonByCounter(resultSet, doctor, k);
		patientCard.setDoctor(doctor);

		k = extractPearsonByCounter(resultSet, patient, k);

		patientCard.setPatient(patient);

		k = extractPearsonByCounter(resultSet, nurse, k);
		patientCard.setNurse(nurse);

		Statuses patientStatus = Statuses.getStatusByName(resultSet.getString(k++));
		patientCard.setStatus(patientStatus);

		Diagnosis diagnosis = Diagnosis.getDiagnosisByName(resultSet.getString(k++));
		patientCard.setDiagnosis(diagnosis);
		Diagnosis finalDiagnosis = Diagnosis.getDiagnosisByName(resultSet.getString(k++));
		patientCard.setFinalDiagnosis(finalDiagnosis);

		patientCard.setProcedures(PatientCard.parseData(resultSet.getString(k++)));
		patientCard.setDrugs(PatientCard.parseData(resultSet.getString(k++)));
		patientCard.setOperations(PatientCard.parseData(resultSet.getString(k++)));
		return patientCard;
	}

	/**
	 * Extract person by counter
	 * 
	 * @return List of patirnt card item entities.
	 */
	private static int extractPearsonByCounter(ResultSet resultSet, Person person, int k) throws SQLException {
		Role role;
		person.setId(resultSet.getInt(k++));
		person.setLogin(resultSet.getString(k++));
		person.setPassword(resultSet.getString(k++));
		person.setName(resultSet.getString(k++));
		person.setSurname(resultSet.getString(k++));
		role = Role.getRoleByName((resultSet.getString(k++)));
		person.setRole(role);
		person.setAdditionalInfo(resultSet.getString(k++));
		person.setCount_patients(resultSet.getInt(k++));
		return k;
	}

	/**
	 * Returns a person with the given identifier.
	 * 
	 * @param id Person identifier.
	 * @return Person entity.
	 * @throws DBException
	 */
	public Person findPersondById(Integer id) throws ClassNotFoundException {
		Person person = null;
		try {
			person = personById(id);
		} catch (DBException e) {
			e.printStackTrace();
		}
		return person;
	}

	/**
	 * Returns a person with the given identifier.
	 * 
	 * @param id Person identifier.
	 * @return Person entity.
	 * @throws DBException
	 */
	public Person personById(Integer id) throws DBException, ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Person person = new Person();

		try {
			connection = DriverManager.getConnection(url, userName, password);
			System.out.println("personById con: " + connection);
			preparedStatement = connection.prepareStatement(FIND_PERSON_BY_ID);

			preparedStatement.setInt(1, id);

			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				person = extractPers(resultSet);
			}
		} catch (SQLException e) {
			LOG.error("Can't find person id: " + id + "Error: " + e);
			throw new DBException("Can't find person id: " + id, e);
		} finally {
			UtilServlet.close(resultSet);
			UtilServlet.close(preparedStatement);
			UtilServlet.close(connection);
		}

		return person;
	}

	/**
	 * Delete person.
	 * 
	 * @param person to update.
	 * @throws DBException
	 */
	public boolean deletePerson(Person person) throws ClassNotFoundException {
		boolean result = false;

		try {
			result = deletePersonById(person);
		} catch (DBException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * Delete person.
	 * 
	 * @param person to update.
	 * @throws DBException
	 */
	public boolean deletePersonById(Person person) throws DBException, ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection connection = null;
		PreparedStatement pstmt = null;
		boolean result = false;
		int k = 1;

		try {
			connection = DriverManager.getConnection(url, userName, password);
			System.out.println("Delete connected: " + connection);
			pstmt = connection.prepareStatement(DELETE_PERSON);

			pstmt.setInt(k++, person.getId());

			if (pstmt.executeUpdate() > 0) {
				result = true;
				System.out.println("result delete: " + result);
			}
		} catch (SQLException e) {
			LOG.error("Can't deleted person id: " + person.getId());
			throw new DBException("Can't delete person id: " + person.getId(), e);
		} finally {
			UtilServlet.close(pstmt);
			UtilServlet.close(connection);
		}
		return result;
	}
}
