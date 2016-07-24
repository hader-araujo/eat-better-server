package com.eat.better.service.test.user;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.eat.better.entity.User;
import com.eat.better.repository.UserJpaRepository;
import com.eat.better.service.UserService;
import com.eat.better.service.UserServiceImpl;
import com.eat.better.service.exception.DeleteException;
import com.eat.better.service.exception.ReadException;
import com.eat.better.service.exception.enums.DeleteExceptionMessageEnum;
import com.eat.better.service.exception.enums.ReadExceptionMessageEnum;

public class UserServiceTest_Delete {

	private UserJpaRepository repository;
	private UserService service;

	private final Long id = 1L;

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

		verify(service, times(1)).delete(id);
		verify(service, times(2)).findOne(id);

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

		when(repository.findOne(any(Long.class))).thenThrow(new IllegalArgumentException());

		service.delete(null);
	}

	@Test
	public void delete_GivenExceptionOnRepositoryShouldThowException() throws DeleteException {
		thrown.expect(DeleteException.class);
		thrown.expectMessage(ReadExceptionMessageEnum.UNEXPECTED_EXCEPTION.name());

		User entity = new User();
		entity.setId(id);
		when(repository.findOne(id)).thenReturn(entity);

		doThrow(new RuntimeException()).when(repository).delete(any(Long.class));

		service.delete(id);
	}

}
