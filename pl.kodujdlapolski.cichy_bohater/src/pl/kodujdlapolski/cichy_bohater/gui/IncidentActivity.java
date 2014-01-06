package pl.kodujdlapolski.cichy_bohater.gui;

import pl.kodujdlapolski.cichy_bohater.Constants;
import pl.kodujdlapolski.cichy_bohater.R;
import pl.kodujdlapolski.cichy_bohater.data.Category;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class IncidentActivity extends BaseAcitivity {

	private FormFragment formFragment;
	private Category incidentCategory;
	private static ContentValues formData;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_incident);

		formFragment = (FormFragment) getSupportFragmentManager()
				.findFragmentById(R.id.form_fragment);
		incidentCategory = getCategoryFromIntent();

	}

	public void onSendButtonClick(View view) {
		Intent intent = new Intent(this,
				incidentCategory.requireLocation() ? GeolocationActivity.class
						: SummaryActivity.class);
		formData = formFragment.getAllInputs();
		intent.putExtra(Constants.CATEGORY_EXTRA, incidentCategory);
		startActivity(intent);
	}

	public static ContentValues getFormData() {
		return formData;
	}
}
