package com.eat.better.service.user;

import com.eat.better.entity.User;
import com.eat.better.service.exception.crudgeneric.CreateGenericException;

public interface UserService {

	User saveAndFlush(UserDTO dto) throws CreateGenericException;

}