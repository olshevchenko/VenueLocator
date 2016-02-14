package com.example.ol.venuelocator;

import com.example.ol.venuelocator.http.FoursquareClient;
import com.example.ol.venuelocator.venues.Venue;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.ol.venuelocator.venues.VenuesHelper;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;


public class MainActivity extends FragmentActivity implements
    VenuesListFragment.onVenueClickListener,
    Logic.onPlacesUpdateProcessor,
    Logic.onLocationUpdateProcessor,
    OnMapReadyCallback,
    View.OnClickListener {
  //for logging
  private static final String LOG_TAG = MainActivity.class.getName();

  /// VenuesListFragment reference & pure interface reference on VenuesListFragment
  private Logic.onPlacesRefreshHeadersProcessor mPlacesRefreshHeadersProcessor = null;

  /// location client instance
  private LocationClient mLocationClient = null;

  /// map instance & pure interface reference on it
  private MapClient mMapClient = null;
  private Logic.onPlacesMapProcessor mPlacesMarkProcessor = null;

  /// Foursquare client instance & pure interface reference on it
  private FoursquareClient mHttpClient = null;
  private Logic.onPlacesSearchProcessor mPlacesSearchProcessor = null;

  private ProgressDialog mPleaseWaitDialog;
  ///ToDo REMOVE it after debug!
  private Button mBtLoc1;

  /**
   * current venue header index & his event type
   */
  private int mVenueClickedNumber = -1;
  private boolean mIsVenueClicked4Details = false;

  /// current location data
  private static LatLng mCurrentLatLng;

  private VenuesHelper mVHelper;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    if (savedInstanceState != null) {
      mVenueClickedNumber = savedInstanceState.getInt(Constants.VenueClickParams.VENUE_CLICKED_NUMBER);
      mIsVenueClicked4Details = savedInstanceState.getBoolean(Constants.VenueClickParams.IS_VENUE_CLICKED_4_DETAILS);
    }

    mBtLoc1 = (Button) findViewById(R.id.bt1);
    mBtLoc1.setOnClickListener(this);

    ///get access to globally stored venues list
    GlobalStorage globalStorage = (GlobalStorage)getApplicationContext();
    mVHelper = globalStorage.getVHelper();

    ///get interface reference on the VenuesListFragment
    mPlacesRefreshHeadersProcessor = (VenuesListFragment) getSupportFragmentManager()
        .findFragmentById(R.id.frVenuesList);

    ///get a Google map instance
    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
        .findFragmentById(R.id.fragment_map);

    ///show "wait for the map" dialog due to indefinite getMapAsync() result
    mPleaseWaitDialog = ProgressDialog.show(this, "",
        getString(R.string.dlgGettingAccess2GoogleMap), false, false);

    mapFragment.getMapAsync(this);
  }

  @Override
  public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putInt(Constants.VenueClickParams.VENUE_CLICKED_NUMBER, mVenueClickedNumber);
    outState.putBoolean(Constants.VenueClickParams.IS_VENUE_CLICKED_4_DETAILS, mIsVenueClicked4Details);
  }

  @Override
  public void onMapReady(GoogleMap googleMap) {
    mPleaseWaitDialog.dismiss();

    mMapClient = new MapClient(googleMap); /// init & tune our map instance from the Google one
    mPlacesMarkProcessor = mMapClient;

    mLocationClient = new LocationClient(this); /// init & tune location instance

    mHttpClient = new FoursquareClient(this); /// init & tune Foursquare HTTP instance
    mPlacesSearchProcessor = mHttpClient;

    mLocationClient.position(); /// start main logic based on location change detection

  }


  @Override
  protected void onDestroy() {
    mLocationClient.exit();
    mVHelper.clear();
    super.onDestroy();
  }


  /**
   *
   */
  @Override
  public void locationUpdate(LatLng location) {
    mCurrentLatLng = location;

    /// change map position
    if (null != mPlacesMarkProcessor)
      mPlacesMarkProcessor.positionMove(location);

    /// asynchronously call (Foursquare) venues search for current location
    if (null != mPlacesSearchProcessor)
      mPlacesSearchProcessor.placesSearch(new Venue(location.latitude, location.longitude));
  }




  /**
   * completely processes venues update event (changes storage, refresh headers, redraws map)
   * @param newList - list of updated venues
   */
  @Override
  public void placesUpdate(List<Venue> newList) {
    mVHelper.setVenueList(newList);
    if (null != mPlacesRefreshHeadersProcessor)
      mPlacesRefreshHeadersProcessor.placesRefreshHeaders();
    if (null != mPlacesMarkProcessor)
      mPlacesMarkProcessor.placesShow(mVHelper.getVenueList());
  }

  /**
   * Either start new activity for venue details show or select venue mark on the map
   * @param position - number of item in the list
   * @param isDetailClick - what exactly has been clicked - the hole item (for selection)
   *                      or the details button
   */
  @Override
  public void venueClick(int position, boolean isDetailClick) {
    mVenueClickedNumber = position;
    mIsVenueClicked4Details = isDetailClick;
    if (isDetailClick)
      showVenueDetail(position);
    else
      mPlacesMarkProcessor.placeSelect(position, mVHelper.getVenue(position));
  }


  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.bt1:
        /// move to West-West-Nord (around Taganrog)
        mCurrentLatLng = new LatLng(mCurrentLatLng.latitude + 0.001,
            mCurrentLatLng.longitude - 0.0005);
        Log.d(LOG_TAG, "DBG - falsely change location to " + mCurrentLatLng);
        locationUpdate(mCurrentLatLng);
        break;
      default:
        break;
    }
  }

  void showVenueDetail(int number) {
    startActivity(new Intent(this, DetailsActivity.class).
        putExtra(Constants.VenueClickParams.VENUE_CLICKED_NUMBER, number));
  }

}