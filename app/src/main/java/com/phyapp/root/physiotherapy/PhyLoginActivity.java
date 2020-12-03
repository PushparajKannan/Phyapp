package com.phyapp.root.physiotherapy;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import com.google.android.material.snackbar.Snackbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.iid.FirebaseInstanceId;
import com.phyapp.root.physiotherapy.APICONFIG.APIConfig;
import com.phyapp.root.physiotherapy.Utils.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import dmax.dialog.SpotsDialog;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CALL_PHONE;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.READ_PHONE_STATE;
import static android.Manifest.permission.READ_SMS;
import static android.Manifest.permission.RECEIVE_SMS;
import static android.Manifest.permission.SEND_SMS;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class PhyLoginActivity extends AppCompatActivity {
    TextView phylogin;
    private static final int PERMISSION_REQUEST_CODE = 200;

    String androidID;
    EditText Username,password;
    android.app.AlertDialog pDialog;
    SessionManager session;
    String refreshedToken;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phy_login);


        callTitleCenterFont();

        refreshedToken = FirebaseInstanceId.getInstance().getToken();




        if (checkPermission()) {
           // Toast.makeText(this, "alreday got", Toast.LENGTH_SHORT).show();

            // Snackbar.make(view, "Permission already granted.", Snackbar.LENGTH_LONG).show();

        } else{
            requestPermission();
           // Toast.makeText(this, "Request" , Toast.LENGTH_SHORT).show();

            //Snackbar.make(view, "Please request permission.", Snackbar.LENGTH_LONG).show();
        }



        androidID= Settings.Secure.getString(this.getContentResolver(),Settings.Secure.ANDROID_ID);

        pDialog = new SpotsDialog(this,R.style.Custom);
        pDialog.setCancelable(false);

        session=new SessionManager(this);

        phylogin=(TextView) findViewById(R.id.phy_login);

        Username=(EditText) findViewById(R.id.edit1);
        password=(EditText) findViewById(R.id.edit2);

        phylogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(!TextUtils.isEmpty(Username.getText().toString()) && !TextUtils.isEmpty(password.getText().toString())){

                    showpDialog();

                    callDoctorLoginAPI(Username.getText().toString(),password.getText().toString(),androidID);

                }else if(TextUtils.isEmpty(Username.getText().toString())){

                    Snackbar snackbar=Snackbar.make(findViewById(android.R.id.content),"Enter Username",Snackbar.LENGTH_LONG);
                    // Change the Snackbar default background color
                 //   snackbar.getView().setBackgroundColor(ContextCompat.getColor(PhyLoginActivity.this, R.color.Red));
                    View snackbarView = snackbar.getView();
                    TextView tv = (TextView) snackbarView.findViewById(com.google.android.material.R.id.snackbar_text);
                   // tv.setTextColor(Color.RED);
                   // tv.setTextSize(18);
                   // tv.setGravity(Gravity.CENTER);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                        tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    } else {
                        tv.setGravity(Gravity.CENTER_HORIZONTAL);
                    }

                    //snackbar.setBackgroundColor(getApplication().getResources().getColor(R.color.btn_background_color));
                    snackbar.show();
                }
                else if(TextUtils.isEmpty(password.getText().toString()))
                {
                    Snackbar snackbar=Snackbar.make(findViewById(android.R.id.content),"Enter Password",Snackbar.LENGTH_LONG);
                    // Change the Snackbar default background color
                   // snackbar.getView().setBackgroundColor(ContextCompat.getColor(PhyLoginActivity.this, R.color.Red));
                    View snackbarView = snackbar.getView();
                    TextView tv = (TextView) snackbarView.findViewById(com.google.android.material.R.id.snackbar_text);
                   // tv.setTextSize(18);
                   // tv.setTextColor(Color.RED);
                   // tv.setGravity(Gravity.CENTER);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                        tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    } else {
                        tv.setGravity(Gravity.CENTER_HORIZONTAL);
                    }

                    //snackbar.setBackgroundColor(getApplication().getResources().getColor(R.color.btn_background_color));
                    snackbar.show();
                }

              //  callDoctorLoginAPI();


            }
        });



    }

    private void callDoctorLoginAPI(final String user, final String pass, final String id) {

     //   http://nbayjobs.com/raksha/api/doctor/login.php?username=fgtasia&password=1MZWeIfo&deviceid=12

        RequestQueue queue= Volley.newRequestQueue(this);

        String url= APIConfig.MAIN_API+APIConfig.Doctor_login;



        //url = "http://httpbin.org/post";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        hidepDialog();

                        // response
                        Log.d("Response", response);

                        String sucess,msg,doctorid,doctorname,doctormail,doctornumber;


                        try {
                            JSONObject object=new JSONObject(response);

                            sucess=object.getString("success");

                            if(sucess.equals("1")){

                                doctorid=object.getString("doctorid");
                                doctorname=object.getString("doctor");
                                doctornumber=object.getString("mobile");
                                doctormail=object.getString("email");

                                session.createDoctorLoginSession(doctorid,"Doctor",doctorname,doctornumber,doctormail);
                                Toast.makeText(PhyLoginActivity.this, "Successfully Logged", Toast.LENGTH_SHORT).show();

                                Intent i=new Intent(PhyLoginActivity.this,PhyMainActivity.class);
                                startActivity(i);
                                finish();




                            }else if(sucess.equals("0"))
                            {

                                msg=object.getString("message");
                                Toast.makeText(PhyLoginActivity.this, msg, Toast.LENGTH_SHORT).show();

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

                        Toast.makeText(PhyLoginActivity.this, "Check your internet connection", Toast.LENGTH_SHORT).show();

                        // error
                        Log.d("Error.Response", error.toString());




                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("username",user );
                params.put("password",pass );
                params.put("deviceid",id );
                params.put("tokenkey",refreshedToken );

                return params;
            }
        };
        queue.add(postRequest);

        postRequest.setRetryPolicy(new DefaultRetryPolicy(500000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));




    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {

                    boolean locationAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean cameraAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    //boolean contactAccepted = grantResults[2] == PackageManager.PERMISSION_GRANTED;
                  //  boolean callAccepted = grantResults[3] == PackageManager.PERMISSION_GRANTED;
                    boolean Readstorage = grantResults[2] == PackageManager.PERMISSION_GRANTED;
                    boolean writStorage = grantResults[3] == PackageManager.PERMISSION_GRANTED;
                   // boolean Readsms = grantResults[6] == PackageManager.PERMISSION_GRANTED;
                    //boolean SendSms = grantResults[7] == PackageManager.PERMISSION_GRANTED;
                    //boolean ReceiveSms = grantResults[8] == PackageManager.PERMISSION_GRANTED;

                  //  if (locationAccepted && cameraAccepted && contactAccepted && callAccepted && Readstorage && writStorage && Readsms && ReceiveSms && SendSms)
                    if (locationAccepted && cameraAccepted   && Readstorage && writStorage )
                    {

                    }
                    //  Snackbar.make(view, "Permission Granted, Now you can access location data and camera.", Snackbar.LENGTH_LONG).show();
                    else {

                        // Snackbar.make(view, "Permission Denied, You cannot access location data and camera.", Snackbar.LENGTH_LONG).show();

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(ACCESS_FINE_LOCATION)) {
                                showMessageOKCancel("You need to allow access to both the permissions",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{ACCESS_FINE_LOCATION,WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE, CAMERA},
                                                            PERMISSION_REQUEST_CODE);
                                                }
                                            }
                                        });
                                return;
                            }
                        }

                    }
                }


                break;
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(PhyLoginActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }


    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_FINE_LOCATION);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA);
        //int result2 = ContextCompat.checkSelfPermission(getApplicationContext(), READ_PHONE_STATE);
        //int result3= ContextCompat.checkSelfPermission(getApplicationContext(),CALL_PHONE);
        int result4 = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int result5 = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);
       // int result6 = ContextCompat.checkSelfPermission(getApplicationContext(), READ_SMS);
       // int result7 = ContextCompat.checkSelfPermission(getApplicationContext(), SEND_SMS);
       // int result8 = ContextCompat.checkSelfPermission(getApplicationContext(), RECEIVE_SMS);


        return result == PackageManager.PERMISSION_GRANTED &&
                result1 == PackageManager.PERMISSION_GRANTED &&
              //  result2== PackageManager.PERMISSION_GRANTED &&
               // result3==PackageManager.PERMISSION_GRANTED &&
                result4==PackageManager.PERMISSION_GRANTED &&
                result5==PackageManager.PERMISSION_GRANTED ;
               // result6==PackageManager.PERMISSION_GRANTED &&
               // result7==PackageManager.PERMISSION_GRANTED &&
                //result8==PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION,WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE, CAMERA}, PERMISSION_REQUEST_CODE);

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


    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }



}
