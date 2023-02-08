package com.mvnproj.admin.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.mvnproj.admin.request.UpdateUserDataRequest;
import com.mvnproj.admin.response.ResponseData;
import com.mvnproj.admin.response.UserResponse;
import com.mvnproj.admin.response.UserResponseList;
import com.mvnproj.admin.service.UserService;

@RestController
@RequestMapping("/admin")
public class UserController {

	@Autowired
	private UserService userService;

	@JsonView(ResponseData.UpdateView.class)
	@PostMapping("/user/update-data")
	public ResponseEntity<ResponseData> updateData(@RequestHeader String authToken,
			@RequestBody @Valid UpdateUserDataRequest userDataRequest) {

		return userService.updateData(userDataRequest);

	}

	@GetMapping("/user/list")
	public ResponseEntity<UserResponseList> listUser(@RequestHeader String authToken, @RequestParam int page_no,
			@RequestParam int limit, @RequestParam(required = false) String src_field,
			@RequestParam(required = false) String src_txt, @RequestParam(required = false) String order_by,
			@RequestParam(required = false) String order) {
		
		if(!(src_field instanceof String))
		{
			src_field="";
		}
		
		if(!(src_txt instanceof String))
		{
		 src_txt="";
		}
			
		if(!(order_by instanceof String))
		{
			order_by="";
		}
		if(!(order instanceof String))
		{
			order="";
		}

		return userService.listUser(page_no, limit, src_field, src_txt, order_by, order);

	}

	@GetMapping("/user/list-by-id")
	public ResponseEntity<UserResponse> listUserById(@RequestHeader String authToken, long id) {

		return userService.listUserById(id);

	}

}
