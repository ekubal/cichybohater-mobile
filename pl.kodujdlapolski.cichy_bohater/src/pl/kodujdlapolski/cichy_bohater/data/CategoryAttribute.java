package pl.kodujdlapolski.cichy_bohater.data;

import java.io.Serializable;

public class CategoryAttribute implements Serializable {
	String name;
	String attribute_type;

	public String getTitle() {
		return name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAttribute_type() {
		return attribute_type;
	}

	public void setAttribute_type(String attribute_type) {
		this.attribute_type = attribute_type;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	Category category;

}
