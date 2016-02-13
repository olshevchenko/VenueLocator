package com.example.ol.venuelocator.http.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ol on 12.02.16.
 */

public class LocationDto implements Serializable {
  private static final long serialVersionUID = 1L;

  private String city;
  private String address;
  private double lat;
  private double lng;
  private int distance;
  private String crossStreet;
/*
  private String postalCode;
  private String cc;
  private String state;
  private String country;
  private List<String> formattedAddress;
*/

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public double getLtt() {
    return lat;
  }

  public void setLtt(double lat) {
    this.lat = lat;
  }

  public double getLng() {
    return lng;
  }

  public void setLng(double lng) {
    this.lng = lng;
  }

  public int getDistance() {
    return distance;
  }

  public void setDistance(int distance) {
    this.distance = distance;
  }

  public String getCrossStreet() {
    return crossStreet;
  }

  public void setCrossStreet(String crossStreet) {
    this.crossStreet = crossStreet;
  }

/*
  public String getPostalCode() {
    return postalCode;
  }

  public void setPostalCode(String postalCode) {
    this.postalCode = postalCode;
  }

  public String getCc() {
    return cc;
  }

  public void setCc(String cc) {
    this.cc = cc;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public List<String> getFormattedAddress() {
    return formattedAddress;
  }

  public void setFormattedAddress(List<String> formattedAddress) {
    this.formattedAddress = formattedAddress;
  }
*/
}
