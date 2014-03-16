package pl.kodujdlapolski.cichy_bohater.rest;

import java.util.List;

import pl.kodujdlapolski.cichy_bohater.data.Category;
import pl.kodujdlapolski.cichy_bohater.data.Schema;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

public interface CichyBohaterRestService {
	@GET("/categories")
	List<Category> getCategories(@Query("lang") String lang);

	@GET("/categories/{id}")
	Category getCategory(@Path("id") Integer id);

	@GET("/mobile")
	List<Schema> getSchemas(@Query("language") String language,
			@Query("location[lat]") double locationLat,
			@Query("location[lgt]") double locationLgt);

}
