package ua.nure.mirzoiev.hospitalSystem.db;

import org.apache.log4j.Logger;

import ua.nure.mirzoiev.hospitalSystem.entity.PatientCard;
import ua.nure.mirzoiev.hospitalSystem.entity.Person;
import ua.nure.mirzoiev.hospitalSystem.exceptions.DBException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CardManager {
    private static final String FIND_ALL_DOCTOR_PATIENTS = "SELECT cl.id, cl.login, cl.password, cl.name, cl.surname, r.name role_name, cl.additional_info, cl.count_patients FROM patient_card LEFT JOIN person cl on patient_card.patient_id = cl.id LEFT JOIN roles r on cl.role_id = r.id WHERE doctor_id=? OR nurse_id=?";
    private static final String FIND_ALL_HOSPITAL_CARDS = "SELECT hosp.id, doc.id, doc.login, doc.password, doc.name,  doc.surname, doc_role.name role_name, doc.additional_info, doc.count_patients, pat.id, pat.login, pat.password, pat.name, pat.surname,  pat_role.name role_name, pat.additional_info, pat.count_patients, nurse.id, nurse.login, nurse.password, nurse.name, nurse.surname, nurse_role.name role_name, nurse.additional_info, nurse.count_patients, " + 
    		"hosp.statuses, hosp.diagnosis, hosp.final_diagnosis, hosp.procedures, hosp.drug, hosp.operations " +
    		"FROM patient_card hosp " + 
    		"LEFT JOIN person doc ON hosp.doctor_id = doc.id " + 
    		"LEFT JOIN roles doc_role ON doc.role_id = doc_role.id " + 
    		"LEFT JOIN person pat ON hosp.patient_id = pat.id " + 
    		"LEFT JOIN roles pat_role ON pat.role_id = pat_role.id " + 
    		"LEFT JOIN person nurse ON hosp.nurse_id = nurse.id " + 
    		"LEFT JOIN roles nurse_role ON nurse.role_id = nurse_role.id";
    private static final String FIND_CARD_BY_ID = "SELECT card.id, doc.id, doc.login, doc.password, doc.name,  doc.surname, doc_role.name role_name, doc.additional_info, doc.count_patients, " +
            "pat.id, pat.login, pat.password, pat.name, pat.surname,  pat_role.name role_name, pat.additional_info, pat.count_patients, " +
            "nurse.id, nurse.login, nurse.password, nurse.name, nurse.surname, nurse_role.name role_name, nurse.additional_info, nurse.count_patients, " +
            "card.statuses, card.diagnosis, card.final_diagnosis, card.drug, card.operations, card.procedures " +
            "FROM patient_card card " +
            "LEFT JOIN person doc ON card.doctor_id = doc.id " +
            "LEFT JOIN person pat ON card.patient_id = pat.id " +
            "LEFT JOIN person nurse ON card.nurse_id = nurse.id " +
            "LEFT JOIN roles doc_role ON doc.role_id = doc_role.id " +
            "LEFT JOIN roles pat_role ON pat.role_id = pat_role.id " +
            "LEFT JOIN roles nurse_role ON nurse.role_id = nurse_role.id WHERE card.id=?";

    private static final String FIND_HOSPITAL_CARD_BY_DOCTOR_ID = "SELECT card.id, doc.id, doc.login, doc.password, doc.name,  doc.surname, doc_role.name role_name, doc.additional_info, doc.count_patients, " + 
    		"pat.id, pat.login, pat.password, pat.name, pat.surname,  pat_role.name role_name, pat.additional_info, pat.count_patients, " + 
    		"nurse.id, nurse.login, nurse.password, nurse.name, nurse.surname, nurse_role.name role_name, nurse.additional_info, nurse.count_patients, " + 
    		"card.statuses, card.diagnosis, card.final_diagnosis, card.drug, card.operations, card.procedures " +
    	"FROM patient_card card " + 
    	"LEFT JOIN person doc ON card.doctor_id = doc.id " + 
    	"LEFT JOIN person pat ON card.patient_id = pat.id " + 
    	"LEFT JOIN person nurse ON card.nurse_id = nurse.id " + 
    	"LEFT JOIN roles doc_role ON doc.role_id = doc_role.id " + 
    	"LEFT JOIN roles pat_role ON pat.role_id = pat_role.id " + 
    	"LEFT JOIN roles nurse_role ON nurse.role_id = nurse_role.id WHERE card.doctor_id=? OR card.nurse_id=?";
    private static final String CREATE_PATIENT_CARD = "INSERT INTO patient_card VALUES (DEFAULT, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_CARD = "UPDATE patient_card SET doctor_id = ?, patient_id = ?, nurse_id = ?, statuses = ? WHERE id=?"; //UPDATE patient_card SET doctor_id = ?, patient_id = ?, nurse_id = ?, statuses = ?, diagnosis = ?, final_diagnosis = ?, procedures = ?, drug = ?, operations = ? WHERE id=?";
    private static final String UPDATE_DOC_CARD = "UPDATE patient_card SET diagnosis = ?, final_diagnosis = ?, procedures = ?, drug = ?, operations = ? WHERE id=?";
    private static final String DELETE_CARD = "DELETE FROM patient_card where id = ?";

	public static final String INCREMENT_PATIENT_COUNT = "UPDATE person SET count_patients=count_patients+1 WHERE id=?";
	public static final String DECREMENT_PATIENT_COUNT = "UPDATE person SET count_patients=count_patients-1 WHERE id=?";

    private static final Logger LOG = Logger.getLogger(CardManager.class);
    private static CardManager instance;
    
    private static String url = "jdbc:mysql://localhost/hospitalTest2?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";
	private static String userName = "root";
	private static String password = "root";
	
	public static void close(AutoCloseable closeable) {
		if (closeable != null) {
			try {
				closeable.close();
			} catch (Exception e) {
				LOG.error("Can't close the resource: " + closeable.toString());
			}
		}
	}

    public static synchronized CardManager getInstance() {
        if (instance == null) {
            instance = new CardManager();
        }
        return instance;
    }
    private CardManager() {
    }

    public List<PatientCard> findAllCards() throws DBException, ClassNotFoundException {
    	Class.forName("com.mysql.cj.jdbc.Driver");
        List<PatientCard> patientCards = new ArrayList<PatientCard>();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
        	connection = DriverManager.getConnection(url, userName, password);
    		System.out.println("Подключение к БД findAllCards: " + connection);
            statement = connection.createStatement();
            resultSet = statement.executeQuery(FIND_ALL_HOSPITAL_CARDS);
            
            while (resultSet.next()) {
                patientCards.add(UtilServlet.extractCard(resultSet));
                System.out.println("findAllCards: " + patientCards);
            }
        } catch (SQLException e) {
            LOG.error("Can't find hospital cards");
            throw new DBException("Can't find hospital cards", e);
        } finally {
            close(resultSet);
            close(statement);
            close(connection);
        }
        return patientCards;
    }
    
    public List<Person> findAllDoctorPatients(int id) throws DBException, ClassNotFoundException {
    	Class.forName("com.mysql.cj.jdbc.Driver");
        List<Person> person = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection(url, userName, password);
            System.out.println("Connection DB findAllDoctorPatients: " + connection);
            preparedStatement = connection.prepareStatement(FIND_ALL_DOCTOR_PATIENTS);
            preparedStatement.setInt(1, id);
            preparedStatement.setInt(2, id);

            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                person.add(UtilServlet.extractPerson(resultSet));
            }

            if (person.size() == 0) {
                throw new SQLException();
            }
        } catch (SQLException e) {
            LOG.error("Can't find any patients for doctor with id: " + id + e);
            throw new DBException("Can't find any patients for doctor with id: " + id, e);
        } finally {
        	UtilServlet.close(resultSet);
        	UtilServlet.close(preparedStatement);
        	UtilServlet.close(connection);
        }

        return person;
    }

    public boolean createPatientCard(PatientCard patientCard) throws DBException, ClassNotFoundException {
    	Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = null;
//        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        boolean result = false;
        int k = 1;
        try {
			connection = DriverManager.getConnection(url, userName, password);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        System.out.println("Подключение к БД createHospitalCard: " + connection);

        try (PreparedStatement pstmt = connection.prepareStatement(CREATE_PATIENT_CARD, Statement.RETURN_GENERATED_KEYS);
        		PreparedStatement pstmt2 = connection.prepareStatement(INCREMENT_PATIENT_COUNT)){
            
            
            connection.setAutoCommit(false);
            pstmt.setInt(k++, patientCard.getDoctor().getId());
            
            pstmt.setInt(k++, patientCard.getPatient().getId());
            if (patientCard.getNurse() != null) {
                pstmt.setInt(k++, patientCard.getNurse().getId());
            } else {
                pstmt.setNull(k++, Types.INTEGER);
            }
            pstmt.setString(k++, patientCard.getStatus().getName());

            if (patientCard.getDiagnosis() != null) {
                pstmt.setString(k++, patientCard.getDiagnosis().getName());
            } else {
                pstmt.setNull(k++, Types.VARCHAR);
            }

            if (patientCard.getFinalDiagnosis() != null) {
                pstmt.setString(k++, patientCard.getFinalDiagnosis().getName());
            } else {
                pstmt.setNull(k++, Types.VARCHAR);
            }

            String drug = String.join(System.lineSeparator(), patientCard.getDrugs());
            pstmt.setString(k++, drug);

            String operations = String.join(System.lineSeparator(), patientCard.getOperations());
            pstmt.setString(k++, operations);

            String procedures = String.join(System.lineSeparator(), patientCard.getProcedures());
            pstmt.setString(k, procedures);

            if (pstmt.executeUpdate() > 0) {
                resultSet = pstmt.getGeneratedKeys();
                if (resultSet.next()) {
                    int generatedClientId = resultSet.getInt(1);
                    patientCard.setId(generatedClientId);
                    result = true;
                }
            }
            pstmt2.setInt(1, patientCard.getDoctor().getId());
            pstmt2.executeUpdate();

			connection.commit();
        } catch (SQLException e) {
            LOG.error("Can't create hospital card! Cause: " + e);
            throw new DBException("Can't create hospital card!", e);
        } finally {
        	UtilServlet.close(resultSet);
        	UtilServlet.close(connection);
        }

        return result;
    }

    public PatientCard findHospitalCardById(Integer id) throws DBException, ClassNotFoundException {
    	Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        PatientCard patientCard = new PatientCard();

        try {
            connection = DriverManager.getConnection(url, userName, password);
            preparedStatement = connection.prepareStatement(FIND_CARD_BY_ID);

            preparedStatement.setInt(1, id);

            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                patientCard = UtilServlet.extractCard(resultSet);
            }
        } catch (SQLException e) {
            LOG.error("Can't find hospital card with such id: " + id + "Error: " + e);
            throw new DBException("Can't find hospital card with such id: " + id, e);
        } finally {
        	UtilServlet.close(resultSet);
        	UtilServlet.close(preparedStatement);
        	UtilServlet.close(connection);
        }

        return patientCard;
    }
    
    public PatientCard downloadCardById(Integer id) throws DBException, ClassNotFoundException {
    	Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        PatientCard patientCard = new PatientCard();

        try {
            connection = DriverManager.getConnection(url, userName, password);
            preparedStatement = connection.prepareStatement(FIND_CARD_BY_ID);

            preparedStatement.setInt(1, id);

            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                patientCard = UtilServlet.extractCard(resultSet);
            }
        } catch (SQLException e) {
            LOG.error("Can't find hospital card with such id: " + id + "Error: " + e);
            throw new DBException("Can't find hospital card with such id: " + id, e);
        } finally {
        	UtilServlet.close(resultSet);
        	UtilServlet.close(preparedStatement);
        	UtilServlet.close(connection);
        }

        return patientCard;
    }

    public List<PatientCard> findHospitalCardsByDoctorId(Integer id) throws DBException, ClassNotFoundException {
    	Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<PatientCard> hospitalCards = new ArrayList<>();

        try {
            connection = DriverManager.getConnection(url, userName, password);
            System.out.println("Подключение к ДБ findHospitalCardsByDoctorId: " + connection);
            preparedStatement = connection.prepareStatement(FIND_HOSPITAL_CARD_BY_DOCTOR_ID);

            preparedStatement.setInt(1, id);
            preparedStatement.setInt(2, id);

            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                hospitalCards.add(UtilServlet.extractCard(resultSet));
                System.out.println("hospitalCards: " + hospitalCards);
            }
        } catch (SQLException e) {
            LOG.error("Can't find hospital cards with such doctor id: " + id + "Error: " + e);
            throw new DBException("Can't find patient card with such doctor id: " + id, e);
        } finally {
        	UtilServlet.close(resultSet);
        	UtilServlet.close(preparedStatement);
        	UtilServlet.close(connection);
        }

        return hospitalCards;
    }

    public boolean updateHospitalCard(PatientCard patientCard) throws DBException, ClassNotFoundException {
    	Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = null; 
        PreparedStatement pstmt = null;
        boolean result = false;
        int k = 1;

        try {
            connection = DriverManager.getConnection(url, userName, password);
            pstmt = connection.prepareStatement(UPDATE_CARD);

            pstmt.setInt(k++, patientCard.getDoctor().getId());
            pstmt.setInt(k++, patientCard.getPatient().getId());
            if (patientCard.getNurse() != null) {
                pstmt.setInt(k++, patientCard.getNurse().getId());
            } else {
                pstmt.setNull(k++, Types.INTEGER);
            }
            pstmt.setString(k++, patientCard.getStatus().getName());
//            pstmt.setString(k++, patientCard.getDiagnosis().getName());
//            pstmt.setString(k++, patientCard.getFinalDiagnosis().getName());
//
//            String drug = String.join(System.lineSeparator(), patientCard.getDrugs());
//            pstmt.setString(k++, drug);
//
//            String operations = String.join(System.lineSeparator(), patientCard.getOperations());
//            pstmt.setString(k++, operations);
//
//            String procedures = String.join(System.lineSeparator(), patientCard.getProcedures());
//            pstmt.setString(k++, procedures);

            pstmt.setInt(k++, patientCard.getId());

            if (pstmt.executeUpdate() > 0) {
                result = true;
            }
        } catch (SQLException e) {
            LOG.error("Can't update hospital card with id: " + patientCard.getId());
            throw new DBException("Can't update hospital card with id: " + patientCard.getId(), e);
        } finally {
            UtilServlet.close(pstmt);
            UtilServlet.close(connection);
        }

        return result;
    }
    
    public boolean updateCard(PatientCard patientCard) throws DBException, ClassNotFoundException {
    	Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = null; 
        PreparedStatement pstmt = null;
        boolean result = false;
        int k = 1;

        try {
            connection = DriverManager.getConnection(url, userName, password);
            pstmt = connection.prepareStatement(UPDATE_DOC_CARD);

//            pstmt.setInt(k++, patientCard.getDoctor().getId());
//            pstmt.setInt(k++, patientCard.getPatient().getId());
//            if (patientCard.getNurse() != null) {
//                pstmt.setInt(k++, patientCard.getNurse().getId());
//            } else {
//                pstmt.setNull(k++, Types.INTEGER);
//            }
//            pstmt.setString(k++, patientCard.getStatus().getName());
            pstmt.setString(k++, patientCard.getDiagnosis().getName());
            pstmt.setString(k++, patientCard.getFinalDiagnosis().getName());

            String procedures = String.join(System.lineSeparator(), patientCard.getProcedures());
            pstmt.setString(k++, procedures);
            
            String drug = String.join(System.lineSeparator(), patientCard.getDrugs());
            pstmt.setString(k++, drug);

            String operations = String.join(System.lineSeparator(), patientCard.getOperations());
            pstmt.setString(k++, operations);

            pstmt.setInt(k++, patientCard.getId());

            if (pstmt.executeUpdate() > 0) {
                result = true;
            }
        } catch (SQLException e) {
            LOG.error("Can't update hospital card with id: " + patientCard.getId());
            throw new DBException("Can't update hospital card with id: " + patientCard.getId(), e);
        } finally {
            UtilServlet.close(pstmt);
            UtilServlet.close(connection);
        }

        return result;
    }
    
    public boolean deleteCard(PatientCard patientCard) throws DBException, ClassNotFoundException {
    	Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = null; 
//        PreparedStatement pstmt = null;
        boolean result = false;
        int k = 1;
        try {
			connection = DriverManager.getConnection(url, userName, password);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        System.out.println("Delete connected: " + connection);
     
        try (PreparedStatement pstmt = connection.prepareStatement(DELETE_CARD);
        		PreparedStatement pstmt2 = connection.prepareStatement(DECREMENT_PATIENT_COUNT)){
//        	connection.setAutoCommit(false);
        	
        	//delete card
            pstmt.setInt(k++, patientCard.getId());
            
         
            
            if (pstmt.executeUpdate() > 0) {
                result = true;
                System.out.println("result delete: " + result);
            }
         // set doctor id for decrement count of patients 
            pstmt2.setInt(1, patientCard.getDoctor().getId());
            pstmt2.executeUpdate();
            System.out.println(pstmt2);
            System.out.println(patientCard.getPatient().getCount_patients());
        } catch (SQLException e) {
            LOG.error("Can't update hospital card with id: " + patientCard.getId());
            throw new DBException("Can't delete hospital card with id: " + patientCard.getId(), e);
        } finally {
            UtilServlet.close(connection);
        }

        return result;
    }
    public boolean downloadCard(PatientCard patientCard) throws DBException, ClassNotFoundException {
    	Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = null; 
        PreparedStatement pstmt = null;
        boolean result = false;
        int k = 1;
     
        try {
            connection = DriverManager.getConnection(url, userName, password);
            System.out.println("Delete connected: " + connection);
            pstmt = connection.prepareStatement(FIND_CARD_BY_ID);

            pstmt.setInt(k++, patientCard.getId());
            
            if (pstmt.executeUpdate() > 0) {
                result = true;
                System.out.println("result delete: " + result);
            }
        } catch (SQLException e) {
            LOG.error("Can't update hospital card with id: " + patientCard.getId());
            throw new DBException("Can't delete hospital card with id: " + patientCard.getId(), e);
        } finally {
            UtilServlet.close(pstmt);
            UtilServlet.close(connection);
        }

        return result;
    }
    
    
}
