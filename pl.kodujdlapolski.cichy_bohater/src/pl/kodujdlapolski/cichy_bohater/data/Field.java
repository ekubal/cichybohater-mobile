package pl.kodujdlapolski.cichy_bohater.data;

import java.io.Serializable;
import java.util.List;

public class Field implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7860544344969067115L;
	private Integer id;
	private String name;
	private String label;
	private String description;
	private Boolean required;
	private String type;
	private List<String> options;
	private Boolean remember;
	private String permalink;

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getLabel() {
		return label;
	}

	public String getDescription() {
		return description;
	}

	public Boolean getRequired() {
		return required;
	}

	public String getType() {
		return type;
	}

	public List<String> getOptions() {
		return options;
	}

	public Boolean getRemember() {
		return remember;
	}

	public String getPermalink() {
		return permalink;
	}

	public List<String> getPossibleValues() {
		return options;
	}

}
