package com.eat.better.configuration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.eat.better.configuration.controller.ControllerConfiguration;
import com.eat.better.configuration.model.JpaConfiguration;

public class ApplicationContext extends AbstractAnnotationConfigDispatcherServletInitializer
		implements WebApplicationInitializer {
	@Override
	protected Class<?>[] getRootConfigClasses() {

		return new Class[] { ControllerConfiguration.class , JpaConfiguration.class};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {

		return null;
	}

	@Override
	protected String[] getServletMappings() {
		System.out.println("\n\n\n\n\n\n now deploying");
		return new String[] { "/" }; // TODO to remove it
	}
}