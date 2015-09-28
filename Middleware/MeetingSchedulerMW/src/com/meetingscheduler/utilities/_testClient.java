package com.meetingscheduler.utilities;

import java.net.URI;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import com.google.gson.Gson;
import com.meetingscheduler.de.user.UserPortfolioRequest;
import com.meetingscheduler.de.user.UserPortfolioResponse;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class _testClient {

	public static void main(String[] args) {

		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		WebResource service = client.resource(getBaseURI());

		UserPortfolioRequest x = new UserPortfolioRequest();
		x.setUserName("SOURAV");
		x.setPassword("1234");

		Gson gson = new Gson();
		String request = gson.toJson(x);
		System.out.println(request);

		UserPortfolioResponse getUserResponse = service
				.path("MeetingScheduler").path("getUserDetails")
				.accept(MediaType.APPLICATION_JSON)
				.post(UserPortfolioResponse.class, request);

		System.out.println(gson.toJson(getUserResponse));

		// String creatUserResponse = service.path("MeetingScheduler")
		// .path("creatUser").accept(MediaType.APPLICATION_JSON)
		// .post(String.class, request);

		// System.out.println();

	}

	private static URI getBaseURI() {
		return UriBuilder.fromUri("http://localhost:8080").build();
	}

}