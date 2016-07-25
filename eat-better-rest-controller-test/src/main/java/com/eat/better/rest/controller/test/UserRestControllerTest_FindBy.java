package com.eat.better.rest.controller.test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.eat.better.entity.User;
import com.eat.better.rest.controller.UserRestController;
import com.eat.better.rest.controller.UserRestControllerImpl;
import com.eat.better.service.UserService;
import com.eat.better.service.exception.ReadException;

public class UserRestControllerTest_FindBy {

	// private Pageable pageable;
	private UserService service;
	private UserRestController controller;

	@Before
	public void setup() {
		// pageable = mock(Pageable.class);
		service = mock(UserService.class);
		controller = new UserRestControllerImpl(service);
	}

	@Test
	public void findBy_GivenAPageShoudReturnOKStatusWithData() throws ReadException {

		// TODO

	}

	@Test
	public void findBy_GivenAnyExceptionShoudReturnInternalServerErrorStatus() throws ReadException {
		doThrow(new RuntimeException()).when(service).findBy(any(String.class), any(String.class), any(Pageable.class));

		ResponseEntity<PagedResources<Resource<User>>> responseEntity = controller.findBy(null, null, null, null);

		HttpStatus httpStatus = responseEntity.getStatusCode();
		assertThat("Wrong HTTP status", httpStatus, equalTo(HttpStatus.INTERNAL_SERVER_ERROR));
		verify(service, times(1)).findBy(any(String.class), any(String.class), any(Pageable.class));
	}
}
