package pl.kodujdlapolski.cichy_bohater.gui;

import java.util.ArrayList;
import java.util.List;

import pl.kodujdlapolski.cichy_bohater.AppStatus;
import pl.kodujdlapolski.cichy_bohater.Constants;
import pl.kodujdlapolski.cichy_bohater.R;
import pl.kodujdlapolski.cichy_bohater.data.Schema;
import pl.kodujdlapolski.cichy_bohater.gps.LocationHelper;
import pl.kodujdlapolski.cichy_bohater.rest.CichyBohaterRestAdapter;
import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;

public class StartActivity extends BaseAcitivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);

		// enable localization service
		Location currentLocation = LocationHelper.getLastKnownLocation(this);
		if (currentLocation == null) {
			// TODO GPS disabled, fix me!
		} else {
			if (AppStatus.getCurrentLocation() == null) {
				AppStatus.getInstance().setCurrentLocation(currentLocation);
			}
			AppStatus.getInstance().enableLocationUpdates(this);

			currentLocation = AppStatus.getCurrentLocation();

			(new LoadingCategoriesAsyncTask(AppStatus.getInstance()
					.getLanguage(), currentLocation)).execute();
		}
	}

	// loading categories data from API
	private class LoadingCategoriesAsyncTask extends
			AsyncTask<String, Void, List<Schema>> {
		private String language;
		private Location location;

		public LoadingCategoriesAsyncTask(String language, Location location) {
			this.language = language;
			this.location = location;
		}

		@Override
		protected List<Schema> doInBackground(String... langs) {

			if (AppStatus.getInstance().isOffline(StartActivity.this)) {
				// TODO device is offline
				return null;
			}
			CichyBohaterRestAdapter adapter = new CichyBohaterRestAdapter();
			return adapter.getSchemas(language, location.getLatitude(),
					location.getLongitude());
		}

		@Override
		protected void onPostExecute(List<Schema> result) {
			if (result != null) {
				ArrayList<Schema> schemasList = new ArrayList<Schema>(result);
				Intent intent = new Intent(StartActivity.this,
						MenuActivity.class);
				intent.putExtra(Constants.SCHEMA_LIST_EXTRA, schemasList);
				StartActivity.this.startActivity(intent);
				StartActivity.this.finish();
			}
			super.onPostExecute(result);
		}
	}
}