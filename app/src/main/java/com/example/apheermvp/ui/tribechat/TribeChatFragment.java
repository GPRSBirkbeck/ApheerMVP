package com.example.apheermvp.ui.tribechat;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apheermvp.conversation.ConversationActivity;
import com.example.apheermvp.R;
import com.example.apheermvp.adapters.FriendListAdapter;
import com.example.apheermvp.adapters.OnPictureListener;
import com.example.apheermvp.models.Friend;

import java.util.List;

public class TribeChatFragment extends Fragment implements OnPictureListener {

    public static final String EXTRA_MESSAGE = "com.example.apheermvp.EXTRA_MESSAGE";
    private TribeChatViewModel tribeChatViewModel;
    EditText friendEmailEditText;
    Button inviteButton;
    private RecyclerView mFriendChatsRecylcerView;
    private FriendListAdapter mFriendChatListAdapter;

    Context context;
    private Friend mFriend;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        context = container.getContext();


        tribeChatViewModel =
                ViewModelProviders.of(this).get(TribeChatViewModel.class);
        View root = inflater.inflate(R.layout.fragment_tribe_chat, container, false);
        friendEmailEditText = root.findViewById(R.id.edit_text_friend_email);
        inviteButton = root.findViewById(R.id.invite_button);
        mFriendChatsRecylcerView = root.findViewById(R.id.your_conversations_recyclerview);
        initRecyclerView(context);
        subscribeObservers();


        //invite the person by email
        inviteButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String email = friendEmailEditText.getText().toString();
                if(email.contains(" "))

                if(!email.equals("")){
                    sendInviteEmail();

                }
                else if (email.equals("")){
                    //TODO find an alternative to toasting
                    //toastMessage("you didn't enter your email");
                }
            }
        });

        return root;
    }

    private void subscribeObservers() {
        tribeChatViewModel.getFriends().observe(getViewLifecycleOwner(), new Observer<List<Friend>>() {
            @Override
            public void onChanged(List<Friend> friends) {
                if(friends!=null){
                    mFriendChatListAdapter.setFriends(friends);
                }
            }
        });
    }

    private void initRecyclerView(Context context) {
        mFriendChatListAdapter = new FriendListAdapter(this);
        mFriendChatsRecylcerView.setAdapter(mFriendChatListAdapter);
        mFriendChatsRecylcerView.setLayoutManager(new LinearLayoutManager(context));


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

    @Override
    public void onFriendPictureClick(int position) {
        mFriend = tribeChatViewModel.getClickedFriend(position);
        Intent intent = new Intent(context, ConversationActivity.class);
        String email = mFriend.getFriendName();
        intent.putExtra(EXTRA_MESSAGE, email);
        startActivity(intent);


    }

    @Override
    public void onFormerLocationClick(int position) {

    }

/*    public void toastMessage(String toastString) {
        Toast.makeText(this, toastString, Toast.LENGTH_SHORT).show();
    }*/

}