package com.meetingscheduler.de.user;

import java.util.ArrayList;

import com.meetingscheduler.data.User;

public class ViewAllUsersResponse {

	private String status;
	private ArrayList<User> users;

	public ViewAllUsersResponse() {
		// TODO Auto-generated constructor stub
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public ArrayList<User> getUsers() {
		return users;
	}

	public void setUsers(ArrayList<User> users) {
		this.users = users;
	}

}
