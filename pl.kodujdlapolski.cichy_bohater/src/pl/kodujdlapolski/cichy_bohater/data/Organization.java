package pl.kodujdlapolski.cichy_bohater.data;

import java.io.Serializable;

public class Organization implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7860544344969067115L;
	private String name;
	private Integer id;
	private String logo_url;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLogoUrl() {
		return logo_url;
	}

	public void setLogoUrl(String logo_url) {
		this.logo_url = logo_url;
	}

}
