package pl.kodujdlapolski.cichy_bohater;

import java.util.List;

import pl.kodujdlapolski.cichy_bohater.data.Category;
import pl.kodujdlapolski.cichy_bohater.data.CategoryAdapter;
import pl.kodujdlapolski.cichy_bohater.rest.CichyBohaterRestAdapter;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class MenuActivity extends Activity {

	static final String INCIDENT_CATEGORY_ID = "incidentCategoryId";
	private ListView categoriesList;
	private CategoryAdapter categoriesAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
		categoriesList = (ListView) findViewById(R.id.categoriesList);

		(new CategoriesAsyncTask()).execute("pl");

		categoriesList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view,
					int position, long id) {
				Category category = categoriesAdapter.getValues().get(position);
				Intent destinationIntent = null;
				if (category.getSubcategories() != null
						&& category.getSubcategories().size() > 0) {
					destinationIntent = new Intent(MenuActivity.this,
							MenuActivity.class);
				} else {
					destinationIntent = new Intent(MenuActivity.this,
							IncidentActivity.class);
				}

				destinationIntent.putExtra(INCIDENT_CATEGORY_ID,
						category.getId());
				startActivity(destinationIntent);

			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}

	private class CategoriesAsyncTask extends
			AsyncTask<String, Void, List<Category>> {

		@Override
		protected List<Category> doInBackground(String... langs) {
			if (!AppStatus.getInstance().isOnline(MenuActivity.this)) {
				Intent intent = new Intent(MenuActivity.this,
						EmergencyActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				finish();
				return null;
			}
			Bundle intentData = getIntent().getExtras();
			CichyBohaterRestAdapter adapter = new CichyBohaterRestAdapter();

			Category category = null;
			List<Category> list = null;
			if (intentData != null) {
				int categoryId = intentData.getInt(INCIDENT_CATEGORY_ID);
				category = adapter.getCategory(categoryId);
			}
			if (category == null && langs.length > 0) {
				list = adapter.getCategories(langs[0]);
			} else {
				list = category.getSubcategories();
			}
			return list;
		}

		@Override
		protected void onPostExecute(List<Category> result) {
			if (result != null) {
				categoriesAdapter = new CategoryAdapter(MenuActivity.this,
						result);
				categoriesList.setAdapter(categoriesAdapter);
			}
			super.onPostExecute(result);
		}
	}
}
