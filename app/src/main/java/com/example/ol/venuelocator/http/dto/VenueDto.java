package com.example.ol.venuelocator.http.dto;

/**
 * Created by ol on 03.02.16.
 */

import java.io.Serializable;
import java.util.List;

/**
 * One venue fragment in the search response example:
 {"id":"51d14444498e48b081f952cb",
 "name":"Рус Визардс, департамент разработки мобильных приложений",
 "contact":{"twitter":"ruswizards"},
 "location":{"address":"пер. Некрасовский, 75",
 "crossStreet":"ул. Энгельса",
 "lat":47.20316999507908,
 "lng":38.93624424934387,
 "distance":282,
 "postalCode":"347922",
 "cc":"RU",
 "city":"Таганрог",
 "state":"Ростовская обл.",
 "country":"Россия",
 "formattedAddress":["пер. Некрасовский, 75 (ул. Энгельса)","347922, Таганрог","Россия"]},
 "categories":[{"id":"4bf58dd8d48988d125941735",
 "name":"IT-стартап",
 "pluralName":"IT-стартапы",
 "shortName":"IT-стартап",
 "icon":{"prefix":"https:\/\/ss3.4sqi.net\/img\/categories_v2\/shops\/technology_","suffix":".png"},
 "primary":true}],
 "verified":false,
 "stats":{"checkinsCount":374,
 "usersCount":9,
 "tipCount":0},
 "url":"http:\/\/ruswizards.com",
 "specials":{"count":0,
 "items":[]},
 "hereNow":{"count":0,
 "summary":"Здесь никого нет",
 "groups":[]},
 "referralId":"v-1454503654",
 "venueChains":[]}
*/
public class VenueDto implements Serializable {
  private static final long serialVersionUID = 1L;

  private String id;
  private String name;
  private LocationDto location;
  private ContactDto contact;
  private List<CategoryDto> categories;

/**
 * Ignore so far...
  private boolean verified;
  private Stats stats;
  private String url;
  private Specials specials;
  private HereNow hereNow;
  private String referralId;
  private List<VenueDto> venueChains;

  private class Stats implements Serializable {
    private static final long serialVersionUID = 1L;

    private int checkinsCount;
    private int usersCount;
    private int tipCount;

    public Stats() {}
  }

  private class Specials implements Serializable {
    private static final long serialVersionUID = 1L;

    private int count;
    private List<Item> items;

    private class Item  implements Serializable {
      private static final long serialVersionUID = 1L;
    } /// try to ignore

    public Specials() {
      items = new ArrayList<>();
    }
  }

  private class HereNow implements Serializable {
    private static final long serialVersionUID = 1L;

    private int count;
    private String summary;
    private List<Group> groups;

    private class Group implements Serializable {
      private static final long serialVersionUID = 1L;
    } /// try to ignore

    public HereNow() {
      groups = new ArrayList<>();
    }
  }

  public String toString() {
    String primCategoryName  = "";
    if (categories.isEmpty() == false) {
      Category category0 = categories.get(0);
      if (category0.isPrimary() == true) {
        primCategoryName = category0.getName();
      }
    }
    return "{name: " + name + ", primCategory: " + primCategoryName + ", distance: " + location.distance+ "}";
  }
 */
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

  public ContactDto getContact() {
    return contact;
  }

  public void setContact(ContactDto contact) {
    this.contact = contact;
  }

  public LocationDto getLocation() {
    return location;
  }

  public void setLocation(LocationDto location) {
    this.location = location;
  }

  public List<CategoryDto> getCategories() {
    return categories;
  }

  public void setCategories(List<CategoryDto> categories) {
    this.categories = categories;
  }
}
