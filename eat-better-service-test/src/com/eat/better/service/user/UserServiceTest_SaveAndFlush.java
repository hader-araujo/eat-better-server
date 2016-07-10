package com.eat.better.service.user;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;	

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.eat.better.entity.User;
import com.eat.better.repository.UserJpaRepository;
import com.eat.better.service.exception.DTONullPointerException;
import com.eat.better.service.exception.FieldNullPointerException;
import com.eat.better.service.exception.crudgeneric.CreateGenericException;
import com.eat.better.service.user.UserDTO;
import com.eat.better.service.user.UserService;
import com.eat.better.service.user.UserServiceImpl;

public class UserServiceTest_SaveAndFlush {

	UserJpaRepository repository;
	UserService service;

	final Long id = 1L;
	final String login = "myLogin";
	final String name = "myName";

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Before
	public void setup() {
		repository = mock(UserJpaRepository.class);
		service = new UserServiceImpl(repository);
	}

	@Test
	public void saveAndFlush_ShouldSaveDto() throws CreateGenericException {
		User userIn = new User();
		userIn.setLogin(login);
		userIn.setName(name);

		User userOut = new User();
		userOut.setId(id);
		userOut.setLogin(login);
		userOut.setName(name);

		when(repository.saveAndFlush(userIn)).thenAnswer(new Answer<Object>() {
			@Override
			public User answer(InvocationOnMock invocation) throws Throwable {
				@SuppressWarnings("unused")
				Object[] args = invocation.getArguments();
				// ((User)args[0]).setId(id); //TODO
				return userOut;
			}
		});

		UserDTO dto = new UserDTO();
		dto.setLogin(login);
		dto.setName(name);

		UserDTO dtoSaved = service.saveAndFlush(dto);

		verify(repository, times(1)).saveAndFlush(userIn);

		// assertThat("DTO should has id", dtoSaved, hasProperty("id",
		// equalTo(id))); //TODO
		assertThat("DTO should has login", dtoSaved, hasProperty("login", equalTo(login)));
		assertThat("DTO should has name", dtoSaved, hasProperty("name", equalTo(name)));
	}

	@Test
	public void saveAndFlush_NullNameShouldThrowException() throws CreateGenericException {
		thrown.expect(FieldNullPointerException.class);
		thrown.expectMessage(String.format("%s-%s", UserDTO.class.getName(), UserDTO.FIELD.NAME));

		UserDTO dto = new UserDTO();
		dto.setLogin(login);
		service.saveAndFlush(dto);
	}

	@Test
	public void saveAndFlush_NullLoginShouldThrowException() throws CreateGenericException {
		thrown.expect(FieldNullPointerException.class);
		thrown.expectMessage(String.format("%s-%s", UserDTO.class.getName(), UserDTO.FIELD.LOGIN));

		UserDTO dto = new UserDTO();
		service.saveAndFlush(dto);
	}

	@Test
	public void saveAndFlush_NullDtoShouldThrowException() throws CreateGenericException {
		thrown.expect(DTONullPointerException.class);
		thrown.expectMessage(UserDTO.class.getName());

		service.saveAndFlush(null);
	}

	@Test(expected = CreateGenericException.class)
	public void saveAndFlush_NullRepositoryShouldThrowException() throws CreateGenericException {
		service = new UserServiceImpl(null);
		
		UserDTO dto = new UserDTO();
		dto.setLogin(login);
		dto.setName(name);
		
		service.saveAndFlush(dto);
	}

}
