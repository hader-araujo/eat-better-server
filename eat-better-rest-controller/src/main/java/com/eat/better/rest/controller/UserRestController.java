package com.eat.better.rest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import com.eat.better.service.user.UserDTO;

public interface UserRestController {

	ResponseEntity<UserDTO> getUser();

	@SuppressWarnings("rawtypes")
	ResponseEntity save(UserDTO user, BindingResult result);

}