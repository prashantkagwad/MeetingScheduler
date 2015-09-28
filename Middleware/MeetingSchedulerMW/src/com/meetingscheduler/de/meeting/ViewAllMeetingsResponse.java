package com.meetingscheduler.de.meeting;

import java.util.ArrayList;

import com.meetingscheduler.data.Meeting;

public class ViewAllMeetingsResponse {

	private String status;
	private ArrayList<Meeting> meetingDetails;

	public ViewAllMeetingsResponse() {
		// TODO Auto-generated constructor stub
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public ArrayList<Meeting> getMeetingDetails() {
		return meetingDetails;
	}

	public void setMeetingDetails(ArrayList<Meeting> meetingDetails) {
		this.meetingDetails = meetingDetails;
	}

}
