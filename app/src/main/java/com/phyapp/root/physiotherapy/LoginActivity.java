package com.phyapp.root.physiotherapy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.phyapp.root.physiotherapy.Utils.SessionManager;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;

import java.util.HashMap;



public class LoginActivity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {
    private static final String TAG = "";
    TextView signin,Register;
    LinearLayout google;
    private GoogleApiClient mGoogleApiClient;
    private static final int RC_SIGN_IN = 007;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    SessionManager session;
    private String PhotoUrl;
    String UserType;

    String currentacces;
    HashMap<String, String> user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_login);

       UserType ="Patient";


        pref = getSharedPreferences("login", MODE_PRIVATE);
        editor = pref.edit();
        session = new SessionManager(getApplicationContext());
       // currentacces=session.getUserDetails()

        // get user data from session
        HashMap<String, String> user = session.getUserDetails();
        // loginType
        currentacces = user.get(SessionManager.KEY_LOGIN_TYPE);

        System.out.println("Google :" +currentacces);





        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        google=(LinearLayout) findViewById(R.id.googlel);

/*

        if(currentacces==null)
        {
            System.out.println();
        }
        else {
            checkAccessToken();
        }
*/



        signin=(TextView) findViewById(R.id.sign_text);
       // signin.setText(Html.fromHtml("<u>Log In</u>"));
        Register=(TextView) findViewById(R.id.register);
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(i);
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(LoginActivity.this,MainActivity.class);
                startActivity(i);
            }
        });


        google.setOnClickListener(this);


    }

    @Override
    protected void onStart() {
        super.onStart();

        if(currentacces==null)
        {
            System.out.println();
        }
        else {
            checkAccessToken();
        }

        // checkAccessToken();

    }

    @Override
    protected void onActivityResult(int requestCode, int responseCode,
                                    Intent data) {
       // super.onActivityResult(requestCode, responseCode, data);

        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
            // if (pDialog.isShowing())
            //  pDialog.dismiss();
        }
       /* else {

            callbackManager.onActivityResult(requestCode, responseCode, data);
        }*/
        super.onActivityResult(requestCode, responseCode, data);

    }


    @Override
    public void onClick(View v) {

        switch(v.getId())
        {

         /*   case R.id.btnf:
                facebooklogin();
                break;*/

            case R.id.googlel:
                signInWithGoogle();

                break;


        }

    }
    private void signInWithGoogle() {
        if (mGoogleApiClient != null) {
            mGoogleApiClient.disconnect();
        }

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        final Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
       // Toast.makeText(this, "hi", Toast.LENGTH_SHORT).show();
    }


    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {

            // Bundle b = new Bundle();
            //Inserts a String value into the mapping of this Bundle
            // b.putString("name", facebookName.getText().toString());
            //Add the bundle to the intent.
            //intent.putExtras(b);

            GoogleSignInAccount account = result.getSignInAccount();
            Toast.makeText(this, ""+account.getDisplayName(), Toast.LENGTH_SHORT).show();

            Log.d(TAG, "display name: " + account.getDisplayName());
            String ids="";
            String type="google";

            String personName = account.getDisplayName().toString();
            Uri personPhotoUrl = account.getPhotoUrl();
            String email = account.getEmail().toString();
            if (personPhotoUrl != null){
                PhotoUrl = personPhotoUrl.toString();
            }else{
                PhotoUrl = "";
            }

            Log.d(TAG, "Name: " + personName + ", email: " + email
                    + ", Image: " + personPhotoUrl);


            //pDialog = new ProgressDialog(MainActivity.this);
            // pDialog.setMessage("Please wait...");
            // pDialog.setCancelable(true);
            // pDialog.show();

            session.createLoginSession(ids, email, type,personName, PhotoUrl, UserType);
            shareData(ids, email, type, personName, PhotoUrl);

           Intent i = new Intent(LoginActivity.this, MainActivity.class);
            //  g.putString("UserEmail",account.getEmail().toString());
            // g.putString("Photourl",account.getPhotoUrl().toString());
            // g.putString("PersonName",account.getDisplayName().toString());
            // i.putExtras(g);
            startActivity(i);

           // finish();

            // Signed in successfully, show authenticated UI.
            //updateUI(account);

        }
        else
        {
            // Toast.makeText(this, "failed", Toast.LENGTH_SHORT).show();
        }
    }

    private void shareData(String str_customerId, String str_email, String str_login_type,String  str_user_name, String str_image) {

        editor.putString("str_customerId", str_customerId);
        editor.putString("str_email", str_email);
        editor.putString("str_login_type", str_login_type);
        editor.putString("str_user_name", str_user_name);
        editor.putString("str_image", str_image);

        editor.commit();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
    private void checkAccessToken() {



            if(currentacces.equals("google"))
            {

                OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
                if (opr.isDone()) {
                    // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
                    // and the GoogleSignInResult will be available instantly.
                    Log.d(TAG, "Got cached sign-in");
                    GoogleSignInResult result = opr.get();
                    handleSignInResult(result);
                } else {
                    // If the user has not previously signed in on this device or the sign-in has expired,
                    // this asynchronous branch will attempt to sign in the user silently.  Cross-device
                    // single sign-on will occur in this branch.
                    //showProgressDialog();
                    opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                        @Override
                        public void onResult(GoogleSignInResult googleSignInResult) {
                            // hideProgressDialog();
                            handleSignInResult(googleSignInResult);
                        }
                    });



            }





            }
            else
            {
                Toast.makeText(this, "others", Toast.LENGTH_SHORT).show();
            }





/*
            OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
            if (opr.isDone()) {
                // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
                // and the GoogleSignInResult will be available instantly.
                Log.d(TAG, "Got cached sign-in");
                GoogleSignInResult result = opr.get();
                handleSignInResult(result);
            } else {
                // If the user has not previously signed in on this device or the sign-in has expired,
                // this asynchronous branch will attempt to sign in the user silently.  Cross-device
                // single sign-on will occur in this branch.
                //showProgressDialog();
                opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                    @Override
                    public void onResult(GoogleSignInResult googleSignInResult) {
                       // hideProgressDialog();
                        handleSignInResult(googleSignInResult);
                    }
                });
            */







        /*if (AccessToken.getCurrentAccessToken() == null) {

            Toast.makeText(LoginActivity.this, "User not logged in", Toast.LENGTH_LONG).show();
            // facebookGraphLoginRequest(AccessToken.getCurrentAccessToken());
            // If already login in then show the Toast.
//            Toast.makeText(LoginActivity.this, "Already logged in", Toast.LENGTH_LONG).show();
        } else {
            facebookGraphLoginRequest(AccessToken.getCurrentAccessToken());
            // If not login in then show the Toast.

        }

        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken accessToken, AccessToken accessToken2) {
                if (accessToken2 == null) {
                    // Clear the TextView after logout.
//                    FacebookDataTextView.setText("");
                }
            }
        };*/
    }

   /* private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }*/

}
