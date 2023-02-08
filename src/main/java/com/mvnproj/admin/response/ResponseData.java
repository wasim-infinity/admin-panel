package com.mvnproj.admin.response;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonView;
import com.mvnproj.admin.entity.urlshortner.UserData;

import lombok.Data;

@Component
@Data
public class ResponseData {

	@JsonView({ AuthenticateView.class, UpdateView.class, UserListView.class, UserDataView.class })
	private String statusMessage;

	@JsonView({ AuthenticateView.class, UpdateView.class, UserListView.class, UserDataView.class })
	private int statusCode;

	@JsonView({ UserListView.class })
	private List<UserData> userData = new ArrayList<UserData>();

	@JsonView({ UserDataView.class })
	private UserData user = new UserData();

	@JsonView({ AuthenticateView.class })
	private String userName;

	@JsonView({ AuthenticateView.class })
	private String authToken;

	public interface UserListView {

	}

	public interface UserDataView {

	}

	public interface AuthenticateView {

	}

	public interface UpdateView {

	}

}
