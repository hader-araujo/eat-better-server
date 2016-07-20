package com.eat.better.service.test.user;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.eat.better.service.user.UserService;
import com.eat.better.service.user.UserServiceImpl;

public class UserServiceTest_Generic {

	UserService service;

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Before
	public void setup() {
	}

	
	@Test
	public void generic_NullRepositotyShouldThrowException()   {
		thrown.expect(RuntimeException.class);
		thrown.expectMessage("Repository unavailable");

		service = new UserServiceImpl(null);
	}
}
