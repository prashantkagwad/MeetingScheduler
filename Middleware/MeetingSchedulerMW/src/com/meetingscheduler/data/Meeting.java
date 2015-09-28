package com.meetingscheduler.data;

import java.sql.Date;
import java.sql.Time;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Meeting {

	private long meetingID;
	private long userID;
	private String title;
	private String description;
	private Time duration;
	private Location location;
	private Time time;
	private Date date;

	public Meeting() {
		// TODO Auto-generated constructor stub
	}

	public long getMeetingID() {
		return meetingID;
	}

	public void setMeetingID(long meetingID) {
		this.meetingID = meetingID;
	}

	public long getUserID() {
		return userID;
	}

	public void setUserID(long userID) {
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

	public Time getDuration() {
		return duration;
	}

	public void setDuration(Time duration) {
		this.duration = duration;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Time getTime() {
		return time;
	}

	public void setTime(Time time) {
		this.time = time;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
