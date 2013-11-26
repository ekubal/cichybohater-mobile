package pl.kodujdlapolski.cichy_bohater.gui;

import pl.kodujdlapolski.cichy_bohater.Constants;
import pl.kodujdlapolski.cichy_bohater.R;
import pl.kodujdlapolski.cichy_bohater.SummaryActivity;
import pl.kodujdlapolski.cichy_bohater.data.Category;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Toast;

public class IncidentActivity extends FragmentActivity {

	private FormFragment formFragment;
	private Category incidentCategory;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_incident);

		formFragment = (FormFragment) getSupportFragmentManager()
				.findFragmentById(R.id.form_fragment);
		incidentCategory = getCategoryFromIntent();

	}

	public void onSendButtonClick(View view) {
		Toast.makeText(this, "test", Toast.LENGTH_LONG).show();
		Intent intent = new Intent(this,
				incidentCategory.requireLocation() ? GeolocationActivity.class
						: SummaryActivity.class);
		ContentValues values = formFragment.getAllInputs();
		intent.putExtra(Constants.INCIDENT_DATA_EXTRA, values);
		intent.putExtra(Constants.CATEGORY_EXTRA, incidentCategory);
		startActivity(intent);
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
