package com.example.ol.venuelocator.venues;

import java.util.ArrayList;
import java.util.List;

/**
 * wrapper class for venues list
 */
public class VenuesHelper {
  private List <Venue> venueList = new ArrayList<>();

  public VenuesHelper() {}

  public List<Venue> getVenueList() {
    return venueList;
  }

  public void clear() {
    venueList.clear(); /// ... venueList
  }

  public void setVenueList(List<Venue> venueList) {
///    this.venueList = venueList; /// not suitable way for further list notifyDataSetChanged()
    if (this.venueList == venueList)
      ; /// case for placesUpdate() call after cfg change
    else {
      this.venueList.clear();
      this.venueList.addAll(venueList);
    }
  }

  public Venue getVenue(int number) throws IndexOutOfBoundsException {
    return venueList.get(number);
  }
}
