package com.eat.better.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eat.better.model.entity.SystemVersionEntity;

@Repository
public interface SystemVersionJpaRespository  extends JpaRepository<SystemVersionEntity, Long>{

}