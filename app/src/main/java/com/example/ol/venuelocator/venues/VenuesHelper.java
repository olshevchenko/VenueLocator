package com.example.ol.venuelocator.venues;

import com.example.ol.venuelocator.model.Venue;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ol on 04.02.16.
 */
public class VenuesHelper {
  private List <Venue> venueList = new ArrayList<>();

  public VenuesHelper() {
    venueList.add(new Venue("RusWizards", "IT-startup", "Некрасовский-Энгельса", "Таганрог", 47.20316, 38.93624, 282));
    venueList.add(new Venue("RusWizards2", "IT-startup", "Некрасовский-Энгельса", "Таганрог", 47.20319, 38.93627, 282));
    venueList.add(new Venue("RusWizards3", "IT-startup", "Некрасовский-Энгельса", "Таганрог", 47.20322, 38.93630, 282));
    venueList.add(new Venue("Аида", "Гастроном", "Некрасовский", "Таганрог", 47.20541, 38.93653, 126));
    venueList.add(new Venue("Аида2", "Гастроном", "Некрасовский", "Таганрог", 47.20544, 38.93656, 126));
    venueList.add(new Venue("Аида3", "Гастроном", "Некрасовский", "Таганрог", 47.20547, 38.93659, 126));
  }

  public List<Venue> getVenueList() {
    return venueList;
  }

  public void  refreshVenueList() {
    venueList.clear();
    venueList.add(new Venue("Food Court", "Дайнер", "Некрасовский", "Таганрог", 47.20331, 38.93604, 273));
    venueList.add(new Venue("Food Court2", "Дайнер", "Некрасовский", "Таганрог", 47.20334, 38.93607, 273));
    venueList.add(new Venue("CBOSS south", "Офис", "Октябрьская 19", "Таганрог", 47.20713, 38.93515, 1807));
    venueList.add(new Venue("CBOSS south2", "Офис", "Октябрьская 19", "Таганрог", 47.20717, 38.93518, 1807));
  }

  public void setVenueList(List<Venue> venueList) {
    this.venueList = venueList;
  }

  public Venue getVenue(int number) throws IndexOutOfBoundsException {
    return venueList.get(number);
  }
}
