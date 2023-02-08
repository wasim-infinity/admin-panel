package com.mvnproj.admin.serviceimpl;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
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

import com.mvnproj.admin.entity.urlshortner.ApiLog;
import com.mvnproj.admin.repository.urlshortner.ApiLogRepository;
import com.mvnproj.admin.response.ApiLogResponse;
import com.mvnproj.admin.response.ApiLogResponseList;
import com.mvnproj.admin.service.ApiLogService;

@Service
public class ApiLogServiceImpl implements ApiLogService {

	@Autowired
	private ApiLogRepository apiLogRepository;

	@Override
	public ResponseEntity<ApiLogResponseList> listApiLog(int page_no, int limit, String src_field, String src_txt,
			String order_by, String order) {

		ApiLogResponseList response = new ApiLogResponseList();
		String sortFieldName = "";
		Sort sort = null;
		if (!Objects.isNull(order_by) && !Objects.isNull(order) && !order_by.contentEquals("")
				&& !order.contentEquals("")) {
			switch (order_by) {
			case "id":
				sortFieldName = "id";
				break;
			case "userId":
				sortFieldName = "userId";
				break;
			case "requestTime":
				sortFieldName = "requestTime";
				break;
			case "responseTime":
				sortFieldName = "responseTime";
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

		List<ApiLog> apiLog = null;
		if (!Objects.isNull(src_field) && !Objects.isNull(src_txt) && src_field != "" && src_txt != "") {
			String[] src_fields = src_field.split(Pattern.quote("@-@"));
			String[] src_texts = src_txt.split(Pattern.quote("@-@"));

			if (src_fields[0].contentEquals("id") && src_fields.length == 1) {
				apiLog = apiLogRepository.findAllById(Long.parseLong(src_texts[0]), pageable);
				response.setCount(apiLog.size());
			} else if (src_fields[0].contentEquals("userId") && src_fields.length == 1) {
				apiLog = apiLogRepository.findAllByUserId(Long.parseLong(src_texts[0]), pageable);
				response.setCount(apiLog.size());
			} else if (src_fields.length == 2) {
				if (src_fields[0].contentEquals("id") && src_fields[1].contentEquals("userId")) {
					apiLog = apiLogRepository.findAllByIdAndUserId(Long.parseLong(src_texts[0]), Long.parseLong(src_texts[1]),
							pageable);
					response.setCount(apiLog.size());
				} else if (src_fields[1].contentEquals("id") && src_fields[0].contentEquals("userId")) {
					apiLog = apiLogRepository.findAllByUserIdAndId(Long.parseLong(src_texts[0]), Long.parseLong(src_texts[1]),
							pageable);
					response.setCount(apiLog.size());
				}
			}
		} else {
			apiLog = apiLogRepository.findAll(pageable).getContent();
			response.setCount(apiLog.size());
		}

		response.setStatusCode(HttpStatus.OK.value());
		response.setStatusMessage("Success");
		response.setApiLog(apiLog);
		return new ResponseEntity<ApiLogResponseList>(response, HttpStatus.OK);
	}

	public Object URLDecodeString_Or_ReturnAsDirected(String stringToBeDecoded, Object returnThisValueIfError) {

		try {
			return java.net.URLDecoder.decode(stringToBeDecoded.trim(), StandardCharsets.UTF_8.name());
		} catch (UnsupportedEncodingException e) {
			return returnThisValueIfError;
		}

	}

	@Override
	public ResponseEntity<ApiLogResponse> listApiLogById(long id) {
		ApiLogResponse response = new ApiLogResponse();
		Optional<ApiLog> apiLog = apiLogRepository.findById(id);
		if (!apiLog.isPresent()) {
			response.setStatusCode(HttpStatus.BAD_REQUEST.value());
			response.setStatusMessage("Api Log with id: " + id + " Not Found");
			response.setApiLog(null);
			return new ResponseEntity<ApiLogResponse>(response, HttpStatus.BAD_REQUEST);
		}

		response.setStatusCode(HttpStatus.OK.value());
		response.setStatusMessage("Success");
		response.setApiLog(apiLog.get());
		return new ResponseEntity<ApiLogResponse>(response, HttpStatus.OK);
	}

}
