package com.am.gemzawy.fifa;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.am.gemzawy.fifa.Api.Webservice;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import droidninja.filepicker.FilePickerBuilder;
import droidninja.filepicker.FilePickerConst;

public class EditProfile extends AppCompatActivity {
    public static ProgressBar progressBar;
    public static ImageView profile_img;
    public  static EditText user_name,phone,email;
    public Button confirm_profile;
    String imageString="";
    public static final int RC_PHOTO_PICKER_PERM = 123;
    public static final int RC_FILE_PICKER_PERM = 321;
    private static final int CUSTOM_REQUEST_CODE = 532;
    private int MAX_ATTACHMENT_COUNT = 1;
    private ArrayList<Uri> photoPaths = new ArrayList<>();
    private ArrayList<Uri> docPaths = new ArrayList<>();
    private static final int PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        user_name=findViewById(R.id.username_id);
        user_name.setEnabled(false);
        phone=findViewById(R.id.phone_id);
        email=findViewById(R.id.user_email_id);

        phone.setEnabled(false);
        progressBar=findViewById(R.id.progressBB);
        profile_img=findViewById(R.id.photoR);
        Webservice webservice = new Webservice();
        webservice.getPlayerInfo(EditProfile.this);
        progressBar.setVisibility(View.VISIBLE);
        confirm_profile=findViewById(R.id.confirm_bu_profile);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#F8C927")));
        profile_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickPhotoClicked();

            }
        });
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {

            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_DENIED) {

                Log.d("permission", "permission denied to SEND_SMS - requesting it");
                String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};

                requestPermissions(permissions, PERMISSION_REQUEST_CODE);

            }
        }
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {

            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_DENIED) {

                Log.d("permission", "permission denied to SEND_SMS - requesting it");
                String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};

                requestPermissions(permissions, PERMISSION_REQUEST_CODE);

            }
        }
    }

    public void edit_profile(View view) {
        confirm_profile.setVisibility(View.VISIBLE);

        //user_name.setEnabled(true);
        phone.setEnabled(true);
    }


    public void confirm_edit_profile(View view) {
        if(user_name.getText().toString().equals("")|| phone.getText().toString().equals(""))
        {
            Toast.makeText(EditProfile.this,"Enter correct data please ",Toast.LENGTH_LONG).show();
        }
        else {
            Webservice webservice = new Webservice();
            webservice.Add_profile_info(EditProfile.this, user_name.getText().toString(),phone.getText().toString(),imageString,email.getText().toString());
            progressBar.setVisibility(View.VISIBLE);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CUSTOM_REQUEST_CODE:
                if (resultCode == Activity.RESULT_OK && data != null) {
                    ArrayList<Uri> dataList = data.getParcelableArrayListExtra(FilePickerConst.KEY_SELECTED_MEDIA);
                    if (dataList != null) {
                        photoPaths = new ArrayList<Uri>();
                        photoPaths.addAll(dataList);
                        try {
                            final InputStream imageStream = getContentResolver().openInputStream(photoPaths.get(0));
                            BufferedInputStream bis = new BufferedInputStream(imageStream,1024*8);
                            ByteArrayOutputStream out = new ByteArrayOutputStream();

                            int len=0;
                            byte[] buffer = new byte[1024];
                            while((len = bis.read(buffer)) != -1){
                                out.write(buffer, 0, len);
                            }
                            out.close();
                            bis.close();

                            byte[] data1 = out.toByteArray();
                            BitmapFactory.Options options = new BitmapFactory.Options();
                            options.inSampleSize = 8;
                            Bitmap bitmap = BitmapFactory.decodeByteArray(data1, 0, data1.length,options);
                            bitmap= scaleDown(bitmap,5000,true);
                            imageString =ImageToString(bitmap);
                            //  Toast.makeText(Register_Page.this,imageString,Toast.LENGTH_LONG).show();

                            profile_img.setImageBitmap(bitmap);


                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }
                break;


        }

        // addThemToView(photoPaths, docPaths);
    }
    private String ImageToString (Bitmap bitmap)
    {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,70,byteArrayOutputStream);
        byte[] imageBytes=byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imageBytes,Base64.DEFAULT);

    }
    public static Bitmap scaleDown(Bitmap realImage, float maxImageSize,
                                   boolean filter) {
        float ratio = Math.min(
                (float) maxImageSize / realImage.getWidth(),
                (float) maxImageSize / realImage.getHeight());
        int width = Math.round((float) ratio * realImage.getWidth());
        int height = Math.round((float) ratio * realImage.getHeight());

        Bitmap newBitmap = Bitmap.createScaledBitmap(realImage, width,
                height, filter);
        if (ratio>=1)
        {
            return realImage;
        }
        else {
            return newBitmap;
        }
    }
    public void pickPhotoClicked() {
        onPickPhoto();

    }
    public void onPickPhoto() {
        int maxCount = MAX_ATTACHMENT_COUNT - docPaths.size();
        if ((docPaths.size() + photoPaths.size()) == MAX_ATTACHMENT_COUNT) {
            Toast.makeText(this, "Cannot select more than " + MAX_ATTACHMENT_COUNT + " items",
                    Toast.LENGTH_SHORT).show();
        } else {
            FilePickerBuilder.getInstance()
                    .setMaxCount(maxCount)
                    .setSelectedFiles(photoPaths)
                    .setActivityTheme(R.style.LibAppTheme)
                    .setActivityTitle("Please select media")
                    .enableVideoPicker(true)
                    .enableCameraSupport(true)
                    .showGifs(true)
                    .showFolderView(false)
                    .enableSelectAll(false)
                    .enableImagePicker(true)
                    .setCameraPlaceholder(R.drawable.camera)
                    .withOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                    .pickPhoto(this, CUSTOM_REQUEST_CODE);
        }
    }
    }

