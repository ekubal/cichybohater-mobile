package pl.kodujdlapolski.cichy_bohater.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CategoryAttribute implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7360336144949479174L;
	private String name;
	private String permalink;
	private String attribute_type;
	private Category category;
	private List<AttributeValue> attribute_values;

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

	public List<String> getPossibleValues() {
		List<String> values = new ArrayList<String>(attribute_values.size());
		for (AttributeValue val : attribute_values) {
			values.add(val.toString());
		}
		return values;
	}

	public void setPossibleValues(List<AttributeValue> possible_values) {
		this.attribute_values = possible_values;
	}
}
