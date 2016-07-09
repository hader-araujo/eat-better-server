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
import com.eat.better.service.systemversion.SystemVersionDTO;
import com.eat.better.service.systemversion.SystemVersionService;

@RestController
@RequestMapping(value = "/systemversion")
public class SystemVersionRestController {

	private static final Logger log = LogManager.getLogger(SystemVersionRestController.class);

	private SystemVersionService service;

	@Autowired
	public SystemVersionRestController(SystemVersionService service) {
		this.service = service;
	}

	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<SystemVersionDTO> getSystemVersion() {
		try {
			SystemVersionDTO dto;
			// TODO move the validation on the service to control
			try {
				dto = service.findOne(1L);
			} catch (DTONotFoundException e) {
				log.error("getSystemVersion::DTO not found", e);
				return new ResponseEntity<SystemVersionDTO>(HttpStatus.NOT_FOUND);
			} catch (ReadGenericException e) {
				log.error("getSystemVersion::Service error", e);
				return new ResponseEntity<SystemVersionDTO>(HttpStatus.SERVICE_UNAVAILABLE);
			}

			log.info("getSystemVersion::Returning the dto	: " + dto);
			return new ResponseEntity<SystemVersionDTO>(dto, HttpStatus.OK);

		} catch (Exception e) {
			log.error("getSystemVersion::Generic Error", e);
			return new ResponseEntity<SystemVersionDTO>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
