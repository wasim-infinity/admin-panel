package com.mvnproj.admin.config.multidb;

import java.util.HashMap;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
		basePackages = "com.mvnproj.admin.repository.login", 
		entityManagerFactoryRef = "loginEntityManager", 
		transactionManagerRef = "loginTransactionManager")
public class LoginDbConfig {

	@Bean
	public LocalContainerEntityManagerFactoryBean loginEntityManager() {
		LocalContainerEntityManagerFactoryBean em
		= new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(loginDataSource());
		em.setPackagesToScan(
				new String[] { "com.mvnproj.admin.entity.login" });
		HibernateJpaVendorAdapter vendorAdapter
		= new HibernateJpaVendorAdapter();
		em.setJpaVendorAdapter(vendorAdapter);
		HashMap<String, Object> properties = new HashMap<>();
		properties.put("hibernate.hbm2ddl.auto", "update");
		properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
		em.setJpaPropertyMap(properties);
		return em;
	}

	@ConfigurationProperties(prefix="spring.loginservice.datasource")
	@Bean
	public DataSource loginDataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean
	public PlatformTransactionManager loginTransactionManager() {
		JpaTransactionManager transactionManager
		= new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(
				loginEntityManager().getObject());
		return transactionManager;
	}

}
