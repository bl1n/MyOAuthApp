package com.lft.myoauthapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static String OAUTH_URL = "https://github.com/login/oauth/authorize";
    public static String OAUTH_ACCESS_TOKEN_URL = "https://github.com/login/oauth/access_token";

    public String clientId = "056d85b6294522af7e7f";
    public String clientSecret = "823933e162ede32d1856fb654de93b2ed634d09d";
    private WebView mWebView;
    private SharedPreferences.Editor mSharedPrefEditor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mWebView = findViewById(R.id.web_view);
        mWebView.getSettings().setJavaScriptEnabled(true);

        String url = OAUTH_URL + "?client_id=" + clientId + "&scope=repo";
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                String accessTokenFragment = "access_token=";
                String accessCodeFragment = "code=";

                if (url.contains(accessTokenFragment)) {
                    String accessToken = url.substring(url.indexOf(accessTokenFragment));
                    Log.d("debug", "onPageStarted:1 " + accessToken);
                } else if (url.contains(accessCodeFragment)) {
                    String accessCode = url.substring(url.indexOf(accessCodeFragment));
                    Log.d("debug", "onPageStarted:2 " + accessCode);
                    String query = "client_id=" + clientId
                            + "&client_secret=" + clientSecret
                            + "&code=" + accessCode;
                    view.postUrl(OAUTH_ACCESS_TOKEN_URL, query.getBytes());
                    mSharedPrefEditor = getSharedPreferences("my_pref",MODE_PRIVATE).edit();
                    mSharedPrefEditor.putString("accessCode", accessCode);
                    mSharedPrefEditor.commit();
                    startActivity(new Intent(MainActivity.this, RepoActivity.class));
                }
            }
        });
        mWebView.loadUrl(url);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
