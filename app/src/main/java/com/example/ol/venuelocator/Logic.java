package com.example.ol.venuelocator;

import com.example.ol.venuelocator.venues.Venue;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

/**
 * superclass for operations abstract interfaces
 */
public class Logic {

  /**
   * abstract interface to search places nearest for the given one
   */
  public interface onPlacesSearchProcessor {
    void placesSearch(Venue currPosition);
  }

  /**
   * abstract interface to (someway) show & select places on a map
   * also controls map
   */
  public interface onPlacesMapProcessor {
    boolean placesShow(List<Venue> venues2ShowList);
    boolean placeSelect(int position, Venue venue2Select);
    void positionMove(LatLng location);
  }

  /**
   * abstract interface to complete places list update processing
   */
  public interface onPlacesUpdateProcessor {
    void placesUpdate(List<Venue> newList);
  }

  /**
   * abstract interface to (someway) refresh places headers list
   */
  public interface onPlacesRefreshHeadersProcessor {
    void placesRefreshHeaders(); /// a sign for refresh header list view
  }

  /**
   * abstract interface to complete location (both network & GPS) update processing
   */
  public interface onLocationUpdateProcessor {
    void locationUpdate(LatLng location);
  }

}


