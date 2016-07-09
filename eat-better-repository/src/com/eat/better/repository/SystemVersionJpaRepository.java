package com.eat.better.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eat.better.entity.SystemVersion;

@Repository
public interface SystemVersionJpaRepository  extends JpaRepository<SystemVersion, Long>{

}