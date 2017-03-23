package model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Image;

public class ImageDAO {
	static Connection con = null;
	private static final String DB_URI = "jdbc:mysql://localhost/employee_information?useUnicode=true&characterEncoding=utf8";

	public byte[] getImage(String imageID) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(DB_URI, "root", "genpsp10");

			String sql = "SELECT IMG FROM IMAGES WHERE ID = ?";
			PreparedStatement pstmt = con.prepareStatement(sql);

			pstmt.setString(1, imageID);
			ResultSet rs = pstmt.executeQuery();
			rs.next();

			byte[] imageData = rs.getBytes("IMG");

			return imageData;
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		} finally {
			conClose(con);
		}
	}

	public boolean addImage(Image image) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(DB_URI, "root", "genpsp10");

			String sql = "INSERT INTO IMAGES(ID, IMG) VALUES(?, ?)";
			PreparedStatement pstmt = con.prepareStatement(sql);

			pstmt.setString(1, image.getImageID());
			pstmt.setBytes(2, image.getData());

			pstmt.executeUpdate();

			return true;

		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		} finally {
			conClose(con);
		}
	}

	public boolean updateImage(Image image) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(DB_URI, "root", "genpsp10");

			String sql = "UPDATE IMAGES SET IMG=? WHERE ID=?";
			PreparedStatement pstmt = con.prepareStatement(sql);

			pstmt.setBytes(1, image.getData());
			pstmt.setString(2, image.getImageID());

			pstmt.executeUpdate();

			return true;

		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		} finally {
			conClose(con);
		}
	}

	public boolean deleteImage(String ID) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(DB_URI, "root", "genpsp10");

			String sql = "DELETE FROM IMAGES WHERE ID = ?";
			PreparedStatement pstmt = con.prepareStatement(sql);

			pstmt.setString(1, ID);

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

	public static String getLastIDNum() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(DB_URI, "root", "genpsp10");

			String id = "P001";

			String sql = "SELECT IMAGE_ID FROM EMPINF ORDER BY IMAGE_ID DESC LIMIT 1";
			PreparedStatement pstmt = con.prepareStatement(sql);

			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				String[] idStr = rs.getString("IMAGE_ID").split("P");

				int idNum = Integer.parseInt(idStr[1]) + 1;
				id = "P" + String.format("%03d", idNum);
			}
			return id;

		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			return "0";
		} finally {
			conClose(con);
		}
	}

	public static void conClose(Connection con) {
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
