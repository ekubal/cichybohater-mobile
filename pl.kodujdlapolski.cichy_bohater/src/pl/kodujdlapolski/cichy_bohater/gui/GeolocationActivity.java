package pl.kodujdlapolski.cichy_bohater.gui;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import pl.kodujdlapolski.cichy_bohater.Constants;
import pl.kodujdlapolski.cichy_bohater.R;
import pl.kodujdlapolski.cichy_bohater.data.Category;
import android.content.ContentValues;
import android.content.Intent;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerDragListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class GeolocationActivity extends FragmentActivity implements
		LocationListener, OnMapClickListener {
	private Location currentLocation;
	private GoogleMap map;
	private Marker locationMarker;
	private LocationManager locationManager;
	private String locationProvider;
	private ContentValues inputValues;
	private Button okButton;
	private Category incidentCategory;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_geolocation);

		inputValues = (ContentValues) getIntent().getExtras().get(
				Constants.INCIDENT_DATA_EXTRA);
		incidentCategory = (Category) getIntent().getExtras().get(
				Constants.CATEGORY_EXTRA);
		// map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
		// .getMap();

		okButton = (Button) findViewById(R.id.geolocation_ok_button);

		locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		locationProvider = locationManager.getBestProvider(new Criteria(),
				false);
		currentLocation = locationManager
				.getLastKnownLocation(locationProvider);
		if (currentLocation != null) {
			onLocationChanged(currentLocation);
		}

		map.setOnMapClickListener(this);
		setTitle(incidentCategory.getName());

		if (false) {
			Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
			startActivity(intent);
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.geolocation, menu);
		return true;
	}

	public void onLocationChanged(LatLng position) {
		MarkerOptions locMarker = new MarkerOptions().position(position);
		boolean newMarker = locationMarker == null;
		if (newMarker) {
			locationMarker = map.addMarker(locMarker);
			locationMarker.setDraggable(true);
			map.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 16));
		} else {
			locationMarker.setPosition(position);
		}
		locationMarker.setTitle(getAddress(position));
		locationMarker.showInfoWindow();

		map.setOnMarkerDragListener(new OnMarkerDragListener() {

			@Override
			public void onMarkerDragStart(Marker marker) {

			}

			@Override
			public void onMarkerDragEnd(Marker marker) {
				onLocationChanged(marker.getPosition());

			}

			@Override
			public void onMarkerDrag(Marker marker) {
				onLocationChanged(marker.getPosition());

			}
		});
		okButton.setEnabled(true);
	}

	@Override
	public void onLocationChanged(Location location) {
		LatLng pos = new LatLng(location.getLatitude(), location.getLongitude());
		onLocationChanged(pos);
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

	/* Request updates at startup */
	@Override
	protected void onResume() {
		super.onResume();
		locationManager.requestLocationUpdates(locationProvider, 400, 1, this);
	}

	/* Remove the locationlistener updates when Activity is paused */
	@Override
	protected void onPause() {
		super.onPause();
		locationManager.removeUpdates(this);
	}

	@Override
	public void onMapClick(LatLng position) {
		onLocationChanged(position);

	}

	private String getAddress(LatLng position) {
		Geocoder gc = new Geocoder(GeolocationActivity.this,
				Locale.getDefault());
		try {
			List<Address> addressesList = gc.getFromLocation(position.latitude,
					position.longitude, 1);
			Address address = addressesList.get(0);
			for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
				Log.d("=Adress=", address.getAddressLine(i));
			}
			locationMarker.setSnippet(address.getLocality());

			return address.getThoroughfare() + " " + address.getFeatureName();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public void onOkClick(View view) {
		Intent intent = new Intent(this, SummaryActivity.class);
		intent.putExtra(Constants.CATEGORY_EXTRA, incidentCategory);
		intent.putExtra(Constants.INCIDENT_DATA_EXTRA, inputValues);
		startActivity(intent);
	}
}
