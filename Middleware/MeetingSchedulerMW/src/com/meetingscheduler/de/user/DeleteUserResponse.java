package com.meetingscheduler.de.user;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DeleteUserResponse {

	private String status;

	public DeleteUserResponse() {
		// TODO Auto-generated constructor stub
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
