package com.example.snap.sup13;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

/**
 * Created by snap on 9/10/15.
 */
public class ParseAppilcation extends Application {

    public static final String YOUR_APPLICATION_ID = "zXgA4FDX16tr1X0M8GM1JjEHH3frVnHlhcdKw1If";
    public static final String YOUR_CLIENT_KEY = "RReRgETTJdc1YYZFODoRUhJjUBcBTMcUZedKbRVk";
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.enableLocalDatastore(this);
        ParseObject.registerSubclass(Message.class);
        Parse.initialize(this, YOUR_APPLICATION_ID, YOUR_CLIENT_KEY);
    }
}
