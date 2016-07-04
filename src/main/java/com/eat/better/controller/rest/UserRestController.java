package com.eat.better.controller.rest;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eat.better.model.entity.UserEntity;
import com.eat.better.service.UserService;
import com.eat.better.service.exception.EntityFieldNullPointerException;

@RestController
@RequestMapping(value = "/user")
public class UserRestController {

	private static Logger log = Logger.getLogger(UserRestController.class);

	@Autowired
	private UserService service;

	@RequestMapping(method = RequestMethod.POST)
	public void saveUser(@RequestParam("login") String login, @RequestParam("name") String name) {

		try {
			UserEntity user = service.saveAndFlush(login, name);
			log.debug("saveUser:: User saved. User = %" + user);
		} catch (EntityFieldNullPointerException e) {
			log.error(e); // TODO handle the error
		}

		// TODO need to return to the client
	}
}
