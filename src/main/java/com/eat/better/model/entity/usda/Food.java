package com.eat.better.model.entity.usda;

public class Food {

	private Long id;
	private String name;

	private FoodDetails details;
	private FoodGroup group;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public FoodDetails getDetails() {
		return details;
	}

	public void setDetails(FoodDetails details) {
		this.details = details;
	}

	public FoodGroup getGroup() {
		return group;
	}

	public void setGroup(FoodGroup group) {
		this.group = group;
	}
}
