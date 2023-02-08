package com.mvnproj.admin.config.multidb;

import java.util.HashMap;

import javax.persistence.EntityManager;
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
    basePackages = "com.mvnproj.admin.repository.urlshortner", 
    entityManagerFactoryRef = "urlshortnerEntityManager", 
    transactionManagerRef = "urlshortnerTransactionManager"
)
public class UrlShortnerDbConfig {
	
	@Bean
    public LocalContainerEntityManagerFactoryBean urlshortnerEntityManager() {
        LocalContainerEntityManagerFactoryBean em
          = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(urlshortnerDataSource());
        em.setPackagesToScan(
          new String[] { "com.mvnproj.admin.entity.urlshortner" });
        HibernateJpaVendorAdapter vendorAdapter
          = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", "none"); // change to none when actual service is up in staging
		properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        em.setJpaPropertyMap(properties);
        return em;
    }

    @ConfigurationProperties(prefix="spring.urlshortner.datasource")
    @Bean
    public DataSource urlshortnerDataSource() {
    	return DataSourceBuilder.create().build();
    }

    @Bean
    public PlatformTransactionManager urlshortnerTransactionManager() {
        JpaTransactionManager transactionManager
          = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(
        		urlshortnerEntityManager().getObject());
        return transactionManager;
    }
    
    @Bean(name = "entityManagerurlshortner")
    public EntityManager entityManager() {
        return urlshortnerEntityManager().getNativeEntityManagerFactory().createEntityManager();
    }

}
