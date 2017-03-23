package model;

public class Department {
	private String id;
	private String department;

	public Department(String id, String department) {
		super();
		this.id = id;
		this.department = department;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

}
