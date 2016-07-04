package com.eat.better.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eat.better.model.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

}
