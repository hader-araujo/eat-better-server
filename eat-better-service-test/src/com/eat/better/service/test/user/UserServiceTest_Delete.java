package com.eat.better.service.test.user;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.eat.better.entity.User;
import com.eat.better.repository.UserJpaRepository;
import com.eat.better.service.exception.DeleteException;
import com.eat.better.service.exception.ReadException;
import com.eat.better.service.exception.enums.DeleteExceptionMessageEnum;
import com.eat.better.service.exception.enums.ReadExceptionMessageEnum;
import com.eat.better.service.user.UserService;
import com.eat.better.service.user.UserServiceImpl;

public class UserServiceTest_Delete {

	UserJpaRepository repository;
	UserService service;

	final Long id = 1L;

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Before
	public void setup() {
		repository = mock(UserJpaRepository.class);
		service = new UserServiceImpl(repository);
	}

	@Test
	public void delete_GivenOneEntityShouldDeleteIt() throws ReadException, DeleteException {
		thrown.expect(ReadException.class);
		thrown.expectMessage(ReadExceptionMessageEnum.NOT_FOUND.name());
		
		User entity = new User();
		entity.setId(id);
		when(repository.findOne(id)).thenReturn(entity).thenReturn(null);

		service.delete(id);
		service.findOne(id);
	}

	@Test
	public void delete_DontExistEntityShouldThrowException() throws DeleteException {
		thrown.expect(DeleteException.class);
		thrown.expectMessage(DeleteExceptionMessageEnum.NOT_FOUND.name());

		when(repository.findOne(id)).thenReturn(null);

		service.delete(id);
	}

	@Test
	public void delete_NullIdShouldThrowException() throws DeleteException {
		thrown.expect(DeleteException.class);
		thrown.expectMessage(DeleteExceptionMessageEnum.ID_NULL_EXCEPTION.name());

		when(repository.findOne(null)).thenThrow(new IllegalArgumentException());
		
		service.delete(null);
	}

}
