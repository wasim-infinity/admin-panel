package com.mvnproj.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.mvnproj.admin.request.AuthRequest;
import com.mvnproj.admin.response.ResponseData;
import com.mvnproj.admin.service.LoginService;

@RestController
public class LoginController {

	@Autowired
	private LoginService loginService;

	@JsonView(ResponseData.AuthenticateView.class)
	@PostMapping("/login")
	public ResponseEntity<ResponseData> generateToken(@RequestBody AuthRequest authRequest) throws Exception {

		return loginService.generateToken(authRequest);

	}

}
