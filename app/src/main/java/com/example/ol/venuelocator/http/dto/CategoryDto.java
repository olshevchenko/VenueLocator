package com.example.ol.venuelocator.http.dto;

import java.io.Serializable;

/**
 * Created by ol on 12.02.16.
 */
public class CategoryDto implements Serializable {
  private static final long serialVersionUID = 1L;

  private String id;
  private String name;
  private String pluralName;
  private String shortName;
  private IconDto icon;
  private boolean primary;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPluralName() {
    return pluralName;
  }

  public void setPluralName(String pluralName) {
    this.pluralName = pluralName;
  }

  public String getShortName() {
    return shortName;
  }

  public void setShortName(String shortName) {
    this.shortName = shortName;
  }

  public IconDto getIcon() {
    return icon;
  }

  public void setIcon(IconDto icon) {
    this.icon = icon;
  }

  public boolean isPrimary() {
    return primary;
  }

  public void setIsPrimary(boolean primary) {
    this.primary = primary;
  }
}
