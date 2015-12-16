package com.kayac.omoshiroapp.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

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
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.indexOf("kayac.com") >= 0) {
                    // KAYACのURLだったら、web view内で開く
                    return false;
                } else {
                    // それ以外なら、外部ブラウザを使う
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                    return true;
                }
            }
        });
        webView.loadUrl(urlString);
    }
}
