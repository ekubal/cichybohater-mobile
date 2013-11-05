package pl.kodujdlapolski.cichy_bohater;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
public class LocationAlertActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	public void onButtonClick(View view) {
		Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
		startActivity(intent);
	}

	@Override
	public void finish() {
		Intent intent = new Intent();
		setResult(RESULT_OK, intent);

		// TODO Auto-generated method stub
		super.finish();
	}

}
