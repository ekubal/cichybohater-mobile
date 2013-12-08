package pl.kodujdlapolski.cichy_bohater.rest;

import java.util.List;

import pl.kodujdlapolski.cichy_bohater.data.Category;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

public interface CichyBohaterRestService {
	@GET("/categories")
	List<Category> getCategories(@Query("lang") String lang);

	@GET("/categories/{id}")
	Category getCategory(@Path("id") Integer id);

}
