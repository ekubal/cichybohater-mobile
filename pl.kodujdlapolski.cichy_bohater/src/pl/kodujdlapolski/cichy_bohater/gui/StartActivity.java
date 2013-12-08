package pl.kodujdlapolski.cichy_bohater.gui;

import java.util.ArrayList;
import java.util.List;

import pl.kodujdlapolski.cichy_bohater.AppStatus;
import pl.kodujdlapolski.cichy_bohater.Constants;
import pl.kodujdlapolski.cichy_bohater.EmergencyActivity;
import pl.kodujdlapolski.cichy_bohater.R;
import pl.kodujdlapolski.cichy_bohater.data.Category;
import pl.kodujdlapolski.cichy_bohater.rest.CichyBohaterRestAdapter;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

public class StartActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);

		(new LoadingCategoriesAsyncTask(Constants.defaultLanguage)).execute();
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