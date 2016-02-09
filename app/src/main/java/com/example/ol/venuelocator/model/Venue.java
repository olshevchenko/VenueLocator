package com.example.ol.venuelocator.model;

import android.location.Location;

/**
 * Created by ol on 03.02.16.
 */
public class Venue extends Place {
  //for logging
  private static final String LOG_TAG = Venue.class.getName();

  private VenueHeader header;
  private VenueDetails details;
  private VenueLocation location;

  /**
   * makes String param copy if the linked reference String value is undefined
   *
   * @param param - constructor parameter outer value
   * @param refer - reference to the linked value
   * @return - parameter COPY or just refererce to the existed value
   */
  private String makeParamCopyIfReferenceEmpty(String param, String refer) {
    if ((null == refer) || (true == refer.isEmpty()))
      return new String(param); ///return the param copy
    else
      return refer; ///return the valuable reference
  }

  public class VenueHeader extends PlaceHeader {
    private String name = ""; ///reference to the one from VenueDetails
    private String primCategoryName = ""; ///...
    private int distance = -1;

    private VenueHeader() {}

    /**
     * copy constructor
     */
    public VenueHeader(String name, String primCategoryName, int distance) {
      this.name = new String(name);
      this.primCategoryName = new String(primCategoryName);
      this.distance = distance;
    }

    public String getName() {
      return name;
    }

    public String getPrimCategoryName() {
      return primCategoryName;
    }

    public int getDistance() {
      return distance;
    }
  }

  public class VenueDetails extends PlaceDetails {
    private String name = ""; ///reference to the one from VenueHeader
    private String primCategoryName = ""; ///...
    private String city = "";
    private String address = ""; ///Hm... ask for "street" here...

    private VenueDetails() {}

    /**
     * copy constructor
     */
    public VenueDetails(String name, String primCategoryName, String city, String address) {
      this.name = new String(name);
      this.primCategoryName = new String(primCategoryName);
      this.city = new String(city);
      this.address = new String(address);
    }

    public String getName() {
      return name;
    }

    public String getPrimCategoryName() {
      return primCategoryName;
    }

    public String getCity() {
      return city;
    }

    public String getAddress() {
      return address;
    }
  }

  public class VenueLocation extends PlaceLocation {
    private double ltt = 0.0d;
    private double lng = 0.0d;

    private VenueLocation() {}

    public VenueLocation(double ltt, double lng) {
      this.ltt = ltt;
      this.lng = lng;
    }

    public double getLtt() {
      return ltt;
    }

    public double getLng() {
      return lng;
    }
  }

  @Override
  public VenueHeader getHeader() {
    return header;
  }

  @Override
  public VenueDetails getDetails() {
    return details;
  }

  @Override
  public VenueLocation getLocation() {
    return location;
  }

  public Venue() {
    this.header = new VenueHeader();
    this.details = new VenueDetails();
    this.location = new VenueLocation();
  }

  private String name = ""; ///reference to the one from VenueDetails
  private String primCategoryName = ""; ///...
  private int distance = -1;
  private String city = "";
  private String address = ""; ///Hm... ask for "street" here...
  private double ltt = 0.0d;
  private double lng = 0.0d;

  public Venue(String name, String primCategoryName, String address, String city, double ltt, double lng, int distance) {
    this.header = new VenueHeader(name, primCategoryName, distance);
    this.details = new VenueDetails(name, primCategoryName, city, address);
    this.location = new VenueLocation(ltt, lng);
  }
}
