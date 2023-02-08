package com.mvnproj.admin.entity.urlshortner;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "api_log")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiLog {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "end_url")
	private String endUrl;

	@Column(columnDefinition = " TEXT NOT NULL COMMENT ''")
	private String request;

	@Column(columnDefinition = " LONGTEXT NULL COMMENT ''")
	private String response;

	@Column(name = "request_time")
	private LocalDateTime requestTime;

	@Column(name = "response_time")
	private LocalDateTime responseTime;
	
//	@JsonIgnore
//	@ManyToOne
	@Column(name = "user_id")
	private long userId;

	@Column(name = "created_at")
	private LocalDateTime createdAt;

	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

}
