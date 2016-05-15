package com.example.snap.sup13;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class Status extends AppCompatActivity {

    EditText stat;
    Button butt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        stat = (EditText) findViewById(R.id.statid);
        butt = (Button) findViewById(R.id.setid);

        List<String> fids = new ArrayList<>();
        final List<String> fids1 = new ArrayList<>();

        final ParseUser currentUser = ParseUser.getCurrentUser();
        stat.setText(currentUser.get("status").toString());

        fids = currentUser.getList("prevstats");
        fids.add("");

        ListAdapter buckysAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, fids);
        ListView buckysListView = (ListView) findViewById(R.id.liststatid);
        buckysListView.setAdapter(buckysAdapter);


        //fids1.addAll(fids);
        /*buckysListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        String stat = fids1.get(position).toString();
                        ParseUser currentuser = ParseUser.getCurrentUser();
                        currentuser.put("status", stat);
                        Toast.makeText(getApplicationContext(), "Changed! ", Toast.LENGTH_LONG).show();

                        Intent i = new Intent(Status.this, Welcome.class);
                        startActivity(i);
                    }
                }
        );
        */
    }

    public void setclick(View view){

        String currstat = stat.toString();
        ParseUser currentuser = ParseUser.getCurrentUser();
        currentuser.addUnique("prevstats", currstat);
        currentuser.put("status", currstat);
        stat.setText("");
        Toast.makeText(getApplicationContext(), "Changed! ", Toast.LENGTH_LONG).show();
        Intent i = new Intent(Status.this, Welcome.class);
        startActivity(i);

    }

}
