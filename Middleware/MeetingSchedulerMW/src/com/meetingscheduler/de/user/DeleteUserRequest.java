package com.meetingscheduler.de.user;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DeleteUserRequest {

	private long userID;

	public DeleteUserRequest() {
		// TODO Auto-generated constructor stub
	}

	public long getUserID() {
		return userID;
	}

	public void setUserID(long userID) {
		this.userID = userID;
	}

}
