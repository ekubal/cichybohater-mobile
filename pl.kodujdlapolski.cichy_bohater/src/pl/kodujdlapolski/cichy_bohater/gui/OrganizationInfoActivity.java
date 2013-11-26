package pl.kodujdlapolski.cichy_bohater.gui;

import pl.kodujdlapolski.cichy_bohater.Constants;
import pl.kodujdlapolski.cichy_bohater.R;
import pl.kodujdlapolski.cichy_bohater.data.Category;
import pl.kodujdlapolski.cichy_bohater.data.Organization;
import pl.kodujdlapolski.cichy_bohater.tasks.DownloadImageTask;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class OrganizationInfoActivity extends Activity {

	private Category selectedCategory;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_organization_info);

		selectedCategory = getCategoryFromIntent();
		if (selectedCategory != null
				&& selectedCategory.getOrganization() != null) {
			Organization organization = selectedCategory.getOrganization();
			ImageView organizationLogo = (ImageView) findViewById(R.id.organization_logo);

			(new DownloadImageTask(organizationLogo, organization.getLogoUrl()))
					.execute();

			if (organization.getName() != null) {
				TextView organizationText = (TextView) findViewById(R.id.organization_text);
				organizationText
						.setText(Html
								.fromHtml("Twoje zgłoszenie zostanie wysłane do następującej organizacji: <b>"
										+ organization.getName() + "</b>"));
			}
		}
		setTitle(selectedCategory.getName());
	}

	public void onAcceptButtonClick(View view) {
		Intent intent = new Intent(this, IncidentActivity.class);
		intent.putExtra(Constants.CATEGORY_EXTRA, selectedCategory);
		startActivity(intent);
		finish();
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
