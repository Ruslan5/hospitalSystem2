package ua.nure.mirzoiev.hospitalSystem.entity;

public enum Diagnosis {

    ORVI("orvi"), DROP("drop"), HEADACHE("Headache"), STRETCHING("stretching");

    private String diagnosis;

    Diagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }


    @Override
    public String toString() {
        return diagnosis;
    }

    public static Diagnosis getDiagnosisByName(String name) {
        Diagnosis findDiagnosis = null;
        for (Diagnosis diagnosis : values()) {
            if (diagnosis.getName().equals(name)) {
                findDiagnosis = diagnosis;
            }
        }

        return findDiagnosis;
    }

    public String getName() {
        return diagnosis;
    }
}
