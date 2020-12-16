package com.example.apheermvp.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.apheermvp.R;
import com.example.apheermvp.models.MapClusterMarker;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.google.maps.android.ui.IconGenerator;

public class MyClusterMarketManagerRenderer extends DefaultClusterRenderer<MapClusterMarker> {
    private final IconGenerator iconGenerator;
    private final ImageView imageView;
    private final int markerWidth;
    private final int markerHeight;
    private Context mContext;

    private FirebaseAuth mAuth;


    public MyClusterMarketManagerRenderer(Context context, GoogleMap map,
                                          ClusterManager<MapClusterMarker> clusterManager
                                         ) {
        super(context, map, clusterManager);

        //initialise all the variables.
        iconGenerator = new IconGenerator(context.getApplicationContext());
        imageView = new ImageView(context.getApplicationContext());
        markerWidth = (int) context.getResources().getDimension(R.dimen.custom_marker_image);
        markerHeight = (int) context.getResources().getDimension(R.dimen.custom_marker_image);

        imageView.setLayoutParams(new ViewGroup.LayoutParams(markerWidth, markerHeight));
        int padding = (int) context.getResources().getDimension(R.dimen.custom_marker_padding);
        imageView.setPadding(padding,padding,padding,padding);
        iconGenerator.setContentView(imageView);
        mContext = context;
    }

    @Override
    protected void onBeforeClusterItemRendered(@NonNull MapClusterMarker item, @NonNull MarkerOptions markerOptions) {

        RequestOptions requestOptions = new RequestOptions()
                //TODO replace the placeholder image with a feedme logo.
                .placeholder(R.drawable.ic_launcher_background);
        //TODO make this run smoother as currently you need to tab in and out of activity for it to work
        Glide.with(mContext /* context */)
                .setDefaultRequestOptions(requestOptions)
                .load(item.getProfilePictures())
                .into(imageView);
        //imageView.setImageResource(item.getIconPicture());
        Bitmap icon = iconGenerator.makeIcon();
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon)).title(item.getTitle());
    }

    @Override
    protected boolean shouldRenderAsCluster(@NonNull Cluster<MapClusterMarker> cluster) {
        return false;
        //this means clustering will never occur, make true if I want to return clusters.
    }
}
