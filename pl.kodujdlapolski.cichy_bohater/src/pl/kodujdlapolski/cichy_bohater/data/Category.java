package pl.kodujdlapolski.cichy_bohater.data;

import java.util.List;

public class Category {
	private String name;
	private Integer id;
	private boolean isMainCategory;

	public boolean isMainCategory() {
		return isMainCategory;
	}

	public void setMainCategory(boolean isMainCategory) {
		this.isMainCategory = isMainCategory;
	}

	public List<Category> getSubcategories() {
		return subcategories;
	}

	public void setSubcategories(List<Category> subcategories) {
		this.subcategories = subcategories;
	}

	private List<Category> subcategories;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Category(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
