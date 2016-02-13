package com.example.ol.venuelocator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.ol.venuelocator.venues.Venue;
import com.example.ol.venuelocator.venues.VenuesHelper;

public class DetailsActivity extends AppCompatActivity {
  //for logging
  private static final String LOG_TAG = DetailsActivity.class.getName();

  private TextView tvName, tvCategory, tvAddressCity, tvAddress;
  private int venueNumber = 0;
  private VenuesHelper mVHelper;
  private Venue venue;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_details);
    if (savedInstanceState != null)
      venueNumber = savedInstanceState.getInt(Constants.VenueClickParams.VENUE_CLICKED_NUMBER);
    else
      venueNumber = getIntent().getIntExtra(Constants.VenueClickParams.VENUE_CLICKED_NUMBER, -1);

    if (venueNumber < 0) {
      Log.e(LOG_TAG, "INCORRECT venue number got through intent parameter - finish activity");
      finish();
    }

    GlobalStorage globalStorage = (GlobalStorage)getApplicationContext();
    mVHelper = globalStorage.getVHelper();

    tvName = (TextView) findViewById(R.id.tvDetailName);
    tvCategory = (TextView) findViewById(R.id.tvDetailCategory);
    tvAddressCity = (TextView) findViewById(R.id.tvDetailAddressCity);
    tvAddress = (TextView) findViewById(R.id.tvDetailAddress);

    try {
      venue = mVHelper.getVenue(venueNumber);
    } catch (IndexOutOfBoundsException ex) {
      Log.e(LOG_TAG, "OUTOFBOUNDS venue number got through intent parameter - finish activity");
      finish();
    }

    tvName.setText(venue.getDetails().getName());
    tvCategory.setText(venue.getDetails().getPrimCategoryName());
    tvAddressCity.setText(venue.getDetails().getCity());
    tvAddress.setText(venue.getDetails().getAddress());
  }


  @Override
  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putInt(Constants.VenueClickParams.VENUE_CLICKED_NUMBER, venueNumber);
    Log.d(LOG_TAG, "onSaveInstanceState");
  }

  @Override
  protected void onRestoreInstanceState(Bundle savedInstanceState) {
    super.onRestoreInstanceState(savedInstanceState);
    venueNumber = savedInstanceState.getInt(Constants.VenueClickParams.VENUE_CLICKED_NUMBER);
    Log.d(LOG_TAG, "onRestoreInstanceState");
  }
}
