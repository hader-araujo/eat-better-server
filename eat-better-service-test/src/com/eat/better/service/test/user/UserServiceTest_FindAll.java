package com.eat.better.service.test.user;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.both;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Matchers.any;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.eat.better.entity.User;
import com.eat.better.repository.UserJpaRepository;
import com.eat.better.service.UserService;
import com.eat.better.service.UserServiceImpl;
import com.eat.better.service.dto.user.UserDTOGet;
import com.eat.better.service.exception.ReadException;
import com.eat.better.service.exception.enums.ReadExceptionMessageEnum;

public class UserServiceTest_FindAll {

	private UserJpaRepository repository;
	private UserService service;

	private String propertyId = "id";
	private String propertyLogin = "login";
	private String propertyName = "name";
	
	private final long id1 = 1;
	private final String login1 = "login_1";
	private final String name1 = "The name of the first person";

	private final long id2 = 2;
	private final String login2 = "login_2";
	private final String name2 = "The name of the second person";

	private final long id3 = 3;
	private final String login3 = "login_3";
	private final String name3 = "The name of the third person";

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Before
	public void setup() {
		repository = mock(UserJpaRepository.class);
		service = new UserServiceImpl(repository);
	}

	@Test
	public void findAll_GivenEntityListShouldReturnDtoList() throws ReadException {

		List<User> userList = getListOfUser();

		when(repository.findAll()).thenReturn(userList);

		List<UserDTOGet> userDtoListExpected = getListOfUserDTO();
		List<UserDTOGet> userDtoListOut = service.findAll();

		assertThat("The list should not be empty", userDtoListOut, not(empty()));
		assertThat("The list should has 3 elements", userDtoListOut, hasSize(3));
		assertThat("The return is wrong", userDtoListOut, is(userDtoListExpected));
		for (UserDTOGet dtoExpected : userDtoListExpected){
			assertThat("The return is wrong", userDtoListOut, hasItem( hasProperty(propertyId, equalTo(dtoExpected.getId()))));
			assertThat("The return is wrong", userDtoListOut, hasItem(hasProperty(propertyLogin, equalTo(dtoExpected.getLogin()))));
			assertThat("The return is wrong", userDtoListOut, hasItem(hasProperty(propertyName, equalTo(dtoExpected.getName()))));
			
		}
	}

	@Test
	public void findAll_NoneDtoShouldReturnEmptyList() throws ReadException {
		when(repository.findAll()).thenReturn(Collections.emptyList());

		List<UserDTOGet> userList = service.findAll();

		assertThat("The list should be empty but not null", userList, both(empty()).and(notNullValue()));
	}

	@Test
	public void findOne_GivenExceptionOnRepositoryShouldThowException() throws ReadException {
		thrown.expect(ReadException.class);
		thrown.expectMessage(ReadExceptionMessageEnum.UNEXPECTED_EXCEPTION.name());

		when(repository.findAll()).thenThrow(new RuntimeException());

		service.findAll();
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

	private List<UserDTOGet> getListOfUserDTO() {
		UserDTOGet userDto1 = new UserDTOGet();
		userDto1.setId(id1);
		userDto1.setLogin(login1);
		userDto1.setName(name1);

		UserDTOGet userDto2 = new UserDTOGet();
		userDto2.setId(id2);
		userDto2.setLogin(login2);
		userDto2.setName(name2);

		UserDTOGet userDto3 = new UserDTOGet();
		userDto3.setId(id3);
		userDto3.setLogin(login3);
		userDto3.setName(name3);

		List<UserDTOGet> userDtoList = new ArrayList<>();
		userDtoList.add(userDto1);
		userDtoList.add(userDto2);
		userDtoList.add(userDto3);

		return userDtoList;
	}
}
