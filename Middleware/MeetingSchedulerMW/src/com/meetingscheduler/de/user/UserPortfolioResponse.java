package com.meetingscheduler.de.user;

import com.meetingscheduler.data.User;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UserPortfolioResponse {

	private String status;
	private User user;

	public UserPortfolioResponse() {
		// TODO Auto-generated constructor stub
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
