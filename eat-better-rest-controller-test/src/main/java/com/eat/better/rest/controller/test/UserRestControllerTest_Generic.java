package com.eat.better.rest.controller.test;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.eat.better.rest.controller.UserRestControllerImpl;

public class UserRestControllerTest_Generic {

	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Test
	public void generic_NullServiceShouldThrowException() {
		thrown.expect(RuntimeException.class);
		thrown.expectMessage("Service unavailable");
		
		new UserRestControllerImpl(null);
	}
	
}
