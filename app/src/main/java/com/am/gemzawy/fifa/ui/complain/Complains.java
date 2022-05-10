package com.am.gemzawy.fifa.ui.complain;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.am.gemzawy.fifa.Api.Webservice;
import com.am.gemzawy.fifa.R;
import com.am.gemzawy.fifa.ui.home.HomeViewModel;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import droidninja.filepicker.FilePickerBuilder;
import droidninja.filepicker.FilePickerConst;

public class Complains extends Fragment {

    private HomeViewModel homeViewModel;
    EditText username,phone,complain;
    public static ProgressBar loading;
    public static int CAMERA_PIC_REQUEST=1;
    ImageView imageview;
    String imageString="";
    boolean istaken=false;

    int round_id=0;
    int type=0;
    int comp_id=0;
    int can=0;
    Button send,attach;
    ImageButton attach_ib;

    public static final int RC_PHOTO_PICKER_PERM = 123;
    public static final int RC_FILE_PICKER_PERM = 321;
    private static final int CUSTOM_REQUEST_CODE = 532;
    private int MAX_ATTACHMENT_COUNT = 1;
    private ArrayList<Uri> photoPaths = new ArrayList<>();
    private ArrayList<Uri> docPaths = new ArrayList<>();
    private static final int PERMISSION_REQUEST_CODE = 1;
    String imagestring="";
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        final View root = inflater.inflate(R.layout.complain, container, false);
        username = root.findViewById(R.id.usernameID);
        phone = root.findViewById(R.id.phoneID);
        complain = root.findViewById(R.id.complainID);
        loading = root.findViewById(R.id.complainBar);
        loading.setVisibility(View.INVISIBLE);
        imageview=root.findViewById(R.id.result_photo_id);
        attach_ib=root.findViewById(R.id.attch_img_bu);
        send=root.findViewById(R.id.send_complain_bu);
        if (getArguments() != null) {

            type = getArguments().getInt("type");
        }

        attach_ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickPhotoClicked();

            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (can == 1)
                {
                    if (username.getText().toString().equals("") || phone.getText().toString().equals("") || complain.getText().toString().equals("")) {
                        Toast.makeText(getActivity(), "Please fill all the fields ", Toast.LENGTH_LONG).show();
                    } else {
                        Webservice webservice = new Webservice();
                        if (type == 1) {
                            if (!imageString.equals("")) {
                                webservice.Add_Complain(getActivity(), username.getText().toString(), phone.getText().toString(), complain.getText().toString(), round_id, imageString);
                            } else {
                                Toast.makeText(getActivity(), "Take a photo please", Toast.LENGTH_LONG).show();

                            }
                        } else if (type == 2) {
                            if (!imageString.equals("")) {
                                webservice.Add_Pubg_Complain(getActivity(), username.getText().toString(), phone.getText().toString(), complain.getText().toString(), comp_id, imageString);
                            } else {
                                Toast.makeText(getActivity(), "Take a photo please", Toast.LENGTH_LONG).show();

                            }
                        }
                        loading.setVisibility(View.VISIBLE);
                    }
                }
                else
                {
                    Toast.makeText(getActivity(), "اذهب الى صفحة المباراة او البطولة الخاصة بك وسيتم تحويلك لكل تحصل على رقم المسابقة او الجولة  ", Toast.LENGTH_LONG).show();

                }
            }
        });

        if (type == 1) {
            round_id = getArguments().getInt("round_id");

            can=1;
        } else if (type == 2)
        {
            comp_id = getArguments().getInt("comp_id");
            can=1;
        }
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {

            if (getActivity().checkSelfPermission(Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_DENIED) {

                Log.d("permission", "permission denied to SEND_SMS - requesting it");
                String[] permissions = {Manifest.permission.CAMERA};

                requestPermissions(permissions, 1);

            }
        }
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {

            if (getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_DENIED) {

                Log.d("permission", "permission denied to SEND_SMS - requesting it");
                String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};

                requestPermissions(permissions, PERMISSION_REQUEST_CODE);

            }
        }
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {

            if (getActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_DENIED) {

                Log.d("permission", "permission denied to SEND_SMS - requesting it");
                String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};

                requestPermissions(permissions, PERMISSION_REQUEST_CODE);

            }
        }



        return root;
    }



    public void pickPhotoClicked() {
        onPickPhoto();

    }
    public void onPickPhoto() {
        int maxCount = MAX_ATTACHMENT_COUNT - docPaths.size();
        if ((docPaths.size() + photoPaths.size()) == MAX_ATTACHMENT_COUNT) {
            Toast.makeText(getActivity(), "Cannot select more than " + MAX_ATTACHMENT_COUNT + " items",
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
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CUSTOM_REQUEST_CODE:
                if (resultCode == Activity.RESULT_OK && data != null) {
                    ArrayList<Uri> dataList = data.getParcelableArrayListExtra(FilePickerConst.KEY_SELECTED_MEDIA);
                    if (dataList != null) {
                        photoPaths = new ArrayList<Uri>();
                        photoPaths.addAll(dataList);
                        try {
                            final InputStream imageStream = getActivity().getContentResolver().openInputStream(photoPaths.get(0));
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
                            bitmap= scaleDown(bitmap,4000,true);
                            imageString =ImageToString(bitmap);
                            Toast.makeText(getActivity(),"attached ",Toast.LENGTH_LONG).show();

                            //photo.setImageBitmap(bitmap);


                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }
                break;


        }

        // addThemToView(photoPaths, docPaths);
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
}
