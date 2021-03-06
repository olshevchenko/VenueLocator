package com.example.ol.venuelocator.http;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.example.ol.venuelocator.GlobalStorage;
import com.example.ol.venuelocator.Informing;
import com.example.ol.venuelocator.Logic;
import com.example.ol.venuelocator.R;
import com.example.ol.venuelocator.http.dto.VenueDto;
import com.example.ol.venuelocator.venues.Venue;
import com.example.ol.venuelocator.venues.VenuesHelper;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * class for Retrofit operation with Foursquare HTTP server
 * requests ('search') for venues list nearest to location pointed
 */
public class FoursquareClient implements Logic.onPlacesSearchProcessor {
  //for logging
  private static final String LOG_TAG = FoursquareClient.class.getName();

  private Context mContext;
  private FragmentManager mFM;
  private FoursquareAPI.Api mApi;
  private ApiParams mApiParams;
  private VenuesHelper mVHelper;

  /// venues update processor interface
  private Logic.onPlacesUpdateProcessor mPlacesUpdateProcessor = null;

  public FoursquareClient(Context context, FragmentManager fm) {
    this.mContext = context;
    this.mFM = fm;
    mPlacesUpdateProcessor = (Logic.onPlacesUpdateProcessor) mContext;
    mApiParams = new ApiParams(); /// init default location
    mApi = FoursquareAPI.getApi();
    GlobalStorage globalStorage = (GlobalStorage) mContext.getApplicationContext();
    mVHelper = globalStorage.getVHelper();
  }

  public void placesSearch(Venue currPosition) {

    if (((Activity) mContext).isFinishing())
      return;

    mApiParams.setLocation(currPosition.getLocation().getLtt(), currPosition.getLocation().getLng());

    final ProgressDialog dialog = ProgressDialog.show(mContext, "",
        mContext.getString(R.string.dlgGettingVenuesList), false, false);

    Call<VenuesSearchResponse> call = mApi.getResponse(mApiParams.getLocation(),
        mApiParams.getClientId(), mApiParams.getClientSecret(), mApiParams.getVersion());

    /// make asynchronous request for venues list
    call.enqueue(new Callback<VenuesSearchResponse>() {
      @Override
      public void onResponse(Call<VenuesSearchResponse> call, Response<VenuesSearchResponse> response) {
        if (((Activity) mContext).isFinishing())
          return;

        dialog.dismiss();
        if (response.isSuccess()) {
          /// request successful (status code 200, 201)
          VenuesSearchResponse resultResponse = response.body();

          /// get original DTO venues list from Foursquare & create work venues list based on DTO one
          List<VenueDto> venueDtoList = resultResponse.getResponse().getVenues();
          List<Venue> venueList = new ArrayList<>(venueDtoList.size());
          Venue venue;
          for (VenueDto venueDto : venueDtoList ) {
            try {
              venue = new Venue(venueDto); /// conversion DTO => model
              venueList.add(venue);
            } catch (IllegalArgumentException ex) {
              Log.w(LOG_TAG, "Got Illegal location lat / lng value in venue '" + venueDto.getName() + "'");
              /// just skip one venue & continue loop
            }
          }
          Log.d(LOG_TAG, "Got new [" + venueList.size() + "] venues");

          if (null !=  mPlacesUpdateProcessor)
            mPlacesUpdateProcessor.placesUpdate(venueList);

        } else {
          /// response received but request not successful (like 400,401,403 etc)
          Log.w(LOG_TAG, "Got unsuccessful HTTP response, code: " + response.code());
        }
      }

      @Override
      public void onFailure(Call<VenuesSearchResponse> call, Throwable t) {
        dialog.dismiss();
        /// it's a network crash!
        Informing.ServiceFailedDialogFragment sfDialogFragment =
            Informing.ServiceFailedDialogFragment.newInstance(
                R.string.dlgHTTPServiceFailedTitle,
                R.string.dlgHTTPServiceFailedMessage,
                R.drawable.ic_sync_problem_white_36dp);
        sfDialogFragment.show(mFM, "dialog");
        Log.w(LOG_TAG, "Got FAILURE while getting venues search list: " + t.getMessage());
      }
    });
  }

} //public class FoursquareAPI

