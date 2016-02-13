package com.example.ol.venuelocator.http.dto;

import java.io.Serializable;

/**
 * Created by ol on 12.02.16.
 */
public class ContactDto implements Serializable {
  private static final long serialVersionUID = 1L;

  private String twitter;
  private String phone;
  private String formattedPhone;

  public String getTwitter() {
    return twitter;
  }

  public void setTwitter(String twitter) {
    this.twitter = twitter;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getFormattedPhone() {
    return formattedPhone;
  }

  public void setFormattedPhone(String formattedPhone) {
    this.formattedPhone = formattedPhone;
  }
}

