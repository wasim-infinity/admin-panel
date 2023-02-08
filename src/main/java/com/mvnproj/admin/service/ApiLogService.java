package com.mvnproj.admin.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.mvnproj.admin.entity.urlshortner.UserData;
import com.mvnproj.admin.request.UpdateUserDataRequest;
import com.mvnproj.admin.response.ApiLogResponse;
import com.mvnproj.admin.response.ApiLogResponseList;
import com.mvnproj.admin.response.ResponseData;
import com.mvnproj.admin.response.UserResponse;
import com.mvnproj.admin.response.UserResponseList;

@Service
public interface ApiLogService {

	ResponseEntity<ApiLogResponseList> listApiLog(int page_no, int limit, String src_field, String src_txt,
			String order_by, String order);

	ResponseEntity<ApiLogResponse> listApiLogById(long id);

}
