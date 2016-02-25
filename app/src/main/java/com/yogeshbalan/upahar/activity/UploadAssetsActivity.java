package com.yogeshbalan.upahar.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.yogeshbalan.upahar.R;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class UploadAssetsActivity extends AppCompatActivity {

    private TextInputLayout description, address, phone, quantity;
    private Button getImageButton;
    static String[] imagePaths = new String[0];
    private ArrayList<ParseFile> parseFileList;
    private String DescriptionString, AddressString, PhoneString, QuantityString, usernameString;
    private ImageView imageView;
    private ProgressDialog progressDialog;
    private static final int RESULT_LOAD_IMAGE = 1;
    String picturePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_assets);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Retrieve current user from Parse.com
        ParseUser currentUser = ParseUser.getCurrentUser();

        // Convert currentUser into String
        usernameString = currentUser.getUsername().toString();

        imageView = (ImageView) findViewById(R.id.imageView_assets);
        description = (TextInputLayout) findViewById(R.id.descriptionWrapper);
        address = (TextInputLayout) findViewById(R.id.addressWrapper);
        phone = (TextInputLayout) findViewById(R.id.phoneWrapper);
        quantity = (TextInputLayout) findViewById(R.id.quantityWrapper);
        getImageButton = (Button) findViewById(R.id.upload_button);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                uploadThisShit();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        parseFileList = new ArrayList<>();

        getImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK
                && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            picturePath = cursor.getString(columnIndex);
            cursor.close();

            Log.v("test", "pic path = " + picturePath);

            //imageView = (ImageView) findViewById(R.id.imageView_assets);
            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
        }

    }

    private byte[] readInFile(String path) throws IOException {
        // TODO Auto-generated method stub
        byte[] data = null;
        File file = new File(path);
        InputStream input_stream = new BufferedInputStream(new FileInputStream(
                file));
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        data = new byte[16384]; // 16K
        int bytes_read;
        while ((bytes_read = input_stream.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, bytes_read);
        }
        input_stream.close();
        return buffer.toByteArray();

    }


    public void uploadThisShit() {

        showProgressDialog();

        DescriptionString = description.getEditText().getText().toString();
        AddressString = address.getEditText().getText().toString();
        PhoneString = phone.getEditText().getText().toString();
        QuantityString = quantity.getEditText().getText().toString();


        // Locate the image in res >
        Bitmap bitmap = BitmapFactory.decodeFile(picturePath);
        // Convert it to byte
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        // Compress image to lower quality scale 1 - 100
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);

        Object image = null;
        try {
            String path = picturePath;
            image = readInFile(path);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Create the ParseFile
        ParseFile file = new ParseFile("picturePath", convertToByteArray(picturePath));

        // Upload the image into Parse Cloud
        file.saveInBackground();

        // Create a New Class called "ImageUpload" in Parse
        ParseObject imgupload = new ParseObject("resource");

        // Create a column named "ImageName" and set the string
        //imgupload.put("ImageName", "picturePath");


        // Create a column named "ImageFile" and insert the image
        imgupload.put("image", file);

        imgupload.put("address", AddressString);
        imgupload.put("contact_details", PhoneString);
        imgupload.put("descripition", DescriptionString);
        imgupload.put("quantity", QuantityString);
        imgupload.put("username", usernameString);

        // Create the class and the columns
        //imgupload.saveInBackground();

        try {
            imgupload.save();
            progressDialog.dismiss();
            Toast.makeText(UploadAssetsActivity.this, "Donation Request Uploaded Successfully", Toast.LENGTH_LONG).show();
        } catch (ParseException e) {
            e.printStackTrace();
        }


        // Show a simple toast message


    }

    private static byte[] convertToByteArray(String imagePath) {
        File file = new File(imagePath);

        if (file.length() < 10485760) {
            byte[] imageByte = new byte[(int) file.length()];
            try {
                Log.d("test", "File Found");
                FileInputStream fileInputStream = new FileInputStream(file);
                fileInputStream.read(imageByte);

            } catch (FileNotFoundException e) {
                Log.d("test", "File Not Found.");

            } catch (IOException e1) {
                Log.d("test", "Error Reading The File.");

            }
            return imageByte;

        } else {
            return null;
        }
    }


    public void showProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);
        progressDialog.show();
    }

}
