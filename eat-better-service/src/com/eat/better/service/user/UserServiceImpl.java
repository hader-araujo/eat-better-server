package com.eat.better.service.user;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
import com.eat.better.service.exception.FieldReadOnlyException;
import com.eat.better.service.exception.crudgeneric.CreateGenericException;
import com.eat.better.service.exception.crudgeneric.DeleteGenericException;
import com.eat.better.service.exception.crudgeneric.ReadGenericException;
import com.eat.better.service.exception.crudgeneric.UpdateGenericException;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

	private static final Logger log = LogManager.getLogger(UserServiceImpl.class.getName());

	private UserJpaRepository repository;

	@Autowired
	public UserServiceImpl(UserJpaRepository repository) {
		this.repository = repository;
	}

	@Override
	@Transactional(readOnly = false)
	public void saveAndFlush(UserDTO dto) throws CreateGenericException, UpdateGenericException, FieldNullPointerException {
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

			if (dto.getId() != null) {
				if (repository.findByIdAndLogin(dto.getId(), dto.getLogin()) == null) {
					throw new FieldReadOnlyException(UserDTO.class, UserDTO.FIELD.LOGIN.name());
				}
			}

			User user = new User();
			user.setId(dto.getId());
			user.setLogin(dto.getLogin());
			user.setName(dto.getName());

			user = repository.saveAndFlush(user);
			dto.setId(user.getId());
		} catch (CreateGenericException | UpdateGenericException | FieldNullPointerException e) {
			throw e;
		} catch (Exception e) {
			log.error("saveAndFlush::Generic Error", e);
			if (dto.getId() != null) {
				throw new UpdateGenericException();
			} else {
				throw new CreateGenericException();
			}
		}
	}

	@Override
	public UserDTO findOne(Long id) throws ReadGenericException, FieldNullPointerException {
		try {

			if (id == null) {
				throw new FieldNullPointerException(UserDTO.class, UserDTO.FIELD.ID.name());
			}
			
			User entity = repository.findOne(id);

			if (entity == null) {
				log.error("findOne::entity null");
				throw new DTONotFoundException();
			}
			log.debug("findOne::Entity finded: " + entity);

			UserDTO dto = new UserDTO(entity);

			return dto;

		} catch (FieldNullPointerException | DTONotFoundException e) {
			throw e;
		} catch (Exception e) {

			log.error("saveAndFlush::Generic Error", e);
			throw new ReadGenericException();
		}
	}

	@Override
	public List<UserDTO> findAll() throws ReadGenericException {
		try {

			List<User> userList = repository.findAll();

			if (userList == null) {
				return Collections.emptyList();
			}

			return userList.stream().map(f -> new UserDTO(f)).collect(Collectors.toList());

		} catch (Exception e) {
			throw new ReadGenericException();
		}
	}

	@Override
	public void delete(Long id) throws DeleteGenericException, DTONotFoundException, FieldNullPointerException {

		try {

			if (id == null) {
				throw new FieldNullPointerException(UserDTO.class, UserDTO.FIELD.ID.name());
			}

			User dto = repository.findOne(id);
			if (dto == null) {
				throw new DTONotFoundException();
			}
			repository.delete(id);
			
		}catch(FieldNullPointerException | DTONotFoundException e){
			throw e;
		} catch (Exception e) {
			throw new DeleteGenericException();
		}

	}
}
