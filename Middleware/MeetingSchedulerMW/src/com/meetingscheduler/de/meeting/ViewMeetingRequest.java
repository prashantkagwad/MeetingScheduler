package com.meetingscheduler.de.meeting;

import java.sql.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ViewMeetingRequest {

	private long userID;
	private Date date;
	private String city;

	public ViewMeetingRequest() {
		// TODO Auto-generated constructor stub
	}

	public long getUserID() {
		return userID;
	}

	public void setUserID(long userID) {
		this.userID = userID;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

}
