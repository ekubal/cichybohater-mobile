package pl.kodujdlapolski.cichy_bohater;

import java.io.FileNotFoundException;
import java.io.InputStream;

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
import android.view.Menu;
import android.view.MenuItem;
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

		Integer category_id = getIntent().getExtras().getInt(
				MenuActivity.INCIDENT_CATEGORY_ID);
		(new CategoryAsyncTask()).execute(category_id);
		// ActionBar ap = getSupportActionBar();
		// ap.hide();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.incident, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:

			Intent intent = new Intent(IncidentActivity.this,
					MenuActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			break;
		}
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
		if (requestCode == TAKE_PHOTO_ACTION && resultCode == RESULT_OK) {
			Bundle extras = data.getExtras();
			Bitmap mImageBitmap = (Bitmap) extras.get("data");
			image.setImageBitmap(mImageBitmap);
		} else if (requestCode == SELECT_PHOTO_ACTION
				&& resultCode == RESULT_OK) {
			Uri selectedImage = data.getData();
			InputStream imageStream;
			try {
				imageStream = getContentResolver().openInputStream(
						selectedImage);
				Bitmap mImageBitmap = BitmapFactory.decodeStream(imageStream);
				image.setImageBitmap(mImageBitmap);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		super.onActivityResult(requestCode, resultCode, data);
	}

	private class CategoryAsyncTask extends AsyncTask<Integer, Void, Category> {

		@Override
		protected Category doInBackground(Integer... params) {
			if (params.length > 0) {
				Integer category_id = params[0];
				CichyBohaterRestAdapter adapter = new CichyBohaterRestAdapter();
				Category category = adapter.getCategory(category_id);
				return category;
			} else {
				return null;
			}
		}

		@Override
		protected void onPostExecute(Category result) {
			if (result != null) {
				Toast.makeText(getApplicationContext(),
						"Category_name = " + result.getName(),
						Toast.LENGTH_SHORT).show();
			}
			super.onPostExecute(result);
		}

	}
}
