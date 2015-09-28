package com.meetingscheduler.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author : Prashant Kagwad Copyright (c) 2014 All rights reserved.
 * 
 */
public class DatabaseConnector {

	public DatabaseConnector() {
		// TODO Auto-generated constructor stub
	}

	public Connection getDBConnection() {

		Connection connection = null;
		try {

			Class.forName("com.mysql.jdbc.Driver");

			connection = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/meeting_scheduler", "root",
					"lakers");

			if (connection != null) {
				// System.out.println("Database connected >> ");
			} else {
				System.out.println("Failed to make database connection << ");
			}

		} catch (ClassNotFoundException e) {
			System.out
					.println("ClassNotFoundException - com.mysql.jdbc.Driver class not found!");
			// e.printStackTrace();

		} catch (SQLException e) {
			System.out
					.println("SQLException - Unable to obtain db connection!");
			// e.printStackTrace();
		}

		return connection;
	}

	public void dbCloseConnection(Connection connection) {

		try {
			connection.close();
		} catch (SQLException e) {
			System.out
					.println("SQL Exception - Error Occured while closing db connection.");
		}
	}
}
