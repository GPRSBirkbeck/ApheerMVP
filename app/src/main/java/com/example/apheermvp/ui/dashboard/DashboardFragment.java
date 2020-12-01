package com.example.apheermvp.ui.dashboard;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.apheermvp.R;

import java.util.Calendar;
import java.util.Locale;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    EditText friendEmailEditText;
    Button inviteButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        final TextView textView = root.findViewById(R.id.text_dashboard);
        friendEmailEditText = root.findViewById(R.id.edit_text_friend_email);
        inviteButton = root.findViewById(R.id.invite_button);
        //invite the person by email
        inviteButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String email = friendEmailEditText.getText().toString();

                if(!email.equals("")){
                    sendInviteEmail();

                }
                else if (email.equals("")){
                    //TODO find an alternative to toasting
                    //toastMessage("you didn't enter your email");
                }
            }
        });


        dashboardViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        return root;
    }

    public void sendInviteEmail() {
        String friendEmail = friendEmailEditText.getText().toString();
        //TODO replace with user displayname
        String inviterName = "Gregory";

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{
                friendEmail});
        intent.putExtra(Intent.EXTRA_SUBJECT, "A mysterious mApp has appeared in your inbox Sire");
        intent.putExtra(Intent.EXTRA_TEXT, "You have received an invitation from fellow high-seas scalleywag " + inviterName + ". Go to apheer.com/signup to join");
        startActivity(intent);

    }

/*    public void toastMessage(String toastString) {
        Toast.makeText(this, toastString, Toast.LENGTH_SHORT).show();
    }*/

}