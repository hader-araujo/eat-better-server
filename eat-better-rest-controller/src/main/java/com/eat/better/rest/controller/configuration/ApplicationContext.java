package com.eat.better.rest.controller.configuration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.eat.better.repository.configuration.JpaConfiguration;
import com.eat.better.service.configuration.ServiceConfiguration;

public class ApplicationContext extends AbstractAnnotationConfigDispatcherServletInitializer
		implements WebApplicationInitializer {

	private static final Logger log = LogManager.getLogger(ApplicationContext.class.getName());

	@Override
	protected Class<?>[] getRootConfigClasses() {

		return new Class[] { ControllerConfiguration.class, ServiceConfiguration.class, JpaConfiguration.class };
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {

		return null;
	}

	@Override
	protected String[] getServletMappings() {

		log.debug("now deploying");
		return new String[] { "/" };
	}
}