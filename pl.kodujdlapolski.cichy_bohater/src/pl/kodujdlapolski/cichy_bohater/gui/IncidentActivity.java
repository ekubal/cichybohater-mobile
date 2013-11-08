package pl.kodujdlapolski.cichy_bohater.gui;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;

import pl.kodujdlapolski.cichy_bohater.Constants;
import pl.kodujdlapolski.cichy_bohater.R;
import pl.kodujdlapolski.cichy_bohater.data.Category;
import pl.kodujdlapolski.cichy_bohater.rest.CichyBohaterRestAdapter;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class IncidentActivity extends Activity {

	private static final int TAKE_PHOTO_ACTION = 1;
	private static final int SELECT_PHOTO_ACTION = 2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_incident);

		Integer categoryId = getIntent().getExtras().getInt(
				Constants.INCIDENT_CATEGORY_ID);
		(new CategoryAsyncTask()).execute(categoryId);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.incident, menu);
		return true;
	}

	public void onTakePhotoClick(View view) {
		Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		startActivityForResult(takePictureIntent, TAKE_PHOTO_ACTION);
	}

	public void onSelectPhotoClick(View view) {
		Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
		photoPickerIntent.setType("image/*");
		startActivityForResult(photoPickerIntent, SELECT_PHOTO_ACTION);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		ImageView image = (ImageView) findViewById(R.id.image_preview);
		Bitmap mImageBitmap = null;
		if (requestCode == TAKE_PHOTO_ACTION && resultCode == RESULT_OK) {
			Bundle extras = data.getExtras();
			mImageBitmap = (Bitmap) extras.get("data");
		} else if (requestCode == SELECT_PHOTO_ACTION
				&& resultCode == RESULT_OK) {
			Uri selectedImage = data.getData();
			InputStream imageStream;
			try {
				imageStream = getContentResolver().openInputStream(
						selectedImage);
				mImageBitmap = BitmapFactory.decodeStream(imageStream);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			return;
		}
		image.setImageBitmap(mImageBitmap);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		mImageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		byte[] imageBytes = baos.toByteArray();
		String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);

		HttpClient httpClient = new DefaultHttpClient();
		HttpPost post = new HttpPost(Constants.create_incident_url);
		MultipartEntityBuilder mEntityBuilder = MultipartEntityBuilder.create();
		mEntityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
		try {
			mEntityBuilder.addPart("image", new StringBody(encodedImage));
			post.setEntity(mEntityBuilder.build());
			HttpResponse resp = httpClient.execute(post);
			HttpEntity entity = resp.getEntity();
			Toast.makeText(getApplicationContext(), "" + resp.getStatusLine(),
					Toast.LENGTH_LONG).show();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private class CategoryAsyncTask extends AsyncTask<Integer, Void, Category> {

		@Override
		protected Category doInBackground(Integer... params) {
			if (params.length > 0) {
				Integer categoryId = params[0];
				CichyBohaterRestAdapter adapter = new CichyBohaterRestAdapter();
				return adapter.getCategory(categoryId);
			} else {
				return null;
			}
		}

		@Override
		protected void onPostExecute(Category result) {
			super.onPostExecute(result);
		}

	}
}
