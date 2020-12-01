package com.phyapp.root.physiotherapy;

import android.Manifest;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

import de.hdodenhof.circleimageview.CircleImageView;
import dmax.dialog.SpotsDialog;

public class PhyEditProfileActivity extends AppCompatActivity {

    private static final String TAG ="TAG" ;
    private static final int REQUEST_TAKE_PHOTO = 1;
    ImageView profileimage;
    private String mCurrentPhotoPath;

    SessionManager session;
    TextInputEditText fname,lname,phone,amil,address;
    String doctname,doctorid,doctorphone,doctormail,doctoraddress;
    FloatingActionButton fabasave,editimg;

    TextInputLayout adressmap;

    LinearLayout leni;

    String latituted,longitude;
    private int imageCode;
    android.app.AlertDialog pDialog;
    private String profileimagebase64,imagetypesurl;

    Dialog dialog;
    private String Imageurl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phy_edit_profile);
        callTitleCenterFont();


        session=new SessionManager(this);
        pDialog=new SpotsDialog(this,R.style.Custom);
        pDialog.setCancelable(false);
        dialog=new Dialog(this,R.style.CustomDialogAnimationss);

        profileimage=(CircleImageView) findViewById(R.id.Phy_profile_image);

        fname=(TextInputEditText) findViewById(R.id.name);
        lname=(TextInputEditText) findViewById(R.id.name_last);

        phone=(TextInputEditText) findViewById(R.id.phone);
        amil=(TextInputEditText) findViewById(R.id.mail);
        address=(TextInputEditText) findViewById(R.id.address);

        fabasave=(FloatingActionButton) findViewById(R.id.fabsave);
        editimg=(FloatingActionButton) findViewById(R.id.edimg);

        adressmap=(TextInputLayout) findViewById(R.id.location_phy);

        leni=(LinearLayout) findViewById(R.id.lenimap);





        HashMap<String,String> doctor=session.getUserDetails();

        doctname=doctor.get(session.KEY_USER_NAME);

        doctorid=doctor.get(session.KEY_USERID);

        doctoraddress=doctor.get(session.KEY_USERS_ADDRESS);

        doctorphone=doctor.get(session.KEY_PROFILE_NUMBER);

        doctormail=doctor.get(session.KEY_EMAIL);

        showpDialog();

        callDoctorProfileAPI(doctorid);


      /* // address = findViewById(R.id.txtLocationAddress);
        address.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        address.setSingleLine(true);
        address.setMarqueeRepeatLimit(-1);
        address.setSelected(true);*/



      //  name.setText(doctname);
       // phone.setText(doctorphone);
       // amil.setText(doctormail);
        //address.setText(doctoraddress);




      //  CallDoctorEditProfileApi();

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
                            } else if(mCurrentPhotoPath !=null) {
                                previewImage(profileimage, mCurrentPhotoPath);
                            }else {

                            }
                        } else {
                            previewImage(profileimage,mCurrentPhotoPath);
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    // Toast.makeText(ProcessActivity.this, "preview", Toast.LENGTH_SHORT).show();
                }

            }
        });


        editimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isWriteStoragePermissionGranted()) {

                    selectImage1();
                }

            }
        });






        fabasave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(!TextUtils.isEmpty(fname.getText().toString()) &&
                        !TextUtils.isEmpty(lname.getText().toString()) &&
                        !TextUtils.isEmpty(address.getText().toString()) &&
                        !TextUtils.isEmpty(phone.getText().toString()) &&
                        !TextUtils.isEmpty(amil.getText().toString())&&
                        profileimage.getDrawable()!=null){

                    if(isValidMail(amil.getText().toString())){

                        showpDialog();


                        if(mCurrentPhotoPath==null){
                            urlimage();

                            CallDoctorEditProfileApi(doctorid,fname.getText().toString(),
                                    lname.getText().toString(),phone.getText().toString(),
                                    amil.getText().toString(),address.getText().toString(),
                                    latituted,longitude);



                        }
                        else {
                            profileimagebase64=encodeImage(mCurrentPhotoPath);

                            CallDoctorEditProfileApi(doctorid,fname.getText().toString(),
                                    lname.getText().toString(),phone.getText().toString(),
                                    amil.getText().toString(),address.getText().toString(),
                                    latituted,longitude);


                        }



                    }else {
                        Snackbar snackbar=Snackbar.make(findViewById(android.R.id.content),"Enter Valid Mail",Snackbar.LENGTH_LONG);
                        // Change the Snackbar default background color


                        View snackbarView = snackbar.getView();
                        TextView tv = (TextView) snackbarView.findViewById(com.google.android.material.R.id.snackbar_text);
                      //  tv.setTextColor(Color.RED);

                        // snackbar.getView().setBackgroundColor(ContextCompat.getColor(PhyEditProfileActivity.this, R.color.white));


                        //snackbar.setBackgroundColor(getApplication().getResources().getColor(R.color.btn_background_color));
                        snackbar.show();
                    }
                }
                else if(TextUtils.isEmpty(fname.getText().toString()))
                {
                    Snackbar snackbar=Snackbar.make(findViewById(android.R.id.content),"Enter First Name",Snackbar.LENGTH_LONG);
                    // Change the Snackbar default background color
                   // snackbar.getView().setBackgroundColor(ContextCompat.getColor(PhyEditProfileActivity.this, R.color.white));


                    View snackbarView = snackbar.getView();
                    TextView tv = (TextView) snackbarView.findViewById(com.google.android.material.R.id.snackbar_text);
                  //  tv.setTextColor(Color.RED);
                    //snackbar.setBackgroundColor(getApplication().getResources().getColor(R.color.btn_background_color));
                    snackbar.show();

                }else if (TextUtils.isEmpty(lname.getText().toString())){
                    Snackbar snackbar=Snackbar.make(findViewById(android.R.id.content),"Enter Last Name",Snackbar.LENGTH_LONG);
                    // Change the Snackbar default background color
                    //snackbar.getView().setBackgroundColor(ContextCompat.getColor(PhyEditProfileActivity.this, R.color.white));
                    View snackbarView = snackbar.getView();
                    TextView tv = (TextView) snackbarView.findViewById(com.google.android.material.R.id.snackbar_text);
                    //tv.setTextColor(Color.RED);


                    //snackbar.setBackgroundColor(getApplication().getResources().getColor(R.color.btn_background_color));
                    snackbar.show();
                }else if(TextUtils.isEmpty(phone.getText().toString())){
                    Snackbar snackbar=Snackbar.make(findViewById(android.R.id.content),"Enter Phone Number",Snackbar.LENGTH_LONG);
                    // Change the Snackbar default background color
                    //snackbar.getView().setBackgroundColor(ContextCompat.getColor(PhyEditProfileActivity.this, R.color.white));
                    View snackbarView = snackbar.getView();
                    TextView tv = (TextView) snackbarView.findViewById(com.google.android.material.R.id.snackbar_text);
                   // tv.setTextColor(Color.RED);

                    //snackbar.setBackgroundColor(getApplication().getResources().getColor(R.color.btn_background_color));
                    snackbar.show();
                }else if(TextUtils.isEmpty(amil.getText().toString())){
                    Snackbar snackbar=Snackbar.make(findViewById(android.R.id.content),"Enter Mail ID",Snackbar.LENGTH_LONG);
                    // Change the Snackbar default background color
                   // snackbar.getView().setBackgroundColor(ContextCompat.getColor(PhyEditProfileActivity.this, R.color.white));
                    View snackbarView = snackbar.getView();
                    TextView tv = (TextView) snackbarView.findViewById(com.google.android.material.R.id.snackbar_text);
                    //tv.setTextColor(Color.RED);

                    //snackbar.setBackgroundColor(getApplication().getResources().getColor(R.color.btn_background_color));
                    snackbar.show();
                }else if(TextUtils.isEmpty(address.getText().toString())){
                    Snackbar snackbar=Snackbar.make(findViewById(android.R.id.content),"Enter Address",Snackbar.LENGTH_LONG);
                    // Change the Snackbar default background color
                  //  snackbar.getView().setBackgroundColor(ContextCompat.getColor(PhyEditProfileActivity.this, R.color.white));
                    View snackbarView = snackbar.getView();
                    TextView tv = (TextView) snackbarView.findViewById(com.google.android.material.R.id.snackbar_text);
                    //tv.setTextColor(Color.RED);

                    //snackbar.setBackgroundColor(getApplication().getResources().getColor(R.color.btn_background_color));
                    snackbar.show();
                }else if(profileimage.getDrawable()==null){
                    Snackbar snackbar=Snackbar.make(findViewById(android.R.id.content),"Profile image needed",Snackbar.LENGTH_LONG);
                    // Change the Snackbar default background color
                  //  snackbar.getView().setBackgroundColor(ContextCompat.getColor(PhyEditProfileActivity.this, R.color.white));
                    View snackbarView = snackbar.getView();
                    TextView tv = (TextView) snackbarView.findViewById(com.google.android.material.R.id.snackbar_text);
                   // tv.setTextColor(Color.RED);

                    //snackbar.setBackgroundColor(getApplication().getResources().getColor(R.color.btn_background_color));
                    snackbar.show();
                }


                //CallDoctorEditProfileApi();

               // Toast.makeText(PhyEditProfileActivity.this, "Profile Updated", Toast.LENGTH_SHORT).show();

               // finish();
            }
        });

        leni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(PhyEditProfileActivity.this,MapSelectionActivity.class);
                startActivityForResult(i,90);
            }
        });


    }


    private void previewImage(final ImageView imgview, String path) throws IOException {

        dialog.setContentView(R.layout.image_layout);
        ImageView preview = (ImageView) dialog.findViewById(R.id.prevewimage);
        TextView retake = (TextView) dialog.findViewById(R.id.retake);
        TextView ok = (TextView) dialog.findViewById(R.id.ok);



        Matrix mat = new Matrix();

        ExifInterface exif = new ExifInterface(path);
        String orientstring = exif.getAttribute(ExifInterface.TAG_ORIENTATION);
        int orientation = orientstring != null ? Integer.parseInt(orientstring) : ExifInterface.ORIENTATION_NORMAL;
        int rotateangle = 0;
        if (orientation == ExifInterface.ORIENTATION_ROTATE_90) rotateangle = 90;
        if (orientation == ExifInterface.ORIENTATION_ROTATE_180) rotateangle = 180;
        if (orientation == ExifInterface.ORIENTATION_ROTATE_270) rotateangle = 270;

        mat.setRotate(rotateangle, (float) preview.getWidth() / 2, (float) preview.getHeight() / 2);

        File f = new File(path);
        Bitmap bmpPic = BitmapFactory.decodeStream(new FileInputStream(f), null, null);
        Bitmap bmpPic1 = Bitmap.createBitmap(bmpPic, 0, 0, bmpPic.getWidth(), bmpPic.getHeight(), mat, true);

        preview.setImageBitmap(bmpPic1);

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


        Glide.with(PhyEditProfileActivity.this).load(path).asBitmap().diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.drawable.ic_person).into(preview);



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

    private void CallDoctorEditProfileApi(final String id, final String fnames, final String lnames, final String phones, final String mail, final String addre, final String latti, final String longgi) {



        RequestQueue queue=Volley.newRequestQueue(this);

        Log.i("id",id);
        Log.i("name1",fnames);
        Log.i("name2",lnames);
        Log.i("phone",phones);
        Log.i("mail",mail);
        Log.i("addr",addre);
        Log.i("lati",latti);
        Log.i("longi",longgi);
        Log.i("img",profileimagebase64);
      //  Log.i("id",id);


        //http://nbayjobs.com/raksha/api/doctor/editprofile.php?id=&title=&fname=&lname=&email=&mobile=&address=&lat=&long=&image=


       String  urls = APIConfig.MAIN_API+APIConfig.Doctor_EditProfile;

        StringRequest postRequest = new StringRequest(Request.Method.POST, urls,
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

                            if(sucess.equals("1")){

                                Toast.makeText(PhyEditProfileActivity.this, "Profile Updated Successfully", Toast.LENGTH_SHORT).show();
                                finish();
                            }else if (sucess.equals("0")){
                                Toast.makeText(PhyEditProfileActivity.this, "Profile  not Updated", Toast.LENGTH_SHORT).show();

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
                params.put("id", id);
                params.put("fname",fnames );
                params.put("lname", lnames);
                params.put("email", mail);
                params.put("mobile",phones );
                params.put("address",addre );
                params.put("lat", latti);
                params.put("long", longgi);
                params.put("image",profileimagebase64 );

                return params;
            }
        };
        queue.add(postRequest);
        postRequest.setRetryPolicy(new DefaultRetryPolicy(500000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

    }


    private void selectImage1() {

        imageCode = 1;


        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};


        AlertDialog.Builder builder = new AlertDialog.Builder(PhyEditProfileActivity.this,R.style.CustomDialogTheme);

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

                    startActivityForResult(intent, 2);


                } else if (options[item].equals("Cancel")) {

                    dialog.dismiss();

                }

            }

        });


        AlertDialog alrt=builder.create();

        alrt.getWindow().getAttributes().windowAnimations = R.style.dialog_animation;


        alrt.show();

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
        //  this.getSupportActionBar().setCustomView(vr,p);
        this.getSupportActionBar().setCustomView(vr);
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

      //  Log.i("reduestcode:", String.valueOf(requestCode));
       // Log.i("resultcode:", String.valueOf(resultCode));



        switch (requestCode)

        {

            case 1:

              //  Toast.makeText(this, "camera", Toast.LENGTH_SHORT).show();


                if (resultCode == RESULT_OK) {

              //  if (requestCode == REQUEST_TAKE_PHOTO) {

                    //path1 = mCurrentPhotoPath;





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

                    case 2:

                if (resultCode == RESULT_OK)
                {


                    Uri selectedImage = data.getData();

                    String[] filePath = {MediaStore.Images.Media.DATA};

                    Cursor c = getContentResolver().query(selectedImage, filePath, null, null, null);

                    c.moveToFirst();

                    int columnIndex = c.getColumnIndex(filePath[0]);

                    String picturePath = c.getString(columnIndex);

                    c.close();

                    mCurrentPhotoPath = picturePath;


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

            case 90:

               // Toast.makeText(this, "map", Toast.LENGTH_SHORT).show();


                if (resultCode == RESULT_OK) {
                    Bundle res = data.getExtras();
                    String addr;
                    // String result = res.getString("results");


                    latituted=res.getString("latitude");
                    longitude=res.getString("longitude");
                    addr=res.getString("address");
                    address.setText(addr);

                    Log.d("FIRST", "lati:"+latituted+"longitude:"+longitude+"address"+address);
                }
                break;


        }
        }


    void setReducedImageSize(ImageView img, String path) throws IOException {
        //  int targetImageViewWidth = i1.getWidth();
        //int targetImageViewHeight = i1.getHeight();

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
        Bitmap bmpPic = BitmapFactory.decodeStream(new FileInputStream(f), null, null);
        Bitmap bmpPic1 = Bitmap.createBitmap(bmpPic, 0, 0, bmpPic.getWidth(), bmpPic.getHeight(), mat, true);

        img.setImageBitmap(bmpPic1);


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





  /*  protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.i("reduestcode:", String.valueOf(requestCode));
        Log.i("resultcode:", String.valueOf(resultCode));

        switch(requestCode) {
            case 90:
                if (resultCode == RESULT_OK) {
                    Bundle res = data.getExtras();
                    String addr;
                    // String result = res.getString("results");


                    latituted=res.getString("latitude");
                    longitude=res.getString("longitude");
                    addr=res.getString("address");
                    address.setText(addr);

                    Log.d("FIRST", "lati:"+latituted+"longitude:"+longitude+"address"+address);
                }
                break;
        }
    }
*/




    /* private void previewImage(final ImageView imgview, String path) throws IOException {

      *//*  dialog.setContentView(R.layout.preview);
        ImageView preview = (ImageView) dialog.findViewById(R.id.prevewimage);
        TextView retake = (TextView) dialog.findViewById(R.id.retake);
        TextView ok = (TextView) dialog.findViewById(R.id.ok);

*//*
       *//* if (imagetypesurl.equals("url")) {

            Glide.with(ProcessActivity.this).load(path).asBitmap().diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.drawable.personprofile).into(imgview);
            path = "";
        } else {*//*


        Matrix mat = new Matrix();

        ExifInterface exif = new ExifInterface(path);
        String orientstring = exif.getAttribute(ExifInterface.TAG_ORIENTATION);
        int orientation = orientstring != null ? Integer.parseInt(orientstring) : ExifInterface.ORIENTATION_NORMAL;
        int rotateangle = 0;
        if (orientation == ExifInterface.ORIENTATION_ROTATE_90) rotateangle = 90;
        if (orientation == ExifInterface.ORIENTATION_ROTATE_180) rotateangle = 180;
        if (orientation == ExifInterface.ORIENTATION_ROTATE_270) rotateangle = 270;

        mat.setRotate(rotateangle, (float) preview.getWidth() / 2, (float) preview.getHeight() / 2);

        File f = new File(path);
        Bitmap bmpPic = BitmapFactory.decodeStream(new FileInputStream(f), null, null);
        Bitmap bmpPic1 = Bitmap.createBitmap(bmpPic, 0, 0, bmpPic.getWidth(), bmpPic.getHeight(), mat, true);

        preview.setImageBitmap(bmpPic1);
        //  }

        *//*Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse("file://" + picturePath),"image/*");
        startActivity(intent);
*//*
        retake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgview.setImageDrawable(null);
              //  dialog.dismiss();

            }
        });
       // ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

       // dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        //dialog.show();
    }*/


    private void callDoctorProfileAPI(final String doctorid) {

        RequestQueue queue= Volley.newRequestQueue(this);



        final String url= APIConfig.MAIN_API+APIConfig.Doctor_Profile;

        //url =  http://nbayjobs.com/raksha/api/doctor/profile.php?id=1;
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);

                        hidepDialog();

                        String sucess,firstname,lastname,mail,addre,phon,id,lati,longi,image;

                        try {
                            JSONObject obj=new JSONObject(response);

                            sucess=obj.getString("success");
                            if(sucess.equals("1")){

                                id=obj.getString("doctorid");
                                firstname=obj.getString("fname");
                                lastname=obj.getString("lname");
                                phon=obj.getString("mobile");
                                mail=obj.getString("email");
                                addre=obj.getString("address");
                                lati=obj.getString("lat");
                                longi=obj.getString("long");
                                image=obj.getString("image");

                                if(image!=null){

                                    imagetypesurl="url";

                                    Imageurl=image;

                                    Glide.with(PhyEditProfileActivity.this).load(image).asBitmap().diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.drawable.person).into(profileimage);

                                   // urlimage();



                                }else {
                                    profileimagebase64="";
                                }


                                fname.setText(firstname);
                                lname.setText(lastname);
                                address.setText(addre);
                                phone.setText(phon);
                                amil.setText(mail);
                                latituted=lati;
                                longitude=longi;



                              //  session.createLoginSession(id,"Doctor",name,phone,mail,address);

                                // locateaddressdetails(lat,lon);






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
                params.put("id",doctorid );
                //params.put("domain", "http://itsalif.info");

                return params;
            }
        };
        queue.add(postRequest);
        postRequest.setRetryPolicy(new DefaultRetryPolicy(500000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
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

    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
    private boolean isValidMail(String phone) {
        return Patterns.EMAIL_ADDRESS.matcher(phone).matches();
    }



}
