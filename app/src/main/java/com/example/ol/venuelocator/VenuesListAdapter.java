package com.example.ol.venuelocator;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.ol.venuelocator.model.Venue;

import java.util.List;

/**
 * Created by ol on 28.11.15.
 */
public class VenuesListAdapter extends ArrayAdapter<Venue> {
  //for logging
  private static final String LOG_TAG = VenuesListAdapter.class.getName();

  private final Context mContext;
  private final List<Venue> mVenues;
  private final VenuesListFragment.onVenueClickListener mListener;
  
  public VenuesListAdapter(Context context, List<Venue> venues,
                           VenuesListFragment.onVenueClickListener listener) {
    super(context, R.layout.venues_item, venues);
    this.mContext = context;
    this.mVenues = venues;
    this.mListener = listener;
  }

  static class ViewHolder{
    TextView tvVenueName;
    TextView tvVenueCategory;
    TextView tvVenueDistance;
    ImageButton ibtSwitch2Details;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {

    ViewHolder holder;
    View rowView = convertView;

    if (rowView == null) {
      LayoutInflater inflater = LayoutInflater.from(mContext);
      rowView = inflater.inflate(R.layout.venues_item, parent, false);
      holder = new ViewHolder();
      holder.tvVenueName = (TextView) rowView.findViewById(R.id.tvVenueName);
      holder.tvVenueCategory = (TextView) rowView.findViewById(R.id.tvVenueCategory);
      holder.tvVenueDistance = (TextView) rowView.findViewById(R.id.tvVenueDistance);
      holder.ibtSwitch2Details = (ImageButton) rowView.findViewById(R.id.btSwitch2Details);
      rowView.setTag(holder);
    } else {
      holder = (ViewHolder) rowView.getTag();
    }

    Venue.VenueHeader vHeader = mVenues.get(position).getHeader();

    holder.tvVenueName.setText(vHeader.getName());
    holder.tvVenueCategory.setText(vHeader.getPrimCategoryName());
    holder.tvVenueDistance.setText("(" + String.valueOf(vHeader.getDistance()) + ")");

    holder.ibtSwitch2Details.setTag(position);
    holder.ibtSwitch2Details.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Log.d(LOG_TAG, "+++++ImageButton.Click: position[" + v.getTag() + "], object = ImageButton");
        if(mListener != null)
          mListener.venueClick((Integer) v.getTag(), true);
      }
    });
/*
    rowView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(mListener != null)
          mListener.venueClick((Integer) v.getTag(), true);
      }
    });
*/
    return rowView;
  }
}


