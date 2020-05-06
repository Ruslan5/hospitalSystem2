package ua.nure.mirzoiev.hospitalSystem.tag;

import org.apache.log4j.Logger;

import ua.nure.mirzoiev.hospitalSystem.db.CardManager;
import ua.nure.mirzoiev.hospitalSystem.entity.PatientCard;
import ua.nure.mirzoiev.hospitalSystem.entity.Person;
import ua.nure.mirzoiev.hospitalSystem.exceptions.DBException;

import java.util.ArrayList;
import java.util.List;

public class CardService {
    private static final CardManager cardManager = CardManager.getInstance();
    private static final Logger LOG = Logger.getLogger(CardService.class);

    public List<PatientCard> getAllCards() throws ClassNotFoundException {
        List<PatientCard> patientCards = new ArrayList<>();
        try {
            patientCards = cardManager.findAllCards();
            System.out.println("getAllCards: " + patientCards);
        } catch (DBException e) {
            LOG.error(e.getMessage());
        }
        return patientCards;
    }

    public boolean insertHospitalCard(PatientCard patientCard) throws ClassNotFoundException {
        boolean result = false;

        try {
            result = cardManager.createPatientCard(patientCard);
        } catch (DBException e) {
            e.printStackTrace();
        }
        return result;
    }

    public PatientCard findCardById(Integer id) throws ClassNotFoundException {
    	PatientCard hospitalCard = null;

        try {
            hospitalCard = cardManager.findHospitalCardById(id);
        } catch (DBException e) {
            e.printStackTrace();
        }

        return hospitalCard;
    }
    
    public PatientCard downloadCardById(Integer id) throws ClassNotFoundException {
    	PatientCard hospitalCard = null;

        try {
            hospitalCard = cardManager.downloadCardById(id);
        } catch (DBException e) {
            e.printStackTrace();
        }

        return hospitalCard;
    }

    public boolean updateHospitalCard(PatientCard hospitalCard) throws ClassNotFoundException {
        boolean result = false;

        try {
            result = cardManager.updateHospitalCard(hospitalCard);
        } catch (DBException e) {
            e.printStackTrace();
        }
        return result;
    }
    
    public boolean updateCard(PatientCard hospitalCard) throws ClassNotFoundException {
        boolean result = false;

        try {
            result = cardManager.updateCard(hospitalCard);
        } catch (DBException e) {
            e.printStackTrace();
        }
        return result;
    }
    
    public boolean deleteCard(PatientCard hospitalCard) throws ClassNotFoundException {
        boolean result = false;

        try {
            result = cardManager.deleteCard(hospitalCard);
        } catch (DBException e) {
            e.printStackTrace();
        }
        return result;
    }

   
    public List<Person> findAllDoctorClients(int docId) throws ClassNotFoundException {
        List<Person> persons = null;

        try {
            persons = cardManager.findAllDoctorPatients(docId);
        } catch (DBException e) {
            e.printStackTrace();
        }
        return persons;
    }

    public List<PatientCard> findAllHospitalCardsByDoctorId(int id) throws ClassNotFoundException {
        List<PatientCard> hospitalCards = null;

        try {
            hospitalCards = cardManager.findHospitalCardsByDoctorId(id);
        } catch (DBException e) {
            e.printStackTrace();
        }
        return hospitalCards;
    }
}
