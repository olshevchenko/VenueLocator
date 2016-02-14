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
//    uiSettings.setMyLocationButtonEnabled(true);
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
              .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))));
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

    /// 1'st, hide info & convert old selection back into normal marker form (if exists)
    if (null != mSelected) {
      mSelected.hideInfoWindow();
      mSelected.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
    }

    /// 2'nd, move maps camera

/**
 * doesn't look as expected - leave it...
    /// try to combine our own location with the selected one
    LatLngBounds bounds = createCorrectLatLngBounds(mCurrentLatLng,
        new LatLng(venue2Select.getLocation().getLtt(),
            venue2Select.getLocation().getLng()));
    mGoogleMap.animateCamera(
        CameraUpdateFactory.newLatLngBounds(bounds, Constants.Locations.MAP_DEFAULT_BOUNDS_PADDING));
*/
    mGoogleMap.animateCamera(CameraUpdateFactory.newLatLng(
        new LatLng(venue2Select.getLocation().getLtt(),
            venue2Select.getLocation().getLng())));

    /// 3'rd, find the new selection in the existed markers list (by venues position)
    mSelected = mMarkersList.get(position);

    /// and convert normal marker into selection view
    mSelected.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
    mSelected.setTitle(venue2Select.getHeader().getName());
    mSelected.setSnippet(venue2Select.getHeader().getPrimCategoryName());
    mSelected.showInfoWindow();
    return true;
  }

  @Override
  public void positionMove(LatLng location) {
    mCurrentLatLng = location;
    mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(location));
  }

  /**
   * creates correct LatLngBounds from ANY corner of bounds rectangle
   * @param corner1
   * @param corner2
   * @return - bounds instance
   */
  private LatLngBounds createCorrectLatLngBounds(LatLng corner1, LatLng corner2) {
    LatLng southwest = new LatLng(
        Math.min(corner1.latitude, corner2.latitude),
        Math.min(corner1.longitude, corner2.longitude));
    LatLng northeast = new LatLng(
        Math.max(corner1.latitude, corner2.latitude),
        Math.max(corner1.longitude, corner2.longitude));
    return new LatLngBounds(southwest, northeast);
  }

}
