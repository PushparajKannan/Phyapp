package com.phyapp.root.physiotherapy;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CALL_PHONE;
import static android.Manifest.permission.CAMERA;

public class OTPLoginActivity extends AppCompatActivity {

    CardView welcome,otps,otpveri,Welcomes;
    Animation moveup;
    FloatingActionButton otplogin,otplogins;
    TextView skip,skips,optsubmit;
    ImageView close;
    private static final int PERMISSION_REQUEST_CODE = 200;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otplogin);

        callTitleCenterFont();


        if (checkPermission()) {
          //  Toast.makeText(this, "alreday got", Toast.LENGTH_SHORT).show();

            // Snackbar.make(view, "Permission already granted.", Snackbar.LENGTH_LONG).show();

        } else{
            requestPermission();
            //Toast.makeText(this, "Request" , Toast.LENGTH_SHORT).show();

            //Snackbar.make(view, "Please request permission.", Snackbar.LENGTH_LONG).show();
        }

        welcome=(CardView) findViewById(R.id.card_wel);
        Welcomes=(CardView) findViewById(R.id.card_wels);
        otps=(CardView) findViewById(R.id.card_otp);
        otpveri=(CardView) findViewById(R.id.card_otp_verify);
        otplogin=(FloatingActionButton) findViewById(R.id.fab_otp);
        otplogins=(FloatingActionButton) findViewById(R.id.fab_otps);
        skip=(TextView) findViewById(R.id.skip);
        skips=(TextView) findViewById(R.id.skips);
        optsubmit=(TextView) findViewById(R.id.phy_subbb);
        close=(ImageView) findViewById(R.id.imgclose);

        moveup= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.move_up);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                if (welcome.getVisibility()==View.GONE) {

                    welcome.setVisibility(View.VISIBLE);
                    welcome.startAnimation(moveup);
                }

            }
        },500);



        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(OTPLoginActivity.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        otplogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(OTPLoginActivity.this,RegisterActivity.class);
                startActivity(i);
                //finish();

                /*FragmentManager fm=getSupportFragmentManager();
                FragmentTransaction ft=fm.beginTransaction().replace(R.id.frm,new E4Fragment());
                ft.commit();*/

            }
        });
        optsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(OTPLoginActivity.this,MainActivity.class);
                startActivity(i);
                finish();

            }
        });

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {

                    boolean locationAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean cameraAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                  //  boolean contactAccepted = grantResults[2] == PackageManager.PERMISSION_GRANTED;
                    //boolean callAccepted = grantResults[3] == PackageManager.PERMISSION_GRANTED;

                   // if (locationAccepted && cameraAccepted && contactAccepted && callAccepted)
                    if (locationAccepted && cameraAccepted)
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
                                                    requestPermissions(new String[]{ACCESS_FINE_LOCATION, CAMERA},
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
        new AlertDialog.Builder(OTPLoginActivity.this)
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
       // int result3= ContextCompat.checkSelfPermission(getApplicationContext(),CALL_PHONE);

       // return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED && result2== PackageManager.PERMISSION_GRANTED && result3==PackageManager.PERMISSION_GRANTED;
        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION, CAMERA}, PERMISSION_REQUEST_CODE);

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




}
