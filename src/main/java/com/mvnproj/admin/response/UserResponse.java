package com.mvnproj.admin.response;

import com.mvnproj.admin.entity.urlshortner.UserData;

import lombok.Data;

@Data
public class UserResponse {

	private String statusMessage;

	private int statusCode;

	private UserData user;

}
