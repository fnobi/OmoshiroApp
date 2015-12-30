package com.kayac.omoshiroapp.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kayac.omoshiroapp.R;

import java.io.File;

public class DetailActivity extends AppCompatActivity {

    // 定数にしておくと打ち間違える心配がないよ
    public static final String EXTRA_KEY = "EXTRA_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // これでToolBarに戻るボタン（左矢印）がでるのだ
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // 戻るボタンをおしたら画面をとじる
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.i("aaa", "onResume");
        final String key = getIntent().getStringExtra(EXTRA_KEY);

        SharedPreferences pref = getSharedPreferences(MainActivity.PREFERENCES_NAME, MODE_PRIVATE);
        String value = pref.getString(key, "");

        if (TextUtils.isEmpty(value)) {
            // SharedPreferencesに保存されていない
            // 編集画面から削除された？

            // とじる
            finish();

            // この関数に書いてある、ここから下の処理をしない
            return;
        }

        String[] elements = TextUtils.split(value, ",");
        String title = elements[0];
        String content = elements[1];
        String category = elements[2];

        // setTitle()をつかうと、一度表示したタイトルの変更ができないため、
        // CollapsingToolbarLayoutを取得してtitleを表示している
        CollapsingToolbarLayout toolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolbarLayout.setTitle(title);

        // 中身を表示

        TextView categoryTextView = (TextView) findViewById(R.id.category_text_view);
        categoryTextView.setText(category);

        TextView contentTextView = (TextView) findViewById(R.id.content_text_view);
        contentTextView.setText(content);

        TextView createdDateTextView = (TextView) findViewById(R.id.created_date_text_view);
        String[] dateElements = TextUtils.split(key, "_");
        createdDateTextView.setText(dateElements[0] + "年" + dateElements[1] + "月" + dateElements[2] + "日");

        File imageFile = new File(getExternalFilesDir(null), "img_" + key + ".jpg");
        ImageView photoImageView = (ImageView) findViewById(R.id.photo_image_view);
        Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
        photoImageView.setImageBitmap(bitmap);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailActivity.this, EditActivity.class);
                intent.putExtra(EditActivity.EXTRA_KEY, key);
                startActivity(intent);
            }
        });
    }
}
