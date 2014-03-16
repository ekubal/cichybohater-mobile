package pl.kodujdlapolski.cichy_bohater;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

public class AppStatus implements LocationListener {
	private static AppStatus instance = new AppStatus();
	private Location location;

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

	public boolean isOffline(Context context) {
		return !isOnline(context);
	}

	public String getDeviceId(Context context) {
		TelephonyManager telephoneManager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		return telephoneManager.getDeviceId();
	}

	public String getLanguage() {
		String lang = Locale.getDefault().getLanguage();
		if (lang == null) {
			lang = Constants.defaultLanguage;
		}
		return lang;
	}

	public String getCountryCode() {
		String lang = Locale.getDefault().getCountry();
		if (lang == null) {
			lang = "";
		}
		return lang;
	}

	public void setCurrentLocation(Location location) {
		this.location = location;
	}

	public static Location getCurrentLocation() {
		return getInstance().location;
	}

	@Override
	public void onLocationChanged(Location loc) {
		this.location = loc;
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}

	public String getCurrentAddress(Context context) {
		if (location != null) {
			Geocoder gc = new Geocoder(context, Locale.getDefault());
			try {
				List<Address> addressesList = gc.getFromLocation(
						location.getLatitude(), location.getLongitude(), 1);
				Address address = addressesList.get(0);

//				Toast.makeText(context, "toString  " + address.toString(),
//						Toast.LENGTH_LONG).show();
				Log.e("Address toString", address.toString());
				return address.getFeatureName() + "\n"
						+ address.getPostalCode() + " " + address.getLocality();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	public void disableLocationUpdates(Context context) {
		LocationManager locManager = (LocationManager) context
				.getSystemService(Context.LOCATION_SERVICE);
		locManager.removeUpdates(this);
	}

	public void enableLocationUpdates(Context context) {
		LocationManager locManager = (LocationManager) context
				.getSystemService(Context.LOCATION_SERVICE);
		locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000,
				1, AppStatus.getInstance());
		locManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
				10000, 1, AppStatus.getInstance());
	}

}