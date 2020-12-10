package com.example.apheermvp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apheermvp.R;
import com.example.apheermvp.models.FormerLocation;

import java.util.ArrayList;
import java.util.List;

public class LocationListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<FormerLocation> mFormerLocations = new ArrayList<>();
    //TODO if we want an onclick for friends, need to create an interface for the below onclick interface
/*    //private OnFriendListener onFriendListener;

    public LocationsListAdapter(OnFriendListener onFriendListener) {
        this.onFriendListener = onFriendListener;
    }*/

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_former_locations_listitem, parent, false);
        return new FormerLocationsViewholder(view);
        //uncomment and use below is you need an onclick
        //return new RatesViewholder(view, onLocationListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((FormerLocationsViewholder)holder).former_location_name.setText(mFormerLocations.get(position).getFormer_city_name());
        ((FormerLocationsViewholder)holder).Dates_in_former_city.setText(mFormerLocations.get(position).getDates_in_former_city());
        ((FormerLocationsViewholder)holder).city_image.setImageResource(mFormerLocations.get(position).getCityImage());
    }

    @Override
    public int getItemCount() {
        if(mFormerLocations != null){
            return mFormerLocations.size();
        }
        return 0;
    }
    public void setFormerLocations(List<FormerLocation> formerLocations){
        mFormerLocations = formerLocations;
        notifyDataSetChanged();
    }
}
