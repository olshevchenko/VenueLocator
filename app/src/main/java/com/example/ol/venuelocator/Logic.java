package com.example.ol.venuelocator;

import com.example.ol.venuelocator.model.Place;
import com.example.ol.venuelocator.model.Venue;

import java.util.List;

/**
 * Created by ol on 06.02.16.
 */
public class Logic {

  /**
   * abstract interface to search places nearest for the given one
   */
  public interface onPlacesSearchProcessor {
    List<Place> placesSearch(Place currPosition);
  }

  /**
   * abstract interface to (someway) show & select places
   */
  public interface onPlacesMarkProcessor {
    boolean placesShow(List<Venue> venues2ShowList);
    boolean placeSelect(int position, Venue venue2Select);
  }
}


