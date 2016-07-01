package com.eat.better.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@Configuration
@EnableJpaRepositories ( basePackages = {"com.eat.better.model.repository"})
@ComponentScan(basePackages = { "com.eat.better.controller.rest" })
public class ControllerConfiguration {

}