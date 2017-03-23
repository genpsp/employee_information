package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.dao.DepartmentDAO;
import model.dao.ImageDAO;

public class CSVInOut {
	public List<Employee> searchEmployee(List<Employee> employeeList) {
		Connection con = null;
		final String DB_URI = "jdbc:mysql://localhost/employee_information?useUnicode=true&characterEncoding=utf8";

		List<Employee> mutterList = new ArrayList<Employee>();
		DepartmentDAO departDao = new DepartmentDAO();
		ImageDAO imageDao = new ImageDAO();

		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(DB_URI, "root", "genpsp10");

			StringBuilder sb = new StringBuilder();
			for (Employee emp : employeeList) {
				sb.append("'" + emp.getEmpID() + "',");
			}
			sb.deleteCharAt(sb.lastIndexOf(","));

			String sql = "SELECT * FROM EMPINF WHERE ID IN (" + sb.toString() + ")";
			PreparedStatement pstmt = con.prepareStatement(sql);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
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
