package com.meetingscheduler.ws;

import java.io.IOException;
import java.util.ArrayList;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.jboss.resteasy.annotations.GZIP;
import org.jboss.resteasy.util.Base64;
import com.google.gson.Gson;
import com.meetingscheduler.data.Meeting;
import com.meetingscheduler.data.User;
import com.meetingscheduler.db.MeetingDatabaseOperations;
import com.meetingscheduler.db.UserDatabaseOperations;
import com.meetingscheduler.de.meeting.AddMeetingRequest;
import com.meetingscheduler.de.meeting.AddMeetingResponse;
import com.meetingscheduler.de.meeting.DeleteMeetingRequest;
import com.meetingscheduler.de.meeting.DeleteMeetingResponse;
import com.meetingscheduler.de.meeting.UpdateMeetingRequest;
import com.meetingscheduler.de.meeting.UpdateMeetingResponse;
import com.meetingscheduler.de.meeting.ViewAllMeetingsResponse;
import com.meetingscheduler.de.meeting.ViewMeetingRequest;
import com.meetingscheduler.de.meeting.ViewMeetingResponse;
import com.meetingscheduler.de.user.AddUserRequest;
import com.meetingscheduler.de.user.AddUserResponse;
import com.meetingscheduler.de.user.DeleteUserRequest;
import com.meetingscheduler.de.user.DeleteUserResponse;
import com.meetingscheduler.de.user.UpdateUserRequest;
import com.meetingscheduler.de.user.UpdateUserResponse;
import com.meetingscheduler.de.user.UserPortfolioRequest;
import com.meetingscheduler.de.user.UserPortfolioResponse;
import com.meetingscheduler.de.user.ViewAllUsersResponse;
import com.meetingscheduler.memcached.MemcachedConnector;
import com.p5.xmemcache.MemcachedClient;

// The class registers its methods for the HTTP GET, POST, PUT, DELETE request using the @GET, @POST, @PUT, @DELETE annotation. 
// Using the @Produces/@Consumes annotation, it defines that it can deliver/receive several MIME types,
// text, XML and HTML.  

/**
 * @author : Prashant Kagwad Copyright (c) 2014 All rights reserved.
 * 
 */
@Path("/")
public class WebServices {

	// This method is called if TEXT_PLAIN is request
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String sayPlainTextHello() {
		System.out.println("Web Services are hosted and running...");
		return "Welcome to the web services home page of Meeting Scheduler! Created by - prash";
	}

	// This method is called if XML is request
	@GET
	@Produces(MediaType.TEXT_XML)
	public String sayXMLHello() {
		System.out.println("Web Services are hosted and running...");
		return "<?xml version=\"1.0\"?>"
				+ "<hello> Welcome to the web services home page of Meeting Scheduler! Created by - prash </hello>";
	}

	// This method is called if HTML is request
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String sayHtmlHello() {
		System.out.println("Web Services are hosted and running...");
		return "<html> "
				+ "<title>"
				+ "Meeting Scheduler WS"
				+ "</title>"
				+ "<body><h1>"
				+ "Welcome to the web services home page of Meeting Scheduler! Created by - prash"
				+ "</body></h1>" + "</html> ";
	}

	// http://localhost:8080/MeetingSchedulerMW/ws
	@Path("/ws")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String testWS() {

		System.out.println("Web Services are hosted and running...");
		String response = "Welcome to the web services home page of Meeting Scheduler! Created by - prash";

		return response;
	}

	// http://localhost:8080/MeetingSchedulerMW/cc
	@Path("/cc")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String clearCache() {

		System.out.println("Clearing Memcache...");
		String response = "";

		try {
			MemcachedConnector mc = new MemcachedConnector();
			MemcachedClient memcache = mc.getMemcachedConnector();

			boolean status = memcache.flushAll();
			if (status)
				response = "Cache was successfully cleared.";
			else
				response = "Unable to clear the cache.";
		} catch (Exception e) {
			System.out.println("WS - Exception - Failed to clear the cache!");
			// e.printStackTrace();
			response = "Exception in clearing the cache.";
		}
		return response;
	}

	// TODO - getUserDetails
	// http://localhost:8080/MeetingSchedulerMW/getUserDetails
	@Path("/getUserDetails")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public UserPortfolioResponse getUserDetails(@GZIP String encodedRequest) {

		UserPortfolioResponse response = new UserPortfolioResponse();
		try {

			String jsonRequest = new String(Base64.decode(encodedRequest));
			Gson json = new Gson();

			UserPortfolioRequest request = json.fromJson(jsonRequest,
					UserPortfolioRequest.class);

			System.out.println("/getUserDetails for User Name : "
					+ request.getUserName() + " ...");

			UserDatabaseOperations dbo = new UserDatabaseOperations();
			User user = dbo.getUserPortfolio(request);

			response.setStatus("Failure");
			response.setUser(null);

			if (user != null) {
				response.setStatus("Success");
				response.setUser(user);
			}

		} catch (IOException ioe) {
			System.out
					.println("WS - IOException - Failed to decode incoming request! ");
			// ioe.printStackTrace();

		} catch (Exception e) {
			System.out
					.println("WS - Exception - Failed to Get User Portfolio!");
			// e.printStackTrace();
		}
		return response;

	}

	// TODO - addUser
	// http://localhost:8080/MeetingSchedulerMW/addUser
	@Path("/addUser")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public AddUserResponse addUser(@GZIP String jsonRequest) {

		AddUserResponse response = new AddUserResponse();
		try {

			Gson json = new Gson();

			AddUserRequest request = json.fromJson(jsonRequest,
					AddUserRequest.class);

			System.out.println("/addUser with User Name : "
					+ request.getUserName() + " ...");

			UserDatabaseOperations dbo = new UserDatabaseOperations();
			String status = dbo.addUser(request);

			response.setStatus(status);

		} catch (Exception e) {
			System.out.println("WS - Exception - Failed to Add User!");
			// e.printStackTrace();
		}
		return response;
	}

	// TODO - updateUser
	// http://localhost:8080/MeetingSchedulerMW/updateUser
	@Path("/updateUser")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public UpdateUserResponse updateUser(@GZIP String jsonRequest) {

		UpdateUserResponse response = new UpdateUserResponse();
		try {

			Gson json = new Gson();

			UpdateUserRequest request = json.fromJson(jsonRequest,
					UpdateUserRequest.class);

			System.out.println("/updateUser with User ID : "
					+ request.getUserID() + " ...");

			UserDatabaseOperations dbo = new UserDatabaseOperations();
			String status = dbo.updateUser(request);

			response.setStatus(status);

		} catch (Exception e) {
			System.out.println("WS - Exception - Failed to Update User!");
			// e.printStackTrace();
		}
		return response;
	}

	// TODO - deleteUser
	// http://localhost:8080/MeetingSchedulerMW/deleteUser
	@Path("/deleteUser")
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public DeleteUserResponse deleteUser(@GZIP String jsonRequest) {

		DeleteUserResponse response = new DeleteUserResponse();
		try {

			Gson json = new Gson();
			DeleteUserRequest request = json.fromJson(jsonRequest,
					DeleteUserRequest.class);

			System.out.println("/deleteUser with User ID : "
					+ request.getUserID() + " ...");

			UserDatabaseOperations dbo = new UserDatabaseOperations();
			String status = dbo.deleteUser(request);

			response.setStatus(status);

		} catch (Exception e) {
			System.out.println("WS - Exception - Failed to Delete User!");
			// e.printStackTrace();
		}
		return response;
	}

	// TODO - getAllUsers
	// http://localhost:8080/MeetingSchedulerMW/getAllUsers
	@Path("/getAllUsers")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public ViewAllUsersResponse getAllUsers() {

		ViewAllUsersResponse response = new ViewAllUsersResponse();
		try {

			UserDatabaseOperations dbo = new UserDatabaseOperations();
			ArrayList<User> users = dbo.getAllUsers();

			System.out.println("/getAllUsers ...");
			response.setStatus("Failure");

			if (users != null) {
				response.setStatus("Success");
				response.setUsers(users);
			}

		} catch (Exception e) {
			System.out.println("WS - Exception - Failed to Get All Users!");
			// e.printStackTrace();
		}
		return response;
	}

	// TODO - getMeetingDetails
	// http://localhost:8080/MeetingSchedulerMW/getMeetingDetails
	@Path("/getMeetingDetails")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ViewMeetingResponse getMeetingDetails(@GZIP String jsonRequest) {

		ViewMeetingResponse response = new ViewMeetingResponse();
		try {

			Gson json = new Gson();

			ViewMeetingRequest request = json.fromJson(jsonRequest,
					ViewMeetingRequest.class);

			MeetingDatabaseOperations dbo = new MeetingDatabaseOperations();
			ArrayList<Meeting> meetings = dbo.getMeetingDetails(request);

			System.out.println("/getMeetingDetails ...");
			response.setStatus("Failure");

			if (meetings != null) {
				response.setStatus("Success");
				response.setMeetingDetails(meetings);
			}

		} catch (Exception e) {
			System.out
					.println("WS - Exception - Failed to Get Meeting Details!");
			// e.printStackTrace();
		}
		return response;

	}

	// TODO - addMeeting
	// http://localhost:8080/MeetingSchedulerMW/addMeeting
	@Path("/addMeeting")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public AddMeetingResponse addMeeting(@GZIP String jsonRequest) {

		AddMeetingResponse response = new AddMeetingResponse();
		try {

			Gson json = new Gson();

			AddMeetingRequest request = json.fromJson(jsonRequest,
					AddMeetingRequest.class);

			System.out.println("/addMeeting for User ID : "
					+ request.getUserID() + ", Title : " + request.getTitle()
					+ " ...");

			MeetingDatabaseOperations dbo = new MeetingDatabaseOperations();
			String status = dbo.addMeeting(request);

			response.setStatus(status);

		} catch (Exception e) {
			System.out.println("WS - Exception - Failed to Add Meeting!");
			// e.printStackTrace();
		}
		return response;
	}

	// TODO - updateMeeting
	// http://localhost:8080/MeetingSchedulerMW/updateMeeting
	@Path("/updateMeeting")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public UpdateMeetingResponse updateMeeting(@GZIP String jsonRequest) {

		UpdateMeetingResponse response = new UpdateMeetingResponse();
		try {

			Gson json = new Gson();

			UpdateMeetingRequest request = json.fromJson(jsonRequest,
					UpdateMeetingRequest.class);

			System.out.println("/updateMeeting for Meeting ID : "
					+ request.getMeetingID() + " ...");

			MeetingDatabaseOperations dbo = new MeetingDatabaseOperations();
			String status = dbo.updateMeeting(request);

			response.setStatus(status);

		} catch (Exception e) {
			System.out.println("WS - Exception - Failed to Update Meeting!");
			// e.printStackTrace();
		}
		return response;
	}

	// TODO - deleteMeeting
	// http://localhost:8080/MeetingSchedulerMW/deleteMeeting
	@Path("/deleteMeeting")
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public DeleteMeetingResponse deleteMeeting(@GZIP String jsonRequest) {

		DeleteMeetingResponse response = new DeleteMeetingResponse();
		try {

			Gson json = new Gson();
			DeleteMeetingRequest request = json.fromJson(jsonRequest,
					DeleteMeetingRequest.class);

			System.out.println("/deleteMeeting for Meeting ID : "
					+ request.getMeetingID() + " ...");

			MeetingDatabaseOperations dbo = new MeetingDatabaseOperations();
			String status = dbo.deleteMeeting(request);

			response.setStatus(status);

		} catch (Exception e) {
			System.out.println("WS - Exception - Failed to Delete Meeting!");
			// e.printStackTrace();
		}
		return response;
	}

	// TODO - getAllMeetings
	// http://localhost:8080/MeetingSchedulerMW/getAllMeetings
	@Path("/getAllMeetings")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public ViewAllMeetingsResponse getAllMeetings() {

		ViewAllMeetingsResponse response = new ViewAllMeetingsResponse();
		try {

			MeetingDatabaseOperations dbo = new MeetingDatabaseOperations();
			ArrayList<Meeting> meetings = dbo.getAllMeetings();

			response.setStatus("Failure");

			if (meetings != null) {
				response.setStatus("Success");
				response.setMeetingDetails(meetings);
			}

		} catch (Exception e) {
			System.out
					.println("WS - Exception - Failed to Get ALL Meeting Details!");
			// e.printStackTrace();
		}
		return response;

	}
}