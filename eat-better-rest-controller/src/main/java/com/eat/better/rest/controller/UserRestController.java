package com.eat.better.rest.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.eat.better.service.exception.DTONotFoundException;
import com.eat.better.service.exception.crudgeneric.ReadGenericException;
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
	public @ResponseBody ResponseEntity<UserDTO> getUser() {
		try {
			UserDTO dto;
			try {
				dto = service.findOne(1L);
			} catch (DTONotFoundException e) {
				log.error("getUser::DTO not found", e);
				return new ResponseEntity<UserDTO>(HttpStatus.NOT_FOUND);
			} catch (ReadGenericException e) {
				log.error("getUser::Service error", e);
				return new ResponseEntity<UserDTO>(HttpStatus.SERVICE_UNAVAILABLE);
			}

			log.debug("getUser::Returning the dto	: " + dto);
			return new ResponseEntity<UserDTO>(dto, HttpStatus.OK);

		} catch (Exception e) {
			log.error("getUser::Generic Error", e);
			return new ResponseEntity<UserDTO>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
