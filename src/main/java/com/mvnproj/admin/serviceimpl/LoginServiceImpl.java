package com.mvnproj.admin.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.mvnproj.admin.authentication.JwtUtil;
import com.mvnproj.admin.entity.login.TblAdminDetails;
import com.mvnproj.admin.repository.login.AdminDetailsRepository;
import com.mvnproj.admin.request.AuthRequest;
import com.mvnproj.admin.response.ResponseData;
import com.mvnproj.admin.response.ResponseHelper;
import com.mvnproj.admin.service.LoginService;

@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private AdminDetailsRepository adminDetailsRepository;

	@Override
	public ResponseEntity<ResponseData> generateToken(AuthRequest authRequest) {

		ResponseData response = null;
		TblAdminDetails adminDtls = adminDetailsRepository.findByAdminNameAndAdminPassword(authRequest.getUserName(),
				authRequest.getPassword());
		if (adminDtls != null) {
			try {
				authenticationManager.authenticate(
						new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword()));
			} catch (Exception ex) {
				response = ResponseHelper.responseSender("inavalid username/password", HttpStatus.UNAUTHORIZED.value());
				return new ResponseEntity<ResponseData>(response, HttpStatus.UNAUTHORIZED);
			}
		} else {
			response = ResponseHelper.responseSender("inavalid username/password", HttpStatus.UNAUTHORIZED.value());
			return new ResponseEntity<ResponseData>(response, HttpStatus.UNAUTHORIZED);
		}
		response = ResponseHelper.responseSender("Success", HttpStatus.OK.value(), 
				jwtUtil.generateToken(authRequest.getUserName()), authRequest.getUserName());

		return new ResponseEntity<ResponseData>(response, HttpStatus.OK);

	}

}
