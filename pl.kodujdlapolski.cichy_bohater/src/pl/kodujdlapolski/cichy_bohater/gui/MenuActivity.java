package pl.kodujdlapolski.cichy_bohater.gui;

import java.util.List;

import pl.kodujdlapolski.cichy_bohater.AppStatus;
import pl.kodujdlapolski.cichy_bohater.Constants;
import pl.kodujdlapolski.cichy_bohater.R;
import pl.kodujdlapolski.cichy_bohater.R.id;
import pl.kodujdlapolski.cichy_bohater.R.layout;
import pl.kodujdlapolski.cichy_bohater.R.menu;
import pl.kodujdlapolski.cichy_bohater.data.Category;
import pl.kodujdlapolski.cichy_bohater.data.CategoryAdapter;
import pl.kodujdlapolski.cichy_bohater.rest.CichyBohaterRestAdapter;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class MenuActivity extends Activity {

	private ListView categoriesList;
	private AlertDialog loadingDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
		createLoadingDialog();

		categoriesList = (ListView) findViewById(R.id.categoriesList);
		categoriesList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view,
					int position, long id) {
				// Category category =
				// categoriesAdapter.getValues().get(position);
				Category category = (Category) categoriesList.getAdapter()
						.getItem(position);

				Intent destinationIntent = null;
				if (category.getSubcategories() != null
						&& category.getSubcategories().size() > 0) {
					// load list of subcategories
					destinationIntent = new Intent(MenuActivity.this,
							MenuActivity.class);
				} else {
					// load incident activity
					destinationIntent = new Intent(MenuActivity.this,
							IncidentActivity.class);
				}

				destinationIntent.putExtra(Constants.INCIDENT_CATEGORY_ID,
						category.getId());
				startActivity(destinationIntent);
			}
		});

		(new CategoriesAsyncTask()).execute("pl");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}

	// loading categories data from API
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
			Bundle inputExtras = getIntent().getExtras();
			CichyBohaterRestAdapter adapter = new CichyBohaterRestAdapter();

			Category category = null;
			List<Category> results = null;
			if (inputExtras != null) {
				int categoryId = inputExtras
						.getInt(Constants.INCIDENT_CATEGORY_ID);
				category = adapter.getCategory(categoryId);
			}
			String lang = langs.length > 0 ? langs[0] : Constants.DEFAULT_LANG;
			if (category == null) {
				return adapter.getCategories(lang);
			} else {
				return category.getSubcategories();
			}
		}

		@Override
		protected void onPostExecute(List<Category> result) {
			if (result != null) {
				CategoryAdapter categoriesAdapter = new CategoryAdapter(
						MenuActivity.this, result);
				categoriesList.setAdapter(categoriesAdapter);
			}
			loadingDialog.dismiss();
			super.onPostExecute(result);
		}
	}

	private void createLoadingDialog() {
		loadingDialog = LoadingDialog.createLoadingDialog(this);
		loadingDialog.show();
	}
}
