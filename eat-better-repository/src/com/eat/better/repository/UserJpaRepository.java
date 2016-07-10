package com.eat.better.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eat.better.entity.User;

@Repository
public interface UserJpaRepository extends JpaRepository<User, Long> {

}
