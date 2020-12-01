package com.phyapp.root.physiotherapy;

import android.content.DialogInterface;
import android.content.Intent;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.phyapp.root.physiotherapy.APICONFIG.APIConfig;
import com.phyapp.root.physiotherapy.Utils.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dmax.dialog.SpotsDialog;

public class RequestServiceActivity extends AppCompatActivity {


    Button click;
    TextInputEditText name,phone,address;
    String sid;
    SessionManager session;
    String userid,mid;
    boolean str;
    private EditText comments;
    android.app.AlertDialog pDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_service);


        callTitleCenterFont();

        session=new SessionManager(this);
        pDialog=new SpotsDialog(this,R.style.Custom);
        pDialog.setCancelable(true);

        HashMap<String,String> user=session.getUserDetails();
        userid=user.get(SessionManager.KEY_USERID);

        if (userid==null)
        {

            str=true;

        }
        else
            {
            str=false;
        }


        if(str)
        {

            alertCreate();


        }





        name=(TextInputEditText) findViewById(R.id.nam);
        phone=(TextInputEditText) findViewById(R.id.num);
        address=(TextInputEditText) findViewById(R.id.nums);
        click=(Button) findViewById(R.id.conforme);
        comments=(EditText) findViewById(R.id.edit_comment);

        mid="2";
        sid=getIntent().getStringExtra("sid");

        Log.i("siid",sid);

        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(name.getText().toString()) &&
                        !TextUtils.isEmpty(phone.getText().toString()) &&
                        !TextUtils.isEmpty(address.getText().toString()))
                {
                    if(isValidPhone(phone.getText().toString()))
                    {
                        showpDialog();

                        CallRequestServiceApi(mid, sid, userid, name.getText().toString(), phone.getText().toString(), address.getText().toString(), comments.getText().toString());

                      //  Toast.makeText(RequestServiceActivity.this, "Request Noted", Toast.LENGTH_SHORT).show();

                    }else
                        {

                            //name.setError("Enter Valid Phone");

                            Snackbar snackbar=Snackbar.make(findViewById(android.R.id.content),"Enter Valid Phone",Snackbar.LENGTH_LONG);
                            snackbar.show();
                    }


                }
                else if(TextUtils.isEmpty(name.getText().toString())){

                  //  name.setError("Enter Name");

                    Snackbar snackbar=Snackbar.make(findViewById(android.R.id.content),"Enter Name",Snackbar.LENGTH_LONG);
                    snackbar.show();

                }else if(TextUtils.isEmpty(phone.getText().toString())){

                  //  name.setError("Enter Phone");

                    Snackbar snackbar=Snackbar.make(findViewById(android.R.id.content),"Enter Phone",Snackbar.LENGTH_LONG);
                    snackbar.show();
                }else if(TextUtils.isEmpty(address.getText().toString())){

                   // name.setError("Enter Address");

                    Snackbar snackbar=Snackbar.make(findViewById(android.R.id.content),"Enter Address",Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }
        });




    }

    private void CallRequestServiceApi(final String mid, final String sid, final String pid, final String name, final String phone, final String address, final String comments) {


      //  http://phyapp.webtoall.in/api/otherserviceappointment.php?mid=2&sid=2&pid=1&name=demo&phone=87965422&address=hi%20text%20area,%20address&comments=hi%20demo%20content

        RequestQueue queue= Volley.newRequestQueue(this);

        final String url= APIConfig.MAIN_API+APIConfig.RequestService;
      //  url = "http://httpbin.org/post";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
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

                            if(sucess.equals("1"))
                            {
                               // hidepDialog();
                                msg=object.getString("message");
                                Toast.makeText(RequestServiceActivity.this, msg, Toast.LENGTH_SHORT).show();
                                finish();

                            }
                            else if (sucess.equals("0"))
                            {

                                //hidepDialog();
                                msg=object.getString("message");
                                Toast.makeText(RequestServiceActivity.this, msg, Toast.LENGTH_SHORT).show();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();


                        }


                        //  finish();
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
                params.put("mid", mid);
                params.put("sid", sid);
                params.put("pid", pid);
                params.put("name", name);
                params.put("phone", phone);
                params.put("address", address);
                params.put("comments", comments);

                return params;
            }
        };
        queue.add(postRequest);
        postRequest.setRetryPolicy(new DefaultRetryPolicy(50000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


    }

    private void alertCreate() {

        final AlertDialog.Builder alert=new AlertDialog.Builder(RequestServiceActivity.this,R.style.CustomDialogTheme);
        alert.setCancelable(false);
        alert.setTitle(Html.fromHtml("<font color='#dd0000'>Alert!</font>"));
        alert.setMessage(Html.fromHtml("<font color='#000000'>You must login to access this page</font>"));
        alert.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Intent i=new Intent(RequestServiceActivity.this,RegisterActivity.class);
                startActivity(i);
                finish();

            }

        });
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
              //  tab2.getMenu().getItem(0).setChecked(true);


                finish();

            }
        });
        alert.create();
        alert.show();
    }

    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }


    /*private boolean isValidPhone(String email) {

        return Patterns.PHONE.matcher(email).matches();
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
        this.getSupportActionBar().setCustomView(vr);
    }

    public static boolean isValidPhone(String phone)
    {
        String expression = "^([0-9\\+]|\\(\\d{1,9}\\))[0-9\\-\\. ]{9,15}$";
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


}
