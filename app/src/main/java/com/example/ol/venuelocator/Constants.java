package com.example.ol.venuelocator;

/**
 * Constants necessary for operation
 */
public final class Constants {
  public class Languages {
    public static final String ENG = "en";
    public static final String RUS = "ru";
  }

  public class SavedParams {
    public static final String VENUE_NUMBER = "venueNumber";
    public static final String LATLNG = "LatLng";
  }

  public class Locations {
    public static final float MAP_DEFAULT_ZOOM_LEVEL = 16.0f; /// streets => buildings
    public static final int MAP_DEFAULT_BOUNDS_PADDING = 10; /// borders gap in pixels

    public static final int UPDATE_INTERVAL_TIME = 10000; /// in mS
    public static final float UPDATE_INTERVAL_DISTANCE = 100.0f; /// in meters

    public static final String LL_FORMAT = "###.#####"; ///format for GET http request
    public static final double VAL1_LATT = 47.2055319;
    public static final double VAL1_LONG = 38.9348749;

    public static final String ILLEGAL_LL_LAT_VALUE = "Illegal location latitude value";
    public static final String ILLEGAL_LL_LNG_VALUE = "Illegal location longitude value";
  }

  public class Url {
    public static final String ENDPOINT_URL = "https://api.foursquare.com/v2/";
//    public static final String ENDPOINT_ACTION_CATEGORIES = "venues/categories";
    public static final String ENDPOINT_ACTION_SEARCH = "venues/search";
    public static final String PARAMS_START = "?";
    public static final String LL_PARAM = "ll";
    public static final String PARAMS_DIVIDER = "&";
    public static final String CLIENT_PARAM = "client_id";
    public static final String CLIENT_VALUE = "SACOAK4HOC0I344GYQLI3F40MYCG5B40ENIVEYN1ENOTGW1A";
    public static final String CLIENT_SECRET_PARAM = "client_secret";
    public static final String CLIENT_SECRET_VALUE = "AVLU3FTLIXEMZJV0D5WRJTOHYV5MMIOADIO310C0FZM1C1J2";
    public static final String V_PARAM = "v"; ///version param
    public static final String V_PARAM_VALUE = "20160203"; ///current date
  }
} //class Constants



