package com.example.ol.venuelocator;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.ol.venuelocator.venues.VenuesHelper;

@SuppressWarnings("deprecation")

/**
 * A fragment representing a list of Venues found.
 */
public class VenuesListFragment extends Fragment implements Logic.onPlacesRefreshHeadersProcessor {
  //for logging
  private static final String LOG_TAG = VenuesListFragment.class.getName();

  public interface onVenueClickListener {
    void venueClick(int position, boolean isDetailClick);
  }

  private MainActivity mActivity;
  private VenuesListAdapter mVenuesListAdapter = null;
  private ListView mLVVenuesList = null;

  private VenuesHelper mVHelper;

  private onVenueClickListener mListener;
  private int mVenueClickedNumber = -1;
  private boolean mIsVenueClicked4Details = false;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Log.i(LOG_TAG, "onCreate()");

    if (savedInstanceState != null) {
      mVenueClickedNumber = savedInstanceState.getInt(Constants.VenueClickParams.VENUE_CLICKED_NUMBER);
    }

    mActivity = (MainActivity) getActivity();
    try {
      mListener = mActivity;
    } catch (ClassCastException ex) {
      throw new ClassCastException(mActivity.toString() + " must implement onVenueClickListener");
    }

    ///get access to globally stored venues list
    GlobalStorage globalStorage = (GlobalStorage)mActivity.getApplicationContext();
    mVHelper = globalStorage.getVHelper();
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    Log.i(LOG_TAG, "onCreateView()");

    View rootView = inflater.inflate(R.layout.fragment_venueslist, container, false);
    mLVVenuesList = (ListView) rootView.findViewById(R.id.lvVenuesList);

    mVenuesListAdapter = new VenuesListAdapter(mActivity, mVHelper.getVenueList(), mActivity);
    mLVVenuesList.setAdapter(mVenuesListAdapter);

    //restore list position
    if (mVenueClickedNumber == -1)
      mLVVenuesList.setSelection(0);
    else
      mLVVenuesList.setSelection(mVenueClickedNumber);

    mLVVenuesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d(LOG_TAG, "+++++List.ItemClick: position[" + position + "], object = ITEM");
        mVenueClickedNumber = position;
        if(mListener != null)
          mListener.venueClick(position, false); /// it's simple click on the venue to select it on the map
      }
    });
    return rootView;
  }

  @Override
  public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putInt(Constants.VenueClickParams.VENUE_CLICKED_NUMBER, mVenueClickedNumber);
  }

  @Override
  public void placesRefreshHeaders() {
    Log.i(LOG_TAG, "placesRefreshHeaders()");

    if (null != mVenuesListAdapter)
      mVenuesListAdapter.notifyDataSetChanged();
  }
}
