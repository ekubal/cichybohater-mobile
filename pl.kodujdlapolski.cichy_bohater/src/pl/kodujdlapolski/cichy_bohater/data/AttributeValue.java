package pl.kodujdlapolski.cichy_bohater.data;

import java.io.Serializable;

public class AttributeValue implements Serializable {
	private static final long serialVersionUID = 7860544344969067115L;

	private Integer id;
	private String name;

	public Integer getId() {
		return id;
	}

	public AttributeValue(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return name;
	}
}
