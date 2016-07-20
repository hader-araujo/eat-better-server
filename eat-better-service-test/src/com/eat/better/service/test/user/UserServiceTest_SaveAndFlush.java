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
import com.eat.better.service.exception.CreateUpdateException;
import com.eat.better.service.exception.ReadException;
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

	// TODO
	// @Test
	// public void saveAndFlush_ChangeTheLoginShouldThrowException()
	// throws CreateUpdateException, ReadException {
	// thrown.expect(FieldReadOnlyException.class);
	// thrown.expectMessage(String.format("%s-%s", UserDTO.class.getName(),
	// UserDTO.FIELD.LOGIN));
	//
	// User userSearch = new User();
	// userSearch.setId(id);
	// userSearch.setLogin(login);
	// userSearch.setName(name);
	//
	// User userUpdated = new User();
	// userUpdated.setId(id);
	// userUpdated.setLogin(newLogin);
	// userUpdated.setName(name);
	//
	// when(repository.findOne(id)).thenReturn(userSearch);
	// when(repository.findByIdAndLogin(id, login)).thenReturn(userSearch);
	// when(repository.saveAndFlush(userUpdated)).thenReturn(userUpdated);
	//
	// UserDTO userDTO = service.findOne(id);
	// userDTO.setLogin(newLogin);
	// service.saveAndFlush(userDTO);
	//
	// }

	@Test
	public void saveAndFlush_GivenEntityWithIdShouldUpdate() throws CreateUpdateException, ReadException {
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
	public void saveAndFlush_GivenEntityWithoutIdShouldSaveDto() throws CreateUpdateException {
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

}
