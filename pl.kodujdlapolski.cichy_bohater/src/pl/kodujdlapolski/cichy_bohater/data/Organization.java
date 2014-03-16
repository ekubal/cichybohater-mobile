package pl.kodujdlapolski.cichy_bohater.data;

import java.io.Serializable;

public class Organization implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7860544344969067115L;
	private String name;
	private Integer id;
	private String badge_url;

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getBadgeUrl() {
		return badge_url;
	}
}
