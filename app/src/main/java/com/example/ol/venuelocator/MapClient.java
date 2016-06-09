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
 * class for operation with Google Map instance object
 */
public class MapClient implements Logic.onPlacesMapProcessor {
  //for logging
  private static final String LOG_TAG = MapClient.class.getName();

  private GoogleMap mGoogleMap;
  private Logic.onLocationUpdateProcessor mLocationUpdateProcessor;
  private Marker mMyLocationMarker = null; /// shows current (or manually selected) user location
  private List<Marker> mMarkersList; /// markers shown storage for further selective removal
  int mSelected; /// ID of the marker made SELECTED


  public MapClient(GoogleMap map, Logic.onLocationUpdateProcessor locationUpdateProcessor) {
    mGoogleMap = map;
    mLocationUpdateProcessor = locationUpdateProcessor;
    mMarkersList = new ArrayList<>();
    mSelected = -1;

    mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(Constants.Locations.MAP_DEFAULT_ZOOM_LEVEL));
    UiSettings uiSettings = mGoogleMap.getUiSettings();
    uiSettings.setCompassEnabled(true);
    uiSettings.setMapToolbarEnabled(true);
    uiSettings.setRotateGesturesEnabled(false);
    uiSettings.setTiltGesturesEnabled(false);
    uiSettings.setScrollGesturesEnabled(true);
    uiSettings.setZoomControlsEnabled(true);
    uiSettings.setZoomGesturesEnabled(true);
//    mGoogleMap.setMyLocationEnabled(true);

    /// process of manual position update via touch on the map
    mGoogleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
      @Override
      public void onMapClick(LatLng newLatLng) {
        if (null != mLocationUpdateProcessor) {
          /// move 'my location' marker, camera, request new venues, etc..
          mLocationUpdateProcessor.locationUpdate(newLatLng);
        }
      }
    });
  }

  /**
   * makes map markers from the venues
   *
   * @param venues2ShowList - list of places on the map for making markers from
   * @return true if succeeded
   */
  @Override
  public boolean placesShow(List<Venue> venues2ShowList) {
//    Log.i(LOG_TAG, "placesShow()");

    ///previously clear all venues marks and storage for them
    for (Marker marker : mMarkersList) {
      marker.remove();
    }
    mMarkersList.clear();
    mSelected = -1;

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
    if (mSelected >= 0) {
      mMarkersList.get(mSelected).hideInfoWindow();
      mMarkersList.get(mSelected).setIcon(
          BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
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
    mSelected = position;
    Marker mSelectedMarker = mMarkersList.get(position);

    /// and convert normal marker into selection view
    mSelectedMarker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
    mSelectedMarker.setTitle(venue2Select.getHeader().getName());
    mSelectedMarker.setSnippet(venue2Select.getHeader().getPrimCategoryName());
    mSelectedMarker.showInfoWindow();
    return true;
  }

  @Override
  public void positionMove(LatLng toPosition) {
    if (null == mMyLocationMarker) {
      mMyLocationMarker = mGoogleMap.addMarker(
          new MarkerOptions()
              .position(toPosition)
              .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_my_location_black_36dp)));
    }
    else { /// move existing one
      mMyLocationMarker.setPosition(toPosition);
    }
    mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(toPosition));
  }

  public void exit() {
    mGoogleMap.clear();
    mMarkersList.clear();
    mSelected = -1;
  }

  /**
   * creates correct LatLngBounds from ANY corner of bounds rectangle
   * @param corner1
   * @param corner2
   * @return - bounds instance
  private LatLngBounds createCorrectLatLngBounds(LatLng corner1, LatLng corner2) {
    LatLng southwest = new LatLng(
        Math.min(corner1.latitude, corner2.latitude),
        Math.min(corner1.longitude, corner2.longitude));
    LatLng northeast = new LatLng(
        Math.max(corner1.latitude, corner2.latitude),
        Math.max(corner1.longitude, corner2.longitude));
    return new LatLngBounds(southwest, northeast);
  }
   */

}
