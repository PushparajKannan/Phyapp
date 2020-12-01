package com.phyapp.root.physiotherapy;

import android.content.Intent;
import android.os.Handler;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;


public class SplashActivity extends AppCompatActivity {
   // ProgressBar progressBar;
    SimpleDraweeView img;
    private ProgressBar progBar;
    private TextView text;
    private Handler mHandler = new Handler();
    private int mProgressStatus=0;
  //  private static final int PERMISSION_REQUEST_CODE = 200;
   // AVLoadingIndicatorView avi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_splash);

        progBar= (ProgressBar)findViewById(R.id.progressBar);
        text = (TextView)findViewById(R.id.textview1);




        dosomething();


       /* if (checkPermission()) {
            Toast.makeText(this, "alreday got", Toast.LENGTH_SHORT).show();

            // Snackbar.make(view, "Permission already granted.", Snackbar.LENGTH_LONG).show();

        } else{
            requestPermission();
            Toast.makeText(this, "Request" , Toast.LENGTH_SHORT).show();

            //Snackbar.make(view, "Please request permission.", Snackbar.LENGTH_LONG).show();
        }
*/

       // avi=(AVLoadingIndicatorView) findViewById(R.id.avi);
        //avi.show();



       /* void startAnim(){
            avi.show();
            // or avi.smoothToShow();
        }

        void stopAnim(){
            avi.hide();
            // or avi.smoothToHide();
        }*/

        //img=(SimpleDraweeView) findViewById(R.id.img_gif);



//        progressBar=(ProgressBar) findViewById(R.id.progress);

      /* Thread progress = new Thread(){

            @Override
            public void run() {
                super.run();
                for(int i=0;i<=10;){
                try{
                    //Delay second
                    sleep(500);
                }catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                //updating progressbar
                    progressBar.setProgress(i);

               // i=i+500;

                }
            }
        };
        progress.start();*/
/*
        Thread splashThread = new Thread()
        {
            @Override
            public void run()
            {
                try {
                    int waited = 0;
                    while (waited < 3000)
                    {
                        sleep(100);
                        waited += 100;
                    }
                } catch (InterruptedException e)
                {
                    // do nothing
                } finally
                {
                  //  avi.hide();
                    finish();
                    Intent i = new Intent(SplashActivity.this,SelectionActivity.class);
                    startActivity(i);
                }
            }
        };
        splashThread.start();*/

      /*  Uri uri=ImageRequestBuilder.newBuilderWithResourceId(R.drawable.run1).build().getSourceUri();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(uri)
                .setAutoPlayAnimations(true)
    .build();
        img.setController(controller);*/



        /*ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithResourceId(R.raw.sample_gif).build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(imageRequest.getSourceUri())
                .setAutoPlayAnimations(true)
                .build();
        simpleDraweeView.setController(controller);
*/




    }
    public void dosomething() {

        new Thread(new Runnable() {
            public void run() {

                try {
                    final int presentage = 0;
                    while (mProgressStatus < 100) {
                        mProgressStatus += 1;
                        // Update the progress bar
                        mHandler.post(new Runnable() {
                            public void run() {
                                progBar.setProgress(mProgressStatus);
                                text.setText("" + mProgressStatus + "%");

                            }
                        });
                        try {


                            Thread.sleep(20);

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                } finally
                {
                    //  avi.hide();
                    finish();
                    Intent i = new Intent(SplashActivity.this,SelectionActivity.class);
                    startActivity(i);
                }
            }
        }).start();
    }
   /* @Override
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
        new AlertDialog.Builder(SplashActivity.this)
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

        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED && result2== PackageManager.PERMISSION_GRANTED && result3== PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION, CAMERA,READ_PHONE_STATE,CALL_PHONE}, PERMISSION_REQUEST_CODE);

    }


*/
}
