package pl.kodujdlapolski.cichy_bohater.gui;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;

import pl.kodujdlapolski.cichy_bohater.Constants;
import pl.kodujdlapolski.cichy_bohater.R;
import pl.kodujdlapolski.cichy_bohater.SummaryActivity;
import pl.kodujdlapolski.cichy_bohater.data.Category;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class IncidentActivity extends FragmentActivity {

	private AlertDialog loadingDialog;
	private View lastUsedView;
	private FormFragment formFragment;
	private Category incidentCategory;

	public void setLastUsedView(View lastUsedView) {
		this.lastUsedView = lastUsedView;
	}

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// createLoadingDialog();

		setContentView(R.layout.activity_incident);

		Bundle inputExtras = getIntent().getExtras();
		formFragment = (FormFragment) getSupportFragmentManager()
				.findFragmentById(R.id.form_fragment);
		if (inputExtras != null) {
			incidentCategory = (Category) inputExtras
					.get(Constants.CATEGORY_EXTRA);
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.incident, menu);
		return true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		Toast.makeText(this, "xx2x", Toast.LENGTH_LONG).show();
		// formFragment.onActivityResult(requestCode, resultCode, data);
		if (lastUsedView != null) {
			if (requestCode == Constants.TAKE_PHOTO_ACTION
					&& resultCode == RESULT_OK) {
				Bundle extras = data.getExtras();
				Bitmap mImageBitmap = (Bitmap) extras.get("data");
				ImageView img = (ImageView) lastUsedView
						.findViewById(R.id.form_image_preview);
				img.setImageBitmap(mImageBitmap);
			} else if (requestCode == Constants.SELECT_PHOTO_ACTION
					&& resultCode == RESULT_OK) {

				Uri selectedImage = data.getData();
				InputStream imageStream;
				try {
					imageStream = getContentResolver().openInputStream(
							selectedImage);
					Bitmap mImageBitmap = BitmapFactory
							.decodeStream(imageStream);
					ImageView img = (ImageView) lastUsedView
							.findViewById(R.id.form_image_preview);
					img.setImageBitmap(mImageBitmap);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	public void onSendButtonClick(View view) {
		Intent intent = new Intent();
		if (incidentCategory.requireLocation()) {
			intent.setClass(this, GeolocationActivity.class);
		} else {
			intent.setClass(this, SummaryActivity.class);
		}
		ContentValues values = new ContentValues();
		for (Map.Entry<String, String> entry : formFragment.getAllInputs()
				.entrySet()) {
			values.put(entry.getKey(), entry.getValue());
		}
		intent.putExtra(Constants.INCIDENT_DATA_EXTRA, values);
		intent.putExtra(Constants.CATEGORY_EXTRA, incidentCategory);
		startActivity(intent);

		// sendToServer(formFragment.getAllInputs());
	}

	private void createLoadingDialog() {
		if (loadingDialog != null && loadingDialog.isShowing()) {
			loadingDialog.dismiss();
		}
		loadingDialog = LoadingDialog.createLoadingDialog(this);
		loadingDialog.show();
	}

	// private void sendToServer(Map<String, String> data) {
	// HttpClient httpclient = new DefaultHttpClient();
	// HttpPost httppost = new HttpPost(Constants.create_incident_url);
	// List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(
	// data.size());
	// for (Entry<String, String> entry : formFragment.getAllInputs()
	// .entrySet()) {
	// nameValuePairs.add(new BasicNameValuePair(entry.getKey(), entry
	// .getValue()));
	// }
	//
	// try {
	// httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	// HttpResponse response = httpclient.execute(httppost);
	// Toast.makeText(getApplicationContext(),
	// "" + response.getStatusLine(), Toast.LENGTH_LONG).show();
	// } catch (UnsupportedEncodingException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (ClientProtocolException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }

}
