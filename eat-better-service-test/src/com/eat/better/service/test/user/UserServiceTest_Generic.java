package com.eat.better.service.test.user;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.eat.better.service.user.UserServiceImpl;

public class UserServiceTest_Generic {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void generic_NullRepositotyShouldThrowException() {
		thrown.expect(RuntimeException.class);
		thrown.expectMessage("Repository unavailable");

		new UserServiceImpl(null);
	}
}
