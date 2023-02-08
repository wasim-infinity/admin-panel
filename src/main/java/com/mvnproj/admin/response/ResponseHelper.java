package com.mvnproj.admin.response;

import java.util.List;

import com.mvnproj.admin.entity.urlshortner.UserData;

public class ResponseHelper {

	public static ResponseData responseSender(String statusMessage, int statusCode) {
		ResponseData response = new ResponseData();
		response.setStatusCode(statusCode);
		response.setStatusMessage(statusMessage);
		return response;
	}

	public static ResponseData responseSenderData(String statusMessage, int statusCode, List<UserData> responseData) {
		ResponseData response = new ResponseData();
		response.setStatusCode(statusCode);
		response.setStatusMessage(statusMessage);
		response.setUserData(responseData);
		return response;
	}

	public static ResponseData responseSenderData(String statusMessage, int statusCode, UserData user) {
		ResponseData response = new ResponseData();
		response.setStatusCode(statusCode);
		response.setStatusMessage(statusMessage);
		response.setUser(user);
		return response;
	}

	public static ResponseData responseSender(String statusMessage, int statusCode, String token, String userName) {
		ResponseData response = new ResponseData();
		response.setStatusCode(statusCode);
		response.setStatusMessage(statusMessage);
		response.setAuthToken(token);
		response.setUserName(userName);
		return response;
	}

}
