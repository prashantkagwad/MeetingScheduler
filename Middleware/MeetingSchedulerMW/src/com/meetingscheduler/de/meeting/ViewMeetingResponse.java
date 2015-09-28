package com.meetingscheduler.de.meeting;

import java.util.ArrayList;

import com.meetingscheduler.data.Meeting;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ViewMeetingResponse {

	private String status;
	private ArrayList<Meeting> meetingDetails;

	public ViewMeetingResponse() {
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
