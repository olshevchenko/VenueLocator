package com.example.ol.venuelocator.model;

/**
 * Created by ol on 06.02.16.
 */
public abstract class Place {
  
  private PlaceHeader header;
  private PlaceDetails details;
  private PlaceLocation location;

  class PlaceHeader {
  }

  class PlaceDetails {
  }

  class PlaceLocation {
  }

  public PlaceHeader getHeader() {
    return header;
  }

  public PlaceDetails getDetails() {
    return details;
  }

  public PlaceLocation getLocation() {
    return location;
  }

  Place() {
    header = new PlaceHeader();
    details = new PlaceDetails();
    location = new PlaceLocation();
  }
}
