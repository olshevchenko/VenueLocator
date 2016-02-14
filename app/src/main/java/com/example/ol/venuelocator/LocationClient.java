package com.example.ol.venuelocator;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;


public class LocationClient {
  //for logging
  private static final String LOG_TAG = LocationClient.class.getName();

  private Context mContext;
  private FragmentManager mFM;
  private LocationManager mLocationManager;

  /// venues update processor interface
  private Logic.onLocationUpdateProcessor mLocationUpdateProcessor = null;


  public class LocationServiceFailedDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
      AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
      builder.setTitle(R.string.dlgLocationServiceFailedTitle)
          .setMessage(R.string.dlgLocationServiceFailedMessage)
          .setIcon(R.drawable.ic_location_disabled_white_36dp)
          .setPositiveButton("ОК", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
              dialog.cancel();
            }
          });
      return builder.create();
    }
  }


  public LocationClient(Context context, FragmentManager fm) {
    this.mContext = context;
    this.mFM = fm;

    mLocationUpdateProcessor = (Logic.onLocationUpdateProcessor) mContext;

    /// get a system location service instance
    mLocationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
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
  public void getLocation() {
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
        Toast toast = Toast.makeText(mContext,
            "Location provider (" + provider + ") disabled",
            Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
        if (! doesLocationWorks()) {
          /// it's a crash!
          LocationServiceFailedDialogFragment lsfDialogFragment =
              new LocationServiceFailedDialogFragment();
          lsfDialogFragment.show(mFM, "dialog");
        }
      }

      @Override
      public void onProviderEnabled(String provider) {
        Toast toast = Toast.makeText(mContext,
            "Location provider (" + provider + ") enabled",
            Toast.LENGTH_SHORT);
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

