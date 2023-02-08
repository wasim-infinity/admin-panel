package com.mvnproj.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mvnproj.admin.response.ApiLogResponse;
import com.mvnproj.admin.response.ApiLogResponseList;
import com.mvnproj.admin.service.ApiLogService;

@RestController
@RequestMapping("/admin")
public class ApiLogController {

	@Autowired
	private ApiLogService apiLogService;

	@GetMapping("/api-log/list")
	public ResponseEntity<ApiLogResponseList> listApiLog(@RequestHeader String authToken, @RequestParam int page_no,
			@RequestParam int limit, @RequestParam(required = false) String src_field,
			@RequestParam(required = false) String src_txt,
			@RequestParam(required = false) String order_by,
			@RequestParam(required = false) String order) {
		if(!(src_field instanceof String))
		{
			src_field="";
		}
		
		if(!(src_txt instanceof String))
		{
		 src_txt="";
		}
			
		if(!(order_by instanceof String))
		{
			order_by="";
		}
		if(!(order instanceof String))
		{
			order="";
		}
		return apiLogService.listApiLog(page_no, limit, src_field, src_txt, order_by, order);

	}

	@GetMapping("/api-log/list-by-id")
	public ResponseEntity<ApiLogResponse> listApiLogById(@RequestHeader String authToken, long id) {

		return apiLogService.listApiLogById(id);

	}

}
