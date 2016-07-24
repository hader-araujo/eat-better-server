package com.eat.better.rest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import com.eat.better.service.dto.user.UserDTOPost;
import com.eat.better.service.dto.user.UserDTOPut;

public interface UserRestController {

	@SuppressWarnings("rawtypes")
	ResponseEntity save(UserDTOPost user, BindingResult result);

	@SuppressWarnings("rawtypes")
	ResponseEntity get(Long id);

	@SuppressWarnings("rawtypes")
	ResponseEntity update(UserDTOPut user, BindingResult result);

}