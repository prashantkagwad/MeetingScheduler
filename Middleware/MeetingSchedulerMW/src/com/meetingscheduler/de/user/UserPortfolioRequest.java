package com.meetingscheduler.de.user;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UserPortfolioRequest {

	private String userName;
	private String password;

	public UserPortfolioRequest() {
		// TODO Auto-generated constructor stub
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
