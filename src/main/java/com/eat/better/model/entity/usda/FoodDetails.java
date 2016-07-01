package com.eat.better.model.entity.usda;

import java.util.List;

public class FoodDetails {

	private Long id;
	private String name;

	private String carbohydrateFactor;
	private String fatFactor;
	private String proteinFactor;

	private List<Food> foodList;

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

	public List<Food> getFoodList() {
		return foodList;
	}

	public void setFoodList(List<Food> foodList) {
		this.foodList = foodList;
	}

	public String getCarbohydrateFactor() {
		return carbohydrateFactor;
	}

	public void setCarbohydrateFactor(String carbohydrateFactor) {
		this.carbohydrateFactor = carbohydrateFactor;
	}

	public String getFatFactor() {
		return fatFactor;
	}

	public void setFatFactor(String fatFactor) {
		this.fatFactor = fatFactor;
	}

	public String getProteinFactor() {
		return proteinFactor;
	}

	public void setProteinFactor(String proteinFactor) {
		this.proteinFactor = proteinFactor;
	}
}
