package ua.nure.mirzoiev.hospitalSystem.entity;

public enum Role {
    ADMIN(0, "admin"), DOCTOR(1, "doctor"), NURSE(2, "nurse"), PATIENT(3, "patient");
    private int id;
    private String name;

    Role(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static Role getRoleByName(String name) {
        Role findRole = null;
        for (Role role : values()) {
            if (role.getName().equals(name)) {
                findRole = role;
            }
        }

        return findRole;
    }

    public static Role getRoleById(int id) {
        Role findRole = null;
        for (Role role : values()) {
            if (role.getId() == id) {
                findRole = role;
            }
        }
        System.out.println("findRole: " + findRole);
        return findRole;
    }
    @Override
    public String toString() {
        return name;
    }
}
