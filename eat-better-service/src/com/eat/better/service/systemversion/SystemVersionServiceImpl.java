package com.eat.better.service.systemversion;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eat.better.entity.SystemVersion;
import com.eat.better.repository.SystemVersionJpaRepository;
import com.eat.better.service.exception.DTONotFoundException;
import com.eat.better.service.exception.DTONullPointerException;
import com.eat.better.service.exception.FieldNullPointerException;
import com.eat.better.service.exception.crudgeneric.CreateGenericException;
import com.eat.better.service.exception.crudgeneric.ReadGenericException;

@Service
public class SystemVersionServiceImpl implements SystemVersionService {

	private static final Logger log = LogManager.getLogger(SystemVersionServiceImpl.class.getName());

	private SystemVersionJpaRepository repository;

	@Autowired
	public SystemVersionServiceImpl(SystemVersionJpaRepository repository) {
		this.repository = repository;
	}

	@Override
	public void saveAndFlush(SystemVersionDTO dto) throws CreateGenericException {
		try {

			if (dto == null) {
				log.error("saveAndFlush::dto null");
				throw new DTONullPointerException(SystemVersionDTO.class);
			}

			if (dto.getVersion() == null) {
				log.error(String.format("saveAndFlush::%s null", SystemVersionDTO.FIELD.VERSION.name()));
				throw new FieldNullPointerException(SystemVersionDTO.class, SystemVersionDTO.FIELD.VERSION.name());
			}

			SystemVersion entity = new SystemVersion();
			entity.setVersion(dto.getVersion());

			repository.saveAndFlush(entity);

		} catch (Exception e) {
			log.error("saveAndFlush::Generic Error", e);
			throw new CreateGenericException();
		}
	}

	@Override
	public SystemVersionDTO findOne(Long id) throws ReadGenericException {
		try {

			SystemVersion entity = repository.findOne(id);

			if (entity == null) {
				log.error("findOne::entity null");
				throw new DTONotFoundException();
			}

			SystemVersionDTO dto = new SystemVersionDTO(entity.getVersion());

			return dto;

		} catch (Exception e) {
			log.error("saveAndFlush::Generic Error", e);
			throw new ReadGenericException();
		}
	}
}
