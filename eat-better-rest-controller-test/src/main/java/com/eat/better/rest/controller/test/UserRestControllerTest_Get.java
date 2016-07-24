package com.eat.better.rest.controller.test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.eat.better.rest.controller.UserRestController;
import com.eat.better.rest.controller.UserRestControllerImpl;
import com.eat.better.service.UserService;
import com.eat.better.service.dto.user.UserDTOGet;
import com.eat.better.service.exception.ReadException;
import com.eat.better.service.exception.enums.ReadExceptionMessageEnum;

public class UserRestControllerTest_Get {

	private UserService service;
	private UserRestController controller;
	private Long id = 1L;
	private String login = "myLogin";
	private String name = "My Name";

	@Before
	public void setup() {
		service = mock(UserService.class);
		controller = new UserRestControllerImpl(service);
	}

	@Test
	public void get_GivenIdShouldReturnOKStatusWithData() throws ReadException {
		UserDTOGet dtoToReturn = new UserDTOGet();
		dtoToReturn.setId(id);
		dtoToReturn.setLogin(login);
		dtoToReturn.setName(name);

		when(service.findOne(id)).thenReturn(dtoToReturn);

		@SuppressWarnings("rawtypes")
		ResponseEntity responseEntity = controller.get(1L);

		HttpStatus httpStatus = responseEntity.getStatusCode();
		UserDTOGet body = (UserDTOGet) responseEntity.getBody();

		assertThat("Wrong HTTP status for correct ID", httpStatus, equalTo(HttpStatus.OK));
		assertThat("Missing error code", body, hasProperty("id", (equalTo(id))));
		assertThat("Missing error code", body, hasProperty("login", (equalTo(login))));
		assertThat("Missing error code", body, hasProperty("name", (equalTo(name))));

		verify(service, times(1)).findOne(any(Long.class));
	}

	@Test
	public void get_GivenUnknownIdShouldReturnNotFoundStatus() throws ReadException {
		when(service.findOne(id)).thenThrow(new ReadException(ReadExceptionMessageEnum.NOT_FOUND));

		@SuppressWarnings("rawtypes")
		ResponseEntity responseEntity = controller.get(1L);

		HttpStatus httpStatus = responseEntity.getStatusCode();

		assertThat("Wrong HTTP status for unknown ID", httpStatus, equalTo(HttpStatus.NOT_FOUND));

		verify(service, times(1)).findOne(id);
	}

	@Test
	public void get_GivenNullIdShouldReturnBadRequestStatus() throws ReadException {
		when(service.findOne(null)).thenThrow(new ReadException(ReadExceptionMessageEnum.ID_NULL_EXCEPTION));

		@SuppressWarnings("rawtypes")
		ResponseEntity responseEntity = controller.get(null);

		HttpStatus httpStatus = responseEntity.getStatusCode();
		assertThat("Wrong HTTP status for null ID", httpStatus, equalTo(HttpStatus.BAD_REQUEST));
		verify(service, times(1)).findOne(any(Long.class));
	}

	@Test
	public void get_GivenExceptionOnServiceShouldReturnInternalServerErrorStatus() throws ReadException {
		when(service.findOne(any(Long.class)))
				.thenThrow(new ReadException(ReadExceptionMessageEnum.UNEXPECTED_EXCEPTION));

		@SuppressWarnings("rawtypes")
		ResponseEntity responseEntity = controller.get(null);

		HttpStatus httpStatus = responseEntity.getStatusCode();
		assertThat("Wrong HTTP status", httpStatus, equalTo(HttpStatus.INTERNAL_SERVER_ERROR));
		verify(service, times(1)).findOne(any(Long.class));
	}

	@Test
	public void get_GivenAnyExceptionShoudReturnInternalServerErrorStatus() throws ReadException {
		doThrow(new RuntimeException()).when(service).findOne(any(Long.class));

		@SuppressWarnings("rawtypes")
		ResponseEntity responseEntity = controller.get(null);

		HttpStatus httpStatus = responseEntity.getStatusCode();
		assertThat("Wrong HTTP status", httpStatus, equalTo(HttpStatus.INTERNAL_SERVER_ERROR));
		verify(service, times(1)).findOne(any(Long.class));
	}
}
