package com.example.ol.venuelocator;


import android.util.Log;

import com.example.ol.venuelocator.venues.Venue;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class Map implements Logic.onPlacesMarkProcessor {
  //for logging
  private static final String LOG_TAG = Map.class.getName();

  private GoogleMap mGoogleMap;
  private List<Marker> mMarkersList = new ArrayList<>(); ///markers made storage for further selective removal
  Marker mSelected = null; ///marker made SELECTED

  public Map(GoogleMap map) {
    mGoogleMap = map;
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
      mMarkersList.add(mGoogleMap.addMarker(new MarkerOptions().position(markerPosition).
          icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))));
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
    Log.i(LOG_TAG, "placesSelect() for venue[" + position + "]");

    ///1'st, convert old selection back into normal marker form (if exists)
    if (null != mSelected)
      mSelected.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));

    ///then, find the new selection in the existed markers list (by venues position)
    mSelected = mMarkersList.get(position);

    ///convert normal marker into selection form
    mSelected.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
    return true;
  }
}
