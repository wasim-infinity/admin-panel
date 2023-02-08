package com.mvnproj.admin.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.mvnproj.admin.request.UpdateUserDataRequest;
import com.mvnproj.admin.response.ResponseData;
import com.mvnproj.admin.response.UserResponse;
import com.mvnproj.admin.response.UserResponseList;

@Service
public interface UserService {

	ResponseEntity<ResponseData> updateData(UpdateUserDataRequest userDataRequest);

	ResponseEntity<UserResponseList> listUser(int page_no, int limit, String src_field, String src_txt, String order_by,
			String order);

	ResponseEntity<UserResponse> listUserById(long id);

}
