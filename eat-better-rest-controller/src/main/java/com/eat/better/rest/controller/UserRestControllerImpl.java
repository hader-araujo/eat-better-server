package com.eat.better.rest.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eat.better.entity.User;
import com.eat.better.service.UserService;
import com.eat.better.service.dto.user.UserDTOGet;
import com.eat.better.service.dto.user.UserDTOPost;
import com.eat.better.service.dto.user.UserDTOPut;
import com.eat.better.service.exception.CreateUpdateException;
import com.eat.better.service.exception.DeleteException;
import com.eat.better.service.exception.ReadException;
import com.eat.better.service.exception.enums.ReadExceptionMessageEnum;

@RestController
@RequestMapping(value = "/user")
public class UserRestControllerImpl implements UserRestController {

	private static final Logger log = LogManager.getLogger(UserRestControllerImpl.class.getName());

	private UserService service;

	@Autowired
	public UserRestControllerImpl(UserService service) {
		if (service == null) {
			throw new RuntimeException("Service unavailable");
		}
		this.service = service;
	}

	@Override
	@SuppressWarnings("rawtypes")
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity save(@RequestBody @Validated UserDTOPost user, BindingResult result) {
		try {
			if (result.hasErrors()) {
				return ResponseEntity.badRequest()
						.body(result.getAllErrors().stream().map(error -> error.getCodes()[0]).toArray());
			}

			service.saveAndFlush(user);

			return new ResponseEntity<>(HttpStatus.CREATED);
		} catch (CreateUpdateException e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			log.error("save::Unexpected error on rest controller", e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity get(@PathVariable("id") Long id) {
		try {
			UserDTOGet dto = service.findOne(id);

			return new ResponseEntity<UserDTOGet>(dto, HttpStatus.OK);

		} catch (ReadException e) {
			switch (ReadExceptionMessageEnum.valueOf(e.getMessage())) {
			case NOT_FOUND:
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			case ID_NULL_EXCEPTION:
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			case UNEXPECTED_EXCEPTION:
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			default:
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}

		} catch (Exception e) {
			log.error("get::Unexpected error on rest controller", e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	@SuppressWarnings("rawtypes")
	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity update(@RequestBody @Validated UserDTOPut user, BindingResult result) {

		try {
			if (result.hasErrors()) {
				return ResponseEntity.badRequest()
						.body(result.getAllErrors().stream().map(error -> error.getCodes()[0]).toArray());
			}

			Assert.notNull(user.getId(), "ID should not be null");

			service.saveAndFlush(new UserDTOPost(user));

			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (CreateUpdateException e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (IllegalArgumentException e) {
			if ("ID should not be null".equals(e.getMessage())) {
				return new ResponseEntity<>(new Object[] { "NotNull.userDTO.id" }, HttpStatus.BAD_REQUEST);
			} else {
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			log.error("update::Unexpected error on rest controller", e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<PagedResources<Resource<User>>> findBy(@RequestParam String searchValue, Pageable pageable,
			@SuppressWarnings("rawtypes") PagedResourcesAssembler assembler) {
		try {
			log.debug(String.format("findBy::searchValue:%s, pageNumber:%d, offset:%d, pagesize:%d, sort:%s",
					searchValue, pageable.getPageNumber(), pageable.getOffset(), pageable.getPageSize(),
					pageable.getSort()));

			Page<User> dtoPage = service.findBy(searchValue, pageable);

			return new ResponseEntity<>(assembler.toResource(dtoPage), HttpStatus.OK);

		} catch (ReadException e) {
			log.error("findBy:: Error getting the user page", e);
			return new ResponseEntity<PagedResources<Resource<User>>>(
					(PagedResources<Resource<User>>) PagedResources.NO_PAGE, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			log.error("findBy::Unexpected error on rest controller", e);
			return new ResponseEntity<PagedResources<Resource<User>>>(
					(PagedResources<Resource<User>>) PagedResources.NO_PAGE, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity delete(@PathVariable("id") Long id) {

		//TODO tests cases need to be written 
		
		try {
			service.delete(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);

		} catch (DeleteException e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

		} catch (Exception e) {
			log.error("get::Unexpected error on rest controller", e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
