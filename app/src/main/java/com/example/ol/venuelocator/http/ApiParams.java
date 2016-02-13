package com.example.ol.venuelocator.http;

/**
 * URL example:
 * https://api.foursquare.com/v2/venues/search
 *     ?ll=47.205,38.934
 *     &client_id=SACOAK4HOC0I344GYQLI3F40MYCG5B40ENIVEYN1ENOTGW1A
 *     &client_secret=AVLU3FTLIXEMZJV0D5WRJTOHYV5MMIOADIO310C0FZM1C1J2
 *     &v=20160203
 */

import com.example.ol.venuelocator.Constants;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;


/**
 *  Helper class for quick call Api.getResponse() via early GET params preparation
 */
public class ApiParams {
  private String location_str; //ll in string form ("ltt,lng")
  private final String clientId = Constants.Url.CLIENT_VALUE;
  private final String clientSecret = Constants.Url.CLIENT_SECRET_VALUE;
  private String version = Constants.Url.V_PARAM_VALUE;

  /// prepare format template for location
  private DecimalFormat df;
  {
    DecimalFormatSymbols othersymbols = new DecimalFormatSymbols();
    othersymbols.setDecimalSeparator('.');
    df = new DecimalFormat(Constants.Locations.LL_FORMAT, othersymbols);
  }

  public ApiParams() {
    setLocation(Constants.Locations.VAL1_LATT, Constants.Locations.VAL1_LONG);
  }

  public ApiParams(double ll_latt, double ll_long) {
    setLocation(ll_latt, ll_long);
  }

  public String getClientId() {
    return clientId;
  }

  public String getClientSecret() {
    return clientSecret;
  }

  public String getVersion() {
    return version;
  }

  public String getLocation() {
    return location_str;
  }

  public void setLocation(double ll_latt, double ll_long) {
    location_str = new String(df.format(ll_latt) + "," + df.format(ll_long));
  }
}
