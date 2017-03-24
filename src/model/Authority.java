package model;

public class Authority {
	private String authority;

	public Authority(Employee employee) {
		super();
		String departmentID = employee.getDepartment().getId();
		String positionID = employee.getPosition().getId();

		if(departmentID.equals("D01")){
			this.authority = "admin";
		}else if(positionID.equals("P04")){
			this.authority = "general";
		}else{
			this.authority = "manager";
		}
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

}
