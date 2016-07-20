package com.eat.better.service.test.user;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.both;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.eat.better.entity.User;
import com.eat.better.repository.UserJpaRepository;
import com.eat.better.service.exception.ReadException;
import com.eat.better.service.user.UserDTO;
import com.eat.better.service.user.UserService;
import com.eat.better.service.user.UserServiceImpl;

public class UserServiceTest_FindAll {

	UserJpaRepository repository;
	UserService service;

	private final long id1 = 1;
	private final String login1 = "login_1";
	private final String name1 = "The name of the first person";

	private final long id2 = 2;
	private final String login2 = "login_2";
	private final String name2 = "The name of the second person";

	private final long id3 = 3;
	private final String login3 = "login_3";
	private final String name3 = "The name of the third person";

	@Before
	public void setup() {
		repository = mock(UserJpaRepository.class);
		service = new UserServiceImpl(repository);
	}

	@Test
	public void findAll_GivenEntityListShouldReturnDtoList() throws ReadException {

		List<User> userList = getListOfUser();

		when(repository.findAll()).thenReturn(userList);

		List<UserDTO> userDtoListExpected = getListOfUserDTO();
		List<UserDTO> userDtoListOut = service.findAll();

		assertThat("The list should not be empty", userDtoListOut, not(empty()));
		assertThat("The list should has 3 elements", userDtoListOut, hasSize(3));
		assertThat("The return is wrong", userDtoListOut, is(userDtoListExpected));
		// the same of the before line
		// assertArrayEquals("The return is wrong",
		// userDtoListExpected.toArray(), userDtoListOut.toArray());

	}

	@Test
	public void findAll_NoneDtoShouldReturnEmptyList() throws ReadException {
		when(repository.findAll()).thenReturn(Collections.emptyList());

		List<UserDTO> userList = service.findAll();

		assertThat("The list should be empty but not null", userList, both(empty()).and(notNullValue()));
	}

	private List<User> getListOfUser() {
		User user1 = new User();
		user1.setId(id1);
		user1.setLogin(login1);
		user1.setName(name1);

		User user2 = new User();
		user2.setId(id2);
		user2.setLogin(login2);
		user2.setName(name2);

		User user3 = new User();
		user3.setId(id3);
		user3.setLogin(login3);
		user3.setName(name3);

		List<User> userList = new ArrayList<>();
		userList.add(user1);
		userList.add(user2);
		userList.add(user3);

		return userList;
	}

	private List<UserDTO> getListOfUserDTO() {
		UserDTO userDto1 = new UserDTO();
		userDto1.setId(id1);
		userDto1.setLogin(login1);
		userDto1.setName(name1);

		UserDTO userDto2 = new UserDTO();
		userDto2.setId(id2);
		userDto2.setLogin(login2);
		userDto2.setName(name2);

		UserDTO userDto3 = new UserDTO();
		userDto3.setId(id3);
		userDto3.setLogin(login3);
		userDto3.setName(name3);

		List<UserDTO> userDtoList = new ArrayList<>();
		userDtoList.add(userDto1);
		userDtoList.add(userDto2);
		userDtoList.add(userDto3);

		return userDtoList;
	}
}
