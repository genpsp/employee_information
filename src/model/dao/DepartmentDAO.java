package model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Department;

public class DepartmentDAO {
	Connection con = null;
	List<Department> mutterList = new ArrayList<Department>();
	private final String DB_URI = "jdbc:mysql://localhost/employee_information?useUnicode=true&characterEncoding=utf8";

	public List<Department> findAll() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(DB_URI, "root", "genpsp10");

			String sql = "SELECT * FROM DEPARTMENTS";
			PreparedStatement pstmt = con.prepareStatement(sql);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				String departID = rs.getString("ID");
				String depart = rs.getString("DEPARTMENT");

				Department department = new Department(departID, depart);
				mutterList.add(department);
			}
			return mutterList;
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		} finally {
			conClose(con);
		}
	}

	public boolean addDepartment(Department department) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(DB_URI, "root", "genpsp10");

			String sql = "INSERT INTO DEPARTMENTS(ID, DEPARTMENT) VALUES(?, ?)";
			PreparedStatement pstmt = con.prepareStatement(sql);

			pstmt.setString(1, department.getId());
			pstmt.setString(2, department.getDepartment());

			pstmt.executeUpdate();

			return true;

		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		} finally {
			conClose(con);
		}
	}

	public boolean updateDepartment(Department department) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(DB_URI, "root", "genpsp10");

			String sql = "UPDATE DEPARTMENTS SET DEPARTMENT = ? WHERE ID = ?";
			PreparedStatement pstmt = con.prepareStatement(sql);

			pstmt.setString(1, department.getDepartment());
			pstmt.setString(2, department.getId());

			pstmt.executeUpdate();

			return true;

		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		} finally {
			conClose(con);
		}
	}

	public boolean deleteDepartment(String id) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(DB_URI, "root", "genpsp10");

			String sql = "DELETE FROM DEPARTMENTS WHERE ID = ?";
			PreparedStatement pstmt = con.prepareStatement(sql);

			pstmt.setString(1, id);

			int result = pstmt.executeUpdate();
			if (result != 1) {
				return false;
			}
			return true;

		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		} finally {
			conClose(con);
		}
	}

	public Department searchDepartment(String id) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(DB_URI, "root", "genpsp10");

			String sql = "SELECT ID,DEPARTMENT FROM DEPARTMENTS WHERE ID = ?";
			PreparedStatement pstmt = con.prepareStatement(sql);

			pstmt.setString(1, id);
			ResultSet rs = pstmt.executeQuery();
			rs.next();

			String departID = rs.getString("ID");
			String depart = rs.getString("DEPARTMENT");
			Department department = new Department(departID, depart);

			return department;
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		} finally {
			conClose(con);
		}
	}

	public String getLastIDNum() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(DB_URI, "root", "genpsp10");

			String sql = "SELECT ID FROM DEPARTMENTS ORDER BY ID DESC LIMIT 1";
			PreparedStatement pstmt = con.prepareStatement(sql);

			ResultSet rs = pstmt.executeQuery();
			String id;

			if (rs.next()) {
				String[] idStr = rs.getString("ID").split("D");

				int idNum = Integer.parseInt(idStr[1]) + 1;
				id = String.valueOf(idNum);

				if (id.length() == 1) {
					id = "0" + id;
				}
				id = "D" + id;
			} else {
				id = "D01";
			}

			return id;

		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		} finally {
			conClose(con);
		}
	}

	public void conClose(Connection con) {
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
