package model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Position;

public class PositionDAO {
	Connection con = null;
	final String DB_URI = "jdbc:mysql://localhost/employee_information?useUnicode=true&characterEncoding=utf8";

	public List<Position> findAll() {
		List<Position> mutterList = new ArrayList<Position>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(DB_URI, "root", "i-standard");

			String sql = "SELECT * FROM POSITION";
			PreparedStatement pstmt = con.prepareStatement(sql);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				String id = rs.getString("ID");
				String pos = rs.getString("POSITION");

				Position position = new Position(id, pos);
				mutterList.add(position);
			}
			return mutterList;
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		} finally {
			conClose(con);
		}
	}

	public Position searchPosition(String id) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(DB_URI, "root", "i-standard");

			String sql = "SELECT ID,POSITION FROM POSITION WHERE ID = ?";
			PreparedStatement pstmt = con.prepareStatement(sql);

			pstmt.setString(1, id);
			ResultSet rs = pstmt.executeQuery();
			rs.next();

			String ID = rs.getString("ID");
			String pos = rs.getString("POSITION");
			Position position = new Position(ID, pos);

			return position;
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
