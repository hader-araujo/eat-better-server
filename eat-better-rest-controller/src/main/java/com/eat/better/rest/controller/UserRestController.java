package com.eat.better.rest.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.eat.better.service.user.UserDTO;
import com.eat.better.service.user.UserService;

@RestController
@RequestMapping(value = "/user")
public class UserRestController {

	private static final Logger log = LogManager.getLogger(UserRestController.class);

	private UserService service;

	@Autowired
	public UserRestController(UserService service) {
		this.service = service;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<UserDTO> getUser() {
		try {
			UserDTO dto;
			dto = service.findOne(1L);
			
			service.saveAndFlush(null);

			log.debug("getUser::Returning the dto	: " + dto);
			return new ResponseEntity<UserDTO>(dto, HttpStatus.OK);

		} catch (Exception e) {
			log.error("getUser::Generic Error", e);
			return new ResponseEntity<UserDTO>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity saveUser(@RequestBody @Validated UserDTO user, Errors errors, BindingResult result) {

		if (errors.hasErrors()) {
			return ResponseEntity.badRequest()
					.body(errors.getAllErrors().stream().map(error -> error.getCodes()[0]).toArray());
		}

		//TODO need to save
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
