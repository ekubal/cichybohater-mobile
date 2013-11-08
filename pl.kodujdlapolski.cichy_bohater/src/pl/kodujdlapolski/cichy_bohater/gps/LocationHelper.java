package pl.kodujdlapolski.cichy_bohater.gps;

import pl.kodujdlapolski.cichy_bohater.gui.MenuActivity;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

public class LocationHelper {
	public static Location getCurrentLocation(final MenuActivity context) {
		LocationManager service = (LocationManager) context
				.getSystemService(Context.LOCATION_SERVICE);

		LocationListener locationListener = new LocationListener() {

			@Override
			public void onStatusChanged(String provider, int status,
					Bundle extras) {
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

			@Override
			public void onLocationChanged(Location location) {
				// context.updateLocation(location);

			}
		};

		Criteria criteria = new Criteria();
		String provider = service.getBestProvider(criteria, false);
		return service.getLastKnownLocation(provider);
	}

	public static boolean locationEnabled(Context context) {
		LocationManager service = (LocationManager) context
				.getSystemService(Context.LOCATION_SERVICE);
		return service.isProviderEnabled(LocationManager.GPS_PROVIDER);
	}
}
