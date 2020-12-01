package com.phyapp.root.physiotherapy;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.phyapp.root.physiotherapy.APICONFIG.APIConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import dmax.dialog.SpotsDialog;

public class PhysiotherapiestActivity extends AppCompatActivity {

    Button details;
    LinearLayout Asknumber, setnumber;
    ImageView numbershown;
    TextView oldnumber, newnumber, Getdierction;
    String phid, ahid;
    AlertDialog pDialog;
    String Lati, Longi,Pendingworks;
    TextView alreadyreach;


    TextView names, services, dates, times, addresss, callpatient;
    private String Plati, Plongi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_physiotherapiest);

        callTitleCenterFont();

        pDialog = new SpotsDialog(PhysiotherapiestActivity.this, R.style.Custom);

        pDialog.setCancelable(false);

        showpDialog();


        phid = getIntent().getStringExtra("phid");
        ahid = getIntent().getStringExtra("ahid");
        Lati = getIntent().getStringExtra("lat");
        Longi = getIntent().getStringExtra("long");
        Pendingworks = getIntent().getStringExtra("mpk");
       // Pendingworks=getIntent().getStringExtra("mpkv");



        numbershown = (ImageView) findViewById(R.id.imgview_number);
        oldnumber = (TextView) findViewById(R.id.old_numbers);
        newnumber = (TextView) findViewById(R.id.set_number);
        Getdierction = (TextView) findViewById(R.id.get_direction);

        names = (TextView) findViewById(R.id.pad_name);
        services = (TextView) findViewById(R.id.pad_ser);
        dates = (TextView) findViewById(R.id.pad_date);
        times = (TextView) findViewById(R.id.pad_tim);
        addresss = (TextView) findViewById(R.id.pad_add);
        callpatient = (TextView) findViewById(R.id.call_patient);
        alreadyreach=(TextView) findViewById(R.id.recahedlocation);


        if(Pendingworks==null)
        {
            alreadyreach.setVisibility(View.GONE);
        }else {
            alreadyreach.setVisibility(View.VISIBLE);
        }


            CallPatientDetailsAPI(phid, ahid, "1", Lati, Longi);




        numbershown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //showpDialog();

                showDialoglogout();


               /* oldnumber.setVisibility(View.GONE);
                newnumber.setVisibility(View.VISIBLE);
                numbershown.setVisibility(View.GONE);
*/
            }
        });




        alreadyreach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CreateAlertDialogForProced();

            }
        });

        Getdierction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri.Builder directionsBuilder = new Uri.Builder().scheme("https").authority("www.google.com").appendPath("maps").appendPath("dir").appendPath("").appendQueryParameter("api", "1").appendQueryParameter("destination", Plati + "," + Plongi);

                try {
                    startActivityForResult(new Intent(Intent.ACTION_VIEW, directionsBuilder.build()),55);


                } catch (ActivityNotFoundException ex) {
                    Toast.makeText(PhysiotherapiestActivity.this, "Please install a maps application", Toast.LENGTH_LONG).show();

                }




                /*String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?daddr=%f,%f (%s)", Plati, Plongi, "Where the party is at");
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                intent.setPackage("com.google.android.apps.maps");
                try
                {
                    startActivity(intent);
                }
                catch(ActivityNotFoundException ex)
                {
                    try
                    {
                        Intent unrestrictedIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                        startActivity(unrestrictedIntent);
                    }
                    catch(ActivityNotFoundException innerEx)
                    {
                        Toast.makeText(PhysiotherapiestActivity.this, "Please install a maps application", Toast.LENGTH_LONG).show();
                    }
                }*/





/*

                Uri navigationIntentUri = Uri.parse("google.navigation:q=" + 12f " +"," + 2f);
                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, navigationIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
*/


              /*  Intent i=new Intent(PhysiotherapiestActivity.this,NotificationActivity.class);
                startActivity(i);
                finish();*/
            }
        });





    /*    Asknumber=(LinearLayout) findViewById(R.id.ask_phone);
        setnumber=(LinearLayout) findViewById(R.id.set_phone);


        Asknumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Asknumber.animate()
                     //   .translationY(Asknumber.getHeight())
                        .alpha(0.0f)
                        .setDuration(300)
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);
                                Asknumber.setVisibility(View.GONE);
                            }
                        });

              //  Asknumber.setVisibility(View.GONE);
                setnumber.setVisibility(View.VISIBLE);



            }
        });*/

        // details=(Button) findViewById(R.id.phy_asknumber);
/*

        details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(PhysiotherapiestActivity.this,ServiceCompletedActivity.class);
                startActivity(i);
            }
        });
*/

    }

    private void CallPatientDetailsAPI(final String phhid, final String ahhid, final String status, final String latis, final String longis) {

        // http://nbayjobs.com/raksha/api/doctor/notification_single.php?phid=1&aid=152


        //   url = "http://httpbin.org/post";

        Log.d("phid", phid);
        Log.d("ahid", ahid);

        RequestQueue queue = Volley.newRequestQueue(this);


        String url = APIConfig.MAIN_API + APIConfig.Doctor_Notification;
        StringRequest postRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // response
                Log.d("Response", response);
                hidepDialog();

                String sucess, msg, service, date, time, address, patient, Patient_Lati, Patient_longi;

                try {
                    JSONObject object = new JSONObject(response);

                    sucess = object.getString("success");

                    if (sucess.equals("1")) {

                        service = object.getString("service");
                        date = object.getString("date");
                        time = object.getString("time");
                        address = object.getString("address");
                        patient = object.getString("patient_name");
                        Patient_Lati = object.getString("latitude");
                        Patient_longi = object.getString("longitude");


                        Log.i("lati", Patient_Lati);
                        Log.i("longi", Patient_longi);


                        names.setText(patient);
                        addresss.setText(address);
                        dates.setText(date);
                        times.setText(time);
                        services.setText(service);

                        Plati = Patient_Lati;
                        Plongi = Patient_longi;


                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // error
                Log.d("Error.Response", error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("phid", phhid);
                params.put("aid", ahhid);
                params.put("lat", latis);
                params.put("long", longis);
                params.put("status", status);

                return params;
            }
        };
        queue.add(postRequest);

        postRequest.setRetryPolicy(new DefaultRetryPolicy(500000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


    }

    public void callTitleCenterFont() {
        this.getSupportActionBar().setDisplayShowCustomEnabled(true);
        this.getSupportActionBar().setDisplayShowTitleEnabled(false);

        LayoutInflater inflator = LayoutInflater.from(this);
        View vr = inflator.inflate(R.layout.titleview, null);

        ActionBar.LayoutParams p = new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, Gravity.CENTER);

//if you need to customize anything else about the text, do it here.
//I'm using a custom TextView with a custom font in my layout xml so all I need to do is set title
        ((TextView) vr.findViewById(R.id.title)).setText(this.getTitle());

//assign the view to the actionbar
        //  this.getSupportActionBar().setCustomView(vr,p);
        this.getSupportActionBar().setCustomView(vr);
    }

    private void showpDialog() {
        if (!pDialog.isShowing()) pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing()) pDialog.dismiss();
    }


    public void showDialoglogout() {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this, R.style.CustomDialogTheme);
        builder.setTitle(Html.fromHtml("<font color='#dd0000'>Get patient number</font"));
        builder.setMessage(Html.fromHtml("<font color='#000000'>Click YES to continue?</font")).setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                showpDialog();

                CallGETpatientnumberAPI(phid, ahid);

            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        androidx.appcompat.app.AlertDialog alert = builder.create();


        alert.getWindow().getAttributes().windowAnimations = R.style.dialog_animation;
        alert.show();
    }

    private void CallGETpatientnumberAPI(final String phid, final String ahid) {


        Log.d("phid", phid);
        Log.d("ahid", ahid);

        RequestQueue queue = Volley.newRequestQueue(this);
        ///  url = "http://httpbin.org/post";

        // http://www.nbayjobs.com/raksha/api/doctor/getnumber.php?aid=1&phid=2&getno=1

        String url = APIConfig.MAIN_API + APIConfig.Doctor_Patient_number;
        StringRequest postRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // response
                Log.d("Response", response);
                hidepDialog();

                final String sucess, msg, number;

                try {
                    JSONObject object = new JSONObject(response);

                    sucess = object.getString("success");

                    if (sucess.equals("1")) {

                        number = object.getString("number");


                        // oldnumber.setVisibility(View.GONE);
                        newnumber.setText(number);
                        // newnumber.setVisibility(View.VISIBLE);
                        numbershown.setVisibility(View.GONE);

                        oldnumber.animate().alpha(0.0f).setDuration(300).setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);
                                oldnumber.setVisibility(View.GONE);
                                // newnumber.setVisibility(View.VISIBLE);
                                // newnumber.animate().alpha(1.0f);
                                newnumber.animate().alpha(1.0f).setDuration(300).setListener(new AnimatorListenerAdapter() {
                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        super.onAnimationEnd(animation);
                                        newnumber.setVisibility(View.VISIBLE);
                                        callpatient.setVisibility(View.VISIBLE);

                                        callpatient.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {


                                                Intent intent = new Intent(Intent.ACTION_DIAL);
                                                intent.setData(Uri.parse("tel:" + number));
                                                startActivity(intent);
                                            }
                                        });
                                    }
                                });

                            }
                        });

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // error
                Log.d("Error.Response", error.toString());

                hidepDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("aid", ahid);
                params.put("phid", phid);
                params.put("getno", "1");

                return params;
            }
        };
        queue.add(postRequest);

        postRequest.setRetryPolicy(new DefaultRetryPolicy(500000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        switch(requestCode) {
            case 55:

                CreateAlertDialogForProced();

                break;
        }

    }

    private void CreateAlertDialogForProced() {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this, R.style.CustomDialogTheme);
        builder.setTitle(Html.fromHtml("<font color='#dd0000'>PHY APP</font"));
        builder.setMessage(Html.fromHtml("<font color='#000000'>Have you reached Patient's location?</font")).setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {



                Intent i=new Intent(PhysiotherapiestActivity.this,ServiceCompletedActivity.class);
                i.putExtra("ahid",ahid);
                i.putExtra("phid",phid);
                startActivity(i);
                finish();
                dialog.dismiss();



            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        androidx.appcompat.app.AlertDialog alert = builder.create();


        alert.getWindow().getAttributes().windowAnimations = R.style.dialog_animation;
        alert.show();
    }
}
