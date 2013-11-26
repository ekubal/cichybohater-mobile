package pl.kodujdlapolski.cichy_bohater.rest;

import java.util.List;

import pl.kodujdlapolski.cichy_bohater.Constants;
import pl.kodujdlapolski.cichy_bohater.data.Category;
import retrofit.RestAdapter;

public class CichyBohaterRestAdapter {
	private RestAdapter adapter;
	private CichyBohaterRestService service;

	public CichyBohaterRestAdapter() {
		adapter = new RestAdapter.Builder().setServer(Constants.api_server)
				.build();
		service = adapter.create(CichyBohaterRestService.class);
	}

	public List<Category> getCategories(String lang) {
		return service.getCategories(lang);
	}

	public Category getCategory(int id) {
		return service.getCategory(id);
	}
}
