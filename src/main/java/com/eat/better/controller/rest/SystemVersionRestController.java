package com.eat.better.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.eat.better.model.entity.SystemVersionEntity;
import com.eat.better.model.repository.SystemVersionJpaRespository;

@RestController
@RequestMapping(value = "/systemversion")
public class SystemVersionRestController {

	@Autowired
	private SystemVersionJpaRespository repository;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<SystemVersionEntity> getSystemVersion() {
		SystemVersionEntity entity = new SystemVersionEntity();
		entity.setVersion("Version 1.0");

		repository.saveAndFlush(entity);
		
		entity = repository.findOne(1L);
		
		if (entity == null) {
			return new ResponseEntity<SystemVersionEntity>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<SystemVersionEntity>(entity, HttpStatus.OK);
	}
}
