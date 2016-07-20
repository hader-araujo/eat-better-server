package com.eat.better.service.user;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eat.better.entity.User;
import com.eat.better.repository.UserJpaRepository;
import com.eat.better.service.exception.CreateUpdateException;
import com.eat.better.service.exception.DeleteException;
import com.eat.better.service.exception.ReadException;
import com.eat.better.service.exception.enums.DeleteExceptionMessageEnum;
import com.eat.better.service.exception.enums.ReadExceptionMessageEnum;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

	private static final Logger log = LogManager.getLogger(UserServiceImpl.class.getName());

	private UserJpaRepository repository;

	@Autowired
	public UserServiceImpl(UserJpaRepository repository) {
		if (repository == null) {
			throw new RuntimeException("Repository unavailable");
		}
		this.repository = repository;
	}

	@Override
	@Transactional(readOnly = false)
	public void saveAndFlush(UserDTO dto) throws CreateUpdateException {

		User user = new User();
		user.setId(dto.getId());
		user.setLogin(dto.getLogin());
		user.setName(dto.getName());

		user = repository.saveAndFlush(user);
		dto.setId(user.getId());
	}

	@Override
	public UserDTO findOne(Long id) throws ReadException {
		try {
			User entity = repository.findOne(id);

			if (entity == null) {
				throw new ReadException(ReadExceptionMessageEnum.NOT_FOUND);
			}

			log.debug("findOne::Entity finded: " + entity);
			UserDTO dto = new UserDTO(entity);
			return dto;

		} catch (ReadException e) {
			throw e;
		} catch (IllegalArgumentException e) {
			throw new ReadException(ReadExceptionMessageEnum.ID_NULL_EXCEPTION);
		} catch (Exception e) {
			log.error(ReadExceptionMessageEnum.UNEXPECTED_EXCEPTION);
			throw new ReadException(ReadExceptionMessageEnum.UNEXPECTED_EXCEPTION, e);
		}
	}

	@Override
	public List<UserDTO> findAll() throws ReadException {
		try {
			List<User> userList = repository.findAll();
			return userList.stream().map(f -> new UserDTO(f)).collect(Collectors.toList());
		} catch (Exception e) {
			log.error(ReadExceptionMessageEnum.UNEXPECTED_EXCEPTION);
			throw new ReadException(ReadExceptionMessageEnum.UNEXPECTED_EXCEPTION, e);
		}
	}

	@Override
	public void delete(Long id) throws DeleteException {
		try {
			findOne(id);
			repository.delete(id);
		} catch (ReadException e) {
			if (e.getMessage().equals(ReadExceptionMessageEnum.ID_NULL_EXCEPTION.name())) {
				throw new DeleteException(DeleteExceptionMessageEnum.ID_NULL_EXCEPTION);
			} else if (e.getMessage().equals(ReadExceptionMessageEnum.NOT_FOUND.name())) {
				throw new DeleteException(DeleteExceptionMessageEnum.NOT_FOUND);
			} else {
				throw new DeleteException(DeleteExceptionMessageEnum.UNEXPECTED_EXCEPTION);
			}

		} catch (IllegalArgumentException e) {
			throw new DeleteException(DeleteExceptionMessageEnum.ID_NULL_EXCEPTION);
		} catch (Exception e) {
			log.error(DeleteExceptionMessageEnum.UNEXPECTED_EXCEPTION);
			throw new DeleteException(DeleteExceptionMessageEnum.UNEXPECTED_EXCEPTION, e);
		}
	}
}
