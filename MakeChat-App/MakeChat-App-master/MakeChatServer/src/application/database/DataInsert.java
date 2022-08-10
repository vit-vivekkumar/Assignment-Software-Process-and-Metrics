package application.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Arafin
 *
 */

public class DataInsert {
	
	private String url, port, userName, message, date;
	
	private String jdbcUrl;
	private String sql;
	
	public DataInsert(String url, String port, String userName, String message,String date) {
		this.url = url;
		this.port = port;
		this.userName = userName;
		this.message = message;
		this.date = date;
		
		jdbcUrl = "jdbc:mysql://localhost:3306/makechat_db";
	}
	
	public void insert() {

		sql = "SELECT * FROM `server` WHERE `server_url` = ? AND `port_number` = ?";

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(jdbcUrl, "root", "");
			
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, url);
			statement.setString(2, port);
			
//			Query SQL data
			ResultSet resultSet = statement.executeQuery();

			String id = null;
			if(resultSet.next()) {
				id = resultSet.getString("server_id");
			}
			
			sql = "INSERT INTO `messages` (`user_name`, `message`, `date`, `server_id`) VALUES (?, ?, ?, ?);";
			
			PreparedStatement statement2 = connection.prepareStatement(sql);
			
			statement2.setString(1, userName);
			statement2.setString(2, message);
			statement2.setString(3, date);
			statement2.setString(4, id);
			
			statement2.executeUpdate();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
