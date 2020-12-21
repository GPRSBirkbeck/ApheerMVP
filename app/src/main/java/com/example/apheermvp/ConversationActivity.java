package com.example.apheermvp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.apheermvp.adapters.ConversationAdapter;
import com.example.apheermvp.adapters.OnPictureListener;
import com.example.apheermvp.models.Conversation;
import com.example.apheermvp.ui.tribechat.TribeChatFragment;

import java.util.List;

public class ConversationActivity extends AppCompatActivity implements OnPictureListener {
    TextView mFriend_name_text_view;
    private ConversationViewModel mConversationViewModel;
    private RecyclerView mConversationRecylcerView;
    private ConversationAdapter mConversationAdapter;
    private EditText text_input_editText;
    private ImageButton sendMessageButton;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        mConversationViewModel =
                ViewModelProviders.of(this).get(ConversationViewModel.class);
        Intent intent = getIntent();
        final String mFriendName = intent.getStringExtra(TribeChatFragment.EXTRA_MESSAGE);
        mFriend_name_text_view = findViewById(R.id.textView_friend_name);
        mFriend_name_text_view.setText("Your conversation with "  + mFriendName);
        mConversationRecylcerView = findViewById(R.id.active_conversation_recyclerview);
        text_input_editText = (EditText) findViewById(R.id.chat_input_editText);
        sendMessageButton = (ImageButton) findViewById(R.id.send_message);

        initRecyclerView();
        subscribeObservers();


        //set an onclick listener to the imagebutton
        sendMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO do something


            }
        });
    }

    private void subscribeObservers() {
        mConversationViewModel.getMessages().observe(this, new Observer<List<Conversation>>() {
            @Override
            public void onChanged(List<Conversation> conversations) {
                if(conversations!=null){
                    mConversationAdapter.setMessages(conversations);

                }
            }
        });
    }

    private void initRecyclerView() {
        mConversationAdapter = new ConversationAdapter(this);
        mConversationRecylcerView.setAdapter(mConversationAdapter);
        mConversationRecylcerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onFriendPictureClick(int position) {

    }

    @Override
    public void onFormerLocationClick(int position) {

    }
}