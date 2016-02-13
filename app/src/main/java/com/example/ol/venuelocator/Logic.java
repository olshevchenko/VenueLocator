package com.example.ol.venuelocator;

import com.example.ol.venuelocator.venues.Venue;

import java.util.List;

/**
 * Created by ol on 06.02.16.
 */
public class Logic {

  /**
   * abstract interface to search places nearest for the given one
   */
  public interface onPlacesSearchProcessor {
    void placesSearch(Venue currPosition);
  }

  /**
   * abstract interface to (someway) show & select places
   */
  public interface onPlacesMarkProcessor {
    boolean placesShow(List<Venue> venues2ShowList);
    boolean placeSelect(int position, Venue venue2Select);
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
}


