package com.example.ol.venuelocator;

/**
 * Created by oshevchenk on 03.02.2016.
 */
public final class Constants {
  public class Languages {
    public static final String ENG = "en";
    public static final String RUS = "ru";
  }

  public class VenueClickParams {
    public static final String VENUE_CLICKED_NUMBER = "venueClickedNumber";
    public static final String IS_VENUE_CLICKED_4_DETAILS = "isVenueClicked4Details";
  }

  public class Locations {
    public static final String LL_FORMAT = "###.#####"; ///format for GET http request
    public static final double VAL1_LATT = 47.2055319;
    public static final double VAL1_LONG = 38.9348749;

    public static final float VAL2_LATT = 47.21043f;
    public static final float VAL2_LONG = 38.92240f;
  }


  public class Url {
    public static final String ENDPOINT_URL = "https://api.foursquare.com/v2/venues/";
//    public static final String ENDPOINT_ACTION_CATEGORIES = "categories";
    public static final String ENDPOINT_ACTION_SEARCH = "search";
    public static final String PARAMS_START = "?";
    public static final String LL_PARAM = "ll=";
    public static final String PARAMS_DIVIDER = "&";
    public static final String CLIENT_PARAM = "client_id=";
    public static final String CLIENT_VALUE = "SACOAK4HOC0I344GYQLI3F40MYCG5B40ENIVEYN1ENOTGW1A";
    public static final String CLIENT_SECRET_PARAM = "client_secret=";
    public static final String CLIENT_SECRET_VALUE = "AVLU3FTLIXEMZJV0D5WRJTOHYV5MMIOADIO310C0FZM1C1J2";
    public static final String V_PARAM = "v="; ///version param
    public static final String V_PARAM_VALUE = "20160203"; ///current date
  }
} //class Constants



