package com.eat.better.controller.configuration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class ApplicationContext extends AbstractAnnotationConfigDispatcherServletInitializer
		implements WebApplicationInitializer {
	@Override
	protected Class<?>[] getRootConfigClasses() {

		return new Class[] { AppConfig.class };
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