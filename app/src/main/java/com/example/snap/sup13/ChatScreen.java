package com.example.snap.sup13;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

public class ChatScreen extends AppCompatActivity {

    public static final String USER_ID_KEY = "userId";
    private static final int MAX_CHAT_MESSAGES_TO_SHOW = 50;
    private static final String TAG = ChatScreen.class.getName();
    private static String uid;
    private static String UserID ;
    private EditText etMessage;
    private Button btSend;
    private ListView lvChat;
    private ArrayList<Message> mMessages;
    private ChatListAdapter mAdapter;
    // Keep track of the initial load
    private boolean mFirstLoad;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_screen);
        Intent i = getIntent();
        Bundle b = i.getExtras();

        String sender = b.getString("Sender");
        String receiver = b.getString("Receiver");

        if(sender.compareToIgnoreCase(receiver) <0)
            uid = sender + receiver;
        else
            uid = receiver + sender;

        setupMessagePosting();
        handler.postDelayed(runnable, 10);
    }


   //  Thread to refresh every 10 ms
    private Runnable runnable = new Runnable(){
        @Override
        public void run() {
            refreshMessages();
            handler.postDelayed(this,10);
        }
    };
    private void refreshMessages(){
        receiveMessage();
    }


    private void setupMessagePosting() {
        etMessage = (EditText) findViewById(R.id.etMessage);
        btSend = (Button) findViewById(R.id.btSend);
       lvChat = (ListView) findViewById(R.id.lvChat);
        mMessages = new ArrayList<Message>();

        // Automatically scroll to the bottom when a data set change notification is received and only if the last item is already visible on screen. Don't scroll to the bottom otherwise.
        lvChat.setTranscriptMode(1);
        mFirstLoad = true;
        mAdapter = new ChatListAdapter(ChatScreen.this, UserID, mMessages);
        lvChat.setAdapter(mAdapter);
        btSend.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String body = etMessage.getText().toString();

                Message message = new Message();
                message.setUserId(ParseUser.getCurrentUser().getObjectId());
                message.setBody(body);
                message.setuid(uid);
                message.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        refreshMessages();
                    }
                });
                etMessage.setText("");
            }
        });

    }

    // Query from parse and load into chat Adapter
    private void receiveMessage() {
        // Construct query to execute
        ParseQuery<Message> query = ParseQuery.getQuery(Message.class);
        // Configure limit and sort order
        query.setLimit(MAX_CHAT_MESSAGES_TO_SHOW);
        query.orderByAscending("createdAt");
        // Execute query to fetch all messages from Parse asynchronously
        // This is equivalent to a SELECT query with SQL

        query.whereEqualTo("uid",uid);
        query.findInBackground(new FindCallback<Message>() {
            public void done(List<Message> messages, ParseException e) {
                if (e == null) {
                    mMessages.clear();
                    mMessages.addAll(messages);
                    mAdapter.notifyDataSetChanged(); // update adapter
                    // Scroll to the bottom of the list on initial load
                    if (mFirstLoad) {
                        lvChat.setSelection(mAdapter.getCount() - 1);
                        mFirstLoad = false;
                    }
                } else {
                    Log.d("message", "Error: " + e.getMessage());
                }
            }
        });
    }

}