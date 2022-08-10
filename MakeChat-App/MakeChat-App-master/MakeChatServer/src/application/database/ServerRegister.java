package application.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ServerRegister {

	private String url, port;
	
	private String jdbcUrl;
	private String sql;
	
	public ServerRegister(String url, String port) {
		this.url = url;
		this.port = port;
		
		jdbcUrl = "jdbc:mysql://localhost:3306/makechat_db";
	}
	
	public void registerToDb() {

		sql = "SELECT * FROM `server` WHERE `server_url` = ? AND `port_number` = ?";

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(jdbcUrl, "root", "");
			
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, url);
			statement.setString(2, port);
			
//			Query SQL data
			ResultSet resultSet = statement.executeQuery();

			if(resultSet.next()) {
				System.out.println("This server is already in database");
			} else {
				System.out.println("New server is added in database");

				sql = "INSERT INTO `server` (`server_url`, `port_number`) VALUES (?, ?);";
				
				PreparedStatement statement2 = connection.prepareStatement(sql);
				
				statement2.setString(1, url);
				statement2.setString(2, port);
				
				statement2.executeUpdate();
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
