package com.eat.better.service.user;

import java.util.List;

import com.eat.better.service.exception.CreateUpdateException;
import com.eat.better.service.exception.DeleteException;
import com.eat.better.service.exception.ReadException;

public interface UserService {

	void saveAndFlush(UserDTO dto) throws CreateUpdateException;

	UserDTO findOne(Long id) throws ReadException;

	List<UserDTO> findAll() throws ReadException;

	void delete(Long id) throws DeleteException;

}