package model;

public class User {
	private String id;
	private String name;
	private String pass;
	private String mailAddress;

	public User(String id, String name, String pass, String mailAddress) {
		super();
		this.id = id;
		this.name = name;
		this.pass = pass;
		this.mailAddress = mailAddress;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getMailAddress() {
		return mailAddress;
	}

	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}

}
