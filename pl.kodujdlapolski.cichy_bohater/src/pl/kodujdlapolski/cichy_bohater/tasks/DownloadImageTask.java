package pl.kodujdlapolski.cichy_bohater.tasks;

import java.io.InputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
	ImageView bmImage;
	private String url;

	public DownloadImageTask(ImageView bmImage, String url) {
		this.bmImage = bmImage;
		this.url = url;
	}

	protected Bitmap doInBackground(String... urls) {
		Bitmap mIcon11 = null;
		try {
			InputStream in = new java.net.URL(this.url).openStream();
			mIcon11 = BitmapFactory.decodeStream(in);
		} catch (Exception e) {
			Log.e("Error", e.getMessage());
			e.printStackTrace();
		}
		return mIcon11;
	}

	protected void onPostExecute(Bitmap result) {
		bmImage.setImageBitmap(result);
	}
}