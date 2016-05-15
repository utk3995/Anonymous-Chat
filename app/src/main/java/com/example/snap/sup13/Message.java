package com.example.snap.sup13;



import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("Message")
public class Message extends ParseObject {
    public String getUserId() { return getString("userId"); }

    public String getBody() { return getString("body"); }

    public void setUserId(String userId) {
        put("userId", userId);
    }

    public void setuid(String uid) { put("uid", uid); }

    public void setBody(String body) {
        put("body", body);
    }
}