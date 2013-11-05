package pl.kodujdlapolski.cichy_bohater;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class AppStatus {
	private static AppStatus instance = new AppStatus();

	private AppStatus() {
	}

	public static AppStatus getInstance() {
		return instance;
	}

	// check if device is connected to any network
	public boolean isNetworkAvailable(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager
				.getActiveNetworkInfo();
		return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}

	// check if device is connected to internet
	public boolean isOnline(Context context) {
		if (isNetworkAvailable(context)) {
			try {
				HttpURLConnection urlc = (HttpURLConnection) (new URL(
						"http://www.google.com").openConnection());
				urlc.setRequestProperty("User-Agent", "Test");
				urlc.setRequestProperty("Connection", "close");
				urlc.setConnectTimeout(1000);
				urlc.connect();
				return (urlc.getResponseCode() == 200);
			} catch (IOException e) {
				if (BuildConfig.DEBUG) {
					Log.e(Constants.LOG, "Error checking internet connection",
							e);
				}
			}
		} else {
			Log.d(Constants.LOG, "No network available!");
		}
		return false;
	}
}
