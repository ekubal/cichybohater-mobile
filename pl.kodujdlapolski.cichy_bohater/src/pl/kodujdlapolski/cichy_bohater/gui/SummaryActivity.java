package pl.kodujdlapolski.cichy_bohater.gui;

import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.DefaultHttpClient;

import pl.kodujdlapolski.cichy_bohater.Constants;
import pl.kodujdlapolski.cichy_bohater.R;
import pl.kodujdlapolski.cichy_bohater.data.Category;
import pl.kodujdlapolski.cichy_bohater.data.CategoryAttribute;
import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class SummaryActivity extends BaseAcitivity {

	private Category incidentCategory;
	private ContentValues inputValues;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_summary);

		inputValues = getIncidentDataFromIntent();
		incidentCategory = getCategoryFromIntent();
		setAppTitle(incidentCategory.getName());

	}

	public void onSendButtonClick(View view) {
		(new SendRequestAsyncTask(inputValues)).execute();
	}

	public ContentValues getIncidentDataFromIntent() {
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			return (ContentValues) extras.get(Constants.INCIDENT_DATA_EXTRA);
		} else {
			return null;
		}
	}

	private class SendRequestAsyncTask extends
			AsyncTask<String, Void, HttpEntity> {
		private ContentValues values;

		public SendRequestAsyncTask(ContentValues values) {
			this.values = values;
		}

		@Override
		protected HttpEntity doInBackground(String... params) {
			return doPostRequest(values);

		}

		@Override
		protected void onPostExecute(HttpEntity result) {
			Toast.makeText(SummaryActivity.this, "Odebrano...",
					Toast.LENGTH_LONG).show();
			super.onPostExecute(result);
		}

	}

	private HttpEntity doPostRequest(ContentValues values) {

		String urlString = Constants.create_incident_url;
		try {
			HttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost(urlString);

			MultipartEntityBuilder entityBuilder = MultipartEntityBuilder
					.create();
			entityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

			List<CategoryAttribute> categoryAttributes = incidentCategory
					.getCategoryAttributes();

			for (CategoryAttribute attribute : categoryAttributes) {
				String value = inputValues
						.getAsString(attribute.getPermalink());
				if (value != null) {
					entityBuilder
							.addTextBody("incident[" + attribute.getPermalink()
									+ "]", value);
				}
			}
			entityBuilder.addTextBody("category_id", incidentCategory.getId()
					.toString());
			entityBuilder.addTextBody("phone_number", getMyPhoneNumber());
			final HttpEntity reqEntity = entityBuilder.build();

			post.setEntity(reqEntity);
			HttpResponse response = client.execute(post);

			HttpEntity resEntity = response.getEntity();
			return reqEntity;
		} catch (Exception ex) {
			Log.e("Debug", "error: " + ex.getMessage(), ex);
		}
		return null;
	}

	private String getMyPhoneNumber() {
		TelephonyManager mTelephonyMgr;
		mTelephonyMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		return mTelephonyMgr.getLine1Number();
	}
}
