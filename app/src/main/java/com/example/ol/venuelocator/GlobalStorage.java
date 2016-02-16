package com.example.ol.venuelocator;

import com.example.ol.venuelocator.venues.VenuesHelper;

/**
 * Created by ol on 05.02.16.
 */
public class GlobalStorage extends android.app.Application {

  private static VenuesHelper vHelper = new VenuesHelper();

  public VenuesHelper getVHelper() {
    return vHelper;
  }
}