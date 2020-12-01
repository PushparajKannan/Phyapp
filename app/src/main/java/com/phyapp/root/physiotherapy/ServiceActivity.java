package com.phyapp.root.physiotherapy;

import android.app.AlertDialog;
import android.os.Build;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.phyapp.root.physiotherapy.Utils.SessionManager;

import java.util.HashMap;
import java.util.Map;

import dmax.dialog.SpotsDialog;

public class ServiceActivity extends AppCompatActivity {

   // RadioButton Bycash,Bycard;

    TextView dname,servicename,dates,times,address,submit;
    EditText comments;
    AlertDialog pDialog;
    SessionManager session;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);


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

        session=new SessionManager(this);


        pDialog=new SpotsDialog(this,R.style.Custom);
        pDialog.setCancelable(false);


        servicename=(TextView) findViewById(R.id.servdoc_name);
        dname=(TextView) findViewById(R.id.servdoc_doctor);
        dates=(TextView) findViewById(R.id.servdoc_date);
        times=(TextView) findViewById(R.id.servdoc_time);
        address=(TextView) findViewById(R.id.servdoc_locati);
        submit=(TextView) findViewById(R.id.serv_conf);


        comments=(EditText) findViewById(R.id.edit_comment);



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



//callServcompletedAPI();




        //Bycard=(RadioButton) findViewById(R.id.pay_card);
        //Bycash=(RadioButton) findViewById(R.id.pay_cash);

        //int textColor = Color.parseColor("#FFFFFF");
        //Bycash.setButtonTintList(ColorStateList.valueOf(textColor));
        //Bycard.setButtonTintList(ColorStateList.valueOf(textColor));



    }

    private void callServcompletedAPI() {
        RequestQueue queue=Volley.newRequestQueue(this);

       String url = "http://httpbin.org/post";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);


                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("name", "Alif");
                params.put("domain", "http://itsalif.info");

                return params;
            }
        };
        queue.add(postRequest);
        postRequest.setRetryPolicy(new DefaultRetryPolicy(500000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    public void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    public void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

}
