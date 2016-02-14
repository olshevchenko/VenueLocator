package com.example.ol.venuelocator;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;


public class LocationClient {
  //for logging
  private static final String LOG_TAG = LocationClient.class.getName();

  private Context mContext;
  private LocationManager mLocationManager;

  /// venues update processor interface
  private Logic.onLocationUpdateProcessor mLocationUpdateProcessor = null;

  public LocationClient(Context context) {
    this.mContext = context;

    mLocationUpdateProcessor = (Logic.onLocationUpdateProcessor) mContext;

    /// get a system location service instance
    mLocationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
  }

  /**
   * starts to define location both through GPS & networks
   */
  public void position() {
    if (null == mLocationManager) {
      Log.w(LOG_TAG, "LocationManager is down. Can not use positioning services.");
      return;
    }

    mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
        Constants.Locations.UPDATE_INTERVAL_TIME,
        Constants.Locations.UPDATE_INTERVAL_DISTANCE,
        locationListener);
    mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
        Constants.Locations.UPDATE_INTERVAL_TIME,
        Constants.Locations.UPDATE_INTERVAL_DISTANCE,
        locationListener);
  }


  protected void exit() {
    mLocationManager.removeUpdates(locationListener);
  }


  private LocationListener locationListener = new LocationListener() {

      @Override
      public void onLocationChanged(Location location) {
        mLocationUpdateProcessor.locationUpdate(new LatLng(location.getLatitude(), location.getLongitude()));
      }

      @Override
      public void onProviderDisabled(String provider) {
        Toast toast = Toast.makeText(mContext, "ProviderDisabled", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
      }

      @Override
      public void onProviderEnabled(String provider) {
        Toast toast = Toast.makeText(mContext, "ProviderEnabled", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
      }

      @Override
      public void onStatusChanged(String provider, int status, Bundle extras) {
        Toast toast = Toast.makeText(mContext, "ProviderStatusChanged", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
      }
    };

} //public class LocationClient

