package pl.kodujdlapolski.cichy_bohater.gui;

import java.util.ArrayList;
import java.util.List;

import pl.kodujdlapolski.cichy_bohater.AppStatus;
import pl.kodujdlapolski.cichy_bohater.Constants;
import pl.kodujdlapolski.cichy_bohater.EmergencyActivity;
import pl.kodujdlapolski.cichy_bohater.R;
import pl.kodujdlapolski.cichy_bohater.data.Category;
import pl.kodujdlapolski.cichy_bohater.rest.CichyBohaterRestAdapter;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;

public class StartActivity extends BaseAcitivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);

		// enable localization service
		final LocationManager locManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		String locationProvider = locManager.getBestProvider(new Criteria(),
				false);

		Location currentLocation = locManager
				.getLastKnownLocation(locationProvider);
		if (currentLocation == null) {
			Intent intent = new Intent(StartActivity.this,
					EmergencyActivity.class);
			startActivity(intent);
			finish();
		} else {
			AppStatus.getInstance().setCurrentLocation(currentLocation);
			locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
					10000, 1, AppStatus.getInstance());
			locManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
					10000, 1, AppStatus.getInstance());
			(new LoadingCategoriesAsyncTask(AppStatus.getInstance()
					.getLanguage())).execute();
		}
	}

	// loading categories data from API
	private class LoadingCategoriesAsyncTask extends
			AsyncTask<String, Void, List<Category>> {
		private String language;

		public LoadingCategoriesAsyncTask(String language) {
			this.language = language;
		}

		@Override
		protected List<Category> doInBackground(String... langs) {

			if (AppStatus.getInstance().isOffline(StartActivity.this)) {
				Intent intent = new Intent(StartActivity.this,
						EmergencyActivity.class);
				StartActivity.this.startActivity(intent);
				StartActivity.this.finish();
				return null;
			}
			CichyBohaterRestAdapter adapter = new CichyBohaterRestAdapter();
			return adapter.getCategories(this.language);
		}

		@Override
		protected void onPostExecute(List<Category> result) {
			if (result != null) {
				ArrayList<Category> categoriesList = new ArrayList<Category>(
						result);
				Intent intent = new Intent(StartActivity.this,
						MenuActivity.class);
				intent.putExtra(Constants.CATEGORIES_LIST_EXTRA, categoriesList);
				StartActivity.this.startActivity(intent);
				StartActivity.this.finish();
			}
			super.onPostExecute(result);
		}
	}
}