package com.eat.better.controller.rest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.eat.better.model.entity.SystemVersionEntity;
import com.eat.better.service.SystemVersionService;

@RestController
@RequestMapping(value = "/systemversion")
public class SystemVersionRestController {

	private static final Logger log = LogManager.getLogger(SystemVersionRestController.class);
	
	@Autowired
	private SystemVersionService service;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<SystemVersionEntity> getSystemVersion() {
		SystemVersionEntity entity = new SystemVersionEntity();
		entity.setVersion("Version 1.0");

		service.saveAndFlush(entity);
		
		entity = service.findOne(1L);
		
		if (entity == null) {
			return new ResponseEntity<SystemVersionEntity>(HttpStatus.NOT_FOUND);
		}
		
		log.info("Returning the entity: " + entity);
		
		return new ResponseEntity<SystemVersionEntity>(entity, HttpStatus.OK);
	}
}
