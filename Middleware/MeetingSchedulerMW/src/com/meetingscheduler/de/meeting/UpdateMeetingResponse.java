package com.meetingscheduler.de.meeting;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UpdateMeetingResponse {

	private String status;

	public UpdateMeetingResponse() {
		// TODO Auto-generated constructor stub
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
