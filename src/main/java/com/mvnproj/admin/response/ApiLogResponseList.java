package com.mvnproj.admin.response;

import java.util.List;

import com.mvnproj.admin.entity.urlshortner.ApiLog;

import lombok.Data;

@Data
public class ApiLogResponseList {

	private String statusMessage;

	private int statusCode;

	private List<ApiLog> apiLog;
	
	private long count;

}
