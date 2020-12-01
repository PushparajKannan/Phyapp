package com.phyapp.root.physiotherapy;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.phyapp.root.physiotherapy.APICONFIG.APIConfig;
import com.phyapp.root.physiotherapy.ModelClass.DescriptionProductModel;
import com.phyapp.root.physiotherapy.Retofit.ApiClient;
import com.phyapp.root.physiotherapy.Retofit.ApiInterface;
import com.phyapp.root.physiotherapy.Utils.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductsDescriptionActivity extends AppCompatActivity {

    TextView quot;
    private String lang,temp,UserID;
    private ArrayList<DescriptionProductModel.Src> resultmodel;

    SessionManager session;

    TextView product_tite,product_content;
     ImageView product_image,backclick;
     AlertDialog pDialog;
     String datas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_description);

       // callTitleCenterFont();



        session=new SessionManager(this);
        datas="user";

        pDialog = new SpotsDialog(this,R.style.Custom);
        pDialog.setCancelable(true);

        showpDialog();


        temp=getIntent().getStringExtra("id");



        product_tite=(TextView) findViewById(R.id.title_pro_des);
        product_content=(TextView) findViewById(R.id.des_content);
        product_image=(ImageView) findViewById(R.id.pro_image);
        backclick=(ImageView) findViewById(R.id.backbotton);


        HashMap<String,String> user=session.getUserDetails();

        lang=user.get(session.KEY_USERS_Language);
        UserID=user.get(SessionManager.KEY_USERID);

        if(UserID==null||UserID.isEmpty()){

            datas="nouser";
        }



       // temp="2";
        //lang="Tamil";



      //  Retrofit mApiInterface = ApiClient.getClient();

        post(temp,lang);



        quot=(TextView) findViewById(R.id.quot);

        quot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(datas.equals("nouser")) {

                    // if()
                    alertCreate();
                }else {

                    alertconfrim();
                }
              //  Toast.makeText(ProductsDescriptionActivity.this, "Quotation noted", Toast.LENGTH_SHORT).show();
                //finish();
            }
        });

        backclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void post(String temp,String lang) {

        final ApiInterface service=ApiClient.getClient().create(ApiInterface.class);

        Call<DescriptionProductModel> descriptionCall= service.post(temp,lang);

        descriptionCall.enqueue(new Callback<DescriptionProductModel>() {
            @Override
            public void onResponse(Call<DescriptionProductModel> call, Response<DescriptionProductModel> response) {

                Log.i("Responce",response.raw().toString());
                String sucess= String.valueOf(response.body().getSuccess());


                Log.i("sucess",sucess);
                hidepDialog();

                if(sucess.equals("1")) {

                    resultmodel = response.body().getSrc();

                    String tit,content,image;

                     tit = resultmodel.get(0).getTitle();

                     content=resultmodel.get(0).getContent();

                     image=resultmodel.get(0).getImage();




                    Log.i("title", tit);

                    product_tite.setText(tit);
                    product_content.setText(content);

                    Glide.with(ProductsDescriptionActivity.this)
                            .load(image)
                            .asBitmap()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .placeholder(R.drawable.noimage).into(product_image);

                            //.transform(new CircleTransform(getApplicationContext())).

                }else if(sucess.equals("0"))
                {
                   String errormsg;//=response.body().getErrorMsg();



                    errormsg=response.body().getErrorMsg();

                   Log.i("errormsg",errormsg);


                }








            }

            @Override
            public void onFailure(Call<DescriptionProductModel> call, Throwable t) {


                hidepDialog();

            }
        });

    }

    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    private void alertCreate() {

        final androidx.appcompat.app.AlertDialog.Builder alert=new androidx.appcompat.app.AlertDialog.Builder(ProductsDescriptionActivity.this,R.style.CustomDialogTheme);
        alert.setTitle(Html.fromHtml("<font color='#dd0000'>Alert!</font>"));
        alert.setMessage(Html.fromHtml("<font color='#000000'>You must login</font>"));
        alert.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Intent i=new Intent(ProductsDescriptionActivity.this,RegisterActivity.class);
                startActivity(i);
                finish();

            }

        });
        alert.setNegativeButton("Cancel",null);
        androidx.appcompat.app.AlertDialog alrt= alert.create();
        alrt.getWindow().getAttributes().windowAnimations = R.style.dialog_animation;
        alrt.show();
    }

    private void alertconfrim() {

        final androidx.appcompat.app.AlertDialog.Builder alert=new androidx.appcompat.app.AlertDialog.Builder(ProductsDescriptionActivity.this,R.style.CustomDialogTheme);
        alert.setTitle(Html.fromHtml("<font color='#dd0000'>Message</font>"));
        alert.setMessage(Html.fromHtml("<font color='#000000'>Click OK to continue.</font>"));
        alert.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

               // Intent i=new Intent(ProductsDescriptionActivity.this,RegisterActivity.class);
               // startActivity(i);
                showpDialog();

                CallQuatationApi(temp,UserID);


             //   Toast.makeText(ProductsDescriptionActivity.this, "Quotation noted", Toast.LENGTH_SHORT).show();
              //  finish();

            }

        });
        alert.setNegativeButton("Cancel",null);
        androidx.appcompat.app.AlertDialog alrt= alert.create();
        alrt.getWindow().getAttributes().windowAnimations = R.style.dialog_animation;
        alrt.show();
    }

    private void CallQuatationApi(final String pro_id, final String userID) {

       // http://phyapp.webtoall.in/api/quotation.php?mid=1&sid=11&prod_id=1&pid=1

        RequestQueue queue= Volley.newRequestQueue(this);

        String url= APIConfig.MAIN_API+APIConfig.Quotation;


       // url = "http://httpbin.org/post";url
        StringRequest postRequest = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("Response", response);

                hidepDialog();
                String sucess,msg;

                try {
                    JSONObject object=new JSONObject(response);

                    sucess=object.getString("success");
                    msg=object.getString("message");

                    if(sucess.equals("1")){


                        Toast.makeText(ProductsDescriptionActivity.this, "Request Received", Toast.LENGTH_SHORT).show();
                        finish();

                    }
                    else if(sucess.equals("0"))
                    {
                        Toast.makeText(ProductsDescriptionActivity.this, msg, Toast.LENGTH_SHORT).show();

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d("Error.Response", error.toString());
                hidepDialog();

            }
        }

        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("mid", "2");
                params.put("sid", "1");
                params.put("prod_id", pro_id);
                params.put("pid", userID);

                return params;
            }
        };
        queue.add(postRequest);

        postRequest.setRetryPolicy(new DefaultRetryPolicy(50000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


    }


    public void   callTitleCenterFont()
    {
        this.getActionBar().setDisplayShowCustomEnabled(true);
        this.getActionBar().setDisplayShowTitleEnabled(false);

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




}
