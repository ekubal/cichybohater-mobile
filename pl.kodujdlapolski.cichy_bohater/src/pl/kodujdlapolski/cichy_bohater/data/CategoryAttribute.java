package pl.kodujdlapolski.cichy_bohater.data;

import java.io.Serializable;

public class CategoryAttribute implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7360336144949479174L;
	private String name;
	private String permalink;
	private String attribute_type;
	private Category category;

	public String getName() {
		return name;
	}

	public String getPermalink() {
		return permalink;
	}

	public String getAttributeType() {
		return attribute_type;
	}

	public Category getCategory() {
		return category;
	}
}
