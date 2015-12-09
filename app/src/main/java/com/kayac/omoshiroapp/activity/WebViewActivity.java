package com.kayac.omoshiroapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import com.kayac.omoshiroapp.R;

public class WebViewActivity extends AppCompatActivity {
    
    public final static String EXTRAS_URL = "EXTRAS_URL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        
        Intent intent = getIntent();
        String urlString = intent.getStringExtra(EXTRAS_URL);
        
        WebView webView = (WebView) findViewById(R.id.web_view);
        webView.loadUrl(urlString);
    }
}
