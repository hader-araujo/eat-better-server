package com.eat.better.service.systemversion;

import com.eat.better.service.GenenricDTO;

public class SystemVersionDTO implements GenenricDTO {

	private static final long serialVersionUID = -4851300417075649571L;

	private String version;

	public static enum FIELD {
		VERSION
	};

	public SystemVersionDTO(String version) {
		this.version = version;

	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@Override
	public String toString() {
		return "SystemVersionDTO [version=" + version + "]";
	}
	
	
}
