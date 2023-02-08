package com.mvnproj.admin.serviceimpl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mvnproj.admin.entity.login.TblAdminDetails;
import com.mvnproj.admin.repository.login.AdminDetailsRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private  AdminDetailsRepository adminDataRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		TblAdminDetails adminData = adminDataRepository.findByAdminName(username);
		return new org.springframework.security.core.userdetails.User(adminData.getAdminName(), adminData.getAdminPassword(), new ArrayList<>());
	}
}
