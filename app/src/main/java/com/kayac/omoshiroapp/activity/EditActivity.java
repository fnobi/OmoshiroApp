package com.kayac.omoshiroapp.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.kayac.omoshiroapp.R;

import java.io.File;

public class EditActivity extends AppCompatActivity {

    File mImageFile = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        FrameLayout photoFrame = (FrameLayout) findViewById(R.id.attachment_photo_frame);
        photoFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File storageDir = getExternalFilesDir(null);
                mImageFile = new File(storageDir, "book.jpg");
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mImageFile));
                startActivityForResult(intent, 1192);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Bitmap bitmap = data.getParcelableExtra("data");
        Bitmap bitmap = BitmapFactory.decodeFile(mImageFile.getAbsolutePath());
        ImageView photoThumbnail = (ImageView) findViewById(R.id.attachment_photo_thumbnail);
        photoThumbnail.setImageBitmap(bitmap);
    }
}
