package model;

public class Employee {
	private String empID;
	private String name;
	private String age;
	private int sex;
	private Image image;
	private String addressNum;
	private String city;
	private String address;
	private Department department;
	private String enterDate;
	private String retireDate;

	public Employee(String empID, String name, String age, int sex, Image Image, String addressNum, String city,
			String address, Department department, String enterDate, String retireDate) {
		super();
		this.empID = empID;
		this.name = name;
		this.age = age;
		this.sex = sex;
		this.image = Image;
		this.addressNum = addressNum;
		this.city = city;
		this.address = address;
		this.department = department;
		this.enterDate = enterDate;
		this.retireDate = retireDate;
	}

	public Employee(String empID, String name) {
		super();
		this.empID = empID;
		this.name = name;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAddressNum() {
		return addressNum;
	}

	public void setAddressNum(String addressNum) {
		this.addressNum = addressNum;
	}

	public String getEnterDate() {
		return enterDate;
	}

	public void setEnterDate(String enterDate) {
		this.enterDate = enterDate;
	}

	public String getRetireDate() {
		return retireDate;
	}

	public void setRetireDate(String retireDate) {
		this.retireDate = retireDate;
	}

	public String getEmpID() {
		return empID;
	}

	public void setEmpID(String empID) {
		this.empID = empID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image Image) {
		this.image = Image;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}
}
