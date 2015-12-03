package com.kayac.omoshiroapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.kayac.omoshiroapp.R;

public class DetailActivity extends AppCompatActivity {

    // 定数にしておくと打ち間違える心配がないよ
    public static final String EXTRA_BOOK_ID = "EXTRA_BOOK_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // これでToolBarに戻るボタン（左矢印）がでるのだ
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String bookId = getIntent().getStringExtra(EXTRA_BOOK_ID);
        setTitle("これは本" + bookId + "だ");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
    }
}
