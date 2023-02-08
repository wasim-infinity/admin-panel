package com.mvnproj.admin.serviceimpl;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.mvnproj.admin.entity.urlshortner.UserData;
import com.mvnproj.admin.repository.urlshortner.UserDataRepository;
import com.mvnproj.admin.request.UpdateUserDataRequest;
import com.mvnproj.admin.response.ResponseData;
import com.mvnproj.admin.response.ResponseHelper;
import com.mvnproj.admin.response.UserResponse;
import com.mvnproj.admin.response.UserResponseList;
import com.mvnproj.admin.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDataRepository userDataRepository;

	@Override
	public ResponseEntity<ResponseData> updateData(UpdateUserDataRequest userDataRequest) {

		Optional<UserData> userData = userDataRepository.findById(userDataRequest.getId());
		UserData updatedUserData = null;
		ResponseData response = null;
		if (userData.isPresent()) {
			if (!ObjectUtils.isEmpty(userDataRequest.getUsername())) {
				userData.get().setUserName(userDataRequest.getUsername());
			}
			if (!ObjectUtils.isEmpty(userDataRequest.getPhone())) {
				userData.get().setPhone(userDataRequest.getPhone());
			}
			if (!ObjectUtils.isEmpty(userDataRequest.getEmail())) {
				userData.get().setEmail(userDataRequest.getEmail());
			}
			userData.get().setUpdatedAt(LocalDateTime.now());

			updatedUserData = userDataRepository.save(userData.get());
		} else {
			response = ResponseHelper.responseSender(
					"User with id : " + userDataRequest.getId() + " not present !!!.",
					HttpStatus.BAD_REQUEST.value());
			return new ResponseEntity<ResponseData>(response, HttpStatus.BAD_REQUEST);
		}

		if (updatedUserData.getId() > 0) {
			response = ResponseHelper.responseSender("Successfully user data updated !!!.", HttpStatus.OK.value());
			return new ResponseEntity<ResponseData>(response, HttpStatus.OK);
		} else {
			response = ResponseHelper.responseSender("Unable to update user data !!!. Please try again.",
					HttpStatus.INTERNAL_SERVER_ERROR.value());
			return new ResponseEntity<ResponseData>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@Override
	public ResponseEntity<UserResponseList> listUser(int page_no, int limit, String src_field, String src_txt,
			String order_by, String order) {

		UserResponseList response = new UserResponseList();
		String sortFieldName = "";
		Sort sort = null;
		if (!Objects.isNull(order_by) && !Objects.isNull(order) && !order_by.contentEquals("")
				&& !order.contentEquals("")) {
			switch (order_by) {
			case "id":
				sortFieldName = "id";
				break;
			case "email":
				sortFieldName = "email";
				break;
			case "phone":
				sortFieldName = "phone";
				break;
			case "userName":
				sortFieldName = "userName";
				break;
			default:
				sortFieldName = "id";
			}

			if (order.contentEquals("asc"))
				sort = Sort.by(Direction.ASC, sortFieldName);
			else if (order.contentEquals("desc"))
				sort = Sort.by(Direction.DESC, sortFieldName);
			else
				throw new RuntimeException("Cannot arrange by the requested field");
		}

		Pageable pageable;
		if (sort != null)
			pageable = PageRequest.of(page_no, limit, sort);
		else
			pageable = PageRequest.of(page_no, limit);

		// URL Decode
		if (!Objects.isNull(src_txt) || src_txt != "") {
			src_txt = String.valueOf(this.URLDecodeString_Or_ReturnAsDirected(src_txt, ""));
		}

		List<UserData> userList = null;

		if (!Objects.isNull(src_field) && !Objects.isNull(src_txt) && src_field != "" && src_txt != "") {
			String[] src_fields = src_field.split(Pattern.quote("@-@"));
			String[] src_texts = src_txt.split(Pattern.quote("@-@"));

			if (src_fields[0].contentEquals("email") && src_fields.length == 1) {
				userList = userDataRepository.findAllByEmail(src_texts[0], pageable);
				response.setCount(userList.size());
			} else if (src_fields[0].contentEquals("phone") && src_fields.length == 1) {
				userList = userDataRepository.findAllByPhone(Integer.parseInt(src_texts[0]), pageable);
				response.setCount(userList.size());
			} else if (src_fields[0].contentEquals("userName") && src_fields.length == 1) {
				userList = userDataRepository.findAllByUserName(src_texts[0], pageable);
				response.setCount(userList.size());
			} else if (src_fields.length == 2) {
				if (src_fields[0].contentEquals("email") && src_fields[1].contentEquals("phone")) {
					userList = userDataRepository.findAllByEmailAndPhone(src_texts[0],
							Integer.parseInt(src_texts[1]), pageable);
					response.setCount(userList.size());
				} else if (src_fields[1].contentEquals("email") && src_fields[0].contentEquals("phone")) {
					userList = userDataRepository.findAllByPhoneAndEmail(Integer.parseInt(src_texts[0]),
							src_texts[1], pageable);
					response.setCount(userList.size());
				} else if (src_fields[0].contentEquals("email") && src_fields[1].contentEquals("userName")) {
					userList = userDataRepository.findAllByEmailAndUserName(src_texts[0],
							src_texts[1], pageable);
					response.setCount(userList.size());
				} else if (src_fields[1].contentEquals("email") && src_fields[0].contentEquals("userName")) {
					userList = userDataRepository.findAllByUserNameAndEmail(src_texts[0],
							src_texts[1], pageable);
					response.setCount(userList.size());
				} else if (src_fields[0].contentEquals("phone") && src_fields[1].contentEquals("userName")) {
					userList = userDataRepository.findAllByPhoneAndUserName(Integer.parseInt(src_texts[0]),
							src_texts[1], pageable);
					response.setCount(userList.size());
				} else if (src_fields[1].contentEquals("phone") && src_fields[0].contentEquals("userName")) {
					userList = userDataRepository.findAllByUserNameAndPhone(src_texts[0],
							Integer.parseInt(src_texts[1]), pageable);
					response.setCount(userList.size());
				}
			}
		} else {
			userList = userDataRepository.findAll(pageable).getContent();
			response.setCount(userList.size());
		}

		response.setStatusCode(HttpStatus.OK.value());
		response.setStatusMessage("Success");
		response.setUser(userList);
		return new ResponseEntity<UserResponseList>(response, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<UserResponse> listUserById(long id) {
		Optional<UserData> user = userDataRepository.findById(id);
		UserResponse response = new UserResponse();
		if(!user.isPresent()) {
			response.setStatusCode(HttpStatus.BAD_REQUEST.value());
			response.setStatusMessage("User with id : "+id+" not found");
			response.setUser(null);
			return new ResponseEntity<UserResponse>(response, HttpStatus.BAD_REQUEST);
		}
		
		response.setStatusCode(HttpStatus.OK.value());
		response.setStatusMessage("Success");
		response.setUser(user.get());
		return new ResponseEntity<UserResponse>(response, HttpStatus.OK);
	}

	public Object URLDecodeString_Or_ReturnAsDirected(String stringToBeDecoded, Object returnThisValueIfError) {

		try {
			return java.net.URLDecoder.decode(stringToBeDecoded.trim(), StandardCharsets.UTF_8.name());
		} catch (UnsupportedEncodingException e) {
			return returnThisValueIfError;
		}

	}

}
