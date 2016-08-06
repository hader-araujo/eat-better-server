package com.eat.better.service.test.user;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.mockito.Matchers.any;
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
import com.eat.better.service.dto.user.UserDTOPost;
import com.eat.better.service.exception.CreateUpdateException;
import com.eat.better.service.exception.ReadException;
import com.eat.better.service.exception.enums.CreateUpdateExceptionMessageEnum;

public class UserServiceTest_SaveAndFlush {

	private UserJpaRepository repository;
	private UserService service;

	private final Long id = 1L;
	private final String login = "myLogin";
	private final String name = "myName";
	private final String newLogin = "myNewLogin";
	private final String newName = "my new name";

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
		userUpdated.setLogin(newLogin);
		userUpdated.setName(newName);

		when(repository.findOne(id)).thenReturn(userSearch);
		when(repository.saveAndFlush(userUpdated)).thenReturn(userUpdated);

		UserDTOPost userDTO = new UserDTOPost();
		userDTO.setId(id);
		userDTO.setLogin(newLogin);
		userDTO.setName(newName);
		service.saveAndFlush(userDTO);

		assertThat("DTO should has id", userDTO, hasProperty("id", equalTo(id)));
		assertThat("DTO should has login", userDTO, hasProperty("login", equalTo(newLogin)));
		assertThat("DTO should has name", userDTO, hasProperty("name", equalTo(newName)));
	}

	@Test
	public void saveAndFlush_GivenEntityWithoutIdShouldSaveDto() throws CreateUpdateException {
		User user = new User();
		user.setLogin(login.toUpperCase());
		user.setName(name.toUpperCase());

		when(repository.saveAndFlush(user)).thenReturn(user);

		UserDTOPost dto = new UserDTOPost();
		dto.setLogin(login);
		dto.setName(name);

		service.saveAndFlush(dto);

		verify(repository, times(1)).saveAndFlush(user);

		assertThat("DTO should has login", dto, hasProperty("login", equalTo(login)));
		assertThat("DTO should has name", dto, hasProperty("name", equalTo(name)));
	}

	@Test
	public void findOne_GivenExceptionOnRepositoryShouldThowException() throws CreateUpdateException {
		thrown.expect(CreateUpdateException.class);
		thrown.expectMessage(CreateUpdateExceptionMessageEnum.UNEXPECTED_EXCEPTION.name());

		when(repository.saveAndFlush(any(User.class))).thenThrow(new RuntimeException());

		service.saveAndFlush(new UserDTOPost());
	}

}
