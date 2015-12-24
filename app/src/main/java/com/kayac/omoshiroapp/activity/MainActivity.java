package com.kayac.omoshiroapp.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.kayac.omoshiroapp.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String PREFERENCES_NAME = "BOOK_LIST";

    private SharedPreferences mPref;
    private ArrayAdapter<String> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mPref = getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                String key = DateFormat.format(
                        "yyyy_MM_dd_HH_mm_ss",
                        Calendar.getInstance()
                ).toString();
                intent.putExtra(EditActivity.EXTRA_KEY, key);
                startActivity(intent);
            }
        });

        ArrayList<String> bookList = new ArrayList<>();
        ListView listView = (ListView) findViewById(R.id.diary_list_view);
        mAdapter = new BookListAdapter(this, R.layout.book_list_item, R.id.title_text_view, bookList);
        listView.setAdapter(mAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateBookList();
    }

    private void updateBookList() {
        ArrayList<String> bookList = new ArrayList<>(mPref.getAll().keySet());
        Collections.sort(bookList);
        Collections.reverse(bookList);

        mAdapter.clear();
        mAdapter.addAll(bookList);

        // リストの中身を更新を画面に反映する
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    class BookListAdapter extends ArrayAdapter<String> {

        public BookListAdapter(Context context, int resource, int textViewResourceId, List<String> objects) {
            super(context, resource, textViewResourceId, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = super.getView(position, convertView, parent);

            // SharedPreferencesからデータを取得
            final String key = getItem(position);
            String value = mPref.getString(key, "");
            String[] elements = TextUtils.split(value, ",");
            String title = elements[0];
            String content = elements[1];
            String category = elements[2];

            TextView titleTextView = (TextView) view.findViewById(R.id.title_text_view);
            TextView categoryTextView = (TextView) view.findViewById(R.id.category_text_view);
            TextView contentTextView = (TextView) view.findViewById(R.id.content_text_view);

            titleTextView.setText(title);
            contentTextView.setText(content);
            categoryTextView.setText(category);

            ImageView thumbnailView = (ImageView) view.findViewById(R.id.item_thumbnail);
            File imageFile = new File(getExternalFilesDir(null), "img_"+key+".jpg");
            if (imageFile.exists()) {
                // サムネイル用なので1/16サイズで読み込む
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 16;
                Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath(), options);
                thumbnailView.setImageBitmap(bitmap);
            } else {
                // 写真ファイルが存在しない
                // Viewが使いまわされているので、前に設定された画像を削除する
                thumbnailView.setImageBitmap(null);
            }

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                    intent.putExtra(DetailActivity.EXTRA_KEY, key);
                    startActivity(intent);
                }
            });

            return view;
        }
    }
}
