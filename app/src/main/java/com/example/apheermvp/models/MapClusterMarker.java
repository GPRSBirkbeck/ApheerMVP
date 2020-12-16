package com.example.apheermvp.models;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.firestore.auth.User;
import com.google.firebase.storage.StorageReference;
import com.google.maps.android.clustering.ClusterItem;

public class MapClusterMarker implements ClusterItem {
    private LatLng position;
    private String title;
    private String snippet;
    private int iconPicture;
    private User user;
    private StorageReference profilePictures;

    public MapClusterMarker(LatLng position, String title, String snippet, int iconPicture) {
        this.position = position;
        this.title = title;
        this.snippet = snippet;
        this.iconPicture = iconPicture;
    }

    public MapClusterMarker(LatLng position, String title, String snippet, StorageReference profilePictures) {
        this.position = position;
        this.title = title;
        this.snippet = snippet;
        this.profilePictures = profilePictures;
    }

    public int getIconPicture() {
        return iconPicture;
    }

    public User getUser() {
        return user;
    }

    @NonNull
    @Override
    public LatLng getPosition() {
        return position;
    }

    @Nullable
    @Override
    public String getTitle() {
        return title;
    }

    @Nullable
    @Override
    public String getSnippet() {
        return snippet;
    }

    public StorageReference getProfilePictures() {
        return profilePictures;
    }
}
