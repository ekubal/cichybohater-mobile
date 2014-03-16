package pl.kodujdlapolski.cichy_bohater.gui;

import java.util.ArrayList;
import java.util.List;

import pl.kodujdlapolski.cichy_bohater.Constants;
import pl.kodujdlapolski.cichy_bohater.R;
import pl.kodujdlapolski.cichy_bohater.data.Schema;
import pl.kodujdlapolski.cichy_bohater.data.SchemaAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class MenuActivity extends BaseAcitivity {
	private ListView schemasList;
	private Schema parentCategory;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);

		schemasList = (ListView) findViewById(R.id.categories_list);

		parentCategory = getCategoryFromIntent();
		if (parentCategory != null) {
			setAppTitle(parentCategory.getLabel());
		}

		List<Schema> schemas = getCategoriesFromIntent();
		if (schemas != null) {
			SchemaAdapter schemasAdapter = new SchemaAdapter(this, schemas);
			schemasList.setAdapter(schemasAdapter);
		}

		schemasList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Schema schema = (Schema) schemasList.getAdapter().getItem(
						position);

				Intent destinationIntent = null;
				if (schema.hasChildren()) {
					// load list of subcategories
					destinationIntent = new Intent(MenuActivity.this,
							MenuActivity.class);
					destinationIntent.putExtra(Constants.SCHEMA_LIST_EXTRA,
							new ArrayList<Schema>(schema.getChildren()));
				} else {

					// organization splash screen or incident activity
					Class<? extends BaseAcitivity> nextActivity = null;
					if (schema.getOrganization() != null
							&& schema.getOrganization().getBadgeUrl() != null) {
						nextActivity = OrganizationInfoActivity.class;
					} else {
						nextActivity = IncidentActivity.class;
					}
					destinationIntent = new Intent(MenuActivity.this,
							nextActivity);
				}
				destinationIntent.putExtra(Constants.SCHEMA_EXTRA, schema);
				startActivity(destinationIntent);
			}
		});
	}
}