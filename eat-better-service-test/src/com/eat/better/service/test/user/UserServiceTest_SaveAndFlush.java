package com.eat.better.service.test.user;

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

import com.eat.better.entity.User;
import com.eat.better.repository.UserJpaRepository;
import com.eat.better.service.exception.DTONullPointerException;
import com.eat.better.service.exception.FieldNullPointerException;
import com.eat.better.service.exception.FieldReadOnlyException;
import com.eat.better.service.exception.crudgeneric.CreateGenericException;
import com.eat.better.service.exception.crudgeneric.ReadGenericException;
import com.eat.better.service.exception.crudgeneric.UpdateGenericException;
import com.eat.better.service.user.UserDTO;
import com.eat.better.service.user.UserService;
import com.eat.better.service.user.UserServiceImpl;

public class UserServiceTest_SaveAndFlush {

	UserJpaRepository repository;
	UserService service;

	final Long id = 1L;
	final String login = "myLogin";
	final String name = "myName";
	final String newLogin = "myNewLogin";
	final String newName = "my new name";

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Before
	public void setup() {
		repository = mock(UserJpaRepository.class);
		service = new UserServiceImpl(repository);
	}

	@Test
	public void saveAndFlush_ChangeTheLoginShouldThrowException()
			throws CreateGenericException, ReadGenericException, UpdateGenericException, FieldNullPointerException {
		thrown.expect(FieldReadOnlyException.class);
		thrown.expectMessage(String.format("%s-%s", UserDTO.class.getName(), UserDTO.FIELD.LOGIN));

		User userSearch = new User();
		userSearch.setId(id);
		userSearch.setLogin(login);
		userSearch.setName(name);

		User userUpdated = new User();
		userUpdated.setId(id);
		userUpdated.setLogin(newLogin);
		userUpdated.setName(name);

		when(repository.findOne(id)).thenReturn(userSearch);
		when(repository.findByIdAndLogin(id, login)).thenReturn(userSearch);
		when(repository.saveAndFlush(userUpdated)).thenReturn(userUpdated);

		UserDTO userDTO = service.findOne(id);
		userDTO.setLogin(newLogin);
		service.saveAndFlush(userDTO);

	}

	@Test
	public void saveAndFlush_GivenEntityWithIdShouldUpdate()
			throws CreateGenericException, ReadGenericException, UpdateGenericException, FieldNullPointerException {
		User userSearch = new User();
		userSearch.setId(id);
		userSearch.setLogin(login);
		userSearch.setName(name);

		User userUpdated = new User();
		userUpdated.setId(id);
		userUpdated.setLogin(login);
		userUpdated.setName(newName);

		when(repository.findOne(id)).thenReturn(userSearch);
		when(repository.findByIdAndLogin(id, login)).thenReturn(userSearch);
		when(repository.saveAndFlush(userUpdated)).thenReturn(userUpdated);

		UserDTO userDTO = service.findOne(id);
		userDTO.setName(newName);
		service.saveAndFlush(userDTO);

		assertThat("DTO should has id", userDTO, hasProperty("id", equalTo(id)));
		assertThat("DTO should has login", userDTO, hasProperty("login", equalTo(login)));
		assertThat("DTO should has name", userDTO, hasProperty("name", equalTo(newName)));
	}

	@Test
	public void saveAndFlush_GivenOneEntityShouldSaveDto()
			throws CreateGenericException, UpdateGenericException, FieldNullPointerException {
		User userIn = new User();
		userIn.setLogin(login);
		userIn.setName(name);

		User userOut = new User();
		userOut.setId(id);
		userOut.setLogin(login);
		userOut.setName(name);

		when(repository.saveAndFlush(userIn)).thenReturn(userOut);
		// thenAnswer(new Answer<Object>() {
		// @Override
		// public User answer(InvocationOnMock invocation) throws Throwable {
		// @SuppressWarnings("unused")
		// Object[] args = invocation.getArguments();
		// // ((User)args[0]).setId(id); //TODO
		// return userOut;
		// }
		// });

		UserDTO dto = new UserDTO();
		dto.setLogin(login);
		dto.setName(name);

		service.saveAndFlush(dto);

		verify(repository, times(1)).saveAndFlush(userIn);

		assertThat("DTO should has id", dto, hasProperty("id", equalTo(id)));
		assertThat("DTO should has login", dto, hasProperty("login", equalTo(login)));
		assertThat("DTO should has name", dto, hasProperty("name", equalTo(name)));
	}

	@Test
	public void saveAndFlush_NullNameShouldThrowException()
			throws CreateGenericException, UpdateGenericException, FieldNullPointerException {
		thrown.expect(FieldNullPointerException.class);
		thrown.expectMessage(String.format("%s-%s", UserDTO.class.getName(), UserDTO.FIELD.NAME.name()));

		UserDTO dto = new UserDTO();
		dto.setLogin(login);
		service.saveAndFlush(dto);
	}

	@Test
	public void saveAndFlush_NullLoginShouldThrowException()
			throws CreateGenericException, UpdateGenericException, FieldNullPointerException {
		thrown.expect(FieldNullPointerException.class);
		thrown.expectMessage(String.format("%s-%s", UserDTO.class.getName(), UserDTO.FIELD.LOGIN.name()));

		UserDTO dto = new UserDTO();
		service.saveAndFlush(dto);
	}

	@Test
	public void saveAndFlush_NullDtoShouldThrowException()
			throws CreateGenericException, UpdateGenericException, FieldNullPointerException {
		thrown.expect(DTONullPointerException.class);
		thrown.expectMessage(UserDTO.class.getName());

		service.saveAndFlush(null);
	}

	@Test(expected = CreateGenericException.class)
	public void saveAndFlush_NullRepositoryShouldThrowException()
			throws CreateGenericException, UpdateGenericException, FieldNullPointerException {
		service = new UserServiceImpl(null);

		UserDTO dto = new UserDTO();
		dto.setLogin(login);
		dto.setName(name);

		service.saveAndFlush(dto);
	}

	@Test(expected = UpdateGenericException.class)
	public void saveAndFlush_UpdateWithNullRepositoryShouldThrowException()
			throws CreateGenericException, UpdateGenericException, FieldNullPointerException {
		service = new UserServiceImpl(null);

		UserDTO dto = new UserDTO();
		dto.setId(id);
		dto.setLogin(login);
		dto.setName(name);

		service.saveAndFlush(dto);
	}

}
