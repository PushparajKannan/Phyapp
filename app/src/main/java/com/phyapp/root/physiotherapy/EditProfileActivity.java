package com.phyapp.root.physiotherapy;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import androidx.annotation.RequiresApi;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.iid.FirebaseInstanceId;
import com.phyapp.root.physiotherapy.APICONFIG.APIConfig;
import com.phyapp.root.physiotherapy.Utils.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;
import dmax.dialog.SpotsDialog;

public class EditProfileActivity extends AppCompatActivity {

    private static final String TAG ="" ;
    EditText profile_name,profile_number,profile_email,profile_address;

    SessionManager session;

    String Language,PatientId;

    String number;

    TextView save;
    AlertDialog pDialog;
    private static final int REQUEST_TAKE_PHOTO = 1;
    private static final int REQUEST_TAKE_GALLERY_PHOTO = 2;

    Dialog dialog;

    CircleImageView profileimage;
    private String mCurrentPhotoPath;
    private String profileimagebase64;
    private String imagetypesurl;
    private String Imageurl;
    FloatingActionButton penimage;
    private String refreshedToken;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        callTitleCenterFont();


        session=new SessionManager(this);

        pDialog=new SpotsDialog(this,R.style.Custom);

        pDialog.setCancelable(false);

        dialog=new Dialog(this,R.style.CustomDialogAnimationss);
        dialog.setCancelable(false);

        refreshedToken = FirebaseInstanceId.getInstance().getToken();


        showpDialog();


        HashMap<String,String> user=session.getUserDetails();

        Language=user.get(SessionManager.KEY_USERS_Language);

        PatientId=user.get(SessionManager.KEY_USERID);

        number=user.get(SessionManager.KEY_PROFILE_NUMBER);

       // number=getIntent().getStringExtra("number");


        imagetypesurl="typ";

        profile_name=(EditText) findViewById(R.id.edit_profile_name);
        profile_number=(EditText) findViewById(R.id.edit_profile_number);
        profile_email=(EditText) findViewById(R.id.edit_profile_mail);
        profile_address=(EditText) findViewById(R.id.edit_profile_address);
        save=(TextView) findViewById(R.id.save_profile);
        penimage=(FloatingActionButton) findViewById(R.id.penimg);

        profileimage=(CircleImageView) findViewById(R.id.profilepic);

        CallProileApi(PatientId);



        penimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isWriteStoragePermissionGranted()) {

                    selectImage1();
                }

            }
        });

/*

        penimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectImage1();


            }
        });
*/




        profile_number.setText(number);

        profileimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (profileimage.getDrawable() == null) {

                    if (isWriteStoragePermissionGranted()) {

                        selectImage1();
                    }

                } else {
                    try {
                        if (imagetypesurl.equals("url")) {
                            if (mCurrentPhotoPath == null) {
                                previewUrlImage(profileimage, Imageurl);
                            } else {
                                previewImage(profileimage, mCurrentPhotoPath);
                            }
                        } else if(mCurrentPhotoPath!=null) {
                            previewImage(profileimage,mCurrentPhotoPath);
                        }else {
                            previewplaceholerImage(profileimage);
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    // Toast.makeText(ProcessActivity.this, "preview", Toast.LENGTH_SHORT).show();
                }

            }
        });


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                if(!TextUtils.isEmpty(profile_address.getText().toString()) && !TextUtils.isEmpty(profile_email.getText().toString()) && !TextUtils.isEmpty(profile_number.getText().toString()) && !TextUtils.isEmpty(profile_name.getText().toString()) && profileimage.getDrawable()!=null){

                    if(isValidMail(profile_email.getText().toString())){


                        if(mCurrentPhotoPath==null){



                            showpDialog();

                            urlimage();

                            CallApiProfile(PatientId,profile_name.getText().toString(),profile_number.getText().toString(),profile_email.getText().toString(),profile_address.getText().toString());



                        }
                        else {

                            showpDialog();

                            profileimagebase64=encodeImage(mCurrentPhotoPath);

                            CallApiProfile(PatientId,profile_name.getText().toString(),profile_number.getText().toString(),profile_email.getText().toString(),profile_address.getText().toString());


                        }



                      /*  if(mCurrentPhotoPath==null){

                            if(profileimagebase64==null){

                              // profileimagebase64="";

                                Toast.makeText(EditProfileActivity.this, "Profile image needed", Toast.LENGTH_SHORT).show();


                            }else {

                                showpDialog();


                                urlimage();
                                CallApiProfile(PatientId,profile_name.getText().toString(),profile_number.getText().toString(),profile_email.getText().toString(),profile_address.getText().toString());

                            }


                        }
                        else {
                            showpDialog();

                            profileimagebase64=encodeImage(mCurrentPhotoPath);

                            CallApiProfile(PatientId,profile_name.getText().toString(),profile_number.getText().toString(),profile_email.getText().toString(),profile_address.getText().toString());


                        }*/






                    }else {

                        Snackbar snackbar=Snackbar.make(findViewById(android.R.id.content),"Enter Valid Email",Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }




                }else if(TextUtils.isEmpty(profile_name.getText().toString())){
                   // hidepDialog();

                    Snackbar snackbar=Snackbar.make(findViewById(android.R.id.content),"Enter Name",Snackbar.LENGTH_LONG);
                    snackbar.show();

                }else if(TextUtils.isEmpty(profile_number.getText().toString())){

                    Snackbar snackbar=Snackbar.make(findViewById(android.R.id.content),"Enter Number",Snackbar.LENGTH_LONG);
                    snackbar.show();

                }else if(TextUtils.isEmpty(profile_email.getText().toString())){

                    Snackbar snackbar=Snackbar.make(findViewById(android.R.id.content),"Enter Email",Snackbar.LENGTH_LONG);
                    snackbar.show();

                }else if(TextUtils.isEmpty(profile_address.getText().toString())){

                    Snackbar snackbar=Snackbar.make(findViewById(android.R.id.content),"Enter Address",Snackbar.LENGTH_LONG);
                    snackbar.show();

                }else if(profileimage.getDrawable()==null){

                    Snackbar snackbar=Snackbar.make(findViewById(android.R.id.content),"You must update Profile photo",Snackbar.LENGTH_LONG);
                    snackbar.show();

                }


            }
        });

        
        


     /*  if(validateEmailAddress()) {

       }

*/





    }

    private void previewplaceholerImage(final ImageView imageView) {




        dialog.setContentView(R.layout.image_layout);
        ImageView preview = (ImageView) dialog.findViewById(R.id.prevewimage);
        TextView retake = (TextView) dialog.findViewById(R.id.retake);
        TextView ok = (TextView) dialog.findViewById(R.id.ok);

        retake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView.setImageDrawable(null);
                dialog.dismiss();

            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        dialog.show();


    }

    private void CallApiProfile(final String id, final String name, final String phone, final String email, final String address) {



        //url = "http://httpbin.org/post";

        Log.i("pstient_id",id);
        Log.i("imga64",profileimagebase64);

       // http://nbayjobs.com/raksha/api/profileupdate.php?pid=11&name=zxcwe&phone=9556845543&email=test@gmail.com&address=dfgdf

        RequestQueue queue= Volley.newRequestQueue(this);

        final  String url= APIConfig.MAIN_API+APIConfig.EditProfile;


        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {

                        hidepDialog();
                        // response
                        Log.d("Response", response);

                        String sucess,msg,otp;

                        try {
                            JSONObject object=new JSONObject(response);

                            sucess=object.getString("success");
                            msg=object.getString("message");
                            otp=object.getString("OTP");

                            if(sucess.equals("1")){

                                Log.i("otp",otp);

                                if(otp.equals("null"))
                                {

                                    Toast.makeText(EditProfileActivity.this, "Profile Updated Successfully", Toast.LENGTH_SHORT).show();

                                    session.createLoginSession(id,"Patient",name,phone,email,address);

                                   // session.createLoginSession();
                                   finish();

                                }else {
                                    createotpchangeAlert(phone, name, email, address);

                                }
                            }
                            else if(sucess.equals("0")){
                                Toast.makeText(EditProfileActivity.this, msg, Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        hidepDialog();
                        // error
                        Log.d("Error.Response", error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("pid", id);
                params.put("name", name);
                params.put("phone", phone);
                params.put("email", email);
                params.put("address", address);
                params.put("image", profileimagebase64);


                return params;
            }
        };

        queue.add(postRequest);
        postRequest.setRetryPolicy(new DefaultRetryPolicy(50000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));



    }

    private void createotpchangeAlert(final String number,final String name, final String email, final String address) {


        dialog.setContentView(R.layout.changenumber);

        TextView nubershown,submitnumb,OTPresend;
        final EditText OTPnumb;


        nubershown=(TextView) dialog.findViewById(R.id.otpsend_number_alert);
        submitnumb=(TextView) dialog.findViewById(R.id.phy_sub1_alert);
        OTPnumb=(EditText) dialog.findViewById(R.id.otp_alert);
        OTPresend=(TextView) dialog.findViewById(R.id.resend_otp_alert);

        nubershown.setText("+91-"+number);

        submitnumb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!TextUtils.isEmpty(OTPnumb.getText().toString())){

                    showpDialog();

                    callSigninAPI(number,OTPnumb.getText().toString(),name,email,address);

                    dialog.dismiss();


                }else {

                    Toast.makeText(EditProfileActivity.this, "Enter OTP", Toast.LENGTH_SHORT).show();
                   // Toast.makeText(this, "Enter OTP", LENGTH_SHORT).show();
                }
            }
        });


        OTPresend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showpDialog();




                    callResendOTPapi(number);



            }
        });


        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.show();





    }

    private boolean validateEmailAddress(String emailAddress){
        String  expression="^[\\w\\-]([\\.\\w])+[\\w]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = emailAddress;
        Pattern pattern = Pattern.compile(expression,Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        return matcher.matches();
    }

    private boolean isValidMail(String email) {

        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }


    private void callSigninAPI(final String number, final String otpnumber, final String name, final String email, final String address) {

        RequestQueue queue = Volley.newRequestQueue(this);  // this = context



        // http://nbayjobs.com/raksha/api/patient/login.php?phone=9556845541&otp=396485

        // http://nbayjobs.com/raksha/api/patient/login.php?mobile=8248094245&device_id=396485

        final  String Url = APIConfig.MAIN_API+APIConfig.PATIENT+APIConfig.LOGIN;

        StringRequest postRequest=new StringRequest(Request.Method.POST, Url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("Response", response);
                hidepDialog();

                try {
                    JSONObject responcestring=new JSONObject(response);

                    String msg,success,error,otp,pid;

                    success=responcestring.getString("success");
                    error=responcestring.getString("error");
                    msg=responcestring.getString("message");

                    Log.d("msg", msg);

                    pid=responcestring.getString("pid");

                    if(!pid.equals("null"))
                    {
                        Log.d("msg", msg);



                        // otp=responcestring.getString("otp");

                        Log.d("pid", pid);

                      //  session.createloginNumber(phonenumber.getText().toString().trim(),"Patient",pid);


                        session.createLoginSession(pid,"Patient",name,number,email,address);


                       // Toast.makeText(RegisterActivity.this, msg, Toast.LENGTH_SHORT).show();
                     //   Intent i=new Intent(RegisterActivity.this,MainActivity.class);
                       // startActivity(i);
                        finish();


                    }else {
                        Log.d("msg", msg);

                        Toast.makeText(EditProfileActivity.this, msg, Toast.LENGTH_SHORT).show();




                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d("Response", error.toString());



            }
        })

        {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("phone",number );
                params.put("otp", otpnumber);


                return params;
            }
        };
        queue.add(postRequest);
        postRequest.setRetryPolicy(new DefaultRetryPolicy(50000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));





    }


    private void callResendOTPapi(final String number) {

        RequestQueue queue = Volley.newRequestQueue(this);  // this = context

        //http://nbayjobs.com/raksha/api/patient/login.php?number=9865485&type=resend



        final  String Url = APIConfig.MAIN_API+APIConfig.PATIENT+APIConfig.LOGIN;

        StringRequest postRequest=new StringRequest(Request.Method.POST, Url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("Response", response);
                hidepDialog();

                try {
                    JSONObject responcestring=new JSONObject(response);




                    String msg,success,error,otp;

                    success=responcestring.getString("success");
                    error=responcestring.getString("error");
                    msg=responcestring.getString("message");

                    Log.d("msg", msg);



                    if(success.equals("null")){
                        Log.d("msg", msg);

                        otp=responcestring.getString("otp");

                        Log.d("OTP", otp);










                    }else if(success.equals("0")){
                        Log.d("msg", msg);




                    }












                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hidepDialog();

                Log.d("Response", error.toString());



            }
        })

        {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("number", number);
                params.put("type", "resend");

                return params;
            }
        };
        queue.add(postRequest);
        postRequest.setRetryPolicy(new DefaultRetryPolicy(50000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));






    }


    private void CallProileApi(final String userIds) {

        // String Url="http://nbayjobs.com/raksha/api/profileshow.php?pid=1";

         final String Url= APIConfig.MAIN_API+APIConfig.ShowProfile;

        RequestQueue queue=Volley.newRequestQueue(this);

        StringRequest postRequest = new StringRequest(Request.Method.POST, Url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                        hidepDialog();

                        String sucess,msg;


                        try {
                            JSONObject object=new JSONObject(response);

                            sucess=object.getString("success");

                            if(sucess.equals("1")) {


                                hidepDialog();

                                String name, phone, address, email, proimg=null;

                                name = object.getString("name");

                                phone=object.getString("phone"); ;
                                email =object.getString("email");;
                                address =object.getString("address");;

                                if(object.has("image"))
                                    proimg =object.getString("image");;

                                if (name.equals("null")) {


                                } else {

                                    Log.i("name", name);
                                    Log.i("address", address);
                                    Log.i("email", email);
                                    Log.i("phone", phone);
                                  //  Log.i("img", proimg);


                                    if (proimg != null) {

                                        imagetypesurl = "url";

                                        Imageurl = proimg;

                                        Glide.with(EditProfileActivity.this).load(proimg).asBitmap().diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.drawable.person).into(profileimage);
                                    } else {
                                        profileimagebase64 = "";
                                    }


                                    profile_address.setText(address);
                                    profile_name.setText(name);
                                    profile_number.setText(phone);
                                    profile_email.setText(email);


                                }
                            }else if(sucess.equals("0")){


                                msg=object.getString("message");
                                Toast.makeText(EditProfileActivity.this, msg, Toast.LENGTH_SHORT).show();

                            }



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.toString());

                        hidepDialog();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("pid", userIds);


                return params;
            }
        };
        queue.add(postRequest);

        postRequest.setRetryPolicy(new DefaultRetryPolicy(500000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));




     /*   final ApiInterface service= ApiClient.getClient().create(ApiInterface.class);

        Call<ProfileShowModel> descriptionCall= service.Profilelist(userIds);


        descriptionCall.enqueue(new Callback<ProfileShowModel>() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onResponse(Call<ProfileShowModel> call, retrofit2.Response<ProfileShowModel> response) {

                Log.i("Responce",response.raw().toString());

                Log.i("Responcefull",response.toString());

                String sucess,error;

                sucess= String.valueOf(response.body().getSuccess());

                if(sucess.equals("1"))
                {



                    hidepDialog();

                    String name,phone,address,email,proimg;

                    name=response.body().getName();

                    phone=response.body().getPhone();
                    address=response.body().getAddress();
                    email=response.body().getEmail();
                    proimg= response.body().getImage();

                    if(name==null)
                    {


                    }else
                    {

                        Log.i("name",name);
                        Log.i("address",address);
                        Log.i("email",email);
                        Log.i("phone",phone);
                        Log.i("img",proimg);




                        if(proimg!=null) {

                            imagetypesurl = "url";

                            Imageurl = proimg;

                            Glide.with(EditProfileActivity.this).load(proimg).asBitmap().diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.drawable.person).into(profileimage);
                        }else {
                            profileimagebase64="";
                        }


                        profile_address.setText(address);
                        profile_name.setText(name);
                        profile_number.setText(phone);
                        profile_email.setText(email);


                    }


                }
                else if(sucess.equals("0"))
                {

                    //  Toast.makeText(getActivity(), "not null", Toast.LENGTH_SHORT).show();


                    hidepDialog();

                }


            }

            @Override
            public void onFailure(Call<ProfileShowModel> call, Throwable t) {

                hidepDialog();
            }
        });
*/

                }



    public void   callTitleCenterFont()
    {
        this.getSupportActionBar().setDisplayShowCustomEnabled(true);
        this.getSupportActionBar().setDisplayShowTitleEnabled(false);

        LayoutInflater inflator = LayoutInflater.from(this);
        View vr = inflator.inflate(R.layout.titleview, null);

        ActionBar.LayoutParams p = new ActionBar.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);

//if you need to customize anything else about the text, do it here.
//I'm using a custom TextView with a custom font in my layout xml so all I need to do is set title
        ((TextView)vr.findViewById(R.id.title)).setText(this.getTitle());

//assign the view to the actionbar
        this.getSupportActionBar().setCustomView(vr);
    }


    private String encodeImage(String path) {
        File imagefile = new File(path);
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(imagefile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //Bitmap bm = BitmapFactory.decodeStream(fis);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 8;

        Bitmap bm = BitmapFactory.decodeFile(path, options);
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.DEFAULT);
        //Base64.de
        return encImage;

    }


    public boolean isWriteStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG, "Permission is granted2");
                return true;
            } else {

                Log.v(TAG, "Permission is revoked2");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG, "Permission is granted2");
            return true;
        }

    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
                Log.i(TAG, "file created");
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {


                String authority = getApplicationContext().getPackageName() + ".fileprovider";
                Log.i(TAG, "autho :" + authority);

                Uri photoURI = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".fileprovider", photoFile);

                Log.i(TAG, "photo :" + photoURI);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, 1);


               /* Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);*/
            } else {
                Log.i(TAG, "null");
            }
        }
    }
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */);

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }



    @Override

    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {


        //  super.onActivityResult(requestCode, resultCode, data);

          Log.i("reduestcode:", String.valueOf(requestCode));
         Log.i("resultcode:", String.valueOf(resultCode));


        //  Toast.makeText(this, "camera", Toast.LENGTH_SHORT).show();


        switch (requestCode) {

            case REQUEST_TAKE_PHOTO:

            if (resultCode == RESULT_OK) {

               // if (requestCode == REQUEST_TAKE_PHOTO) {

                    //path1 = mCurrentPhotoPath;

                   // Toast.makeText(this, "camera", Toast.LENGTH_SHORT).show();


                    Log.i("path", mCurrentPhotoPath);

                    try {
                        setReducedImageSize(profileimage, mCurrentPhotoPath);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    // profileimagebase64=encodeImage(mCurrentPhotoPath);

                }else {

                mCurrentPhotoPath=null;
            }

              break;

                case REQUEST_TAKE_GALLERY_PHOTO:

                if (resultCode == RESULT_OK) {

                   // Toast.makeText(this, "gallery", Toast.LENGTH_SHORT).show();


                    Uri selectedImage = data.getData();

                    String[] filePath = {MediaStore.Images.Media.DATA};

                    Cursor c = getContentResolver().query(selectedImage, filePath, null, null, null);

                    c.moveToFirst();

                    int columnIndex = c.getColumnIndex(filePath[0]);

                    String picturePath = c.getString(columnIndex);

                    c.close();

                    mCurrentPhotoPath = picturePath;

                    Log.i("path", mCurrentPhotoPath);


                    //  profileimagebase64=encodeImage(mCurrentPhotoPath);


                    try {
                        setReducedImageSize(profileimage, mCurrentPhotoPath);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    // Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));

                    // Log.i("path of image fr", picturePath + "");

                    // i1.setImageBitmap(thumbnail);

                }
                break;
            }
        }






   public void setReducedImageSize(ImageView img, String path) throws IOException {
        //  int targetImageViewWidth = i1.getWidth();
        //int targetImageViewHeight = i1.getHeight();

      //  img.setImageDrawable(null);

       /* BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int cameraImageWidth = bmOptions.outWidth;
        int cameraImageHeight = bmOptions.outHeight;

       // int scaleFactor = Math.min(cameraImageWidth/targetImageViewWidth, cameraImageHeight/targetImageViewHeight);
       // bmOptions.inSampleSize = scaleFactor;
        bmOptions.inJustDecodeBounds = false;
        Bitmap photoReducedSizeBitmp = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        img.setImageBitmap(photoReducedSizeBitmp);
        img.setScaleType(ImageView.ScaleType.FIT_XY);*/


        Matrix mat = new Matrix();

        ExifInterface exif = new ExifInterface(path);
        String orientstring = exif.getAttribute(ExifInterface.TAG_ORIENTATION);
        int orientation = orientstring != null ? Integer.parseInt(orientstring) : ExifInterface.ORIENTATION_NORMAL;
        int rotateangle = 0;
        if (orientation == ExifInterface.ORIENTATION_ROTATE_90) rotateangle = 90;
        if (orientation == ExifInterface.ORIENTATION_ROTATE_180) rotateangle = 180;
        if (orientation == ExifInterface.ORIENTATION_ROTATE_270) rotateangle = 270;

        mat.setRotate(rotateangle, (float) img.getWidth() / 2, (float) img.getHeight() / 2);
        File f = new File(path);

        Log.i("path",path);
        Bitmap bmpPic = BitmapFactory.decodeStream(new FileInputStream(f), null, null);
       // Bitmap bmpPic1 = Bitmap.createBitmap(bmpPic, 0, 0, bmpPic.getWidth(), bmpPic.getHeight(), true);

        img.setImageBitmap(bmpPic);


    }

    private void urlimage()
    {
        BitmapDrawable drawable = (BitmapDrawable) profileimage.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        profileimagebase64 = encodeToBase64(bitmap, Bitmap.CompressFormat.JPEG, 100);


    }
    public static String encodeToBase64(Bitmap image, Bitmap.CompressFormat compressFormat, int quality)
    {
        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
        image.compress(compressFormat, quality, byteArrayOS);
        return Base64.encodeToString(byteArrayOS.toByteArray(), Base64.DEFAULT);
    }

/*

    private String encodeImage(String path) {
        File imagefile = new File(path);
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(imagefile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //Bitmap bm = BitmapFactory.decodeStream(fis);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 8;

        Bitmap bm = BitmapFactory.decodeFile(path, options);
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.DEFAULT);
        //Base64.de
        return encImage;

    }


*/

    private void selectImage1() {

        //imageCode = 1;


        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};


        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(EditProfileActivity.this,R.style.CustomDialogTheme);

        //builder.setTitle("Add Photo!");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override

            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Take Photo"))

                {

                    dispatchTakePictureIntent();
                  /*  Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                    File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");

                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));

                    startActivityForResult(intent, 1);
*/
                } else if (options[item].equals("Choose from Gallery"))

                {

                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                    startActivityForResult(intent, REQUEST_TAKE_GALLERY_PHOTO);


                } else if (options[item].equals("Cancel")) {

                    dialog.dismiss();

                }

            }

        });


        androidx.appcompat.app.AlertDialog alrt=builder.create();

        alrt.getWindow().getAttributes().windowAnimations = R.style.dialog_animation;


        alrt.show();

    }

    private void previewImage(final ImageView imgview, String path) throws IOException {

        dialog.setContentView(R.layout.image_layout);
        ImageView preview = (ImageView) dialog.findViewById(R.id.prevewimage);
        TextView retake = (TextView) dialog.findViewById(R.id.retake);
        TextView ok = (TextView) dialog.findViewById(R.id.ok);



       /* Matrix mat = new Matrix();

        ExifInterface exif = new ExifInterface(path);
        String orientstring = exif.getAttribute(ExifInterface.TAG_ORIENTATION);
        int orientation = orientstring != null ? Integer.parseInt(orientstring) : ExifInterface.ORIENTATION_NORMAL;
        int rotateangle = 0;
        if (orientation == ExifInterface.ORIENTATION_ROTATE_90) rotateangle = 90;
        if (orientation == ExifInterface.ORIENTATION_ROTATE_180) rotateangle = 180;
        if (orientation == ExifInterface.ORIENTATION_ROTATE_270) rotateangle = 270;

        mat.setRotate(rotateangle, (float) preview.getWidth() / 2, (float) preview.getHeight() / 2);*/

        File f = new File(path);
        Bitmap bmpPic = BitmapFactory.decodeStream(new FileInputStream(f), null, null);
        //Bitmap bmpPic1 = Bitmap.createBitmap(bmpPic, 0, 0, bmpPic.getWidth(), bmpPic.getHeight(), mat, true);

        preview.setImageBitmap(bmpPic);

        retake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgview.setImageDrawable(null);
                dialog.dismiss();

            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        dialog.show();
    }



    private void previewUrlImage(final ImageView imgview, String path) throws IOException {

        dialog.setContentView(R.layout.image_layout);
        ImageView preview = (ImageView) dialog.findViewById(R.id.prevewimage);
        TextView retake = (TextView) dialog.findViewById(R.id.retake);
        TextView ok = (TextView) dialog.findViewById(R.id.ok);


       /* Dialog settingsDialog = new Dialog(this);
        settingsDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        settingsDialog.setContentView(getLayoutInflater().inflate(R.layout.image_layout
                , null));
        settingsDialog.show();*/


        Glide.with(EditProfileActivity.this).load(path).asBitmap().diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.drawable.ic_person).into(preview);



        retake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgview.setImageDrawable(null);
                dialog.dismiss();

            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        dialog.show();
    }




}
