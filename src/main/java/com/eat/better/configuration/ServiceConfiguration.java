package com.eat.better.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = { "com.eat.better.service" })
public class ServiceConfiguration {

}