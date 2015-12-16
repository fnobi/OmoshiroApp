package com.kayac.omoshiroapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.kayac.omoshiroapp.R;

public class BookmarkActivity extends AppCompatActivity {
    
    private OnClickListener bookmarkButtonClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            Button button = (Button) v;
            String urlString = button.getText().toString();
            
            Intent intent = new Intent(BookmarkActivity.this, WebViewActivity.class);
            intent.putExtra(WebViewActivity.EXTRAS_URL, urlString);
            startActivity(intent);
        }
    };
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);

        findViewById(R.id.button1).setOnClickListener(bookmarkButtonClickListener);
        findViewById(R.id.button2).setOnClickListener(bookmarkButtonClickListener);
        findViewById(R.id.button3).setOnClickListener(bookmarkButtonClickListener);
    }
}
