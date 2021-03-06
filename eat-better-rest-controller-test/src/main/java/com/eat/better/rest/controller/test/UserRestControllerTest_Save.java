package com.eat.better.rest.controller.test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import com.eat.better.rest.controller.UserRestController;
import com.eat.better.rest.controller.UserRestControllerImpl;
import com.eat.better.service.UserService;
import com.eat.better.service.dto.user.UserDTOPost;
import com.eat.better.service.exception.CreateUpdateException;
import com.eat.better.service.exception.enums.CreateUpdateExceptionMessageEnum;

public class UserRestControllerTest_Save {

	private UserService service;
	private UserRestController controller;
	private BindingResult result;

	private String fieldName_Login = "login";
	private String fieldName_Name = "name";

	private String login = "myLogin";
	private String name = "My Name";

	@Before
	public void setup() {
		result = mock(BindingResult.class);
		service = mock(UserService.class);
		controller = new UserRestControllerImpl(service);
	}

	@Test
	public void save_GivenDTOShoudReturnOKStatus() throws CreateUpdateException {

		UserDTOPost dto = new UserDTOPost();
		dto.setLogin(login);
		dto.setName(name);

		when(result.hasErrors()).thenReturn(false);

		@SuppressWarnings("rawtypes")
		ResponseEntity responseEntity = controller.save(dto, result);

		HttpStatus httpStatus = responseEntity.getStatusCode();
		assertThat("Wrong HTTP status", httpStatus, equalTo(HttpStatus.CREATED));

		verify(service, times(1)).saveAndFlush(dto);
	}

	@Test
	public void save_GivenSomeErrorsShouldReturnTheCodeForEachField() throws CreateUpdateException {
		String errorCodeLoginNotNull = "NotNull.userDTO.login";
		String errorCodeNameSize = "Size.userDTO.name";

		List<ObjectError> listObjectError = new ArrayList<>();
		listObjectError.add(new ObjectError(fieldName_Login, new String[] { errorCodeLoginNotNull }, null, null));
		listObjectError.add(new ObjectError(fieldName_Name, new String[] { errorCodeNameSize }, null, null));

		when(result.hasErrors()).thenReturn(true);
		when(result.getAllErrors()).thenReturn(listObjectError);

		@SuppressWarnings({ "rawtypes" })
		ResponseEntity responseEntity = controller.save(null, result);

		HttpStatus httpStatus = responseEntity.getStatusCode();
		Object[] body = (Object[]) responseEntity.getBody();

		assertThat("Wrong HTTP status", httpStatus, equalTo(HttpStatus.BAD_REQUEST));
		assertThat("Missing error code", Arrays.asList(body), hasItem(equalTo(errorCodeLoginNotNull)));
		assertThat("Missing error code", Arrays.asList(body), hasItem(equalTo(errorCodeNameSize)));

		verify(service, times(0)).saveAndFlush(any(UserDTOPost.class));
	}

	@Test
	public void save_GivenExceptionOnServiceShoudReturnInternalServerErrorStatus() throws CreateUpdateException {
		doThrow(new CreateUpdateException(CreateUpdateExceptionMessageEnum.UNEXPECTED_EXCEPTION)).when(service)
				.saveAndFlush(any(UserDTOPost.class));
		when(result.hasErrors()).thenReturn(false);

		@SuppressWarnings("rawtypes")
		ResponseEntity responseEntity = controller.save(null, result);

		HttpStatus httpStatus = responseEntity.getStatusCode();
		assertThat("Wrong HTTP status", httpStatus, equalTo(HttpStatus.INTERNAL_SERVER_ERROR));
		verify(service, times(1)).saveAndFlush(any(UserDTOPost.class));
	}

	@Test
	public void save_GivenAnyExceptionShoudReturnInternalServerErrorStatus() throws CreateUpdateException {
		doThrow(new RuntimeException()).when(result).hasErrors();

		@SuppressWarnings("rawtypes")
		ResponseEntity responseEntity = controller.save(null, result);

		HttpStatus httpStatus = responseEntity.getStatusCode();
		assertThat("Wrong HTTP status", httpStatus, equalTo(HttpStatus.INTERNAL_SERVER_ERROR));
		verify(service, times(0)).saveAndFlush(any(UserDTOPost.class));
	}

}
