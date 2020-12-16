package com.example.apheermvp.ui.profile;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.apheermvp.R;
import com.example.apheermvp.Sign_In_Activity;
import com.example.apheermvp.adapters.FriendListAdapter;
import com.example.apheermvp.adapters.LocationListAdapter;
import com.example.apheermvp.models.FormerLocation;
import com.example.apheermvp.models.Friend;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

public class ProfileFragment extends Fragment {

    private ProfileViewModel mProfileViewModel;
    private Button signOutButton;
    private FirebaseAuth mAuth;
    private RecyclerView mFriendsRecylcerView;
    private RecyclerView mLocationsRecyclerView;
    private TextView userNameTextView;
    private TextView your_current_location;

    //adapters for our ViewModels
    private FriendListAdapter mFriendListAdapter;
    private LocationListAdapter mLocationListAdapter;
    Context context;

    //firebase storage
    private StorageReference mStorageRef;
    private CircleImageView mProfile_picture;
    private Uri imageUri;
    private FirebaseStorage mStorage;
    private StorageReference mStorageReference;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        context = container.getContext();
        mStorage = FirebaseStorage.getInstance();
        mStorageReference = mStorage.getReference();


        mProfileViewModel =
                ViewModelProviders.of(this).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        userNameTextView = root.findViewById(R.id.you_apheer_in);
        your_current_location = root.findViewById(R.id.you_apheer_in);
        mFriendsRecylcerView = root.findViewById(R.id.friends_recycler_view);
        mLocationsRecyclerView = root.findViewById(R.id.locations_recycler_view);
        mProfile_picture = root.findViewById(R.id.profile_picture);

        signOutButton = (Button) root.findViewById(R.id.sign_out_button);
        signOutButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent intent = new Intent(v.getContext(), Sign_In_Activity.class);
                v.getContext().startActivity(intent);
                getActivity().finish();


            }


        });

        //onclick listener for profile pic
        mProfile_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setNewProfilePicture();
            }
        });

        //link up username to DB username (from viewmodel)
        mProfileViewModel.getUserNameText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                userNameTextView.setText(s);
            }
        });


        //link up user location to DB user location (from viewmodel)
        mProfileViewModel.getUserLocation().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                your_current_location.setText(s);
            }
        });

        initRecylcerViews(context);
        subscribeObservers();
        getProfilePicture(root);

        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == RESULT_OK && data.getData()!=null){
            imageUri = data.getData();
            mProfile_picture.setImageURI(imageUri);
            uploadPicture();

        }
    }

    private void uploadPicture() {
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("uploading image...");
        progressDialog.show();
        StorageReference profilePictures = mStorageReference.child("profilepictures/" + mProfileViewModel.getUid());

        profilePictures.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        progressDialog.dismiss();
                        // Get a URL to the uploaded content
                        //Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        Toast.makeText(context, "Profile picture added", Toast.LENGTH_LONG).show();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        progressDialog.dismiss();
                        Toast.makeText(context, "Failed to upload", Toast.LENGTH_LONG).show();

                        // Handle unsuccessful uploads
                        // ...
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        double progressSnapshot = (100.00 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                        int progressSnapshotInt = (int)progressSnapshot;
                        progressDialog.setMessage("Progress " + progressSnapshotInt + "%");
                    }
                });
    }

    private void setNewProfilePicture() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);

    }

    public void subscribeObservers(){
        mProfileViewModel.getFriends().observe(getViewLifecycleOwner(), new Observer<List<Friend>>() {
            @Override
            public void onChanged(List<Friend> friends) {
                if(friends!=null){
                    mFriendListAdapter.setFriends(friends);
                }
            }
        });
        mProfileViewModel.getFormerLocation().observe(getViewLifecycleOwner(), new Observer<List<FormerLocation>>() {
            @Override
            public void onChanged(List<FormerLocation> formerLocations) {
                if(formerLocations!=null){
                    mLocationListAdapter.setFormerLocations(formerLocations);
                }

            }
        });
    }


    public void getProfilePicture(View root){
        StorageReference profilePictures = mStorageReference.child("profilepictures/" + mProfileViewModel.getUid());
        // Reference to an image file in Cloud Storage
        //StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        //StorageReference storageReference = FirebaseStorage.getInstance().getReference();
       // StorageReference profilePictures = storageReference.child("profilepictures/" + mProfileViewModel.getUid());
        // ImageView in your Activity
        ImageView imageView = root.findViewById(R.id.profile_picture);

        RequestOptions requestOptions = new RequestOptions()
                //TODO replace the placeholder image with a feedme logo.
                .placeholder(R.drawable.ic_launcher_background);

        // Download directly from StorageReference using Glide
        // (See MyAppGlideModule for Loader registration)
        Glide.with(this /* context */)
                .setDefaultRequestOptions(requestOptions)
                .load(profilePictures)
                .into(mProfile_picture);
    }


    //set up recyclerViews
    private void initRecylcerViews(Context context){
        //TODO add "this" into the constructors for new FriendListAdapter
        // and new LocationListAdapter if you want onclick events
        mFriendListAdapter = new FriendListAdapter();
        mFriendsRecylcerView.setAdapter(mFriendListAdapter);
        mFriendsRecylcerView.setLayoutManager(new LinearLayoutManager(context));

        mLocationListAdapter = new LocationListAdapter();
        mLocationsRecyclerView.setAdapter(mLocationListAdapter);
        mLocationsRecyclerView.setLayoutManager(new LinearLayoutManager(context));
    }

}