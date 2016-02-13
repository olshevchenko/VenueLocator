package com.example.ol.venuelocator.venues;

import com.example.ol.venuelocator.Constants;
import com.example.ol.venuelocator.http.dto.CategoryDto;
import com.example.ol.venuelocator.http.dto.LocationDto;
import com.example.ol.venuelocator.http.dto.VenueDto;

import java.util.List;

/**
 * Created by ol on 03.02.16.
 */
public class Venue {
  //for logging
  private static final String LOG_TAG = Venue.class.getName();

  private VenueHeader header;
  private VenueDetails details;
  private VenueLocation location;


  public class VenueHeader {
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

  public class VenueDetails {
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

  public class VenueLocation {
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

  public VenueHeader getHeader() {
    return header;
  }

  public VenueDetails getDetails() {
    return details;
  }

  public VenueLocation getLocation() {
    return location;
  }

  public Venue() {
    this.header = new VenueHeader();
    this.details = new VenueDetails();
    this.location = new VenueLocation();
  }

  public Venue(double ltt, double lng) {
    this.header = new VenueHeader();
    this.details = new VenueDetails();
    this.location = new VenueLocation(ltt, lng);
  }

  public Venue(String name, String primCategoryName, String address, String city,
               double ltt, double lng, int distance) {
    this.header = new VenueHeader(name, primCategoryName, distance);
    this.details = new VenueDetails(name, primCategoryName, city, address);
    this.location = new VenueLocation(ltt, lng);
  }

  /// converter constructor DTO => model data type
  public Venue(VenueDto dto) throws IllegalArgumentException {
    LocationDto dtoLocation = dto.getLocation();
    double ltt = dtoLocation.getLtt();
    double lng = dtoLocation.getLng();

    /// skip the venue all with incorrect coordinates
    if ((ltt < -180) || (ltt > 180))
      throw new IllegalArgumentException(Constants.Locations.ILLEGAL_LL_LAT_VALUE + ltt);
    if ((lng < -180) || (lng > 180))
      throw new IllegalArgumentException(Constants.Locations.ILLEGAL_LL_LNG_VALUE + lng);

    this.location = new VenueLocation(ltt, lng);

    int distance = dtoLocation.getDistance();
    String primCategoryName = "";
    List<CategoryDto> dtoCategories = dto.getCategories();
    if (null != dtoCategories)
      if (! dtoCategories.isEmpty()) {
        CategoryDto dtoCategory0 = dtoCategories.get(0);
        if (dtoCategory0.isPrimary()) {
          primCategoryName = dtoCategory0.getName();
        }
      }

    String name = dto.getName();
    this.header = new VenueHeader((null!= name)?name:"", primCategoryName, distance);

    String city = dtoLocation.getCity();
    String address = dtoLocation.getAddress();
    this.details = new VenueDetails(name, primCategoryName,
        (null!= city)?city:"", (null!= address)?address:"");
  }
}
