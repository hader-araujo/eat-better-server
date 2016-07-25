package com.eat.better.service.test.user;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.eat.better.entity.User;
import com.eat.better.repository.UserJpaRepository;
import com.eat.better.service.UserService;
import com.eat.better.service.UserServiceImpl;
import com.eat.better.service.exception.ReadException;
import com.eat.better.service.exception.enums.ReadExceptionMessageEnum;

public class UserServiceTest_FindBy {

	private UserJpaRepository repository;
	private UserService service;
	private Pageable pageable;
	
	private final String login = "";
	private final String name = "";
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Before
	public void setup(){
		pageable = mock(Pageable.class);
		repository = mock(UserJpaRepository.class);
		service = new UserServiceImpl(repository);
	}
	
	@Test
	public void findBy_GivenSearchShouldReturnIt() throws ReadException{

		@SuppressWarnings("unchecked")
		Page<User> page = mock(Page.class);
		
		when(repository.findByLoginIgnoreCaseStartingWithAndNameIgnoreCaseStartingWith(login,name, pageable)).
		thenReturn(page);
		
		Page<User> dtoPage = service.findBy(login, name, pageable);
		
		assertThat("DTO should not be null", dtoPage, notNullValue());
		//TODO more asserts
		
		
		
	}
	
	@Test
	public void findBy_GivenExceptionOnRepositoryShouldThrownException() throws ReadException{
		
		thrown.expect(ReadException.class);
		thrown.expectMessage(ReadExceptionMessageEnum.UNEXPECTED_EXCEPTION.name());
		
		when(repository.findByLoginIgnoreCaseStartingWithAndNameIgnoreCaseStartingWith(any(String.class),any(String.class), any(Pageable.class))).
				thenThrow(new RuntimeException());
		
		service.findBy(login, name, pageable);
		
	}
	
}
