
package com.mvnproj.admin.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class UpdateUserDataRequest {
	
	//TODO: validation pending

	private long id;

	private String username;

	private String phone;

	@Email(message = "Provide valid email id")
	private String email;


}
