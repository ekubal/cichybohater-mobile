package pl.kodujdlapolski.cichy_bohater.gui;

import pl.kodujdlapolski.cichy_bohater.Constants;
import pl.kodujdlapolski.cichy_bohater.R;
import pl.kodujdlapolski.cichy_bohater.data.Organization;
import pl.kodujdlapolski.cichy_bohater.data.Schema;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class OrganizationInfoActivity extends BaseAcitivity {

	private Schema category;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_organization_info);

		category = getCategoryFromIntent();
		if (category != null && category.getOrganization() != null) {
			Organization organization = category.getOrganization();
			ImageView logoView = (ImageView) findViewById(R.id.organization_logo);
			if (organization.getBadgeUrl() != null) {
				Picasso.with(this).load(organization.getBadgeUrl())
						.into(logoView);
			}
			TextView organizationText = (TextView) findViewById(R.id.organization_text);
			organizationText.setText(R.string.organization_info_header);
			setAppTitle(category.getLabel());
		}
	}

	public void onAcceptButtonClick(View view) {
		Intent intent = new Intent(this, IncidentActivity.class);
		intent.putExtra(Constants.SCHEMA_EXTRA, category);
		startActivity(intent);
		finish();
	}
}
