package pl.kodujdlapolski.cichy_bohater.data;

import java.io.Serializable;
import java.util.List;

public class Category implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7860544344969067115L;
	private String name;
	private Integer id;
	private boolean isMainCategory;
	private String image_url;
	private Organization organization;
	private List<CategoryAttribute> category_attributes;

	public String getImageUrl() {
		return image_url;
	}

	public void setImageUrl(String image_url) {
		this.image_url = image_url;
	}

	public List<CategoryAttribute> getCategoryAttributes() {
		return category_attributes;
	}

	public void setCategoryAttributes(List<CategoryAttribute> categoryAttributes) {
		this.category_attributes = categoryAttributes;
	}

	private List<Category> subcategories;
	private boolean require_location;

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

	public boolean requireLocation() {
		return require_location;
	}

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}
}
