package model;

public class Position {
	private String id;
	private String position;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public Position(String id, String position) {
		super();
		this.id = id;
		this.position = position;
	}

}
