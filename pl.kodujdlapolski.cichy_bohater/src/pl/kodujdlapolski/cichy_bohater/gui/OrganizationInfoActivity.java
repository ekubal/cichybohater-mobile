package pl.kodujdlapolski.cichy_bohater.gui;

import pl.kodujdlapolski.cichy_bohater.Constants;
import pl.kodujdlapolski.cichy_bohater.R;
import pl.kodujdlapolski.cichy_bohater.data.Category;
import pl.kodujdlapolski.cichy_bohater.data.Organization;
import pl.kodujdlapolski.cichy_bohater.tasks.DownloadImageTask;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class OrganizationInfoActivity extends Activity {

	private Category category;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_organization_info);

		category = (Category) getIntent().getExtras().get(
				Constants.CATEGORY_EXTRA);
		if (category != null && category.getOrganization() != null) {
			Organization organization = category.getOrganization();
			ImageView image = (ImageView) findViewById(R.id.organization_logo);
			(new DownloadImageTask(image, organization.getLogoUrl())).execute();
			TextView organizationText = (TextView) findViewById(R.id.organization_text);
			organizationText.setText(organization.getName());
		}
		setTitle(category.getName());
	}

	public void onAcceptButtonClick(View view) {
		Intent intent = new Intent(this, IncidentActivity.class);
		intent.putExtra(Constants.CATEGORY_EXTRA, category);
		startActivity(intent);
		this.finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.organization_info, menu);
		return true;
	}

}
