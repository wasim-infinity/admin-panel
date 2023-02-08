package com.mvnproj.admin.entity.urlshortner;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "user_data")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserData {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "user_name", columnDefinition = "VARCHAR(50)")
	private String userName;

	private String password;
	
	private String phone;
	
	private String email;
	
//	@JsonIgnore
//	@OneToMany(mappedBy = "user")
//	private List<ApiLog> apiLog;

	@Column(name = "created_at")
	private LocalDateTime createdAt;

	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

}
