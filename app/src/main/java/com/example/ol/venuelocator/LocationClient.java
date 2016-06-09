package com.example.ol.venuelocator;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

/**
 * class for operation with location services
 */
public class LocationClient {
  //for logging
  private static final String LOG_TAG = LocationClient.class.getName();

  private Context mContext;
  private FragmentManager mFM;
  private LocationManager mLocationManager;

  /// venues update processor interface
  private Logic.onLocationUpdateProcessor mLocationUpdateProcessor = null;

  public LocationClient(Context context, FragmentManager fm) {
    this.mContext = context;
    this.mFM = fm;
    mLocationUpdateProcessor = (Logic.onLocationUpdateProcessor) mContext;

    /// get a system location service instance
    mLocationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);

    /// check location services state
    if (! doesLocationWorks()) {
      /// it's a location crash!
      Informing.ServiceFixDialogFragment sfDialogFragment =
          Informing.ServiceFixDialogFragment.newInstance(
              R.string.dlgLocationServiceFailedTitle,
              R.string.dlgLocationServiceFailedMessage,
              R.drawable.ic_location_disabled_white_36dp,
              R.string.butPosLocationServiceFix,
              R.string.butNegLocationServiceFix);
      sfDialogFragment.show(mFM, "dialog");
    }
  }

  /**
   * get functional status of location services
   * @return true if anyone works
   */
  private boolean doesLocationWorks() {
    return (mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
        mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER));
  }
  /**
   * starts to define location both through GPS & networks
   */
  public void start() {
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


  public void stop() {
    mLocationManager.removeUpdates(locationListener);
  }


  /**
   * location events processor
   */
  private LocationListener locationListener = new LocationListener() {

      @Override
      public void onLocationChanged(Location location) {
        mLocationUpdateProcessor.locationUpdate(new LatLng(location.getLatitude(), location.getLongitude()));
      }

      @Override
      public void onProviderDisabled(String provider) {
        String title = mContext.getString(R.string.tstLocationProviderDisabled, provider);
        Toast toast = Toast.makeText(mContext, title, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
      }

      @Override
      public void onProviderEnabled(String provider) {
        String title = mContext.getString(R.string.tstLocationProviderEnabled, provider);
        Toast toast = Toast.makeText(mContext, title, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
      }

      @Override
      public void onStatusChanged(String provider, int status, Bundle extras) {
/*
        Toast toast = Toast.makeText(mContext, "ProviderStatusChanged", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
*/
      }
    };

} //public class LocationClient

