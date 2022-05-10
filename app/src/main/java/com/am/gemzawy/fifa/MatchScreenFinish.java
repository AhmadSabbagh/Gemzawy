package com.am.gemzawy.fifa;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.am.gemzawy.fifa.Api.Webservice;
import com.am.gemzawy.fifa.HelperClass.AttolSharedPreference;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import droidninja.filepicker.FilePickerBuilder;
import droidninja.filepicker.FilePickerConst;

public class    MatchScreenFinish extends AppCompatActivity {
    public static final int RC_PHOTO_PICKER_PERM = 123;
    public static final int RC_FILE_PICKER_PERM = 321;
    private static final int CUSTOM_REQUEST_CODE = 532;
    private int MAX_ATTACHMENT_COUNT = 1;
    private ArrayList<Uri> photoPaths = new ArrayList<>();
    private ArrayList<Uri> docPaths = new ArrayList<>();
    private static final int PERMISSION_REQUEST_CODE = 1;

    public static int CAMERA_PIC_REQUEST=1;
    public static ImageView congrats;
    boolean istaken=false;
    String imageString="";
    public static ProgressBar loading;
    int comp_id;
    int competator_id=0;
    int round_id=0;
    int type;
    TextView cd;
    ImageView attach,i_win;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_screen_finish);
        // imageview = (ImageView) findViewById(R.id.result_photo_id); //sets imageview as the bitmap
        loading=findViewById(R.id.progressBar);
        congrats=findViewById(R.id.congrats_img);
        cd=findViewById(R.id.cd);
        attach = (ImageView) findViewById(R.id.attach_id);
        i_win = (ImageView) findViewById(R.id.i_win_id);

        loading.setVisibility(View.INVISIBLE);
        Intent intent= getIntent();
        comp_id=intent.getIntExtra("comp_id",0);
        round_id=intent.getIntExtra("round_id",0);
        type=intent.getIntExtra("type",0);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#F8C927")));
        competator_id=intent.getIntExtra("competator_id",0);
        AttolSharedPreference attolSharedPreference=new AttolSharedPreference(MatchScreenFinish.this);
        String lan=attolSharedPreference.getKey("language");
        if(lan.equals("ar"))
        {
            i_win.setBackgroundResource(R.drawable.i_win_bu);

        }
        else
        {
            i_win.setBackgroundResource(R.drawable.win_en);

        }
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {

            if (checkSelfPermission(Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_DENIED) {

                Log.d("permission", "permission denied to SEND_SMS - requesting it");
                String[] permissions = {Manifest.permission.CAMERA};

                requestPermissions(permissions, 1);

            }
        }
        attach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickPhotoClicked();

            }
        });
        i_win.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imageString.equals(""))
                {
                    Toast.makeText(MatchScreenFinish.this,"Please take photo ",Toast.LENGTH_LONG).show();
                }
                else
                {
                    AlertDialog.Builder a_builder = new AlertDialog.Builder(MatchScreenFinish.this);
                    a_builder.setMessage(getResources().getString(R.string.confirm_win))
                            .setCancelable(false)
                            .setNegativeButton("Edit", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Webservice webservice = new Webservice();
                                    if (type == 2)
                                    {

                                        webservice.Pubg_Win(MatchScreenFinish.this, comp_id, imageString);
                                      //  MyCountDown timer = new MyCountDown(5000, 1000);


                                    }
                                    else {
                                        Log.d("IMAGESTRING0",imageString);
                                        webservice.Win(MatchScreenFinish.this, comp_id, imageString, competator_id, round_id);
                                      //  MyCountDown timer = new MyCountDown(5000, 1000);

                                    }

                                    loading.setVisibility(View.VISIBLE);
                                  //  cd.setVisibility(View.VISIBLE);

                                }
                            });
                    AlertDialog alert = a_builder.create();
                    alert.setTitle("Thank you ");
                    alert.show();

                }
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
                            Toast.makeText(MatchScreenFinish.this,"Photo has been attached",Toast.LENGTH_LONG).show();

                           // photo.setImageBitmap(bitmap);


                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }
                break;


        }

        // addThemToView(photoPaths, docPaths);
    }

    public static class MyCountDown extends CountDownTimer
    {
        int secs=5;
        public MyCountDown(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
            // frameAnimation.start();
            start();
        }

        @Override
        public void onFinish() {
             secs = 10;
            // I have an Intent you might not need one
            //startActivity(intent);

           // MatchScreenFinish.this.finish();
        }

        @Override
        public void onTick(long duration) {
          //   cd.setText(String.valueOf(secs));
            secs = secs - 1;
        }
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
    private String ImageToString (Bitmap bitmap)
    {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,70,byteArrayOutputStream);
        byte[] imageBytes=byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imageBytes,Base64.DEFAULT);

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
