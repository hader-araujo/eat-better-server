package com.eat.better.service.test.user;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.eat.better.entity.User;
import com.eat.better.repository.UserJpaRepository;
import com.eat.better.service.exception.DTONotFoundException;
import com.eat.better.service.exception.FieldNullPointerException;
import com.eat.better.service.exception.crudgeneric.DeleteGenericException;
import com.eat.better.service.exception.crudgeneric.ReadGenericException;
import com.eat.better.service.user.UserDTO;
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

	@Test(expected = DTONotFoundException.class)
	public void delete_GivenOneEntityShouldDeleteIt()  throws  DeleteGenericException, FieldNullPointerException, ReadGenericException   {

		User entity = new User();
		entity.setId(id);
		when(repository.findOne(id)).thenReturn(entity).thenReturn(null);

		service.delete(id);
		service.findOne(id);
	}

	@Test(expected = DTONotFoundException.class)
	public void delete_DontExistEntityShouldThrowException()  throws  DeleteGenericException, DTONotFoundException, FieldNullPointerException   {

		when(repository.findOne(id)).thenReturn(null);

		service.delete(id);
	}
	
	@Test
	public void delete_NullIdShouldThrowException()  throws  DeleteGenericException, DTONotFoundException, FieldNullPointerException   {
		thrown.expect(FieldNullPointerException.class);
		thrown.expectMessage(String.format("%s-%s", UserDTO.class.getName(), UserDTO.FIELD.ID.name()));
		service.delete(null);
	}

	@Test(expected = DeleteGenericException.class)
	public void delete_NullRepositotyShouldThrowException() throws  DeleteGenericException, DTONotFoundException, FieldNullPointerException   {

		when(repository.findOne(id)).thenThrow(new RuntimeException());
		service.delete(id);
	}
}
