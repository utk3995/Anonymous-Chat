package com.example.snap.sup13;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.*;

import java.util.ArrayList;
import java.util.List;

public class Welcome extends AppCompatActivity {

    Button logout,fadd;
    EditText name_friend;
    ParseUser currentUser;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        name_friend = (EditText) findViewById(R.id.add_text);
        name_friend.setVisibility(View.INVISIBLE);

        fadd = (Button) findViewById(R.id.fadd_button);
        fadd.setVisibility(View.INVISIBLE);

        currentUser = ParseUser.getCurrentUser();
        String struser = currentUser.getUsername().toString();
        TextView txtuser = (TextView) findViewById(R.id.txtuser);
        txtuser.setText("You are logged in as " + struser);
        logout = (Button) findViewById(R.id.logout);
    }
    public void onclickadd(View view){
        fadd.setVisibility(View.VISIBLE);
        name_friend.setVisibility(View.VISIBLE);
    }

    public void onclickfadd(View view) {
        final String name = name_friend.getText().toString();
        currentUser = ParseUser.getCurrentUser();

        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereEqualTo("username", name);
        query.findInBackground(new FindCallback<ParseUser>() {
            public void done(List<ParseUser> objects, ParseException e) {
                if (!objects.isEmpty()) {
                    String stat = objects.get(0).get("status").toString();
                    currentUser.addUnique("friends", name+"\n"+stat);
                    currentUser.saveInBackground();
                    Toast.makeText(getApplicationContext(), "Added! " + name, Toast.LENGTH_LONG).show();
                } else {
                    // Something went wrong.
                    Toast.makeText(getApplicationContext(), "Failed to add.", Toast.LENGTH_LONG).show();
                }
                fadd.setVisibility(View.INVISIBLE);
                name_friend.setVisibility(View.INVISIBLE);
                name_friend.setText("");
            }
        });
    }

    public void onclick(View view) {
        ParseUser.logOut();
        ParseUser currentUser = ParseUser.getCurrentUser();
        finish();
        Intent i = new Intent(Welcome.this, MainActivity.class);
        startActivity(i);
    }

    public void loadfriends(View view){
        Intent i =  new Intent(Welcome.this, LoadFriends.class);
        startActivity(i);
    }

    public void statusonclick(View view){
        Intent i = new Intent(Welcome.this, Status.class);
        startActivity(i);
    }
    /*
    public void onclickstatusadd(View view){
        final String stat = statustext.getText().toString();
        currentUser = ParseUser.getCurrentUser();
        currentUser.put("status", stat);
        statustext.setVisibility(View.INVISIBLE);
        addbutt.setVisibility(View.INVISIBLE);
        if (currentUser.getString("status").equals(stat)) {
            Toast.makeText(getApplicationContext(), "Changed! ", Toast.LENGTH_LONG).show();
        } else {
            // Something went wrong.
            Toast.makeText(getApplicationContext(), "Failed to change.", Toast.LENGTH_LONG).show();
        }
    }
    */
}

