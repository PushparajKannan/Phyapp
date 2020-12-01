package com.phyapp.root.physiotherapy;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.provider.Settings;
import androidx.annotation.NonNull;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.core.app.ActivityCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;

import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.iid.FirebaseInstanceId;
import com.phyapp.root.physiotherapy.APICONFIG.APIConfig;
import com.phyapp.root.physiotherapy.Retofit.ApiInterface;
import com.phyapp.root.physiotherapy.Utils.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dmax.dialog.SpotsDialog;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity {


    CardView welcome, otps, otpveri, Welcomes;
    Animation moveup;
    FloatingActionButton otplogin, otplogins;
    TextView submit, otpsubmit,otpsendnum,Resend_otp;
    ImageView close;
    String android_id;

    AlertDialog pDialog;

    SessionManager session;
    BroadcastReceiver receiver;

    String otpnumber;
    final  String UrlMain = APIConfig.MAIN_API+APIConfig.PATIENT;



    EditText phonenumber,otpnum;
    private String refreshedToken;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        callTitleCenterFont();

        //getTokenFCM();

        refreshedToken = FirebaseInstanceId.getInstance().getToken();


        //   import android.provider.Settings.Secure;


      /*  TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        telephonyManager.getDeviceId();*/

      session=new SessionManager(this);

         pDialog = new SpotsDialog(this,R.style.Custom);

        // pDialog = new SpotsDialog(this);
        // pDialog.setMessage("Loading....");
       // pDialog.setCancelable(false);
       // dialog.show();

         android_id = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);


         Log.i("android_id",android_id);
         Log.i("Token",refreshedToken);



        otps=(CardView) findViewById(R.id.card_otps);
        otpveri=(CardView) findViewById(R.id.card_otp_verifys);
        phonenumber=(EditText) findViewById(R.id.edit1);
        otpsendnum=(TextView) findViewById(R.id.otpsend_number);
        Resend_otp=(TextView) findViewById(R.id.resend_otp);
        otpnum=(EditText) findViewById(R.id.otp);
        //otplogin=(FloatingActionButton) findViewById(R.id.fab_otp);
        // otplogins=(FloatingActionButton) findViewById(R.id.fab_otps);
        // skip=(TextView) findViewById(R.id.skip);
        submit=(TextView) findViewById(R.id.phy_subm);
        otpsubmit=(TextView) findViewById(R.id.phy_sub1);
       // close=(ImageView) findViewById(R.id.imgclose);

        moveup= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.move_up);

         final  String Url = APIConfig.MAIN_API+APIConfig.PATIENT;


 receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equalsIgnoreCase("otp")) {
                    final String message = intent.getStringExtra("message");

                   // TextView tv = (TextView) findViewById(R.id.txtview);
                    otpnum.setText(message);
                }
            }
        };




        // smsreceiveauto();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {








                if(!TextUtils.isEmpty(phonenumber.getText().toString())){

                    if(isValidPhone(phonenumber.getText().toString().trim()))
                    {
                        showpDialog();


                        CallLoginApi(phonenumber.getText().toString().trim(),android_id);

                        //loginProcessWithRetrofit();


                    }else {
                        Toast.makeText(RegisterActivity.this, "Enter Valid Phone Number", Toast.LENGTH_SHORT).show();

                    }



                }else{
                    Toast.makeText(RegisterActivity.this, "Enter Phone Number", Toast.LENGTH_SHORT).show();
                }

               /* otps.setVisibility(View.GONE);
                otpveri.setVisibility(View.VISIBLE);
                otpveri.startAnimation(moveup);*/

            }
        });


        otpsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {






                if(!TextUtils.isEmpty(otpnum.getText().toString()) && !TextUtils.isEmpty(phonenumber.getText().toString().trim())){


                    if(isValidPhone(phonenumber.getText().toString().trim())) {
                        showpDialog();

                        callSigninAPI(phonenumber.getText().toString().trim(), otpnum.getText().toString().trim());
                    }
                    else {
                        Toast.makeText(RegisterActivity.this, "Enter Valid Number", Toast.LENGTH_SHORT).show();
                    }
                   /* if (otpnum.getText().toString().trim().equals(otpnumber)){







                       *//* session.createloginNumber(phonenumber.getText().toString().trim(),"Patient");


                        Intent i=new Intent(RegisterActivity.this,MainActivity.class);
                        startActivity(i);
                        finish();*//*


                    }else
                    {
                        Toast.makeText(RegisterActivity.this, "OTP Mismatch", Toast.LENGTH_SHORT).show();
                    }*/


                }else {
                    Toast.makeText(RegisterActivity.this, "Enter OTP Number", Toast.LENGTH_SHORT).show();
                }



               /* Intent i=new Intent(RegisterActivity.this,MainActivity.class);
                startActivity(i);*/
            }
        });



        Resend_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showpDialog();

                callResendOTPapi(phonenumber.getText().toString().trim());
            }
        });






    }

    private void getTokenFCM() {
        //------GET USER TOKEN-------

         refreshedToken = FirebaseInstanceId.getInstance().getToken();

       /* FirebaseUser mUser = FirebaseInstanceId.getInstance().getCurrentUser();
        mUser.getToken(true)
                .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                    public void onComplete(@NonNull Task<GetTokenResult> task) {
                        if (task.isSuccessful()) {
                            String idToken = task.getResult().getToken();
                            // ...
                        }
                    }
                });*/

    }

    private void smsreceiveauto() {

         BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equalsIgnoreCase("otp")) {
                    final String message = intent.getStringExtra("message");

                   // TextView tv = (TextView) findViewById(R.id.txtview);
                    otpnum.setText(message);
                }
            }
        };
    }

    private void callSigninAPI(final String number, final String otpnumber) {

        RequestQueue queue = Volley.newRequestQueue(this);  // this = context

        Log.d("token", refreshedToken);

       // http://nbayjobs.com/raksha/api/patient/login.php?phone=9556845541&otp=396485



        final  String Url = APIConfig.MAIN_API+APIConfig.PATIENT+APIConfig.LOGIN;

        StringRequest postRequest=new StringRequest(Request.Method.POST, Url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("Response", response);


                try {
                    JSONObject responcestring=new JSONObject(response);

                    String msg,success,error,otp,pid;

                    success=responcestring.getString("success");
                    error=responcestring.getString("error");
                    msg=responcestring.getString("message");

                    Log.d("msg", msg);
                    //Log.d("msg", );

                    pid=responcestring.getString("pid");

                    if(!pid.equals("null"))
                    {
                        Log.d("msg", msg);

                        hidepDialog();


                       // otp=responcestring.getString("otp");

                        Log.d("pid", pid);

                        session.createloginNumber(phonenumber.getText().toString().trim(),"Patient",pid);


                        Toast.makeText(RegisterActivity.this, "Successfully Logged", Toast.LENGTH_SHORT).show();
                        Intent i=new Intent(RegisterActivity.this,MainActivity.class);
                        startActivity(i);
                        finish();


                    }else {
                        Log.d("msg", msg);
                        hidepDialog();

                        Toast.makeText(RegisterActivity.this, msg, Toast.LENGTH_SHORT).show();




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


                Toast.makeText(RegisterActivity.this, "Check your internet connection", Toast.LENGTH_SHORT).show();



            }
        })

        {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("phone",number );
                params.put("otp", otpnumber);
                params.put("tokenkey", refreshedToken);

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

    private ApiInterface getInterfaceService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UrlMain)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final ApiInterface mInterfaceService = retrofit.create(ApiInterface.class);
        return mInterfaceService;
    }



    private void CallLoginApi(final String number, final String ID) {


        RequestQueue queue = Volley.newRequestQueue(this);  // this = context

     //   http://nbayjobs.com/raksha/api/patient/login.php?mobile=8248094245&device_id=396485



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


                      otpnumber=otp;
                      otpsendnum.setText("+91-"+number);

                        otps.setVisibility(View.GONE);
                        otpveri.setVisibility(View.VISIBLE);
                        otpveri.startAnimation(moveup);








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

                Log.d("Response", error.toString());



            }
        })

        {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("mobile", number);
                params.put("device_id", ID);
                params.put("tokenkey", refreshedToken);

                return params;
            }
        };
        queue.add(postRequest);
        postRequest.setRetryPolicy(new DefaultRetryPolicy(50000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));




    }


    /*private void loginProcessWithRetrofit(final String mobile, String device_id){


        ApiInterface mApiService = this.getInterfaceService();
       Call<Login> mService = mApiService.registration(mobile, device_id);
        mService.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response)
        {
                Login mLoginObject = response.body();


               // String returnedResponse = mLoginObject.isLogin;




                Toast.makeText(RegisterActivity.this, "Returned " + response, Toast.LENGTH_LONG).show();

                Log.i("responce",response.message());
                //showProgress(false);
              *//*  if(returnedResponse.trim().equals("1")){

                    Toast.makeText(RegisterActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    // redirect to Main Activity page
                  //  Intent loginIntent = new Intent(RegisterActivity.this, MainActivity.class);
                    //loginIntent.putExtra("EMAIL", email);
                    //startActivity(loginIntent);
                }
                if(returnedResponse.trim().equals("0")){

                    Toast.makeText(RegisterActivity.this, "Failed", Toast.LENGTH_SHORT).show();


                    // use the registration button to register
                   // failedLoginMessage.setText(getResources().getString(R.string.registration_message));
                   // mPasswordView.requestFocus();
                }*//*
            }
            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                call.cancel();
                Toast.makeText(RegisterActivity.this, "Please check your network connection and internet permission", Toast.LENGTH_LONG).show();
            }
        });

}
*/

    private boolean isValidMail(String email) {


//
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

   /* private boolean isValidPhone(String email) {

        return Patterns.PHONE.matcher(email).matches();
    }*/

    public static boolean isValidPhone(String phone)
    {
        String expression = "^([0-9\\+]|\\(\\d{1,3}\\))[0-9\\-\\. ]{9,15}$";
        CharSequence inputString = phone;
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(inputString);
        if (matcher.matches())
        {
            return true;
        }
        else{
            return false;
        }
    }


    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
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
        this.getSupportActionBar().setCustomView(vr,p);
    }


    @Override
    public void onResume() {
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, new IntentFilter("otp"));
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }

    /*private static String getAccessToken() throws IOException {
        GoogleCredential googleCredential = GoogleCredential
                .fromStream(new FileInputStream("service-account.json"))
                .createScoped(Arrays.asList(SCOPES));
        googleCredential.refreshToken();
        return googleCredential.getAccessToken();
    }*/





}
