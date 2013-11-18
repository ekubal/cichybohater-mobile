package pl.kodujdlapolski.cichy_bohater.gui;

import java.util.ArrayList;
import java.util.List;

import pl.kodujdlapolski.cichy_bohater.Constants;
import pl.kodujdlapolski.cichy_bohater.R;
import pl.kodujdlapolski.cichy_bohater.data.Category;
import pl.kodujdlapolski.cichy_bohater.data.CategoryAdapter;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class MenuActivity extends Activity {

	private ListView categoriesList;
	private AlertDialog loadingDialog;
	private Category parentCategory;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
		createLoadingDialog();

		categoriesList = (ListView) findViewById(R.id.categoriesList);
		categoriesList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				createLoadingDialog();
				Category category = (Category) categoriesList.getAdapter()
						.getItem(position);

				Intent destinationIntent = null;
				if (category.getSubcategories() != null
						&& category.getSubcategories().size() > 0) {
					// load list of subcategories
					destinationIntent = new Intent(MenuActivity.this,
							MenuActivity.class);
					destinationIntent
							.putExtra(
									Constants.CATEGORIES_LIST_EXTRA,
									new ArrayList<Category>(category
											.getSubcategories()));
				} else {
					// organization splash screen or incident activity
					destinationIntent = new Intent(MenuActivity.this, category
							.getOrganization() == null ? IncidentActivity.class
							: OrganizationInfoActivity.class);
				}
				destinationIntent.putExtra(Constants.CATEGORY_EXTRA, category);
				destinationIntent.putExtra(Constants.CATEGORY_ID_EXTRA,
						category.getId());
				loadingDialog.dismiss();
				startActivity(destinationIntent);
			}
		});

		Bundle inputExtras = getIntent().getExtras();
		if (inputExtras != null) {
			parentCategory = (Category) inputExtras
					.get(Constants.CATEGORY_EXTRA);
			List<Category> categories = (List<Category>) inputExtras
					.get(Constants.CATEGORIES_LIST_EXTRA);
			if (parentCategory != null) {
				setTitle(parentCategory.getName());
			}
			if (categories != null) {
				CategoryAdapter categoriesAdapter = new CategoryAdapter(this,
						categories);
				categoriesList.setAdapter(categoriesAdapter);
				if (loadingDialog != null) {
					loadingDialog.dismiss();
					loadingDialog = null;
				}
				return;
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}

	// // loading categories data from API
	// private class UpdateCategoriesListAsyncTask extends
	// AsyncTask<String, Void, List<Category>> {
	// private String language = null;
	// private String appTitle;
	//
	// public UpdateCategoriesListAsyncTask(String language) {
	// super();
	// this.language = language;
	// }
	//
	// @Override
	// protected List<Category> doInBackground(String... langs) {
	// if (!AppStatus.getInstance().isOnline(MenuActivity.this)) {
	// Intent intent = new Intent(MenuActivity.this,
	// EmergencyActivity.class);
	// intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	// startActivity(intent);
	// finish();
	// return null;
	// }
	// CichyBohaterRestAdapter adapter = new CichyBohaterRestAdapter();
	//
	// Bundle inputExtras = getIntent().getExtras();
	//
	// List<Category> results = null;
	//
	// if (inputExtras != null) {
	// Category parentCat = (Category) inputExtras
	// .get(Constants.CATEGORY_EXTRA);
	// if (parentCat != null) {
	// appTitle = parentCat.getName();
	// } else {
	// appTitle = getString(R.string.app_name);
	// }
	//
	// results = (List<Category>) inputExtras
	// .get(Constants.CATEGORIES_LIST_EXTRA);
	// if (results != null) {
	// return results;
	// }
	// }
	// return adapter.getCategories(this.language);
	// }
	//
	// @Override
	// protected void onPreExecute() {
	// createLoadingDialog();
	// super.onPreExecute();
	// }
	//
	// @Override
	// protected void onPostExecute(List<Category> result) {
	// if (appTitle != null) {
	// setTitle(appTitle);
	// }
	// if (result != null) {
	// CategoryAdapter categoriesAdapter = new CategoryAdapter(
	// MenuActivity.this, result);
	// categoriesList.setAdapter(categoriesAdapter);
	// }
	// if (loadingDialog != null) {
	// loadingDialog.dismiss();
	// loadingDialog = null;
	// }
	// super.onPostExecute(result);
	// }
	// }

	private void createLoadingDialog() {
		if (loadingDialog != null && loadingDialog.isShowing()) {
			loadingDialog.dismiss();
		}
		loadingDialog = LoadingDialog.createLoadingDialog(this);
		loadingDialog.show();
	}

}
