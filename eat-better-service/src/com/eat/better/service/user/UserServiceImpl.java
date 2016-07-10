package com.eat.better.service.user;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eat.better.entity.User;
import com.eat.better.repository.UserJpaRepository;
import com.eat.better.service.exception.DTONotFoundException;
import com.eat.better.service.exception.DTONullPointerException;
import com.eat.better.service.exception.FieldNullPointerException;
import com.eat.better.service.exception.crudgeneric.CreateGenericException;
import com.eat.better.service.exception.crudgeneric.ReadGenericException;

@Service
@Transactional (readOnly = true)
public class UserServiceImpl implements UserService {

	private static final Logger log = LogManager.getLogger(UserServiceImpl.class.getName());

	private UserJpaRepository repository;

	@Autowired
	public UserServiceImpl(UserJpaRepository repository) {
		this.repository = repository;
	}

	@Override
	@Transactional (readOnly = false)
	public UserDTO saveAndFlush(UserDTO dto) throws CreateGenericException {
		try {
			// TODO save with id (update)

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

			repository.saveAndFlush(user);

			return new UserDTO(user);

		} catch (CreateGenericException e) {
			throw e;
		} catch (Exception e) {
			log.error("saveAndFlush::Generic Error", e);
			throw new CreateGenericException();
		}
	}

	@Override
	public UserDTO findOne(Long id) throws ReadGenericException {
		try {

			User entity = repository.findOne(id);

			if (entity == null) {
				log.error("findOne::entity null");
				throw new DTONotFoundException();
			}
			log.debug("findOne::Entity finded: " + entity);

			UserDTO dto = new UserDTO(entity);

			return dto;

		} catch (ReadGenericException e) {
			throw e;
		} catch (Exception e) {

			log.error("saveAndFlush::Generic Error", e);
			throw new ReadGenericException();
		}
	}
}
