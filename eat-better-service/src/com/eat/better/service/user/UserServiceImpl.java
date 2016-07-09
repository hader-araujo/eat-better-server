package com.eat.better.service.user;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eat.better.entity.User;
import com.eat.better.repository.UserRepository;
import com.eat.better.service.exception.DTONullPointerException;
import com.eat.better.service.exception.FieldNullPointerException;
import com.eat.better.service.exception.crudgeneric.CreateGenericException;

@Service
public class UserServiceImpl implements UserService {

	private static final Logger log = LogManager.getLogger(UserServiceImpl.class.getName());
	
	private UserRepository repository;

	@Autowired
	public UserServiceImpl(UserRepository repository) {
		this.repository = repository;
	}
	
	@Override
	public User saveAndFlush(UserDTO dto) throws CreateGenericException {
		try {

			
		if (dto == null) {
			log.error("saveAndFlush::dto null");
			throw new DTONullPointerException(UserDTO.class);
		}
		
		if (dto.getLogin() == null) {
			log.error(String.format("saveAndFlush::%s null", UserDTO.FIELD.LOGIN.name()));
			throw new FieldNullPointerException(UserDTO.class, UserDTO.FIELD.LOGIN.name());
		}

		if (dto.getName() == null) {
			log.error(String.format("saveAndFlush::%s null", UserDTO.FIELD.NAME.name()));
			throw new FieldNullPointerException(UserDTO.class, UserDTO.FIELD.NAME.name());
		}

		User user = new User();
		user.setLogin(dto.getLogin());
		user.setName(dto.getName());
		
		return repository.saveAndFlush(user);

		} catch (Exception e) {
			log.error("saveAndFlush::Generic Error", e);
			throw new CreateGenericException();
		}
	}
}
