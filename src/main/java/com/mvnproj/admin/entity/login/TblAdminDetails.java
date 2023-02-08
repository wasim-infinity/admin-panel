package com.mvnproj.admin.entity.login;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "tbl_admin_login_details")
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(callSuper = false)
public class TblAdminDetails {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;

	@Column(name = "admin_name")
	String adminName;

	@Column(name = "admin_password")
	String adminPassword;

	@NotBlank
	@Column(name = "firstName", columnDefinition = " VARCHAR(255) default ''")
	String firstName;

	@NotBlank
	@Column(name = "lastName", columnDefinition = " VARCHAR(255) default ''")
	String lastName;

	@NotBlank
	@Column(name = "emailAddress", columnDefinition = " VARCHAR(255) default ''")
	String emailAddress;

	@NotBlank
	@Column(name = "contactNumber", columnDefinition = " VARCHAR(255) default ''")
	String contactNumber;

	@NotBlank
	@Column(name = "userStatus", columnDefinition = " VARCHAR(255) default 'ACTIVE'")
	String userStatus;

	@Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createdAt;

	@Column(name = "updated_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updatedAt;
}
