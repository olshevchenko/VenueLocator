package com.example.ol.venuelocator;

import com.example.ol.venuelocator.http.FoursquareClient;
import com.example.ol.venuelocator.venues.Venue;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.ol.venuelocator.venues.VenuesHelper;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;


/**
 * application Main activity
 */
public class MainActivity extends AppCompatActivity implements
    VenuesListFragment.onVenueClickListener,
    Logic.onPlacesUpdateProcessor,
    Logic.onLocationUpdateProcessor,
    OnMapReadyCallback,
    View.OnClickListener {
  //for logging
  private static final String LOG_TAG = MainActivity.class.getName();

  /// global venues list holder
  private VenuesHelper mVHelper = null;

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
  private ActionBar mActionBar;
  ///ToDo REMOVE it after debug!
  private Button mBtLoc1;

  /// current SELECTED venue header index
  private int mVenueSelectedNumber = -1;

  /// current location data
  private static LatLng mCurrentLatLng = new LatLng(0, 0);


  private static class CustomNonConfigurationInstance {
    private VenuesHelper mVHelper = null;
    private LocationClient mLocationClient = null;
    private MapClient mMapClient = null;
    private FoursquareClient mHttpClient = null;

    public CustomNonConfigurationInstance(VenuesHelper vHelper,
        LocationClient locationClient, MapClient mapClient, FoursquareClient httpClient) {
      this.mVHelper = vHelper;
      this.mLocationClient = locationClient;
      this.mMapClient = mapClient;
      this.mHttpClient = httpClient;
    }

    public VenuesHelper getVHelper() {
      return mVHelper;
    }

    public LocationClient getLocationClient() {
      return mLocationClient;
    }

    public MapClient getMapClient() {
      return mMapClient;
    }

    public FoursquareClient getHttpClient() {
      return mHttpClient;
    }
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    /// check for exit return from child Details activity
    if (getIntent().getBooleanExtra("EXIT", false)) {
      finish();
    }

    /// check if Google Play services is available
    int res = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this);
    if (res != ConnectionResult.SUCCESS) {
      Log.e(LOG_TAG, "GooglePlayServices is unavailable (ConnectionResult code " + res + "). Finishing...");
      Informing.ServiceFailedDialogFragment sfDialogFragment =
          Informing.ServiceFailedDialogFragment.newInstance(
              R.string.dlgGoogleApiFailedTitle,
              R.string.dlgGoogleApiFailedMessage,
              R.drawable.ic_error_white_36dp);
      sfDialogFragment.show(getSupportFragmentManager(), "dialog");
      finish();
    }

    setContentView(R.layout.activity_main);

    /// check whether it's cfg change or real new activity creation
    CustomNonConfigurationInstance nonCfgInstance =
        (CustomNonConfigurationInstance) getLastCustomNonConfigurationInstance();
    if (nonCfgInstance != null) {
      this.mVHelper = nonCfgInstance.getVHelper();
      this.mLocationClient = nonCfgInstance.getLocationClient();
      this.mMapClient = nonCfgInstance.getMapClient();
      this.mHttpClient = nonCfgInstance.getHttpClient();
    }

    mBtLoc1 = (Button) findViewById(R.id.bt1);
    mBtLoc1.setOnClickListener(this);

    /// get interface reference on the VenuesListFragment
    mPlacesRefreshHeadersProcessor = (VenuesListFragment) getSupportFragmentManager()
        .findFragmentById(R.id.frVenuesList);

    ///get access to globally stored venues list - IF NOT EXISTS ALREADY
    if (null == mVHelper) {
      GlobalStorage globalStorage = (GlobalStorage) getApplicationContext();
      mVHelper = globalStorage.getVHelper();
    }

    /// get location instance - IF NOT EXISTS ALREADY
    if (null == mLocationClient) {
      mLocationClient = new LocationClient(this, getSupportFragmentManager());
    }

    /// init & tune Foursquare HTTP instance - IF NOT EXISTS ALREADY
    if (null == mHttpClient) {
      mHttpClient = new FoursquareClient(this, getSupportFragmentManager());
    }
    mPlacesSearchProcessor = mHttpClient;

    ///get a Google map instance if not exists
    if (null == mMapClient) {
      SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
          .findFragmentById(R.id.fragment_map);
      mapFragment.setRetainInstance(true);

      ///show "wait for the map" dialog due to indefinite getMapAsync() result
      mPleaseWaitDialog = ProgressDialog.show(this, "",
          getString(R.string.dlgGettingAccess2GoogleMap), false, false);

      mapFragment.getMapAsync(this);
    }
    else {
      /**
       * we're after cfg change - therefore no needs ti init / tune map
       * venue headers list, map markers & selection are up-to-date
       */
      mPlacesMarkProcessor = mMapClient;
      ; /// just wait now for NEW user events
    }
  }

  @Override
  public void onMapReady(GoogleMap googleMap) {
    mPleaseWaitDialog.dismiss();
    mMapClient = new MapClient(googleMap); /// init & tune our map instance from the Google one
    mPlacesMarkProcessor = mMapClient;

    mLocationClient.getLocation(); /// start main logic based on location change detection
  }

  @Override
  public Object onRetainCustomNonConfigurationInstance() {
    super.onRetainCustomNonConfigurationInstance();
    return new CustomNonConfigurationInstance(mVHelper, mLocationClient, mMapClient, mHttpClient);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    if (isFinishing()) {
      mLocationClient.exit();
      mVHelper.clear();
      mMapClient.exit();
    }
    else
      ; /// skip finalization in change orientation case (data will be needed soon)
  }


  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.action_about:
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
        return true;
      case R.id.action_exit:
        finish();
        return true;
      default:
        return super.onOptionsItemSelected(item);
    } //switch
  }

  /**
   * process update location event - change map view position & request for new venues for it
   * @param location - new location to process
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
    /// look at placesUpdate() below to view how to process placesSearch() results...
  }


  /**
   * completely processes venues update event (changes storage, refresh headers, redraws map)
   * @param newList - list of updated venues
   */
  @Override
  public void placesUpdate(List<Venue> newList) {
    mVHelper.setVenueList(newList);
    mVenueSelectedNumber = -1;
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
    if (isDetailClick)
      showVenueDetail(position);
    else {
      mVenueSelectedNumber = position;
      if (null != mPlacesMarkProcessor)
        mPlacesMarkProcessor.placeSelect(position, mVHelper.getVenue(position));
    }
  }


  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.bt1:
        /// ToDo DEBUG feature - REMOVE it later for release version
        /// move to West-West-Nord (around Taganrog)
        LatLng newLatLng = new LatLng(mCurrentLatLng.latitude + 0.001,
            mCurrentLatLng.longitude - 0.0005);
        Log.d(LOG_TAG, "DBG - falsely change location to " + mCurrentLatLng);
        locationUpdate(newLatLng);
        break;
      default:
        break;
    }
  }

  void showVenueDetail(int number) {
    startActivity(new Intent(this, DetailsActivity.class).
        putExtra(Constants.SavedParams.VENUE_NUMBER, number));
  }

}