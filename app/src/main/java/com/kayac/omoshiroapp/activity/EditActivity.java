package com.kayac.omoshiroapp.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.kayac.omoshiroapp.R;

import java.io.File;

public class EditActivity extends AppCompatActivity {

    public static final String EXTRA_KEY = "EXTRA_KEY";

    private File mTmpImageFile = null;

    private SharedPreferences mPref;
    private String mKey;
    // 画面に表示するImageFile
    private File mDisplayingImageFile = null;
    private TextView mTitleEditText;
    private TextView mContentEditText;
    private Spinner mCategorySpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        mPref = getSharedPreferences(MainActivity.PREFERENCES_NAME, MODE_PRIVATE);

        mTitleEditText = (TextView) findViewById(R.id.title_edit_text);
        mContentEditText = (TextView) findViewById(R.id.content_edit_text);

        // スピナーの設定
        mCategorySpinner = (Spinner) findViewById(R.id.category_spinner);
        // ListViewのようにAdapterをセットする必要がある。
        // values/arrays.xml にあらかじめリストを定義しておくと、
        // ArrayAdapter.createFromResourceでArrayAdapterがつくれちゃう
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.category_array, android.R.layout.simple_spinner_dropdown_item);
        mCategorySpinner.setAdapter(spinnerAdapter);


        // 撮影した画像を取得するための一時的なファイル
        mTmpImageFile = new File(getExternalFilesDir(null), "tmp.jpg");
        // アプリが終了するときに一緒に削除するようにする
        mTmpImageFile.deleteOnExit();

        FrameLayout photoFrame = (FrameLayout) findViewById(R.id.attachment_photo_frame);
        photoFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mTmpImageFile));
                startActivityForResult(intent, 1192);
            }
        });

        Intent intent = getIntent();
        mKey = intent.getStringExtra(EXTRA_KEY);
        String value = mPref.getString(mKey, "");

        if (TextUtils.isEmpty(value)) {  // （value が "" か null のとき true）
            // SharedPreferencesには指定されたキーのデータはないため、新しい日記を追加する
            // 画像ファイルは一時的な名前にしておく
            mDisplayingImageFile = mTmpImageFile;
        } else {
            // EXTRAS_KEYがIntentに渡されているので、その日の日記を表示する
            String[] elements = TextUtils.split(value, ",");
            mTitleEditText.setText(elements[0]);
            mContentEditText.setText(elements[1]);
            mCategorySpinner.setSelection(spinnerAdapter.getPosition(elements[2]));
            mDisplayingImageFile = new File(getExternalFilesDir(null), "img_" + mKey + ".jpg");
        }

        updatePhotoThumbnail();
    }

    private void updatePhotoThumbnail() {
        Bitmap bitmap = BitmapFactory.decodeFile(mDisplayingImageFile.getAbsolutePath());
        ImageView photoThumbnail = (ImageView) findViewById(R.id.attachment_photo_thumbnail);
        photoThumbnail.setImageBitmap(bitmap);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // 撮影した写真ファイルを表示する
        mDisplayingImageFile = mTmpImageFile;
        updatePhotoThumbnail();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_delete) {
            // 日記をSharedPreferencesから削除
            mPref.edit().remove(mKey).commit();
            // 画像も削除
            mDisplayingImageFile.delete();

            // 画面をとじる
            finish();
            return true;
        } else if (id == R.id.action_create) {
            String titleText = mTitleEditText.getText().toString();
            String contentText = mContentEditText.getText().toString();
            String categoryText = (String) mCategorySpinner.getSelectedItem();

            mPref.edit()
            .putString(mKey, titleText + "," + contentText + "," + categoryText)
            .commit();

            // 画像の名前をキーを使ったものに変更
            mDisplayingImageFile.renameTo(new File(getExternalFilesDir(null), "img_" + mKey + ".jpg"));

            // 画面をとじる
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
