package com.eat.better.service.user;

import java.util.List;

import com.eat.better.service.exception.crudgeneric.CreateGenericException;
import com.eat.better.service.exception.crudgeneric.ReadGenericException;

public interface UserService {

	UserDTO saveAndFlush(UserDTO dto) throws CreateGenericException;

	UserDTO findOne(Long id) throws ReadGenericException;

	List<UserDTO> findAll() throws ReadGenericException;

}