package com.am.gemzawy.fifa.ui.tools;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.am.gemzawy.fifa.Add_EmailID;
import com.am.gemzawy.fifa.Add_FifaID;
import com.am.gemzawy.fifa.Add_PubgID;
import com.am.gemzawy.fifa.ChangePass;
import com.am.gemzawy.fifa.EditProfile;
import com.am.gemzawy.fifa.HelperClass.AttolSharedPreference;
import com.am.gemzawy.fifa.Language_chooser_Settings;
import com.am.gemzawy.fifa.NotificationControlPage;
import com.am.gemzawy.fifa.Payout;
import com.am.gemzawy.fifa.R;
import com.am.gemzawy.fifa.adapters.Settings_Adapter;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class ToolsFragment extends Fragment {
    ListView listView;
    String[] listItem;
    int av=1;
    boolean[] listImages={true, false, true, false, true};
    private ArrayList<String> comp_text = new ArrayList<String>();//offer pic
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AttolSharedPreference attolSharedPreference= new AttolSharedPreference(getActivity());
        String adnroid_ver=attolSharedPreference.getKey("android_ver");
        View root = inflater.inflate(R.layout.fragment_tools, container, false);

        listView=root.findViewById(R.id.list_setting);
        listItem = getResources().getStringArray(R.array.staticText1Elements);
        if(adnroid_ver!=null)
        {
            av= Integer.parseInt(adnroid_ver);

        }
        for (int i=0;i<listItem.length;i++)
        {
            if(listItem[i].equals("Request Payment") || listItem[i].equals("Awards Information")
            ||listItem[i].equals("معلومات استلام الارباح") || listItem[i].equals("طلب الدفع") )
            {
                if(av==1)
                {

                }
                else {
                    comp_text.add(listItem[i]);
                }

            }
            else {
                comp_text.add(listItem[i]);
            }
        }

        Settings_Adapter settings_adapter=new Settings_Adapter(comp_text, getActivity());
        listView.setAdapter(settings_adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        startActivity(new Intent(getActivity(), EditProfile.class));
                        Toast.makeText(getActivity(),"0",Toast.LENGTH_LONG).show();
                        break;
                    case 1:
                        startActivity(new Intent(getActivity(), NotificationControlPage.class));
                        break;
                    case 2:
                        startActivity(new Intent(getActivity(), Add_FifaID.class));
                        break;
                    case 3:
                        startActivity(new Intent(getActivity(), Add_PubgID.class));
                        break;
                    case 4:
                            //startActivity(new Intent(getActivity(), Add_EmailID.class));
                        break;
                    case 5:
                        startActivity(new Intent(getActivity(),ChangePass.class));
                        break;
                    case 6:
                        startActivity(new Intent(getActivity(), Payout.class));
                        break;
                    case 7:
                        startActivity(new Intent(getActivity(), Language_chooser_Settings.class));
                        break;


                }
            }
        });
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {

            if (getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_DENIED) {

                Log.d("permission", "permission denied to SEND_SMS - requesting it");
                String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};

                requestPermissions(permissions, 1);

            }
        }


        return root;
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
/*
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            picturePath = cursor.getString(columnIndex);

            cursor.close();
            bitmap= BitmapFactory.decodeFile(picturePath);
            bitmap= scaleDown(bitmap,700,true);
            // agentamage.setImageBitmap(bitmap);
            if(img_type==1) {
                profile_img.setImageBitmap(bitmap);
                AttolSharedPreference attolSharedPrefence = new AttolSharedPreference(getActivity());
                attolSharedPrefence.setKey("ImagePath", imageString);
            }
            else if ( img_type==2)
            {
                pubg_id_img.setImageBitmap(bitmap);
                AttolSharedPreference attolSharedPrefence = new AttolSharedPreference(getActivity());
                attolSharedPrefence.setKey("PubgImagePath", imageString);
            }

            imageString =ImageToString(bitmap);


        }
    }
    */
}