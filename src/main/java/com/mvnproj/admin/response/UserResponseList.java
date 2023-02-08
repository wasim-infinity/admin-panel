package com.mvnproj.admin.response;

import java.util.List;

import com.mvnproj.admin.entity.urlshortner.UserData;

import lombok.Data;

@Data
public class UserResponseList {

	private String statusMessage;

	private int statusCode;

	private List<UserData> user;
	
	private long count;

}
