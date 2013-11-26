package pl.kodujdlapolski.cichy_bohater.gui;

import java.util.ArrayList;
import java.util.List;

import pl.kodujdlapolski.cichy_bohater.Constants;
import pl.kodujdlapolski.cichy_bohater.R;
import pl.kodujdlapolski.cichy_bohater.data.Category;
import pl.kodujdlapolski.cichy_bohater.data.CategoryAdapter;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class MenuActivity extends Activity {
	private ListView categoriesList;
	private Category parentCategory;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
		categoriesList = (ListView) findViewById(R.id.categories_list);

		parentCategory = getCategoryFromIntent();
		if (parentCategory != null) {
			setTitle(parentCategory.getName());
		}

		List<Category> categories = getCategoriesFromIntent();
		if (categories != null) {
			CategoryAdapter categoriesAdapter = new CategoryAdapter(this,
					categories);
			categoriesList.setAdapter(categoriesAdapter);
		}

		categoriesList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Category category = (Category) categoriesList.getAdapter()
						.getItem(position);

				Intent destinationIntent = null;
				if (category.hasSubcategories()) {
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
				startActivity(destinationIntent);
			}
		});
	}

	private List<Category> getCategoriesFromIntent() {
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			return (List<Category>) extras.get(Constants.CATEGORIES_LIST_EXTRA);
		} else {
			return null;
		}
	}

	private Category getCategoryFromIntent() {
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			return (Category) extras.get(Constants.CATEGORY_EXTRA);
		} else {
			return null;
		}
	}
}