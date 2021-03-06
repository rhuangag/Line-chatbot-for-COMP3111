package com.example.bot.spring;

import lombok.extern.slf4j.Slf4j;
import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.*;
import java.net.URISyntaxException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;

@Slf4j
public class SQLDatabaseEngine extends DatabaseEngine {
	@Override
	String search(String text) throws Exception {
		//Write your code here
		String result=null;
	
		Connection connection = this.getConnection();
		PreparedStatement stmt = connection.prepareStatement("SELECT id, keyword, response FROM response WHERE keyword = ?"); 
		
		try {


			stmt.setString(1,text);
			ResultSet rs = stmt.executeQuery();

			if(rs.next())
				result=rs.getString(3);
			//if (!rs.first())
				//throw new Exception("NOT FOUND");
			rs.close(); stmt.close(); connection.close();

			//else
				//throw new Exception("NOT FOUND");
		} catch (Exception e) {
			System.out.println(e);
			
			//throw new Exception("NOT FOUND");
			}
	
		if (result !=null)
			return result;
		throw new Exception("NOT FOUND");
		//return null;
		
	}
	
	
	private Connection getConnection() throws URISyntaxException, SQLException {
		Connection connection;
		URI dbUri = new URI(System.getenv("DATABASE_URL"));

		String username = dbUri.getUserInfo().split(":")[0];
		String password = dbUri.getUserInfo().split(":")[1];
		String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath() +  "?ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory";

		log.info("Username: {} Password: {}", username, password);
		log.info ("dbUrl: {}", dbUrl);
		
		connection = DriverManager.getConnection(dbUrl, username, password);

		return connection;
	}

}
