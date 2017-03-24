package model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.User;

public class UserDAO {
	Connection con = null;
	final String DB_URI = "jdbc:mysql://localhost/employee_information?useUnicode=true&characterEncoding=utf8";

	public User searchUser(String ID) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(DB_URI, "root", "i-standard");

			String sql = "SELECT IMG FROM IMAGES WHERE ID = ?";
			PreparedStatement pstmt = con.prepareStatement(sql);

			pstmt.setString(1, ID);
			ResultSet rs = pstmt.executeQuery();
			rs.next();

			String id = rs.getString("ID");
			String pass = rs.getString("PASS");

			User user = new User(id, pass);

			return user;
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		} finally {
			conClose(con);
		}
	}

	public boolean addUser(User user) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(DB_URI, "root", "i-standard");

			String sql = "INSERT INTO USER(ID,PASS) VALUES(?,HEX(AES_ENCRYPT(?, 'i-standard')))";
			PreparedStatement pstmt = con.prepareStatement(sql);

			pstmt.setString(1, user.getId());
			pstmt.setString(2, user.getPass());

			pstmt.executeUpdate();
			return true;

		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		} finally {
			conClose(con);
		}
	}

	public boolean confirmUser(User user) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(DB_URI, "root", "i-standard");


			String sql = "SELECT CONVERT(AES_DECRYPT(UNHEX(PASS),'i-standard') USING utf8) PASS FROM USER WHERE ID = ?";
			PreparedStatement pstmt = con.prepareStatement(sql);

			pstmt.setString(1, user.getId());
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				boolean result = rs.getString("PASS").equals(user.getPass());
				return result;
			}else{
				return false;
			}

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
			Connection con = DriverManager.getConnection(DB_URI, "root", "i-standard");

			String id = "EMP001";

			String sql = "SELECT ID FROM USER ORDER BY ID DESC LIMIT 1";
			PreparedStatement pstmt = con.prepareStatement(sql);

			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				String[] idStr = rs.getString("ID").split("EMP");

				int idNum = Integer.parseInt(idStr[1]) + 1;
				id = "EMP" + String.format("%03d", idNum);
			}
			return id;

		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		} finally {
			conClose(con);
		}
	}

	private void conClose(Connection con) {
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
