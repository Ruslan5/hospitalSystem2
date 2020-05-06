package ua.nure.mirzoiev.hospitalSystem.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
 * PatientCard entity.
 * 
 * @author R.Mirzoiev
 * 
 */
public class PatientCard {
		private int id;
	    private Person doctor;
	    private Person patient;
	    private Person nurse;
	    private Statuses status;
	    private Diagnosis diagnosis;
	    private Diagnosis finalDiagnosis;
	    private List<String> procedures;
	    private List<String> drugs;
	    private List<String> operations;
	    
		public PatientCard(int id, Person doctor, Person patient, Person nurse, Statuses status, Diagnosis diagnosis,
				Diagnosis finalDiagnosis, List<String> procedures, List<String> drugs, List<String> operations) {
			this.id = id;
			this.doctor = doctor;
			this.patient = patient;
			this.nurse = nurse;
			this.status = status;
			this.diagnosis = diagnosis;
			this.finalDiagnosis = finalDiagnosis;
			this.procedures = procedures;
			this.drugs = drugs;
			this.operations = operations;
		}

		public PatientCard() {
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public Person getDoctor() {
			return doctor;
		}

		public void setDoctor(Person doctor) {
			this.doctor = doctor;
		}

		public Person getPatient() {
			return patient;
		}

		public void setPatient(Person patient) {
			this.patient = patient;
		}

		public Person getNurse() {
			return nurse;
		}

		public void setNurse(Person nurse) {
			this.nurse = nurse;
		}

		public Statuses getStatus() {
			return status;
		}

		public void setStatus(Statuses status) {
			this.status = status;
		}

		public Diagnosis getDiagnosis() {
			return diagnosis;
		}

		public void setDiagnosis(Diagnosis diagnosis) {
			this.diagnosis = diagnosis;
		}

		public Diagnosis getFinalDiagnosis() {
			return finalDiagnosis;
		}

		public void setFinalDiagnosis(Diagnosis finalDiagnosis) {
			this.finalDiagnosis = finalDiagnosis;
		}

		public List<String> getProcedures() {
			return procedures;
		}

		public void setProcedures(List<String> procedures) {
			this.procedures = procedures;
		}

		public List<String> getDrugs() {
			return drugs;
		}

		public void setDrugs(List<String> drugs) {
			this.drugs = drugs;
		}

		public List<String> getOperations() {
			return operations;
		}

		public void setOperations(List<String> operations) {
			this.operations = operations;
		}

		public static List<String> parseData(String data) {
	        List<String> proceduresList = new ArrayList<>();

	        if (data != null && !data.isEmpty()) {
	            if (",".equals(data.substring(data.length() - 1))) {
	                data = data.substring(0, data.length() - 1);
	            }

	            proceduresList = Arrays.asList(data.split(","));
	            System.out.println("proceduresList: " + proceduresList);
	        }

	        return proceduresList;
	    }
		
		@Override
		public String toString() {
			return "PatientCard [id=" + id + ", doctor=" + doctor + ", patient=" + patient + ", nurse=" + nurse
					+ ", status=" + status + ", diagnosis=" + diagnosis + ", finalDiagnosis=" + finalDiagnosis
					+ ", procedures=" + procedures + ", drugs=" + drugs + ", operations=" + operations + "]";
		}

}
