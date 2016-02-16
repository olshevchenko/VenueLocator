package com.example.ol.venuelocator.http;

import com.example.ol.venuelocator.Constants;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * describes Retrofit annotations for 'search' venues response & programming request for it
 */
public class FoursquareAPI {
  //for logging
  private static final String LOG_TAG = FoursquareAPI.class.getName();

  /**
   * URL example:
   * https://api.foursquare.com/v2/
   *     venues/search
   *     ?ll=47.205,38.934
   *     &client_id=SACOAK4HOC0I344GYQLI3F40MYCG5B40ENIVEYN1ENOTGW1A
   *     &client_secret=AVLU3FTLIXEMZJV0D5WRJTOHYV5MMIOADIO310C0FZM1C1J2
   *     &v=20160203
   */
  public interface Api {
    @GET(Constants.Url.ENDPOINT_ACTION_SEARCH)
    Call<VenuesSearchResponse> getResponse(
        @Query(Constants.Url.LL_PARAM) String ll,
        @Query(Constants.Url.CLIENT_PARAM) String clientId,
        @Query(Constants.Url.CLIENT_SECRET_PARAM) String clientSecret,
        @Query(Constants.Url.V_PARAM) String version);
  }

  private static Api sApi = null;

  private static Retrofit providesRetrofitClient(String baseUrl) {
/**
    HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
    logging.setLevel(HttpLoggingInterceptor.Level.BODY);
    OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    // add logging as last interceptor
    httpClient.addInterceptor(logging);
*/
    Retrofit client = new Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
//        .client(httpClient.build())
        .build();
    return client;
  }


  private static void initApi(){
    if (null == sApi) {
      Retrofit client = providesRetrofitClient(Constants.Url.ENDPOINT_URL);
      sApi = client.create(Api.class);
    }
  }

  public static void releaseApi(){
    if (null != sApi) {
//      ;
      sApi = null;
    }
  }

  public static Api getApi() {
    if (null == sApi)
      initApi();
    return sApi;
  }

} //public class FoursquareAPI

