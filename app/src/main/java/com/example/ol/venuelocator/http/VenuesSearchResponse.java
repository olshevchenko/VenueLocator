package com.example.ol.venuelocator.http;

import com.example.ol.venuelocator.http.dto.MetaDto;
import com.example.ol.venuelocator.http.dto.ResponseDto;

/**
 * Search FULL response example:
{
"meta":{"code":200,"requestId":"56b1f6e6498ed171da607f03"},
"response":{"venues":
  [{"id":"51d14444498e48b081f952cb",
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
                "venueChains":[]},
   {"id":"5097b49ee4b08e0dbf8d3254",
    "name":"Аида",
    "contact":{},
    "location":{"address":"ул. Чехова, 43",
                "crossStreet":"пер. Некрасовский",
                "lat":47.20541501465012,
                "lng":38.93653392791748,
                "distance":126,
                "cc":"RU",
                "city":"Таганрог",
                "state":"Ростовская обл.",
                "country":"Россия",
                "formattedAddress":["ул. Чехова, 43 (пер. Некрасовский)","Таганрог","Россия"]},
                "categories":[{"id":"4bf58dd8d48988d118951735",
                               "name":"Гастроном",
                               "pluralName":"Гастрономы",
                               "shortName":"Гастроном",
                               "icon":{"prefix":"https:\/\/ss3.4sqi.net\/img\/categories_v2\/shops\/food_grocery_","suffix":".png"},
                               "primary":true}],
                "verified":false,
                "stats":{"checkinsCount":157,
                         "usersCount":43,
                         "tipCount":2},
                "allowMenuUrlEdit":true,
                "specials":{"count":0,
                            "items":[]},
                "hereNow":{"count":0,
                           "summary":"Здесь никого нет",
                           "groups":[]},
                "referralId":"v-1454503654",
                "venueChains":[]}],
  "confident":false}}
}
*/

public class VenuesSearchResponse{
  private MetaDto meta; /// "meta":{"code":200,"requestId":"56b1f6e6498ed171da607f03"},
  private ResponseDto response; /// "response":{"venues":

  public MetaDto getMeta() {
    return meta;
  }

  public void setMeta(MetaDto meta) {
    this.meta = meta;
  }

  public ResponseDto getResponse() {
    return response;
  }

  public void setResponse(ResponseDto response) {
    this.response = response;
  }
/*
  public String toString() {
    return "{\n" + meta + ",\n" + response + "\n}";
  }
*/
}

