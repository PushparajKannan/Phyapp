package com.phyapp.root.physiotherapy;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.phyapp.root.physiotherapy.APICONFIG.APIConfig;
import com.phyapp.root.physiotherapy.Utils.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import dmax.dialog.SpotsDialog;

public class Phy_Notification_Activity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "position";
    ImageView collabsebackground;
    CircleImageView dpimg;
    Dialog dlgs;
    FloatingActionButton editprofile;
    SessionManager session;
    String Doctorid,lat,lon;
    AlertDialog pDialog;
    TextView profilename,profileaddrss,profilemail,profilephone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phy__notification_);

       setSupportActionBar((Toolbar) findViewById(R.id.toolbarss));
       getSupportActionBar().setDisplayHomeAsUpEnabled(true);

       callTitleCenterFont();


        lat=  "9.9149843";
          lon="78.1108863";




        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

        editprofile=(FloatingActionButton) findViewById(R.id.fab);
        session=new SessionManager(this);

        pDialog=new SpotsDialog(this,R.style.Custom);
        pDialog.setCancelable(false);
        dlgs=new Dialog(this);

        dpimg=(CircleImageView) findViewById(R.id.profile_image);

        profileaddrss=(TextView) findViewById(R.id.Phy_doctor_name_address);
        profilemail=(TextView) findViewById(R.id.phy_doctor_profile_mailid);
        profilephone=(TextView) findViewById(R.id.phy_doctor_profile_phone);
        profilename=(TextView) findViewById(R.id.Phy_doctor_name_profile);



        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (Math.abs(verticalOffset) == appBarLayout.getTotalScrollRange()) {
                    // If collapsed, then do this
                    dpimg.setVisibility(View.GONE);
                   // dpimg.setVisibility(View.VISIBLE);
                } else if (verticalOffset == 0) {
                    // If expanded, then do this
                    dpimg.setVisibility(View.VISIBLE);
                  //  dpimg.setVisibility(View.GONE);
                } else {
                    // Somewhere in between
                    // Do according to your requirement
                }
            }
        });


        HashMap<String,String> doctor=session.getUserDetails();

        Doctorid=doctor.get(session.KEY_USERID);


showpDialog();

        callDoctorProfileAPI(Doctorid);




        editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(Phy_Notification_Activity.this,PhyEditProfileActivity.class);
                startActivity(i);
            }
        });




        // Set title of Detail page
        // collapsingToolbar.setTitle(getString(R.string.item_title));


        //collapsingToolbar.setTitle(getTitle());

       // collapsingToolbar.setExpandedTitleTextAppearance(R.style.CollapsedAppBar);
        collapsingToolbar.setExpandedTitleColor(Color.TRANSPARENT);

        collabsebackground=(ImageView) findViewById(R.id.collabse_backimage);

       // callDoctorProfileAPI(Doctorid);

       // Drawable drawable=getResources().getDrawable(R.drawable.background);

        /*runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Bitmap icon = BitmapFactory.decodeResource(getApplicationContext().getResources(),R.drawable.background);

                Bitmap img=doColorFilter(icon,0,0,100);

                collabsebackground.setImageBitmap(img);
            }
        });
*/


       // collapsingToolbar.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);

        int postion = getIntent().getIntExtra(EXTRA_POSITION, 0);
        Resources resources = getResources();
       // String[] places = resources.getStringArray(R.array.places);
       // collapsingToolbar.setTitle("Physiotherapy Profile");

        //String[] placeDetails = resources.getStringArray(R.array.place_details);
      //  TextView placeDetail = (TextView) findViewById(R.id.place_detail);
       // placeDetail.setText(placeDetails[postion % placeDetails.length]);

       // String[] placeLocations = resources.getStringArray(R.array.place_locations);
       // TextView placeLocation =  (TextView) findViewById(R.id.place_location);
        //placeLocation.setText(placeLocations[postion % placeLocations.length]);

        //TypedArray placePictures = resources.obtainTypedArray(R.array.places_picture);
        ImageView placePicutre = (ImageView) findViewById(R.id.image);
       // placePicutre.setImageDrawable(placePictures.getDrawable(postion % placePictures.length()));

       // placePictures.recycle();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        showpDialog();

        callDoctorProfileAPI(Doctorid);


    }

    private void callDoctorProfileAPI(final String doctorid) {

       RequestQueue queue= Volley.newRequestQueue(this);



       String url= APIConfig.MAIN_API+APIConfig.Doctor_Profile;

        //url =  http://nbayjobs.com/raksha/api/doctor/profile.php?id=1;
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);

                        hidepDialog();

                        final String sucess,name,mail,address,phone,id,image;

                        try {
                            JSONObject obj=new JSONObject(response);

                            sucess=obj.getString("success");
                            if(sucess.equals("1")){

                                id=obj.getString("doctorid");
                                name=obj.getString("fullname");
                                phone=obj.getString("mobile");
                                mail=obj.getString("email");
                                address=obj.getString("address");
                                image=obj.getString("image");

                               /* runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Glide.with(Phy_Notification_Activity.this).load(image).asBitmap().diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.drawable.user_ic).into(dpimg);

                                    }
                                });
*/

                                Glide.with(Phy_Notification_Activity.this).load(image).asBitmap().diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.drawable.ic_person).into(dpimg);

/*
                                new Handler().postDelayed(new Runnable() {
                                   @Override
                                   public void run() {
                                       Glide.with(Phy_Notification_Activity.this).load(image).asBitmap().diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.drawable.ic_person).into(dpimg);

                                   }
                               },1000);*/


                             //   Glide.with(Phy_Notification_Activity.this).load(image).asBitmap().diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.drawable.user_ic).into(dpimg);


                                profilename.setText(name);
                                profileaddrss.setText(address);
                                profilephone.setText(phone);
                                profilemail.setText(mail);


                                session.createLoginSession(id,"Doctor",name,phone,mail,address);

                               // locateaddressdetails(lat,lon);






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
                params.put("id",doctorid );
                //params.put("domain", "http://itsalif.info");

                return params;
            }
        };
        queue.add(postRequest);
        postRequest.setRetryPolicy(new DefaultRetryPolicy(500000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }


    private void locateaddressdetails(String lati,String longi) {


   // Double  lat=  9.9149843;
    // Double   lon=78.1108863;



        Geocoder geocoder;
        List<Address> addresses = null;
        geocoder = new Geocoder(this, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(Double.parseDouble(lati), Double.parseDouble(longi), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
        } catch (IOException e) {
            e.printStackTrace();
        }

        String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
        String city = addresses.get(0).getLocality();
        String state = addresses.get(0).getAdminArea();
        String country = addresses.get(0).getCountryName();
        String postalCode = addresses.get(0).getPostalCode();
        String knownName = addresses.get(0).getFeatureName(); // Only if available else return NULL


        profileaddrss.setText(address);


    }

    private void showview(View v) {
        dlgs.setContentView(R.layout.editprofile_phy);

        dlgs.show();


    }


    public static Bitmap doColorFilter(Bitmap src, double red, double green, double blue) {
        // image size
        int width = src.getWidth();
        int height = src.getHeight();
        // create output bitmap
        Bitmap bmOut = Bitmap.createBitmap(width, height, src.getConfig());
        // color information
        int A, R, G, B;
        int pixel;

        // scan through all pixels
        for(int x = 0; x < width; ++x) {
            for(int y = 0; y < height; ++y) {
                // get pixel color
                pixel = src.getPixel(x, y);
                // apply filtering on each channel R, G, B
                A = Color.alpha(pixel);
                R = (int)(Color.red(pixel) * red);
                G = (int)(Color.green(pixel) * green);
                B = (int)(Color.blue(pixel) * blue);
                // set new color pixel to output bitmap
                bmOut.setPixel(x, y, Color.argb(A, R, G, B));
            }
        }

        // return final image
        return bmOut;
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
        //  this.getSupportActionBar().setCustomView(vr,p);
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




