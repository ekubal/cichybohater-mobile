package pl.kodujdlapolski.cichy_bohater.data;

import java.io.Serializable;
import java.util.List;

public class Schema implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7860544344969067115L;
	private Integer id;
	private String label;
	private String description;
	private List<Schema> children;
	private List<Field> fields;
	private Organization organization;

	public Integer getId() {
		return id;
	}

	public String getLabel() {
		return label;
	}

	public String getDescription() {
		return description;
	}

	public List<Schema> getChildren() {
		return children;
	}

	public List<Field> getFields() {
		return fields;
	}

	public boolean hasChildren() {
		return getChildren().size() > 0;
	}

	public Organization getOrganization() {
		return organization;
	}
}
