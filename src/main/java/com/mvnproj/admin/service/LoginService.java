package com.mvnproj.admin.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.mvnproj.admin.request.AuthRequest;
import com.mvnproj.admin.response.ResponseData;

@Service
public interface LoginService {

	ResponseEntity<ResponseData> generateToken(AuthRequest authRequest);

}
