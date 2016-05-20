package com.synchron.ncpl.synchron;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.DialogPreference;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfile extends AppCompatActivity
{
    private static final String TAG = "ProfileActivity";

    // Profile Image setting
    private EditText inputAddress;
    private String address;
    private String currentlat, currentlng;
    private ImageView img;
    private Uri outputFileUri;
    private static int RESULT_IMAGE = 1;
    private static int RESULT_CANCELED = 0;
    private final int RESULT_CROP = 2;
    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 3;

    Bitmap bitmap, circularBitmap;
    TextView edit_mail;
    EditText edit_fname,edit_profesion,edit_location,edit_phone,edit_mobile,edit_pass,edit_newPass,edit_reEnterPass;
    String firstName,proffesion,location,eMail,phone,mobile,password,newpassword,reEnterpassword,userId;
    String fname,uProffesion,uLocation,uMail,uPhone,uMobile,uPassword,profile,imgDecodableString;
    Button ok,back;
    CircleImageView profileImage;
    String result;
    ProgressDialog pDialog;
    //int RESULT_IMAGE = 0, RESULT_CROP = 1;
    //Uri outputFileUri;
    int REQUEST_CAMERA =0,SELECT_FILE=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        edit_fname = (EditText) findViewById(R.id.edit_fname);
        edit_profesion = (EditText) findViewById(R.id.edit_profile_proffesion);
        edit_location = (EditText) findViewById(R.id.edit_profile_location);
        edit_mail = (TextView) findViewById(R.id.edit_profile_mail);
        edit_phone = (EditText) findViewById(R.id.edit_profil_phone);
        edit_mobile = (EditText) findViewById(R.id.edit_profile_mobile);
        edit_pass = (EditText) findViewById(R.id.edit_profile_password);
        edit_newPass = (EditText) findViewById(R.id.edit_profile_newPass);
        edit_reEnterPass = (EditText) findViewById(R.id.edit_profile_reenter_pass);
        profileImage = (CircleImageView) findViewById(R.id.btn_prifile_pic);
        ok = (Button) findViewById(R.id.btn_save);
        back = (Button) findViewById(R.id.btn_back);

        Intent intent = getIntent();
        userId = intent.getStringExtra("UserId");
        fname = intent.getStringExtra("name");
        uProffesion = intent.getStringExtra("position");
        uLocation = intent.getStringExtra("address");
        uMail = intent.getStringExtra("email");
        uPhone = intent.getStringExtra("phone");
        uMobile = intent.getStringExtra("mobile");
        uPassword = intent.getStringExtra("password");
        profile = intent.getStringExtra("profile_img");
        Toast.makeText(EditProfile.this,userId + "" + uPassword, Toast.LENGTH_LONG).show();
        edit_mail.setText(userId + "" + uMail);
        edit_fname.setText(fname);
        edit_profesion.setText(uProffesion);
        edit_location.setText(uLocation);
        edit_mail.setText(uMail);
        edit_phone.setText(uPhone);
        edit_mobile.setText(uMobile);
        edit_pass.setText(uPassword);
        Glide.with(EditProfile.this).load(profile).into(profileImage);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                firstName = edit_fname.getText().toString();
                proffesion = edit_profesion.getText().toString();
                location = edit_location.getText().toString();
                phone = edit_phone.getText().toString();
                mobile = edit_mobile.getText().toString();
                password = edit_pass.getText().toString();
                newpassword = edit_newPass.getText().toString();
                reEnterpassword = edit_reEnterPass.getText().toString();
                if (firstName.isEmpty())
                {

                    Toast.makeText(getApplicationContext(), "Please enter the details", Toast.LENGTH_LONG).show();
                }
                else if (proffesion.isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "Please enter the details", Toast.LENGTH_LONG).show();
                }
                else if (phone.isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "Please enter the details", Toast.LENGTH_LONG).show();
                }
                else if (mobile.isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "Please enter the details", Toast.LENGTH_LONG).show();
                }
                else if (location.isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "Please enter the details", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Updated Successfully", Toast.LENGTH_LONG).show();
                }

                //Toast.makeText(getApplicationContext(), userId+"\n"+ firstName + "\n" + proffesion + "\n" + location + "\n" + eMail + "\n" + phone + "\n" + mobile + "\n" + password + "\n" + newpassword + "\n" + reEnterpassword, Toast.LENGTH_LONG).show();
                loadUrl("http://www.synchron.6elements.net/webservices/update_profile.php?UserId=" + userId + "&firstName=" + firstName + "&proffesion=" + proffesion + "&location=" + location + "&phone=" + phone + " &mobile=" + mobile + "&password=" + password + "&newPassword=" + newpassword + "&reEnterPassword=" + reEnterpassword + "&profilepic=" + result + "");
            }

        });
        edit_fname .setText("");
        edit_profesion.setText("");
        edit_location.setText("");
        edit_mail.setText("");
        edit_phone.setText("");
        edit_mobile.setText("");
        edit_pass.setText("");
        edit_mail.setText(uMail);
        edit_pass.setText(userId + "" + uPassword);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
    }
    private void selectImage() {
        // Determine Uri of camera image to save.
        final File root = new File(Environment.getExternalStorageDirectory() + File.separator + "MyDir" + File.separator);
        root.mkdirs();
        final String fname = "img_"+ System.currentTimeMillis() + ".jpg";
        final File sdImageMainDirectory = new File(root, fname);
        outputFileUri = Uri.fromFile(sdImageMainDirectory);

        // Camera.
        final List<Intent> cameraIntents = new ArrayList<Intent>();
        final Intent captureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        final PackageManager packageManager = EditProfile.this.getPackageManager();
        final List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
        for(ResolveInfo res : listCam) {
            final String packageName = res.activityInfo.packageName;
            final Intent intent = new Intent(captureIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(packageName);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
            cameraIntents.add(intent);
        }

        // Filesystem.
        final Intent galleryIntent = new Intent();
        galleryIntent.setType("image/*");
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);

        // Chooser of filesystem options.
        final Intent chooserIntent = Intent.createChooser(galleryIntent, "Select Source");

        // Add the camera options.
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, cameraIntents.toArray(new Parcelable[cameraIntents.size()]));

        startActivityForResult(chooserIntent, RESULT_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_IMAGE && resultCode == Activity.RESULT_OK) {
            final boolean isCamera;
            if (data == null) {
                isCamera = true;
            } else {
                final String action = data.getAction();
                if (action == null) {
                    isCamera = false;
                } else {
                    isCamera = action.equals(MediaStore.ACTION_IMAGE_CAPTURE);
                }
            }

            Uri selectedImageUri;
            if (isCamera) {
                selectedImageUri = outputFileUri;
                imgDecodableString = selectedImageUri.getPath();
                //perform Crop on the Image Selected from Gallery
                performCrop(imgDecodableString);
            } else {
                selectedImageUri = data.getData();
                Log.d("ImageURI", selectedImageUri.getLastPathSegment());
                try {//Using Input Stream to get uri did the trick
                    InputStream input = EditProfile.this.getContentResolver().openInputStream(selectedImageUri);
                    imgDecodableString = selectedImageUri.getPath();
                    //perform Crop on the Image Selected from Gallery
                    performCrop(imgDecodableString);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }

        }

        else if (requestCode == RESULT_CROP && resultCode == Activity.RESULT_OK){

            Log.i(TAG, "image crop done");
            Bundle extras = data.getExtras();
            bitmap = extras.getParcelable("data");
            // Set The Bitmap Data To ImageView
            img.setImageBitmap(bitmap);

        }

        else if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Log.i(TAG, "place picker started");
            Place place = PlaceAutocomplete.getPlace(EditProfile.this, data);

            address = place.getAddress().toString();
            inputAddress.setText(address);

            String a = place.getLatLng().toString();
            StringTokenizer b = new StringTokenizer(a, ":");
            String first = b.nextToken();
            String location = b.nextToken().trim();
            String[] parts = location.split(",");
            currentlat = parts[0].replaceAll("[\\[\\](){}]", "");
            currentlng = parts[1].replaceAll("[\\[\\](){}]", "");

            Log.i(TAG, "Place: " + place.getName());
        }

        else if (resultCode == RESULT_CANCELED){
            // user cancelled Image capture
            Toast.makeText(EditProfile.this, "User cancelled image capture", Toast.LENGTH_SHORT).show();
        } else {
            // failed to capture image
            Toast.makeText(EditProfile.this, "Sorry! Failed to capture image", Toast.LENGTH_SHORT).show();
        }

    }
    private void performCrop(String imgDecodableString) {

        try {
            //Start Crop Activity

            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            // indicate image type and Uri
            File f = new File(imgDecodableString);
            Uri contentUri = Uri.fromFile(f);

            cropIntent.setDataAndType(contentUri, "image/*");
            // set crop properties
            cropIntent.putExtra("crop", "true");
            // indicate aspect of desired crop
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            // indicate output X and Y
            cropIntent.putExtra("outputX", 280);
            cropIntent.putExtra("outputY", 280);

            // retrieve data on return
            cropIntent.putExtra("return-data", true);
            // start the activity - we handle returning in onActivityResult
            startActivityForResult(cropIntent, RESULT_CROP);
        }
        // respond to users whose devices do not support the crop action
        catch (ActivityNotFoundException anfe) {
            // display an error message
            String errorMessage = "your device doesn't support the crop action!";
            Toast toast = Toast.makeText(EditProfile.this, errorMessage, Toast.LENGTH_SHORT);
            toast.show();
        }
    }


    public void loadUrl(String url) {
        // JsonArrayRequest jsonRequest = new JsonArrayRequest();
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {



                Log.e("My Response", response.toString());
            }
        }, new Response.ErrorListener() {
            //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                //This code is executed if there is an error.

            }
        }) {

        };
        Volley.newRequestQueue(this).add(MyStringRequest);
        edit_fname.setText("userId");
        edit_profesion.setText("profession");
        edit_location.setText("location");
        edit_mobile.setText("mobile");
        edit_phone.setText("phone");
        edit_newPass.setText("");
        edit_reEnterPass.setText("");
        edit_fname.setText("");
        edit_profesion.setText("");
        edit_location.setText("");
        edit_mobile.setText("");
        edit_phone.setText("");
        edit_newPass.setText("");
        edit_reEnterPass.setText("");






    }

}
