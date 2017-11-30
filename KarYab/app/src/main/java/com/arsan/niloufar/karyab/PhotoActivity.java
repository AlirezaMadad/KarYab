package com.arsan.niloufar.karyab;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

public class PhotoActivity extends AppCompatActivity {
    Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FromCard();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHandler db = new DBHandler(PhotoActivity.this);
                IDS humanID = db.getIDbyName(IDNames.humanID);
                //IMAGE image = new IMAGE();
                //image.IMAGE1 = ImageStatic.encodeTobase64(bitmap);
                //image.HUMANID = humanID.getData();
                ImageForTransfer image = new ImageForTransfer();
                image.Bitmap = ImageStatic.encodeTobase64(bitmap);
                //String temp = UrlStatic.UrlOfSetImageApi+"?HumanID="+humanID.getData()+"&Image="+ImageStatic.encodeTobase64(bitmap);
                long ID = HttpMadad.postObjectAndGetID(image, UrlStatic.UrlOfSetImageApi+"?HumanID="+humanID.getData());//+"&Image="+ImageStatic.encodeTobase64(bitmap));
                IDS.storeInDB(IDNames.imageID, ID, PhotoActivity.this);

                //Toast.makeText(PhotoActivity.this, "HumanID: " + humanID.getData() + " ID: " + ID, Toast.LENGTH_LONG).show();
                Toast.makeText(PhotoActivity.this, "ثبت شد", Toast.LENGTH_LONG).show();
            }
        });
    }
    String path = "src/main/assets/";
    public void FromCamera() {

        Log.i("camera", "startCameraActivity()");
        File file = new File(path);
        Uri outputFileUri = Uri.fromFile(file);
        Intent intent = new Intent(
                android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
        startActivityForResult(intent, 1);

    }

    public void FromCard() {
        Intent i = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, 2);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2 && resultCode == RESULT_OK
                && null != data) {

            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            bitmap = BitmapFactory.decodeFile(picturePath);
            ImageView image = (ImageView) findViewById(R.id.imageView2);
            image.setImageBitmap(bitmap);


            if (bitmap != null) {
                //ImageView rotate = (ImageView) findViewById(R.id.rotate);

            }

        } else {

            Log.i("SonaSys", "resultCode: " + resultCode);
            switch (resultCode) {
                case 0:
                    Log.i("SonaSys", "User cancelled");
                    break;
                case -1:
                    onPhotoTaken();
                    break;

            }

        }

    }

    protected void onPhotoTaken() {
        // Log message
        Log.i("SonaSys", "onPhotoTaken");
        //taken = true;
        //imgCapFlag = true;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 4;
        Bitmap bitmap;
        bitmap = BitmapFactory.decodeFile(path, options);
        ImageView image = (ImageView) findViewById(R.id.imageView2);
        image.setImageBitmap(bitmap);


    }

}
