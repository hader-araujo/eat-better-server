package com.eat.better.model.repository;

import org.springframework.stereotype.Repository;

import com.eat.better.model.entity.SystemVersionEntity;

@Repository
public class SystemVersionRepository {
	
	private static SystemVersionEntity entity;
	
	static{
		entity = new SystemVersionEntity();
		entity.setId(1L);
		entity.setVersion("0.1");
	}
	public SystemVersionEntity getSystemVersion(){
		return entity;
	}

}
