package com.meetingscheduler.utilities;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.meetingscheduler.data.Location;
import com.meetingscheduler.data.Meeting;
import com.meetingscheduler.data.User;
import com.meetingscheduler.db.MeetingDatabaseOperations;
import com.meetingscheduler.db.UserDatabaseOperations;
import com.meetingscheduler.de.meeting.AddMeetingRequest;
import com.meetingscheduler.de.meeting.DeleteMeetingRequest;
import com.meetingscheduler.de.meeting.UpdateMeetingRequest;
import com.meetingscheduler.de.meeting.ViewMeetingRequest;
import com.meetingscheduler.de.user.AddUserRequest;
import com.meetingscheduler.de.user.DeleteUserRequest;
import com.meetingscheduler.de.user.UpdateUserRequest;
import com.meetingscheduler.de.user.UserPortfolioRequest;

public class utility {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws ParseException {
		// TODO Auto-generated method stub

		getUser();
		// addUser();
		// deleteUser();
		// updateUser();
		// getMeetingDetails();
		// addMeeting();
		// updateMeeting();
		// deleteMeeting();
		// getAllUsers();
		// getAllMeetings();

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date parsed = format.parse("2014-04-29");
		java.sql.Date sqlDate = new java.sql.Date(parsed.getTime());
		System.out.println(sqlDate);

		String timeString = "15:30";
		String[] timeSpliter = timeString.split(":");

		int[] time = new int[3];
		for (int i = 0; i < time.length; i++) {
			time[i] = 0;
		}
		for (int i = 0; i < timeSpliter.length; i++) {
			time[i] = Integer.parseInt(timeSpliter[i]);
		}
		java.sql.Time sqlTime = new Time(time[0], time[1], time[2]);
		System.out.println(sqlTime);

		String durationString = "1";
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
		System.out.println(sqlDuration);

	}

	public static void getAllUsers() {

		Gson gson = new Gson();

		UserDatabaseOperations dbo = new UserDatabaseOperations();
		ArrayList<User> users = dbo.getAllUsers();

		System.out.println(gson.toJson(users));
	}

	public static void getUser() {
		UserPortfolioRequest x = new UserPortfolioRequest();
		x.setUserName("sourav");
		x.setPassword("1234");

		Gson gson = new Gson();
		String request = gson.toJson(x);
		System.out.println(request);

		UserDatabaseOperations dbo = new UserDatabaseOperations();
		User user = dbo.getUserPortfolio(x);

		System.out.println("client " + gson.toJson(user));
	}

	public static void addUser() {

		AddUserRequest x = new AddUserRequest();
		x.setUserName("MATT");
		x.setPassword("TRON");
		x.setEmailID("MATTTRON@GMAIL.COM");
		x.setFirstName("MATT");
		x.setLastName("TRON");
		x.setOrganization("DC COMICS");
		x.setPhoneNumber("5466533344");
		x.setStatus("A");
		x.setUserType("N");

		Gson gson = new Gson();
		String request = gson.toJson(x);
		System.out.println(request);

		UserDatabaseOperations dbo = new UserDatabaseOperations();
		String status = dbo.addUser(x);

		System.out.println(status);
	}

	public static void updateUser() {

		UpdateUserRequest x = new UpdateUserRequest();

		x.setUserName("Prasanjit");
		x.setPassword("abc");
		x.setEmailID("pkhuntia@gmail.com");
		x.setFirstName("Prasanjit");
		x.setLastName("Khuntia");
		x.setOrganization("LOST");
		x.setPhoneNumber(1466533344);
		x.setStatus("A");
		x.setUserType("N");
		x.setUserID(1004);
		Gson gson = new Gson();
		String request = gson.toJson(x);
		System.out.println(request);

		UserDatabaseOperations dbo = new UserDatabaseOperations();
		String status = dbo.updateUser(x);

		System.out.println(status);
	}

	public static void deleteUser() {

		DeleteUserRequest x = new DeleteUserRequest();
		x.setUserID(1002);

		Gson gson = new Gson();
		String request = gson.toJson(x);
		System.out.println(request);

		UserDatabaseOperations dbo = new UserDatabaseOperations();
		String status = dbo.deleteUser(x);

		System.out.println(status);
	}

	public static void getAllMeetings() {

		Gson gson = new Gson();

		MeetingDatabaseOperations dbo = new MeetingDatabaseOperations();
		ArrayList<Meeting> meetings = dbo.getAllMeetings();

		System.out.println(gson.toJson(meetings));
	}

	public static void getMeetingDetails() {

		java.util.Date utilDate = new java.util.Date();
		java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
		ViewMeetingRequest x = new ViewMeetingRequest();
		x.setUserID(1000);
		x.setDate(sqlDate);
		x.setCity("Dallas");

		Gson gson = new Gson();
		String request = gson.toJson(x);
		System.out.println(request);

		MeetingDatabaseOperations dbo = new MeetingDatabaseOperations();
		ArrayList<Meeting> meeting = dbo.getMeetingDetails(x);

		System.out.println(gson.toJson(meeting));
	}

	@SuppressWarnings("deprecation")
	public static void addMeeting() {

		java.util.Date utilDate = new java.util.Date();
		java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
		java.sql.Time time = new Time(13, 0, 0);
		java.sql.Time duration = new Time(1, 0, 0);

		Location location = new Location();
		location.setAddressLine1("2400 W Parkway");
		location.setAddressLine2("Coit Road");
		location.setCity("Dallas");
		location.setState("Texas");
		location.setZipCode(75252);

		AddMeetingRequest x = new AddMeetingRequest();
		x.setUserID(1000);
		x.setTitle("Meet and Greet");
		x.setLocation(location);
		x.setDate(sqlDate.toString());
		x.setTime(time.toString());
		x.setDuration(duration.toString());
		x.setDescription("Users are meeting for the first time.");
		x.setStatus("A");

		Gson gson = new Gson();
		String request = gson.toJson(x);
		System.out.println(request);

		// MeetingDatabaseOperations dbo = new MeetingDatabaseOperations();
		// String status = dbo.addMeeting(x);

		// System.out.println(status);
	}

	@SuppressWarnings("deprecation")
	public static void updateMeeting() {

		java.util.Date utilDate = new java.util.Date();
		java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
		java.sql.Time time = new Time(13, 0, 0);
		java.sql.Time duration = new Time(1, 0, 0);

		Location location = new Location();
		location.setAddressLine1("Add line");
		location.setAddressLine2("Add line");
		location.setCity("Dallas");
		location.setState("Texas");
		location.setZipCode(75252);

		UpdateMeetingRequest x = new UpdateMeetingRequest();
		x.setMeetingID(1005);
		x.setUserID(1001);
		x.setTitle("Test meet");
		x.setLocation(location);
		x.setDate(sqlDate);
		x.setTime(time);
		x.setDuration(duration);
		x.setDescription("Test Meetings");
		x.setStatus("A");

		Gson gson = new Gson();
		String request = gson.toJson(x);
		System.out.println(request);

		MeetingDatabaseOperations dbo = new MeetingDatabaseOperations();
		String status = dbo.updateMeeting(x);

		System.out.println(status);
	}

	public static void deleteMeeting() {

		DeleteMeetingRequest x = new DeleteMeetingRequest();
		x.setMeetingID(1004);

		Gson gson = new Gson();
		String request = gson.toJson(x);
		System.out.println(request);

		MeetingDatabaseOperations dbo = new MeetingDatabaseOperations();
		String status = dbo.deleteMeeting(x);

		System.out.println(status);
	}
}
