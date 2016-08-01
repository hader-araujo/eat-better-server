package com.eat.better.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.eat.better.entity.User;
import com.eat.better.service.dto.user.UserDTOGet;
import com.eat.better.service.dto.user.UserDTOPost;
import com.eat.better.service.exception.CreateUpdateException;
import com.eat.better.service.exception.DeleteException;
import com.eat.better.service.exception.ReadException;

public interface UserService {

	void saveAndFlush(UserDTOPost dto) throws CreateUpdateException;

	UserDTOGet findOne(Long id) throws ReadException;

	List<UserDTOGet> findAll() throws ReadException;

	void delete(Long id) throws DeleteException;

	Page<User> findBy(String searchValue, Pageable pageable) throws ReadException;

}