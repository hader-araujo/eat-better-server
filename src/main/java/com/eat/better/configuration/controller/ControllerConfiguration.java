package com.eat.better.configuration.controller;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@Configuration
@ComponentScan(basePackages = { "com.eat.better.model.repository", "com.eat.better.controller.rest" })
public class ControllerConfiguration {

}