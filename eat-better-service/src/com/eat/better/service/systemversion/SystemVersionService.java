package com.eat.better.service.systemversion;

import com.eat.better.service.exception.crudgeneric.CreateGenericException;
import com.eat.better.service.exception.crudgeneric.ReadGenericException;

public interface SystemVersionService {

	void saveAndFlush(SystemVersionDTO dto) throws CreateGenericException;

	SystemVersionDTO findOne(Long id) throws ReadGenericException;

}