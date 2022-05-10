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
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.am.gemzawy.fifa.Api.Webservice;

import com.mukesh.countrypicker.Country;
import com.mukesh.countrypicker.CountryPicker;
import com.mukesh.countrypicker.OnCountryPickerListener;


import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import droidninja.filepicker.FilePickerBuilder;
import droidninja.filepicker.FilePickerConst;


public class Register_Page extends AppCompatActivity implements OnCountryPickerListener{

    public static final int RC_PHOTO_PICKER_PERM = 123;
    public static final int RC_FILE_PICKER_PERM = 321;
    private static final int CUSTOM_REQUEST_CODE = 532;
    private int MAX_ATTACHMENT_COUNT = 1;
    private ArrayList<Uri> photoPaths = new ArrayList<>();
    private ArrayList<Uri> docPaths = new ArrayList<>();


    EditText username,phone,city,password,test;
    String countryname="Egypt";
    ImageView photo ;
    String picturePath;
    private static final int PERMISSION_REQUEST_CODE = 1;
    private static int RESULT_LOAD_IMAGE = 1;
    Bitmap bitmap;
    public  static  ProgressBar loading;
    String imageString="/9j/4AAQSkZJRgABAQAAAQABAAD/2wBDAKBueIx4ZKCMgoy0qqC+8P//8Nzc8P//////////////\n" +
            "////////////////////////////////////////////2wBDAaq0tPDS8P//////////////////\n" +
            "////////////////////////////////////////////////////////////wAARCADIAMgDASIA\n" +
            "AhEBAxEB/8QAHwAAAQUBAQEBAQEAAAAAAAAAAAECAwQFBgcICQoL/8QAtRAAAgEDAwIEAwUFBAQA\n" +
            "AAF9AQIDAAQRBRIhMUEGE1FhByJxFDKBkaEII0KxwRVS0fAkM2JyggkKFhcYGRolJicoKSo0NTY3\n" +
            "ODk6Q0RFRkdISUpTVFVWV1hZWmNkZWZnaGlqc3R1dnd4eXqDhIWGh4iJipKTlJWWl5iZmqKjpKWm\n" +
            "p6ipqrKztLW2t7i5usLDxMXGx8jJytLT1NXW19jZ2uHi4+Tl5ufo6erx8vP09fb3+Pn6/8QAHwEA\n" +
            "AwEBAQEBAQEBAQAAAAAAAAECAwQFBgcICQoL/8QAtREAAgECBAQDBAcFBAQAAQJ3AAECAxEEBSEx\n" +
            "BhJBUQdhcRMiMoEIFEKRobHBCSMzUvAVYnLRChYkNOEl8RcYGRomJygpKjU2Nzg5OkNERUZHSElK\n" +
            "U1RVVldYWVpjZGVmZ2hpanN0dXZ3eHl6goOEhYaHiImKkpOUlZaXmJmaoqOkpaanqKmqsrO0tba3\n" +
            "uLm6wsPExcbHyMnK0tPU1dbX2Nna4uPk5ebn6Onq8vP09fb3+Pn6/9oADAMBAAIRAxEAPwCaiiig\n" +
            "AooooAKKKKACigkAZJxUTTf3R+NAEtNLqOpqBmZuppKYiYzL2BNJ53+zUVFAEnnH0FHnH+7UdFAE\n" +
            "wmHcGnCRT3x9ar0UAWqKqgkdDipFmP8AEM0hk1FIrBhwaWgAooooAKKKKACiiigAooooAKKKKACm\n" +
            "PIF4HJpskvZfzqKgBWYsck0lFFMQUUUUAFFFFABRRRQAUUUUAFFFFAACQcipUl7N+dRUUAWqKgjk\n" +
            "K8HkVODkZFIYUUUUAFFFFABRRRQAVFLJ/CPxp0r7RgdTUFABRRRTEFFFFABRRRQAUUU4Rse2PrQA\n" +
            "2inmJvY0wqR1BFABRRRQAUUUUAFFFFABT432nB6UyigC1RUML/wn8KmpDCiiigAoJwCTRUUzdF/O\n" +
            "gCNiWJJpKKKYgooooAKKKKACgc0VLCvVqAHRxheTyafRRSGFFFFADGiU+xqFlKnBqzSOu5cUAVqK\n" +
            "KKYgooooAKKKKACrEbbl9+9V6fE21/Y0AT0UUUhhVZjuYmp5DhDVemIKKKKACiiigAooooAKnh+5\n" +
            "UFTQ/cP1oAkooopDCiiigAooooArP98/WkpWOWJ96SmIKKKKACiiigAooooAsodyg0VHAeCKKQxZ\n" +
            "zwBUNST/AHh9KjpiCiiigAooooAKKKKACp4gAmfWoKmhb5cdxQBJRRRSGFFFFABRRQSFBJoAqsMM\n" +
            "R70UHk0UxBRRRQAUUUUAFFFFAD4Th/rRSR/fFFIY6f74+n+NR1JP94fSo6YgooooAKKKKACiiigA\n" +
            "p0Rw49+KbRQBaopqNuXPfvTqQwooooAKjmPygetSHgZNVnbc2aAEooopiCiiigAooooAKKKKAFT7\n" +
            "6/UUUJ98fWikMknHQ1FU8wyn0qCmIKKKKACiiigAooooAKKKesbN14FADUYqcirCsGGRSbF27ccV\n" +
            "GY2U5Xn6Uhk1BOBk1B5knT+lG1365/GgAkk3cDp/OmVOsagYPNMeIjleaYiOigjHWigAooooAKKK\n" +
            "KACiiigB0QzIKKdAOSaKQyYjII9aqng4q1UEy4bPrQAyiiimIKKKKAAAk4FSrD/eP5U6Jdq57mn0\n" +
            "hiKir0FLRRQAUUUUAFFFFABRRRQAEA9Rmo2hB+6cVJRQBWZSp5FJVllDDBqsRg4NMQUUUUAFFFKi\n" +
            "7mAoAniGEHvzRTqKQwprruUinUUAVaKkmTB3D8ajpiClQbmApKkgHzE+lAE1FFFIYUUUUAFFFFAB\n" +
            "RRRQAUUUUAFFFFABUMww2fWpqjmGUz6GgCGiiimIKmhXA3HvUcabm9u9WKACiiikMKKKKAAjIwar\n" +
            "umw+1WKCAwwaAKtTQD5Sfeo3Qofb1qWHGymIfRRRSGFFFFABRRRQAUUUUAFFFFABRRRQAU2QZQ/S\n" +
            "nUjY2nNAFahQWOBQoLHAqwiBB70xCqoUYFLRRSGFFFFABRRRQAUUUUABAIwahZGQ7l6VNRQAxJQe\n" +
            "DwafTHjDcjg0z54/cfpQBNRUazKevFSAg9DmgAooooAKKKKACiijpQAUUxpVHfP0pm934UYH+e9A\n" +
            "EjSBfc+lR/NKfQU5YgOW5qSgBFUKMCloooAKKKKACiiigAooooAKKKKACiiigAooooAa0at2/KmG\n" +
            "Ej7rUUUAJiVff9aN8g6r+lFFAB5r/wB39DR5kh/h/Q0UUxB+9Pt+lHlMfvN/WiikMesSj3+tP6UU\n" +
            "UAFFFFABRRRQAUUUUAFFFFAH/9k=\n";
  EditText email;
  public CountryPicker countryPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register__page);
      //  CountryCurrencyButton country = (CountryCurrencyButton) findViewById(R.id.country_pick);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#F8C927")));
        username = (EditText)findViewById(R.id.usernameR);
        password = findViewById(R.id.passwordR);
        phone = (EditText) findViewById(R.id.phoneR);
        city = (EditText) findViewById(R.id.cityR);
        photo=(ImageView) findViewById(R.id.photoR);
        email=findViewById(R.id.email);
        test=findViewById(R.id.test);
        ;

//          test.setOnTouchListener(new View.OnTouchListener() {
//              @Override
//              public boolean onTouch(View v, MotionEvent event) {
//                  countryPicker.showDialog(getSupportFragmentManager());
//                  return true;
//              }
//          });
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countryPicker.showDialog(getSupportFragmentManager());

            }
        });

           initialize1();


           photo.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
//                   FilePickerBuilder.getInstance().setMaxCount(5)
//                           .setSelectedFiles(photoPaths)
//                           .setActivityTheme(R.style.LibAppTheme)
//                           .pickPhoto(Register_Page.this);
                   pickPhotoClicked();
               }
           });
        loading = findViewById(R.id.loading2);
        loading.setVisibility(View.INVISIBLE);
//        country.setOnClickListener(new CountryCurrencyPickerListener() {
//            @Override
//            public void onSelectCountry(Country country) {
//                countryname=country.getName();
//                if (country.getCurrency() == null) {
//                    countryname=country.getName();
//
//                } else {
//                    Toast.makeText(Register_Page.this,
//                            String.format("name: %s\ncurrencySymbol: %s", country.getName(), country.getCurrency().getSymbol())
//                            , Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onSelectCurrency(Currency currency) {
//
//            }
//        });
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
    public void register(View view) {
//        startActivity(new Intent(getApplicationContext(),MainActivity.class));
//


        if(username.getText().toString().equals("") ||password.getText().toString().equals("") ||
                phone.getText().toString().equals("")
                || city.getText().toString().equals("") || email.getText().toString().equals("")) {

            AlertDialog.Builder a_builder = new AlertDialog.Builder(this);
            a_builder.setMessage("Enter All information please ")
                    .setCancelable(false)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //  startActivity(new Intent(Teacher_Reg.this,EditProfile.class));
                        }
                    });
            AlertDialog alert = a_builder.create();
            alert.setTitle("Error :( ");
            alert.show();
        }
        else
        {
            if ( !email.getText().toString().contains("@")) {
                email.setError("Wrong Email ");
            }
            else if(password.getText().length()<8)
            {
              password.setError("Password at least 8 Characters");
            }
            else {
                AlertDialog.Builder a_builder = new AlertDialog.Builder(this);
                a_builder.setMessage("Your Email is   "  + email.getText().toString())
                        .setCancelable(false)
                        .setNegativeButton("Edit", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setPositiveButton(" I Confirm that my email is correct ", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                               Webservice webservice = new Webservice();
                                webservice.Register(Register_Page.this,username.getText().toString(),phone.getText().toString(),countryname,password.getText().toString(),city.getText().toString(),imageString,0,0,"NoIdYet",email.getText().toString());
                                //Register(username.getText().toString(),phone.getText().toString(),countryname,password.getText().toString(),city.getText().toString(),imageString,0,0,0);
                                loading.setVisibility(View.VISIBLE);

                            }
                        });
                AlertDialog alert = a_builder.create();
                alert.setTitle("شكرا ");
                alert.show();
            }
        }
      //  Webservice webservice = new Webservice();
      //  webservice.Register(Register_Page.this,username.getText().toString(),phone.getText().toString(),countryname,password.getText().toString(),city.getText().toString(),imageString,0,0,0);
    }
    /*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            String[] projection = new String[]{
                    MediaStore.Images.ImageColumns._ID,
                    MediaStore.Images.ImageColumns.DATA,
                    MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME,
                    MediaStore.Images.ImageColumns.DATE_TAKEN,
                    MediaStore.Images.ImageColumns.MIME_TYPE,
                    MediaStore.Images.ImageColumns.DISPLAY_NAME,
            };

//            Uri selectedImage = data.getData();
//            String[] filePathColumn = {MediaStore.Images.Media.DATA};
//            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
//            cursor.moveToFirst();
//            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//            picturePath = cursor.getString(columnIndex);
//
//            cursor.close();
            final Cursor cursor = this.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    projection, null, null, MediaStore.Images.ImageColumns.DATE_TAKEN + " DESC");
            if (cursor.moveToFirst()) {
              //  final ImageView imageView = (ImageView) findViewById(R.id.pictureView);


                if (Build.VERSION.SDK_INT >= 29) {
                    // You can replace '0' by 'cursor.getColumnIndex(MediaStore.Images.ImageColumns._ID)'
                    // Note that now, you read the column '_ID' and not the column 'DATA'
                    Uri imageUri= ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, cursor.getInt(0));

                    // now that you have the media URI, you can decode it to a bitmap
                    try (ParcelFileDescriptor pfd = this.getContentResolver().openFileDescriptor(imageUri, "r")) {
                        if (pfd != null) {
                            bitmap = BitmapFactory.decodeFileDescriptor(pfd.getFileDescriptor());
                        }
                    } catch (IOException ex) {

                    }
                } else {
                    // Repeat the code you already are using

                    BitmapFactory.Options options = null;
                    Bitmap bm = null;

                    String[] projection1 = new String[]{
                            MediaStore.Images.ImageColumns._ID,
                            MediaStore.Images.ImageColumns.DATA,
                            MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME,
                            MediaStore.Images.ImageColumns.DATE_TAKEN,
                            MediaStore.Images.ImageColumns.MIME_TYPE,
                            MediaStore.Images.ImageColumns.DISPLAY_NAME,
                    };

                    final Cursor cursor1 = this.getContentResolver()
                            .query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection1, null,
                                    null, MediaStore.Images.ImageColumns.DATE_TAKEN + " DESC");
                    if (cursor1.moveToFirst()) {
                        //final ImageView imageView = (ImageView) findViewById(R.id.pictureView);
                        String imageLocation = cursor1.getString(1);
                        File imageFile = new File(imageLocation);

                        if (imageFile.exists()) {
                            options = new BitmapFactory.Options();
                            options.inSampleSize = 2;

                            try {
                                bm = BitmapFactory.decodeFile(imageLocation, options);
                            } catch(Exception e) {
                                e.printStackTrace();
                            }
                        }

                      //  imageView.setImageBitmap(bm);




                    }
                }
            }
         //   bitmap= BitmapFactory.decodeFile(picturePath);
            bitmap= scaleDown(bitmap,700,true);
            // agentamage.setImageBitmap(bitmap);


            imageString =ImageToString(bitmap);

            // agentamage.setImageBitmap(bitmap);
//            AttolSharedPreference attolSharedPrefence = new AttolSharedPreference(this);
//            attolSharedPrefence.setKey("ImagePath", picturePath);


        }

    }
    */
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
                            bitmap= scaleDown(bitmap,1000,true);
                            imageString =ImageToString(bitmap);
                          //  Toast.makeText(Register_Page.this,imageString,Toast.LENGTH_LONG).show();

                            photo.setImageBitmap(bitmap);


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
    String result_reg_face3;


    @Override
    public void onSelectCountry(Country country) {
       countryname=country.getName();
       test.setText(countryname);

    }
    public  void initialize1()
    {
        countryPicker=new CountryPicker.Builder().with(this).listener(this).build();

    }


}
