package com.lft.myoauthapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    public String clientId = "056d85b6294522af7e7f";
    public String clientSecret = "823933e162ede32d1856fb654de93b2ed634d09d";
    public String redirectUrl = "lf.team/callback";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("https://github.com/login/oauth/authorize"
                + "?client_id="+ clientId
                + "&scope=repo&redirect_url="+ redirectUrl));
        startActivity(intent);

    }
}
