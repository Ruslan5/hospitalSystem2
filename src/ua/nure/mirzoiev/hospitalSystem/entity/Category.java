package ua.nure.mirzoiev.hospitalSystem.entity;
/**
 * Category of doctors entity.
 * 
 * @author R.Mirzoiev
 * 
 */
public enum Category {
	PEDIATRICAN(0, "pediatrician"), TRAUMATOLOGIST(1, "traumatologist"), SURGEON(2, "surgeon");

	 private int id;
	 private String name;

	 Category(int id, String name) {
	        this.id = id;
	        this.name = name;
	    }

	    public int getId() {
	        return id;
	    }

	    public String getName() {
	        return name;
	    }

	    public static Category getCategoryByName(String name) {
	    	Category findCategory = null;
	        for (Category role : values()) {
	            if (role.getName().equals(name)) {
	                findCategory = role;
	            }
	        }

	        return findCategory;
	    }

	    public static Category getCategoryById(String name) {
	    	Category findCategory = null;
	        for (Category role : values()) {
	            if (role.getName().equals(name)) {
	                return role;
	            }
	        }
	        System.out.println("findCategory: " + findCategory);
	        return findCategory;
	    }
	    @Override
	    public String toString() {
	        return name;
	    }
}
