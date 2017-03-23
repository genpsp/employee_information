package model;

public class TypeOfSearch {
	private String department;
	private String empID;
	private String searchString;

	public TypeOfSearch(String department, String empID, String likeString) {
		super();
		this.department = department;
		this.empID = empID;
		this.searchString = likeString;
	}

	public TypeOfSearch(String empID) {
		super();
		this.empID = empID;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getEmpID() {
		return empID;
	}

	public void setEmpID(String empID) {
		this.empID = empID;
	}

	public String getSearchString() {
		return searchString;
	}

	public void setSearchString(String likeString) {
		this.searchString = likeString;
	}

}
