package com.eat.better.rest.controller.test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doThrow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import com.eat.better.entity.User;
import com.eat.better.rest.controller.UserRestController;
import com.eat.better.rest.controller.UserRestControllerImpl;
import com.eat.better.service.exception.CreateUpdateException;
import com.eat.better.service.exception.DeleteException;
import com.eat.better.service.exception.ReadException;
import com.eat.better.service.exception.enums.CreateUpdateExceptionMessageEnum;
import com.eat.better.service.exception.enums.ReadExceptionMessageEnum;
import com.eat.better.service.user.UserDTO;
import com.eat.better.service.user.UserService;

public class UserRestControllerTest_Save {

	private UserService service;
	private UserRestController controller;
	private BindingResult result;

	private String fieldName_Id = "id";
	private String fieldName_Login = "login";
	private String fieldName_Name = "name";

	private Long id = 1L;
	private String login = "myLogin";
	private String name = "My Name";

	@Before
	public void setup() {
		result = mock(BindingResult.class);
		service = mock(UserService.class);
		controller = new UserRestControllerImpl(service);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void save_sGivenDTOWithoutIdShoudReturnOKStatus() throws CreateUpdateException {
		UserDTO dtoToBeChanged = new UserDTO();
		dtoToBeChanged.setLogin(login);
		dtoToBeChanged.setName(name);

		UserDTO dtoToSave = new UserDTO();
		dtoToSave.setLogin(login);
		dtoToSave.setName(name);

		when(result.hasErrors()).thenReturn(false);

		doAnswer(new Answer<UserDTO>() {

			public UserDTO answer(InvocationOnMock invocation) throws Throwable {
				Object[] args = invocation.getArguments();
				if (args[0] instanceof UserDTO) {
					UserDTO userDtoParam = (UserDTO) args[0];
					userDtoParam.setId(id);
				}
				return null;
			}
		}).when(service).saveAndFlush(dtoToBeChanged);

		@SuppressWarnings("rawtypes")
		ResponseEntity responseEntity = controller.save(dtoToSave, result);

		HttpStatus httpStatus = responseEntity.getStatusCode();
		assertThat("Wrong HTTP status", httpStatus, equalTo(HttpStatus.OK));

		verify(service, times(1)).saveAndFlush(dtoToSave);
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

		verify(service, times(0)).saveAndFlush(null);
	}

	@Test
	public void save_GivenExceptionOnServiceShoudReturnInternalServerErrorStatus() throws CreateUpdateException {
		doThrow(new CreateUpdateException(CreateUpdateExceptionMessageEnum.UNEXPECTED_EXCEPTION)).when(service).saveAndFlush(any(UserDTO.class));
		when(result.hasErrors()).thenReturn(false);

		controller.save(null, result);
	}

}
