package pl.kodujdlapolski.cichy_bohater.gui;

import java.util.ArrayList;
import java.util.List;

import pl.kodujdlapolski.cichy_bohater.AppStatus;
import pl.kodujdlapolski.cichy_bohater.Constants;
import pl.kodujdlapolski.cichy_bohater.R;
import pl.kodujdlapolski.cichy_bohater.data.Category;
import pl.kodujdlapolski.cichy_bohater.rest.CichyBohaterRestAdapter;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;

public class StartActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);

		(new LoadingCategoriesAsyncTask(this, "pl")).execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		return true;
	}

	// loading categories data from API
	private class LoadingCategoriesAsyncTask extends
			AsyncTask<String, Void, List<Category>> {
		private Activity activity;
		private String language = null;

		public LoadingCategoriesAsyncTask(Activity activity, String language) {
			super();
			this.activity = activity;
			this.language = language;
		}

		@Override
		protected List<Category> doInBackground(String... langs) {
			if (!AppStatus.getInstance().isOnline(StartActivity.this)) {
				Intent intent = new Intent(activity, EmergencyActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				activity.startActivity(intent);
				activity.finish();
				return null;
			}
			CichyBohaterRestAdapter adapter = new CichyBohaterRestAdapter();
			return adapter.getCategories(this.language);
		}

		@Override
		protected void onPostExecute(List<Category> result) {
			if (result != null) {
				ArrayList<Category> aList = new ArrayList<Category>(result);
				Intent intent = new Intent(activity, MenuActivity.class);
				intent.putExtra(Constants.CATEGORIES_LIST_EXTRA, aList);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				activity.startActivity(intent);
				activity.finish();
			}
			super.onPostExecute(result);
		}
	}

}
