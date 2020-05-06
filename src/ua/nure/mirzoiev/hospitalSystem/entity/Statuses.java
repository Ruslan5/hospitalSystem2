package ua.nure.mirzoiev.hospitalSystem.entity;

public enum Statuses {
	//ОБСЛЕДОВАНИЕ               ЛЕЧЕНИЕ                  ВЫПИСАН
    EXAMINATION("examination"), TREATMENT("treatment"), DISCHARGED("discharged");

    private String status;

    Statuses(String status) {
        this.status = status;
    }


    @Override
    public String toString() {
        return status;
    }

    public static Statuses getStatusByName(String name) {
        Statuses findStatus = null;
        for (Statuses patientStatus : values()) {
            if (patientStatus.getName().equals(name)) {
                findStatus= patientStatus;
            }
        }
        return findStatus;
    }

    public String getName() {
        return status;
    }
}
