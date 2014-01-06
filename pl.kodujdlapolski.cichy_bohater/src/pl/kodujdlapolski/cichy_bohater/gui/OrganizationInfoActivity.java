package pl.kodujdlapolski.cichy_bohater.gui;

import java.util.ArrayList;

import pl.kodujdlapolski.cichy_bohater.Constants;
import pl.kodujdlapolski.cichy_bohater.R;
import pl.kodujdlapolski.cichy_bohater.data.Category;
import pl.kodujdlapolski.cichy_bohater.data.Organization;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class OrganizationInfoActivity extends BaseAcitivity {

	private Category category;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_organization_info);

		category = getCategoryFromIntent();
		if (category != null && category.getOrganization() != null) {
			Organization organization = category.getOrganization();
			ImageView logoView = (ImageView) findViewById(R.id.organization_logo);

			Picasso.with(this).load(organization.getLogoUrl()).into(logoView);

			if (organization.getName() != null) {
				TextView organizationText = (TextView) findViewById(R.id.organization_text);
				organizationText
						.setText(Html
								.fromHtml("Twoje zgłoszenie zostanie wysłane do następującej organizacji: <b>"
										+ organization.getName() + "</b>"));
			}
			setAppTitle(category.getName());
		} else {
			ArrayList<Category> categories = new ArrayList<Category>(
					getCategoriesFromIntent());

			Intent intent = new Intent(this, MenuActivity.class);
			intent.putExtra(Constants.CATEGORIES_LIST_EXTRA, categories);
			startActivity(intent);
			finish();
		}
	}

	public void onAcceptButtonClick(View view) {
		Intent intent = new Intent(this, IncidentActivity.class);
		intent.putExtra(Constants.CATEGORY_EXTRA, category);
		startActivity(intent);
		finish();
	}
}
