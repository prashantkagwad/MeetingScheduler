package com.meetingscheduler.de.meeting;

import javax.xml.bind.annotation.XmlRootElement;

import com.meetingscheduler.data.Location;

@XmlRootElement
public class AddMeetingRequest {

	private int userID;
	private String title;
	private String description;
	private String duration;
	private Location location;
	private String time;
	private String date;
	private String status;

	public AddMeetingRequest() {
		// TODO Auto-generated constructor stub
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
