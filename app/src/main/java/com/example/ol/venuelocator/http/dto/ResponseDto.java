package com.example.ol.venuelocator.http.dto;

import java.util.List;

/**
 * Created by ol on 10.02.16.
 */
public class ResponseDto {

  private List<VenueDto> venues;
//  private boolean confident;

  public List<VenueDto> getVenues() {
    return venues;
  }

  public void setVenues(List<VenueDto> venues) {
    this.venues = venues;
  }
/*
  public boolean isConfident() {
    return confident;
  }

  public void setConfident(boolean confident) {
    this.confident = confident;
  }

  public String toString() {
    return "response-venues:{[\n" + venues.toString() + "\n]}";
  }
*/
}
