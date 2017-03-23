package model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Department;
import model.Employee;
import model.Image;
import model.TypeOfSearch;

public class EmployeeDAO {
	Connection con = null;
	private final String DB_URI = "jdbc:mysql://localhost/employee_information?useUnicode=true&characterEncoding=utf8";
	ImageDAO imageDao = new ImageDAO();

	public List<Employee> searchEmployee(TypeOfSearch type) {
		List<Employee> mutterList = new ArrayList<Employee>();

		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(DB_URI, "root", "genpsp10");

			String department = type.getDepartment();
			String empID = type.getEmpID();
			String searchString = "%" + type.getSearchString() + "%";
			PreparedStatement pstmt = null;
			String sql = null;

			if (!empID.equals("")) {
				sql = "SELECT ID,NAME FROM EMPINF WHERE ID = ?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, empID);
			} else if (searchString.equals("")) {
				if (department.equals("all")) {
					return searchEmployee();
				}
				sql = "SELECT ID,NAME FROM EMPINF WHERE DEPARTMENT_ID = ?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, department);
			} else {
				if (department.equals("all")) {
					sql = "SELECT ID,NAME FROM EMPINF WHERE NAME LIKE ?";
					pstmt = con.prepareStatement(sql);
					pstmt.setString(1, searchString);
				} else {
					sql = "SELECT ID,NAME FROM EMPINF WHERE NAME LIKE ? AND DEPARTMENT_ID = ?";
					pstmt = con.prepareStatement(sql);
					pstmt.setString(1, searchString);
					pstmt.setString(2, department);
				}
			}

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				empID = rs.getString("ID");
				String name = rs.getString("NAME");

				Employee employee = new Employee(empID, name);
				mutterList.add(employee);
			}
			return mutterList;
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		} finally {
			conClose(con);
		}
	}

	public List<Employee> searchEmployee() {
		List<Employee> mutterList = new ArrayList<Employee>();

		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(DB_URI, "root", "genpsp10");

			String sql = "SELECT ID,NAME FROM EMPINF";
			PreparedStatement pstmt = con.prepareStatement(sql);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				String empID = rs.getString("ID");
				String name = rs.getString("NAME");

				Employee employee = new Employee(empID, name);
				mutterList.add(employee);
			}
			return mutterList;
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		} finally {
			conClose(con);
		}
	}

	public Employee getEmployee(String empID) {
		DepartmentDAO departDao = new DepartmentDAO();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(DB_URI, "root", "genpsp10");

			String sql = "SELECT * FROM EMPINF WHERE ID = ?";
			PreparedStatement pstmt = con.prepareStatement(sql);

			pstmt.setString(1, empID);
			ResultSet rs = pstmt.executeQuery();

			rs.next();
			String ID = rs.getString("ID");
			String name = rs.getString("NAME");
			String age = rs.getString("AGE");
			int sex = rs.getInt("SEX");
			String imageID = rs.getString("IMAGE_ID");
			String addressNum = rs.getString("ADDRESS_NUM");
			String city = rs.getString("CITY");
			String address = rs.getString("ADDRESS");
			String departID = rs.getString("DEPARTMENT_ID");
			Department department = departDao.searchDepartment(departID);
			String enterDate = rs.getString("ENTER_DATE");
			String retireDate = rs.getString("RETIRE_DATE");

			Image image = new Image();
			image.setData(imageDao.getImage(imageID));
			image.setImageID(imageID);

			Employee employee = new Employee(ID, name, age, sex, image, addressNum, city, address, department,
					enterDate, retireDate);

			return employee;
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		} finally {
			conClose(con);
		}
	}

	public boolean addEmployee(Employee employee) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(DB_URI, "root", "genpsp10");

			String sql = "INSERT INTO EMPINF(ID, NAME, AGE, SEX, IMAGE_ID, ADDRESS_NUM, CITY, ADDRESS, DEPARTMENT_ID, ENTER_DATE, RETIRE_DATE) VALUES(?,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement pstmt = con.prepareStatement(sql);

			int count = 1;
			pstmt.setString(count++, employee.getEmpID());
			pstmt.setString(count++, employee.getName());
			pstmt.setString(count++, employee.getAge());
			pstmt.setInt(count++, employee.getSex());
			pstmt.setString(count++, employee.getImage().getImageID());
			pstmt.setString(count++, employee.getAddressNum());
			pstmt.setString(count++, employee.getCity());
			pstmt.setString(count++, employee.getAddress());
			pstmt.setObject(count++, employee.getDepartment().getId());
			pstmt.setString(count++, employee.getEnterDate());
			pstmt.setString(count++, employee.getRetireDate());

			imageDao.addImage(employee.getImage());
			pstmt.executeUpdate();
			return true;

		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		} finally {
			conClose(con);
		}
	}

	public boolean updateEmployee(Employee employee) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(DB_URI, "root", "genpsp10");

			String sql = "UPDATE EMPINF SET ID=?, NAME=?, AGE=?, SEX=?, IMAGE_ID=?, ADDRESS_NUM=?, CITY=?, ADDRESS=?, DEPARTMENT_ID=?, ENTER_DATE=?, RETIRE_DATE=? WHERE ID = ?";
			PreparedStatement pstmt = con.prepareStatement(sql);

			int count = 1;
			pstmt.setString(count++, employee.getEmpID());
			pstmt.setString(count++, employee.getName());
			pstmt.setString(count++, employee.getAge());
			pstmt.setInt(count++, employee.getSex());
			pstmt.setString(count++, employee.getImage().getImageID());
			pstmt.setString(count++, employee.getAddressNum());
			pstmt.setString(count++, employee.getCity());
			pstmt.setString(count++, employee.getAddress());
			pstmt.setObject(count++, employee.getDepartment().getId());
			pstmt.setString(count++, employee.getEnterDate());
			pstmt.setString(count++, employee.getRetireDate());
			pstmt.setString(count++, employee.getEmpID());

			imageDao.updateImage(employee.getImage());
			pstmt.executeUpdate();
			return true;

		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		} finally {
			conClose(con);
		}
	}

	public boolean deleteEmployee(String empID) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(DB_URI, "root", "genpsp10");

			String sql = "SELECT IMAGE_ID FROM EMPINF WHERE ID =?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, empID);
			ResultSet rs = pstmt.executeQuery();
			rs.next();

			imageDao.deleteImage(rs.getString("IMAGE_ID"));

			sql = "DELETE FROM EMPINF WHERE ID = ?";
			pstmt = con.prepareStatement(sql);

			pstmt.setString(1, empID);

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

	public String getLastIDNum() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(DB_URI, "root", "genpsp10");

			String sql = "SELECT ID FROM EMPINF ORDER BY ID DESC LIMIT 1";
			PreparedStatement pstmt = con.prepareStatement(sql);

			ResultSet rs = pstmt.executeQuery();
			rs.next();
			String[] idStr = rs.getString("ID").split("EMP");

			int idNum = Integer.parseInt(idStr[1]) + 1;
			String id = "EMP" + String.format("%03d", idNum);

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
