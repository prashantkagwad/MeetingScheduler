package com.meetingscheduler.de.user;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UpdateUserResponse {

	private String status;

	public UpdateUserResponse() {
		// TODO Auto-generated constructor stub
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
