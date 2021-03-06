package com.eat.better.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eat.better.entity.User;

@Repository
public interface UserJpaRepository extends JpaRepository<User, Long> {

	User findByIdAndLogin(Long id, String login);
	Page<User> findByLoginIgnoreCaseContainsOrNameIgnoreCaseContains(String login,  String name, Pageable pageable);
}
