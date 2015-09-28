package com.meetingscheduler.db;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.meetingscheduler.data.User;
import com.meetingscheduler.de.user.AddUserRequest;
import com.meetingscheduler.de.user.DeleteUserRequest;
import com.meetingscheduler.de.user.UpdateUserRequest;
import com.meetingscheduler.de.user.UserPortfolioRequest;
import com.meetingscheduler.memcached.MemcachedConnector;
import com.p5.xmemcache.MemcachedClient;

/**
 * @author : Prashant Kagwad Copyright (c) 2014 All rights reserved.
 * 
 */
public class UserDatabaseOperations {

	public UserDatabaseOperations() {
		// TODO Auto-generated constructor stub
	}

	public User getUserPortfolio(UserPortfolioRequest request) {

		MemcachedConnector mc = new MemcachedConnector();
		DatabaseConnector dbc = new DatabaseConnector();
		Connection connection = dbc.getDBConnection();
		CallableStatement callableStatment = null;
		User user = null;
		Gson gson = new Gson();

		try {

			// First search in cache
			MemcachedClient memcache = mc.getMemcachedConnector();
			if (memcache.get(request.getUserName()) != null) {

				user = new User();
				System.out.println("User Info extracted from cache.");
				String tempUser = (String) memcache.get(request.getUserName());
				if (tempUser != null) {
					user = gson.fromJson(tempUser, User.class);
				}

			} else {
				// else search in database
				System.out.println("User Info extracted from database.");
				callableStatment = connection
						.prepareCall("{ call viewUser(?, ?) }");

				callableStatment.setString(1, request.getUserName());
				callableStatment.setString(2, request.getPassword());
				ResultSet rs = callableStatment.executeQuery();

				if (rs != null) {
					while (rs.next()) {
						user = new User();
						user.setFirstName(rs.getString("FIRST_NAME"));
						user.setLastName(rs.getString("LAST_NAME"));
						user.setOrganization(rs.getString("ORGANIZATION"));
						user.setPhoneNumber(rs.getLong("PHONE"));
						user.setUserID(rs.getInt("USER_ID"));
						user.setUserType(rs.getString("TYPE"));
						user.setEmailID(rs.getString("EMAIL_ID"));
					}
				}

				// add to cache
				String jsonUser = gson.toJson(user);
				if (!memcache.add(request.getUserName(), jsonUser)) {
					memcache.set(request.getUserName(), jsonUser);
				}

				try {
					callableStatment.close();

				} catch (SQLException e) {
					System.out
							.println("SQLException - Failed to close callableStatement!");
					// e.printStackTrace();
				}
			}

		} catch (SQLException e) {
			System.out.println("SQLException - Failed to fetch user data!");
			// e.printStackTrace();
		}
		return user;
	}

	public String addUser(AddUserRequest request) {

		DatabaseConnector dbc = new DatabaseConnector();
		Connection connection = dbc.getDBConnection();
		CallableStatement callableStatment = null;
		String status = "Failure";

		try {
			callableStatment = connection
					.prepareCall("{ call addUser(?, ?, ?, ?, ?, ?, ?, ?, ?) }",
							ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_READ_ONLY);

			callableStatment.setString(1, request.getLastName());
			callableStatment.setString(2, request.getFirstName());
			callableStatment.setString(3, request.getUserType());
			callableStatment.setString(4, request.getEmailID());
			callableStatment.setLong(5,
					Long.parseLong(request.getPhoneNumber()));
			callableStatment.setString(6, request.getOrganization());
			callableStatment.setString(7, request.getUserName());
			callableStatment.setString(8, request.getPassword());
			callableStatment.setString(9, request.getStatus());

			ResultSet rs = callableStatment.executeQuery();

			if (rs != null) {
				rs.next();
				status = rs.getString("result");
			}

		} catch (SQLException e) {
			System.out.println("SQLException - Failed to add user with FName -"
					+ request.getFirstName() + " and LName - "
					+ request.getLastName());
			// e.printStackTrace();
			status = "Failed to add user with FName -" + request.getFirstName()
					+ " and LName - " + request.getLastName();

		} finally {

			try {
				callableStatment.close();

			} catch (SQLException e) {
				System.out
						.println("SQLException - Failed to close callableStatement!");
				// e.printStackTrace();
			}
		}
		return status;
	}

	public String updateUser(UpdateUserRequest request) {

		DatabaseConnector dbc = new DatabaseConnector();
		Connection connection = dbc.getDBConnection();
		CallableStatement callableStatment = null;
		String status = "Failure";

		try {
			callableStatment = connection
					.prepareCall(
							"{ call updateUser(?, ?, ?, ?, ?, ?, ?, ?, ?, ?) }",
							ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_READ_ONLY);

			callableStatment.setLong(1, request.getUserID());
			callableStatment.setString(2, request.getLastName());
			callableStatment.setString(3, request.getFirstName());
			callableStatment.setString(4, request.getUserType());
			callableStatment.setString(5, request.getEmailID());
			callableStatment.setLong(6, request.getPhoneNumber());
			callableStatment.setString(7, request.getOrganization());
			callableStatment.setString(8, request.getUserName());
			callableStatment.setString(9, request.getPassword());
			callableStatment.setString(10, request.getStatus());

			ResultSet rs = callableStatment.executeQuery();

			if (rs != null) {
				rs.next();
				status = rs.getString("result");
			}

		} catch (SQLException e) {
			System.out
					.println("SQLException - Failed to update user with user ID - "
							+ request.getUserID());
			// e.printStackTrace();
			status = "Failed to update user with user ID - "
					+ request.getUserID();

		} finally {

			try {
				callableStatment.close();

			} catch (SQLException e) {
				System.out
						.println("SQLException - Failed to close callableStatement!");
				// e.printStackTrace();
			}
		}
		return status;
	}

	public String deleteUser(DeleteUserRequest request) {

		DatabaseConnector dbc = new DatabaseConnector();
		Connection connection = dbc.getDBConnection();
		CallableStatement callableStatment = null;
		String status = "Failure";

		try {
			callableStatment = connection
					.prepareCall("{ call deleteUser(?) }",
							ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_READ_ONLY);

			callableStatment.setLong(1, request.getUserID());

			ResultSet rs = callableStatment.executeQuery();

			if (rs != null) {

				rs.next();
				status = rs.getString("result");
			}

		} catch (SQLException e) {
			System.out
					.println("SQLException - Failed to delete user with user ID - "
							+ request.getUserID());
			// e.printStackTrace();
			status = " Failed to delete user with user ID - "
					+ request.getUserID();
		} finally {

			try {
				callableStatment.close();

			} catch (SQLException e) {
				System.out
						.println("SQLException - Failed to close callableStatement!");
				// e.printStackTrace();
			}
		}
		return status;
	}

	public ArrayList<User> getAllUsers() {

		DatabaseConnector dbc = new DatabaseConnector();
		Connection connection = dbc.getDBConnection();
		CallableStatement callableStatment = null;
		ArrayList<User> users = new ArrayList<User>();

		try {
			callableStatment = connection.prepareCall("{ call viewAllUser() }");
			ResultSet rs = callableStatment.executeQuery();

			if (rs != null) {
				while (rs.next()) {

					User tempUser = new User();
					tempUser = new User();
					tempUser.setFirstName(rs.getString("FIRST_NAME"));
					tempUser.setLastName(rs.getString("LAST_NAME"));
					tempUser.setOrganization(rs.getString("ORGANIZATION"));
					tempUser.setPhoneNumber(rs.getLong("PHONE"));
					tempUser.setUserID(rs.getInt("USER_ID"));
					tempUser.setUserType(rs.getString("TYPE"));
					tempUser.setEmailID(rs.getString("EMAIL_ID"));
					users.add(tempUser);
				}
			}

		} catch (SQLException e) {
			System.out.println("SQLException - Failed to fetch user data!");
			// e.printStackTrace();

		} finally {

			try {
				callableStatment.close();

			} catch (SQLException e) {
				System.out
						.println("SQLException - Failed to close callableStatement!");
				// e.printStackTrace();
			}
		}
		return users;
	}
}
