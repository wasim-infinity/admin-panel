package com.mvnproj.admin.config;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.mvnproj.admin.entity.login.TblAdminDetails;
import com.mvnproj.admin.repository.login.AdminDetailsRepository;

@Configuration
@Component
public class InsertTableBeanConfiguration {

	@Autowired
	private AdminDetailsRepository adminDtlsRepository;

	@Bean
	public void saveAdmin() {
		adminDtlsRepository.deleteAll();
		adminDtlsRepository.truncateTable();
		adminDtlsRepository.save(new TblAdminDetails(1, "wasim", "12345", "Wasim", "Akram",
				"wasim.akram@indusnet.co.in", "1234567890", "ACTIVE", new Date(), new Date()));
	}

}
