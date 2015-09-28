package com.meetingscheduler.db;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.meetingscheduler.data.Location;
import com.meetingscheduler.data.Meeting;
import com.meetingscheduler.de.meeting.AddMeetingRequest;
import com.meetingscheduler.de.meeting.DeleteMeetingRequest;
import com.meetingscheduler.de.meeting.UpdateMeetingRequest;
import com.meetingscheduler.de.meeting.ViewMeetingRequest;

/**
 * @author : Prashant Kagwad Copyright (c) 2014 All rights reserved.
 * 
 */
public class MeetingDatabaseOperations {

	public MeetingDatabaseOperations() {
		// TODO Auto-generated constructor stub
	}

	public ArrayList<Meeting> getMeetingDetails(ViewMeetingRequest request) {

		DatabaseConnector dbc = new DatabaseConnector();
		Connection connection = dbc.getDBConnection();
		CallableStatement callableStatment = null;
		ArrayList<Meeting> meetings = new ArrayList<Meeting>();

		try {
			callableStatment = connection
					.prepareCall("{ call viewMeetings(?, ?, ?) }",
							ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_READ_ONLY);

			callableStatment.setLong(1, request.getUserID());
			callableStatment.setDate(2, request.getDate());
			callableStatment.setString(3, request.getCity());
			ResultSet rs = callableStatment.executeQuery();

			if (rs != null) {
				while (rs.next()) {

					Meeting tempMeeting = new Meeting();

					tempMeeting.setMeetingID(rs.getLong("Meeting_ID"));
					tempMeeting.setUserID(rs.getLong("User_ID"));
					tempMeeting.setTitle(rs.getString("Title"));
					tempMeeting.setDate(rs.getDate("Meeting_Date"));
					tempMeeting.setTime(rs.getTime("Meeting_Time"));
					tempMeeting.setDescription(rs.getString("Meeting_Desc"));
					tempMeeting.setDuration(rs.getTime("Meeting_Duration"));

					Location tempLocation = new Location();
					tempLocation.setAddressLine1(rs.getString("Address_Line1"));
					tempLocation.setAddressLine2(rs.getString("Address_Line2"));
					tempLocation.setCity(rs.getString("City"));
					tempLocation.setState(rs.getString("State"));
					tempLocation.setZipCode(rs.getInt("Zipcode"));
					tempMeeting.setLocation(tempLocation);
					meetings.add(tempMeeting);
				}
			}

		} catch (SQLException e) {
			System.out
					.println("SQLException - Failed to fetch meeting details!");
			// e.printStackTrace();;

		} finally {

			try {
				callableStatment.close();

			} catch (SQLException e) {
				System.out
						.println("SQLException - Failed to close callableStatement!");
				// e.printStackTrace();;
			}
		}
		return meetings;
	}

	@SuppressWarnings("deprecation")
	public String addMeeting(AddMeetingRequest request) {

		DatabaseConnector dbc = new DatabaseConnector();
		Connection connection = dbc.getDBConnection();
		CallableStatement callableStatment = null;
		String status = "Failure";

		try {

			// Converting String date to SQL date format for field date
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date parsed = format.parse(request.getDate());
			java.sql.Date sqlDate = new java.sql.Date(parsed.getTime());

			// Converting String time to SQL time format for field time
			String timeString = request.getTime();
			String[] timeSpliter = timeString.split(":");
			int[] time = new int[3];
			for (int i = 0; i < time.length; i++) {
				time[i] = 0;
			}
			for (int i = 0; i < timeSpliter.length; i++) {
				time[i] = Integer.parseInt(timeSpliter[i]);
			}
			java.sql.Time sqlTime = new Time(time[0], time[1], time[2]);

			// Converting String time to SQL time format for field duration.
			String durationString = request.getDuration();
			String[] dateSpliter = durationString.split(":");
			int[] duration = new int[3];
			for (int i = 0; i < duration.length; i++) {
				duration[i] = 0;
			}
			for (int i = 0; i < dateSpliter.length; i++) {
				duration[i] = Integer.parseInt(dateSpliter[i]);
			}
			java.sql.Time sqlDuration = new Time(duration[0], duration[1],
					duration[2]);

			// System.out.println(sqlTime);
			// System.out.println(sqlDuration);

			callableStatment = connection
					.prepareCall(
							"{ call addMeetings(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) }",
							ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_READ_ONLY);

			callableStatment.setString(1, request.getTitle());
			callableStatment.setInt(2, request.getUserID());
			callableStatment.setDate(3, sqlDate);
			callableStatment.setTime(4, sqlTime);
			callableStatment.setTime(5, sqlDuration);
			callableStatment.setString(6, request.getLocation()
					.getAddressLine1());
			callableStatment.setString(7, request.getLocation()
					.getAddressLine2());
			callableStatment.setString(8, request.getLocation().getCity());
			callableStatment.setString(9, request.getLocation().getState());
			callableStatment.setInt(10, request.getLocation().getZipCode());
			callableStatment.setString(11, request.getStatus());
			callableStatment.setString(12, request.getDescription());

			ResultSet rs = callableStatment.executeQuery();

			if (rs != null) {
				rs.next();
				status = rs.getString("result");
			}

		} catch (SQLException e) {
			System.out.println("SQLException - Failed to add a meeting - "
					+ request.getTitle() + " by " + request.getUserID());
			// e.printStackTrace();;

		} catch (ParseException e) {
			System.out.println("ParseException - Failed to add a meeting - "
					+ request.getTitle() + " by " + request.getUserID());
			// e.printStackTrace();;
		} finally {

			try {
				callableStatment.close();

			} catch (SQLException e) {
				System.out
						.println("SQLException - Failed to close callableStatement!");
				// e.printStackTrace();;
			}
		}
		return status;
	}

	public String updateMeeting(UpdateMeetingRequest request) {

		DatabaseConnector dbc = new DatabaseConnector();
		Connection connection = dbc.getDBConnection();
		CallableStatement callableStatment = null;
		String status = "Failure";

		try {
			callableStatment = connection
					.prepareCall(
							"{ call updateMeetings(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}",
							ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_READ_ONLY);

			callableStatment.setString(1, request.getTitle());
			callableStatment.setLong(2, request.getMeetingID());
			callableStatment.setDate(3, request.getDate());
			callableStatment.setTime(4, request.getTime());
			callableStatment.setTime(5, request.getDuration());
			callableStatment.setString(6, request.getLocation()
					.getAddressLine1());
			callableStatment.setString(7, request.getLocation()
					.getAddressLine2());
			callableStatment.setString(8, request.getLocation().getCity());
			callableStatment.setString(9, request.getLocation().getState());
			callableStatment.setInt(10, request.getLocation().getZipCode());
			callableStatment.setString(11, request.getStatus());
			callableStatment.setString(12, request.getDescription());

			ResultSet rs = callableStatment.executeQuery();

			if (rs != null) {
				rs.next();
				status = rs.getString("result");
			}

		} catch (SQLException e) {
			System.out
					.println("SQLException - Failed to update meeting with meeting ID -  "
							+ request.getMeetingID());
			// e.printStackTrace();;
		} finally {

			try {
				callableStatment.close();

			} catch (SQLException e) {
				System.out
						.println("SQLException - Failed to close callableStatement!");
				// e.printStackTrace();;
			}
		}
		return status;
	}

	public String deleteMeeting(DeleteMeetingRequest request) {

		DatabaseConnector dbc = new DatabaseConnector();
		Connection connection = dbc.getDBConnection();
		CallableStatement callableStatment = null;
		String status = "Failure";

		try {
			callableStatment = connection
					.prepareCall("{ call deleteMeetings(?) }",
							ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_READ_ONLY);

			callableStatment.setLong(1, request.getMeetingID());

			ResultSet rs = callableStatment.executeQuery();

			if (rs != null) {
				rs.next();
				status = rs.getString("result");
			}

		} catch (SQLException e) {
			System.out
					.println("SQLException - Failed to delete meeting with meeting ID - "
							+ request.getMeetingID());
			// e.printStackTrace();;
		} finally {

			try {
				callableStatment.close();

			} catch (SQLException e) {
				System.out
						.println("SQLException - Failed to close callableStatement!");
				// e.printStackTrace();;
			}
		}
		return status;
	}

	public ArrayList<Meeting> getAllMeetings() {

		DatabaseConnector dbc = new DatabaseConnector();
		Connection connection = dbc.getDBConnection();
		CallableStatement callableStatment = null;
		ArrayList<Meeting> meetings = new ArrayList<Meeting>();

		try {
			callableStatment = connection
					.prepareCall("{ call viewAllMeetings() }",
							ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = callableStatment.executeQuery();

			if (rs != null) {
				while (rs.next()) {

					Meeting tempMeeting = new Meeting();
					tempMeeting.setMeetingID(rs.getLong("Meeting_ID"));
					tempMeeting.setUserID(rs.getLong("User_ID"));
					tempMeeting.setTitle(rs.getString("Title"));
					tempMeeting.setDate(rs.getDate("Meeting_Date"));
					tempMeeting.setTime(rs.getTime("Meeting_Time"));
					tempMeeting.setDescription(rs.getString("Meeting_Desc"));
					tempMeeting.setDuration(rs.getTime("Meeting_Duration"));

					Location tempLocation = new Location();
					tempLocation.setAddressLine1(rs.getString("Address_Line1"));
					tempLocation.setAddressLine2(rs.getString("Address_Line2"));
					tempLocation.setCity(rs.getString("City"));
					tempLocation.setState(rs.getString("State"));
					tempLocation.setZipCode(rs.getInt("Zipcode"));
					tempMeeting.setLocation(tempLocation);
					meetings.add(tempMeeting);
				}
			}

		} catch (SQLException e) {
			System.out
					.println("SQLException - Failed to fetch meeting details!");
			// e.printStackTrace();;

		} finally {

			try {
				callableStatment.close();

			} catch (SQLException e) {
				System.out
						.println("SQLException - Failed to close callableStatement!");
				// e.printStackTrace();;
			}
		}
		return meetings;
	}
}
