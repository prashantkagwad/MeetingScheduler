package com.meetingscheduler.de.meeting;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DeleteMeetingRequest {

	private long meetingID;

	public DeleteMeetingRequest() {
		// TODO Auto-generated constructor stub
	}

	public long getMeetingID() {
		return meetingID;
	}

	public void setMeetingID(long meetingID) {
		this.meetingID = meetingID;
	}

}
