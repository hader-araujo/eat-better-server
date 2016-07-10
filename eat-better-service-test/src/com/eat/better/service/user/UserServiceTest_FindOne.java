package com.eat.better.service.user;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import com.eat.better.entity.User;
import com.eat.better.repository.UserJpaRepository;
import com.eat.better.service.exception.DTONotFoundException;
import com.eat.better.service.exception.crudgeneric.ReadGenericException;

public class UserServiceTest_FindOne {
	
	UserJpaRepository repository;
	UserService service;
	
	final Long id = 1L;
	final String login = "myLogin";
	final String name = "myName";
	
	@Before
	public void setup() {
		repository = mock(UserJpaRepository.class);
	}
	
	@Test
	public void findOne_ShouldReturnEntry() throws ReadGenericException {

		User entity = new User();
		entity.setId(id);
		entity.setLogin(login);
		entity.setName(name);
		when(repository.findOne(id)).thenReturn(entity);

		service = new UserServiceImpl(repository);

		UserDTO dto = service.findOne(id);

		assertThat("DTO should not be null", dto, notNullValue());
		assertThat("Service should return the login", dto.getLogin(), equalTo(login));
		assertThat("Service should return the name", dto.getName(), equalTo(name));
	}

	@Test(expected = DTONotFoundException.class)
	public void findOne_DontExistsShouldThrowException() throws ReadGenericException {

		when(repository.findOne(id)).thenReturn(null);

		service = new UserServiceImpl(repository);

		service.findOne(id);
	}

	@Test (expected = ReadGenericException.class)
	public void findOne_NullRespositotyShouldThrowException() throws ReadGenericException {

		when(repository.findOne(id)).thenThrow(new RuntimeException());

		service = new UserServiceImpl(repository);

		service.findOne(id);
	}
}
