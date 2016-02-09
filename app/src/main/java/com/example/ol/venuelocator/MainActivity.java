package com.example.ol.venuelocator;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;

import com.example.ol.venuelocator.venues.VenuesHelper;


public class MainActivity extends FragmentActivity implements
    VenuesListFragment.onVenueClickListener, OnMapReadyCallback, View.OnClickListener {
  //for logging
  private static final String LOG_TAG = MainActivity.class.getName();

  ///map instance & pure interface reference on it
  private Map mMap = null;
  private Logic.onPlacesMarkProcessor mPlacesMarkProcessor;

  private ProgressDialog mPleaseWaitDialog;
  private Button mBtLoc1, mBtLoc2;

  private int mVenueClickedNumber = -1;
  private boolean mIsVenueClicked4Details = false;
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
    mBtLoc2 = (Button) findViewById(R.id.bt2);
    mBtLoc2.setOnClickListener(this);

    ///get access to globally stored venues list
    GlobalStorage globalStorage = (GlobalStorage)getApplicationContext();
    mVHelper = globalStorage.getVHelper();

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
  public void venueClick(int position, boolean isDetailClick) {
    mVenueClickedNumber = position;
    mIsVenueClicked4Details = isDetailClick;
    if (true == isDetailClick)
      showVenueDetail(position);
    else
      selectVenueOnTheMap(position);
  }

  @Override
  public void onMapReady(GoogleMap googleMap) {
    mPleaseWaitDialog.dismiss();
    mMap = new Map(googleMap); ///init & tune our map instance from the Google one
    mPlacesMarkProcessor = mMap;
    showVenuesOnTheMap(); ///immediately show existed (may be empty) venues list on the map just created
  }


  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.bt1:
      case R.id.bt2:
//        mVHelper.refreshVenueList();
//        venuesListAdapter.notifyDataSetChanged();
//        showVenuesOnTheMap();
        break;
      default:
        break;
    }
  }

  void showVenueDetail(int number) {
    startActivity(new Intent(this, DetailsActivity.class).
        putExtra(Constants.VenueClickParams.VENUE_CLICKED_NUMBER, number));
  }

  void showVenuesOnTheMap() {
    mPlacesMarkProcessor.placesShow(mVHelper.getVenueList());
  }

  void selectVenueOnTheMap(int number) {
    mPlacesMarkProcessor.placeSelect(number, mVHelper.getVenue(number));
  }
}