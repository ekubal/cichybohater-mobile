package pl.kodujdlapolski.cichy_bohater.gui;

import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.DefaultHttpClient;

import pl.kodujdlapolski.cichy_bohater.AppStatus;
import pl.kodujdlapolski.cichy_bohater.Constants;
import pl.kodujdlapolski.cichy_bohater.R;
import pl.kodujdlapolski.cichy_bohater.data.Field;
import pl.kodujdlapolski.cichy_bohater.data.Schema;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SummaryActivity extends BaseAcitivity {

	private Schema interventionSchema;
	private ContentValues inputValues;
	private AlertDialog loadingDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_summary);

		inputValues = IncidentActivity.getFormData();

		interventionSchema = getCategoryFromIntent();
		setAppTitle(interventionSchema.getLabel());

		LinearLayout fragmentLayout = (LinearLayout) findViewById(R.id.summary_inputs);
		LayoutInflater inflater = getLayoutInflater();

		TextView summaryCategoryDescription = (TextView) findViewById(R.id.summary_category);
		summaryCategoryDescription.setText(interventionSchema.getDescription());

		List<Field> attributes = interventionSchema.getFields();
		for (Field attribute : attributes) {
			String attributeType = attribute.getType();
			String attributeName = attribute.getPermalink();
			String attributeValue = inputValues.getAsString(attributeName);
			if (attributeType != null && attributeValue != null) {
				if (attributeType.equals(Constants.TEXT_FIELD_TYPE)
						|| attributeType.equals(Constants.TEXT_AREA_FIELD_TYPE)
						|| attributeType.equals(Constants.NUMBER_FIELD_TYPE)
						|| attributeType.equals(Constants.COMBO_FIELD_TYPE)) {
					View layout = inflater.inflate(
							R.layout.summary_fragment_text, null, false);
					TextView attributeLabel = (TextView) layout
							.findViewById(R.id.summary_label);
					attributeLabel.setText(attribute.getName() + ":");
					TextView inputText = (TextView) layout
							.findViewById(R.id.summary_text);
					inputText.setText(attributeValue);
					fragmentLayout.addView(layout);
				} else if (attributeType.equals(Constants.CHECKBOX_FIELD_TYPE)) {
					View layout = inflater.inflate(
							R.layout.summary_fragment_text, null, false);
					TextView attributeLabel = (TextView) layout
							.findViewById(R.id.summary_label);
					attributeLabel.setText(attribute.getName() + ":");
					TextView inputText = (TextView) layout
							.findViewById(R.id.summary_text);
					inputText.setText(attributeValue.equals("1") ? R.string.yes
							: R.string.no);
					fragmentLayout.addView(layout);
				} else if (attributeType.equals(Constants.PHOTO_FIELD_TYPE)) {
					View layout = inflater.inflate(
							R.layout.summary_fragment_photo, null, false);
					TextView attributeLabel = (TextView) layout
							.findViewById(R.id.summary_label);
					attributeLabel.setText(attribute.getName() + ":");
					ImageView imageView = (ImageView) layout
							.findViewById(R.id.summary_photo);
					Bitmap bitmap = IncidentActivity.getBitmap(attributeName);
					if (bitmap != null) {
						imageView.setImageBitmap(IncidentActivity
								.scaleDownBitmap(bitmap, 150, this));
					}
					fragmentLayout.addView(layout);
				}
			}
		}
		String currentAddress = AppStatus.getInstance().getCurrentAddress(this);
		if (currentAddress != null) {
			View layout = inflater.inflate(R.layout.summary_fragment_text,
					null, false);
			TextView attributeLabel = (TextView) layout
					.findViewById(R.id.summary_label);
			attributeLabel.setText("Lokalizacja:");
			TextView inputText = (TextView) layout
					.findViewById(R.id.summary_text);
			inputText.setText(currentAddress);
			fragmentLayout.addView(layout);
		}
	}

	public void onSendButtonClick(View view) {
		loadingDialog = LoadingDialog.createLoadingDialog(this);
		loadingDialog.show();
		(new SendRequestAsyncTask(inputValues)).execute();
	}

	private class SendRequestAsyncTask extends
			AsyncTask<String, Void, HttpResponse> {
		private ContentValues values;

		public SendRequestAsyncTask(ContentValues values) {
			this.values = values;
		}

		@Override
		protected HttpResponse doInBackground(String... params) {
			return doPostRequest(values);

		}

		@Override
		protected void onPostExecute(HttpResponse result) {
			if (loadingDialog != null && loadingDialog.isShowing()) {
				loadingDialog.dismiss();
			}
			if (result.getStatusLine().getStatusCode() == 200) {
				startActivity(new Intent(SummaryActivity.this,
						ThanksActivity.class));
				finish();
			} else {
				Intent intent = new Intent(SummaryActivity.this,
						ErrorActivity.class);
				intent.putExtra(Constants.ERROR_MESSAGE, result.getStatusLine()
						.getReasonPhrase());
				startActivity(intent);
			}
			super.onPostExecute(result);
		}

	}

	private HttpResponse doPostRequest(ContentValues values) {

		String urlString = Constants.create_intervention_url;
		try {
			HttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost(urlString);

			MultipartEntityBuilder entityBuilder = MultipartEntityBuilder
					.create();
			entityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

			List<Field> categoryAttributes = interventionSchema.getFields();

			for (Field attribute : categoryAttributes) {
				if (attribute.getType().equals(Constants.PHOTO_FIELD_TYPE)) {
					byte[] value = inputValues.getAsByteArray(attribute
							.getPermalink());
					if (value != null) {
						entityBuilder.addBinaryBody(
								"intervention[" + attribute.getPermalink()
										+ "]", value);
					}
				} else if (attribute.getType().equals(
						Constants.CHECKBOX_FIELD_TYPE)) {
					String value = inputValues.getAsString(attribute
							.getPermalink());
					if (value != null) {
						entityBuilder.addTextBody(
								"intervention[" + attribute.getPermalink()
										+ "]", value);
					}
				}

				else {
					String value = inputValues.getAsString(attribute
							.getPermalink());
					if (value != null) {
						entityBuilder.addTextBody(
								"intervention[" + attribute.getPermalink()
										+ "]", value);
					}
				}
			}
			entityBuilder.addTextBody("category_id", interventionSchema.getId()
					.toString());
			entityBuilder.addTextBody("device_id", AppStatus.getInstance()
					.getDeviceId(this));
			entityBuilder.addTextBody("phone_number", getMyPhoneNumber());

			Location loc = AppStatus.getCurrentLocation();
			if (loc != null) {
				entityBuilder.addTextBody("location[latitude]",
						Double.toString(loc.getLatitude()));
				entityBuilder.addTextBody("location[longitude]",
						Double.toString(loc.getLongitude()));

			}
			final HttpEntity reqEntity = entityBuilder.build();

			post.setEntity(reqEntity);
			HttpResponse response = client.execute(post);
			return response;
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
