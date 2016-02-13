package com.example.ol.venuelocator.http.dto;

import java.io.Serializable;

/**
 * Created by ol on 12.02.16.
 */
public class IconDto implements Serializable {
  private static final long serialVersionUID = 1L;

  private String prefix;
  private String suffix;

  public String getPrefix() {
    return prefix;
  }

  public void setPrefix(String prefix) {
    this.prefix = prefix;
  }

  public String getSuffix() {
    return suffix;
  }

  public void setSuffix(String suffix) {
    this.suffix = suffix;
  }
}

