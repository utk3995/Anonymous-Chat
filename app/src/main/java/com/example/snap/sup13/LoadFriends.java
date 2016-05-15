package com.example.snap.sup13;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class LoadFriends extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_friends);


        List<String> fids = new ArrayList<>();

        final ParseUser currentUser = ParseUser.getCurrentUser();
        fids = currentUser.getList("friends");
        ListAdapter buckysAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, fids);
        ListView buckysListView = (ListView) findViewById(R.id.liststatid);
        buckysListView.setAdapter(buckysAdapter);

        buckysListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String receiver = String.valueOf(parent.getItemAtPosition(position));
                        Toast.makeText(LoadFriends.this, receiver, Toast.LENGTH_LONG).show();

                        Intent i = new Intent(LoadFriends.this, ChatScreen.class);
                        i.putExtra("Sender",currentUser.getUsername());
                        i.putExtra("Receiver",receiver);
                        startActivity(i);
                    }
                }
        );
     }

}

