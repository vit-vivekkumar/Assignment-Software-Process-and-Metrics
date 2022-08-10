package application.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import application.Message;

/**
 * @author Arafin
 *
 */

public class DataQuery {
	
	private String responseUserName, responseMessage, responseDate;
	
	private String jdbcUrl;
	private String sql;
	
	public DataQuery() {
		jdbcUrl = "jdbc:mysql://localhost:3306/makechat_db";
	}
	
	public ArrayList<Message> retrieveMessages(String url, String port) {
		
		ArrayList<Message> arrayList = new ArrayList<>();

		sql = "SELECT * FROM `server` WHERE `server_url` = ? AND `port_number` = ?";

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(jdbcUrl, "root", "");
			
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, url);
			statement.setString(2, port);
			
//			Query SQL data
			ResultSet resultSetQuery = statement.executeQuery();
			
			String id = null;
			if(resultSetQuery.next()) {
				id = resultSetQuery.getString("server_id");
			}
			
			sql = "SELECT * FROM `messages` WHERE `server_id` = ? ORDER BY `messages`.`message_id` ASC";
			PreparedStatement statement2 = connection.prepareStatement(sql);
			statement2.setString(1, id);
			
//			Query SQL data
			ResultSet resultSet = statement2.executeQuery();

			while(resultSet.next()) {
				Message messageObj = new Message();
				
				responseUserName = resultSet.getString("user_name");
				messageObj.setUserName(responseUserName);
				
				responseMessage = resultSet.getString("message");
				messageObj.setMessage(responseMessage);
				
				responseDate = resultSet.getString("date");
				messageObj.setMsgProcessTime(responseDate);
				
				arrayList.add(messageObj);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return arrayList;
	}
}
