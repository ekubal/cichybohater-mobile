package pl.kodujdlapolski.cichy_bohater.data;

import java.io.Serializable;
import java.util.List;

public class Category implements Serializable {
	private static final long serialVersionUID = 7860544344969067115L;

	private Integer id;
	private String name;
	private String permalink;
	private boolean isMainCategory;
	private String image_url;
	private Organization organization;
	private List<CategoryAttribute> category_attributes;
	private List<Category> subcategories;
	private boolean require_location;

	public String getImageUrl() {
		return image_url;
	}

	public List<CategoryAttribute> getCategoryAttributes() {
		return category_attributes;
	}

	public boolean isMainCategory() {
		return isMainCategory;
	}

	public List<Category> getSubcategories() {
		return subcategories;
	}

	public boolean hasSubcategories() {
		return getSubcategories() != null && getSubcategories().size() > 0;
	}

	public Integer getId() {
		return id;
	}

	public Category(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public String getPermalink() {
		return permalink;
	}

	public boolean requireLocation() {
		return false;
		// return require_location;
	}

	public Organization getOrganization() {
		return organization;
	}

	public String getDescription() {
		return name;
	}
}
