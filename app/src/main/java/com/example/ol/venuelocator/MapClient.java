package com.example.ol.venuelocator;


import android.util.Log;

import com.example.ol.venuelocator.venues.Venue;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class MapClient implements Logic.onPlacesMapProcessor {
  //for logging
  private static final String LOG_TAG = MapClient.class.getName();

  private GoogleMap mGoogleMap;
  private List<Marker> mMarkersList = new ArrayList<>(); ///markers made storage for further selective removal
  Marker mSelected = null; ///marker made SELECTED

  /**
   * current GPS / network location data
   */
  private static LatLng mCurrentLatLng;


  /// tune the map
  public MapClient(GoogleMap map) {
    mGoogleMap = map;

    mGoogleMap.animateCamera(
        CameraUpdateFactory.zoomTo(Constants.Locations.MAP_DEFAULT_ZOOM_LEVEL));

    UiSettings uiSettings = mGoogleMap.getUiSettings();
    uiSettings.setCompassEnabled(true);
    uiSettings.setMapToolbarEnabled(true);
    uiSettings.setMyLocationButtonEnabled(true);
    uiSettings.setRotateGesturesEnabled(false);
    uiSettings.setTiltGesturesEnabled(false);
    uiSettings.setScrollGesturesEnabled(true);
    uiSettings.setZoomControlsEnabled(true);
    uiSettings.setZoomGesturesEnabled(true);

    mGoogleMap.setMyLocationEnabled(true);
  }

  /**
   * makes map markers
   *
   * @param venues2ShowList - list of places on the map for making markers from
   * @return true if succeeded
   */
  @Override
  public boolean placesShow(List<Venue> venues2ShowList) {
    Log.i(LOG_TAG, "placesShow()");

    ///previously clear ALL marks and storage for them
    mGoogleMap.clear();
    mMarkersList.clear();
    mSelected = null;

    for (Venue venue: venues2ShowList) {
      LatLng markerPosition = new LatLng(venue.getLocation().getLtt(), venue.getLocation().getLng());
      mMarkersList.add(mGoogleMap.addMarker(
          new MarkerOptions()
              .position(markerPosition)
              .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))));
    }
    return true;
  }


  /**
   * turns map marker into specially SELECTED one
   *
   * @param venue2Select - place on the map for making SPECIAL marker from
   * @return true if succeeded
   */
  @Override
  public boolean placeSelect(int position, Venue venue2Select) {
//    Log.d(LOG_TAG, "placesSelect() for venue[" + position + "]");

    /// try to combine our own location with the selected one
/*
    LatLngBounds bounds = new LatLngBounds(mCurrentLatLng,
        new LatLng(venue2Select.getLocation().getLtt(),
            venue2Select.getLocation().getLng()));
    mGoogleMap.animateCamera(
        CameraUpdateFactory.newLatLngBounds(bounds, Constants.Locations.MAP_DEFAULT_BOUNDS_PADDING));
*/
    mGoogleMap.animateCamera(CameraUpdateFactory.newLatLng(
        new LatLng(venue2Select.getLocation().getLtt(),
            venue2Select.getLocation().getLng())));

    ///1'st, convert old selection back into normal marker form (if exists)
    if (null != mSelected)
      mSelected.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));

    ///then, find the new selection in the existed markers list (by venues position)
    mSelected = mMarkersList.get(position);

    ///convert normal marker into selection form
    mSelected.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
    return true;
  }

  @Override
  public void positionMove(LatLng location) {
    mCurrentLatLng = location;
    mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(location));
  }

}
