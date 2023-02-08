package com.mvnproj.admin.response;

import com.mvnproj.admin.entity.urlshortner.ApiLog;

import lombok.Data;

@Data
public class ApiLogResponse {

	private String statusMessage;

	private int statusCode;

	private ApiLog apiLog;

}
