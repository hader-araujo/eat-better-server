package com.eat.better.service.user;

import java.util.List;

import com.eat.better.service.exception.DTONotFoundException;
import com.eat.better.service.exception.FieldNullPointerException;
import com.eat.better.service.exception.crudgeneric.CreateGenericException;
import com.eat.better.service.exception.crudgeneric.DeleteGenericException;
import com.eat.better.service.exception.crudgeneric.ReadGenericException;
import com.eat.better.service.exception.crudgeneric.UpdateGenericException;

public interface UserService {

	void saveAndFlush(UserDTO dto) throws CreateGenericException, UpdateGenericException, FieldNullPointerException;

	UserDTO findOne(Long id) throws ReadGenericException, FieldNullPointerException;

	List<UserDTO> findAll() throws ReadGenericException;
	
	void delete(Long id) throws DeleteGenericException, DTONotFoundException, FieldNullPointerException;

}