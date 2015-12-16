package com.kayac.omoshiroapp.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.kayac.omoshiroapp.R;

public class Lecture5Activity extends AppCompatActivity {

    SharedPreferences mSharedPreference;
    AppCompatEditText mEditText;

    private static final String SHARED_PREFERENCE_NAME = "hoge_shared_preference";
    private static final String KEY_SAVE_TEXT = "key_save_text";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        mSharedPreference = getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mEditText = (AppCompatEditText) findViewById(R.id.hoge_edit_text);

        AppCompatButton saveButton = (AppCompatButton)findViewById(R.id.hoge_save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveText();
            }
        });
        AppCompatButton restoreButton = (AppCompatButton)findViewById(R.id.hoge_restore_button);
        restoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restoreText();
            }
        });
        AppCompatButton clearButton = (AppCompatButton)findViewById(R.id.hoge_clear_button);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearText();
            }
        });
    }

    /**
     * editTextに入力された文字をSharedPreferenceに保存します
     */
    private void saveText() {
        mSharedPreference.edit().putString(KEY_SAVE_TEXT, mEditText.getText().toString()).apply();
    }

    /**
     * SharedPreferenceから文字を復元します
     */
    private void restoreText() {
        mEditText.setText(mSharedPreference.getString(KEY_SAVE_TEXT, ""));
    }

    /**
     * SharedPreferenceとEditTextから文字を削除します
     */
    private void clearText() {
        mSharedPreference.edit().remove(KEY_SAVE_TEXT).apply();
        restoreText();
    }

}
