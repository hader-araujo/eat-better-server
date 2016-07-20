package com.eat.better.service.test.user;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.eat.better.entity.User;
import com.eat.better.repository.UserJpaRepository;
import com.eat.better.service.exception.ReadException;
import com.eat.better.service.exception.enums.ReadExceptionMessageEnum;
import com.eat.better.service.user.UserDTO;
import com.eat.better.service.user.UserService;
import com.eat.better.service.user.UserServiceImpl;

public class UserServiceTest_FindOne {

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
	public void findOne_GivenOneEntityShouldReturnOneDto() throws ReadException {

		User entity = new User();
		entity.setId(id);
		entity.setLogin(login);
		entity.setName(name);
		when(repository.findOne(id)).thenReturn(entity);

		UserDTO dto = service.findOne(id);

		assertThat("DTO should not be null", dto, notNullValue());
		assertThat("Service should return the login", dto.getLogin(), equalTo(login));
		assertThat("Service should return the name", dto.getName(), equalTo(name));
	}

	@Test
	public void findOne_DontExistEntityShouldThrowException() throws ReadException {
		thrown.expect(ReadException.class);
		thrown.expectMessage(ReadExceptionMessageEnum.NOT_FOUND.name());

		when(repository.findOne(id)).thenReturn(null);

		service.findOne(id);
	}

	@Test
	public void findOne_NullIdShouldThrowException() throws ReadException {
		thrown.expect(ReadException.class);
		thrown.expectMessage(ReadExceptionMessageEnum.ID_NULL_EXCEPTION.name());
		
		when(repository.findOne(null)).thenThrow(new IllegalArgumentException());
		
		service.findOne(null);
	}

}
