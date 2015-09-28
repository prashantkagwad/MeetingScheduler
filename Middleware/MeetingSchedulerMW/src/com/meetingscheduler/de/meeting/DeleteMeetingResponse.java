package com.meetingscheduler.de.meeting;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DeleteMeetingResponse {

	private String status;

	public DeleteMeetingResponse() {
		// TODO Auto-generated constructor stub
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
