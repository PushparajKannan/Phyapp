package com.phyapp.root.physiotherapy;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.provider.Settings;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.phyapp.root.physiotherapy.Utils.SessionManager;

import java.net.InetAddress;
import java.util.HashMap;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CALL_PHONE;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.READ_PHONE_STATE;
import static android.Manifest.permission.READ_SMS;
import static android.Manifest.permission.RECEIVE_SMS;
import static android.Manifest.permission.SEND_SMS;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class SelectionActivity extends AppCompatActivity {

    Button patient,doctors;
    private SessionManager session;
    String Session_user_type,usernumber;
    private static final int PERMISSION_REQUEST_CODE = 200;
    private BottomSheetDialog mBottomSheetDialog;
    ImageView phy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);

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
        this.getSupportActionBar().setCustomView(vr,p);


        if (checkPermission()) {
            //Toast.makeText(this, "alreday got", Toast.LENGTH_SHORT).show();

            // Snackbar.make(view, "Permission already granted.", Snackbar.LENGTH_LONG).show();

        } else{
            requestPermission();
            //Toast.makeText(this, "Request" , Toast.LENGTH_SHORT).show();

            //Snackbar.make(view, "Please request permission.", Snackbar.LENGTH_LONG).show();
        }


        CallinterISON();




        session = new SessionManager(getApplicationContext());

        HashMap<String, String> user = session.getUserDetails();

        Session_user_type=user.get(SessionManager.KEY_LOGIN_TYPE);
        usernumber=user.get(SessionManager.KEY_PROFILE_NUMBER);

        patient=(Button) findViewById(R.id.select_button1);
        doctors=(Button) findViewById(R.id.select_button2);
        phy=(ImageView) findViewById(R.id.seltimg);







        if (session.isLoggedIn()) {


            if(Session_user_type.equals("Patient"))
            {
                Intent i=new Intent(SelectionActivity.this,MainActivity.class);
                //i.putExtra("number",usernumber);
                startActivity(i);
                finish();
            }
            else if(Session_user_type.equals("Doctor"))
            {
                Intent i=new Intent(SelectionActivity.this,PhyMainActivity.class);
                startActivity(i);
                finish();

               //Toast.makeText(this, "Doctors", Toast.LENGTH_SHORT).show();
            }
            else {
               // Toast.makeText(this, "null", Toast.LENGTH_SHORT).show();
            }


            //logoutviewFalse();
            //  Toast.makeText(MainActivity.this, "Please Log In", Toast.LENGTH_LONG).show();
            // startActivity(new Intent(MainActivity.this, LoginActivity.class));
            // finish();   //finish current activity
        }




        patient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(SelectionActivity.this,OTPLoginActivity.class);
                startActivity(i);
                //finish();
            }
        });

        doctors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               /* Intent i=new Intent(SelectionActivity.this,PhyLoginActivity.class);
                startActivity(i);
                finish();
                //callBottomDialog();*/
            }
        });

        phy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(SelectionActivity.this,PhyLoginActivity.class);
                startActivity(i);
                // finish();
            }
        });







    }
    private void callBottomDialog() {

        final View bottomSheetLayout = getLayoutInflater().inflate(R.layout.bottom_sheet_dialog, null);
        (bottomSheetLayout.findViewById(R.id.button_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBottomSheetDialog.dismiss();
            }
        });
        (bottomSheetLayout.findViewById(R.id.button_ok)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.dismiss();
                // Toast.makeText(getApplicationContext(), "Ok button clicked", Toast.LENGTH_SHORT).show();
            }
        });

        mBottomSheetDialog = new BottomSheetDialog(this);
        mBottomSheetDialog.setContentView(bottomSheetLayout);
        mBottomSheetDialog.show();
    }


    /*@Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {

                    boolean locationAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean cameraAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean contactAccepted = grantResults[2] == PackageManager.PERMISSION_GRANTED;
                    boolean callAccepted = grantResults[3] == PackageManager.PERMISSION_GRANTED;

                    if (locationAccepted && cameraAccepted && contactAccepted && callAccepted)
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
                                                    requestPermissions(new String[]{ACCESS_FINE_LOCATION, CAMERA,READ_PHONE_STATE,CALL_PHONE},
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
        new AlertDialog.Builder(SelectionActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }


    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_FINE_LOCATION);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA);
        int result2 = ContextCompat.checkSelfPermission(getApplicationContext(), READ_PHONE_STATE);
        int result3= ContextCompat.checkSelfPermission(getApplicationContext(),CALL_PHONE);

        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED && result2== PackageManager.PERMISSION_GRANTED && result3==PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION, CAMERA,READ_PHONE_STATE,CALL_PHONE}, PERMISSION_REQUEST_CODE);

    }

*/


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
      this.getSupportActionBar().setCustomView(vr,p);
  }



    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {

                    boolean locationAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean cameraAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean contactAccepted = grantResults[2] == PackageManager.PERMISSION_GRANTED;
                    boolean callAccepted = grantResults[3] == PackageManager.PERMISSION_GRANTED;
                    boolean Readstorage = grantResults[4] == PackageManager.PERMISSION_GRANTED;
                    boolean writStorage = grantResults[5] == PackageManager.PERMISSION_GRANTED;
                    boolean Sendsms = grantResults[6] == PackageManager.PERMISSION_GRANTED;
                    boolean Receivesms = grantResults[7] == PackageManager.PERMISSION_GRANTED;
                    boolean ReadSms = grantResults[8] == PackageManager.PERMISSION_GRANTED;

                    if (locationAccepted && cameraAccepted && contactAccepted && callAccepted && Readstorage && writStorage && ReadSms && Sendsms && Receivesms)
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
                                                    requestPermissions(new String[]{ACCESS_FINE_LOCATION,WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE, CAMERA,READ_PHONE_STATE,CALL_PHONE,SEND_SMS,RECEIVE_SMS,READ_SMS},
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
        new AlertDialog.Builder(SelectionActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }


    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_FINE_LOCATION);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA);
        int result2 = ContextCompat.checkSelfPermission(getApplicationContext(), READ_PHONE_STATE);
        int result3= ContextCompat.checkSelfPermission(getApplicationContext(),CALL_PHONE);
        int result4 = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int result5 = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);
        int result6 = ContextCompat.checkSelfPermission(getApplicationContext(), SEND_SMS);
        int result7 = ContextCompat.checkSelfPermission(getApplicationContext(), RECEIVE_SMS);
        int result8 = ContextCompat.checkSelfPermission(getApplicationContext(), READ_SMS);


        return result == PackageManager.PERMISSION_GRANTED &&
                result1 == PackageManager.PERMISSION_GRANTED &&
                result2== PackageManager.PERMISSION_GRANTED &&
                result3==PackageManager.PERMISSION_GRANTED &&
                result4==PackageManager.PERMISSION_GRANTED &&
                result5==PackageManager.PERMISSION_GRANTED &&
                result6==PackageManager.PERMISSION_GRANTED &&
                result7==PackageManager.PERMISSION_GRANTED &&
                result8==PackageManager.PERMISSION_GRANTED ;
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION,WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE, CAMERA,READ_PHONE_STATE,CALL_PHONE, SEND_SMS,RECEIVE_SMS,READ_SMS}, PERMISSION_REQUEST_CODE);

    }


    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

    public boolean isInternetAvailable() {
        try {
            InetAddress ipAddr = InetAddress.getByName("google.com");
            //You can replace it with your name
            return !ipAddr.equals("");

        } catch (Exception e) {
            return false;
        }
    }

    private void buildAlertMessageNointernet() {
        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this,R.style.CustomDialogTheme);
        builder.setTitle(Html.fromHtml("<font color='#dd0000'>Alert!</font>"));
        builder.setMessage(Html.fromHtml("<font color='#000000'>Your internet connection seems to be disabled, you must enable it to continue</font>"))
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {

                        Intent intent = new Intent(Settings.ACTION_SETTINGS);
                       // intent.setClassName("com.android.phone", "com.android.phone.NetworkSetting");
                       // startActivity(intent);
                        startActivityForResult(intent,13);

                        dialog.cancel();
                    }
                });
        // .setNegativeButton("No", null);
        final android.app.AlertDialog alert = builder.create();

        alert.getWindow().getAttributes().windowAnimations = R.style.dialog_animation;
        alert.show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode) {
            case 13:

                //CreateAlertDialogForProced();


                CallinterISON();

                break;
        }
    }

    private void CallinterISON() {
        if(isInternetAvailables()){



        }else {

            buildAlertMessageNointernet();

        }

    }

    private boolean isInternetAvailables() {
        ConnectivityManager mgr = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = mgr.getActiveNetworkInfo();

        if (netInfo != null) {
            if (netInfo.isConnected()) {
                // Internet Available
                return true;
            }else {
                //No internet
                return false;
            }
        } else {
            //No internet
            return false;
        }
    }
}
